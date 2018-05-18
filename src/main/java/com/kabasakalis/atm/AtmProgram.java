package com.kabasakalis.atm;

import org.beryx.textio.*;
import org.beryx.textio.web.RunnerData;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.kabasakalis.atm.Banknote.TWENTY;


public class AtmProgram implements BiConsumer<TextIO, RunnerData> {

  private Atm atm;
  private TextIO textIO;
  private TextTerminal<?> terminal;

  public AtmProgram(Atm atm) {
    this.atm = atm;
    this.textIO = TextIoFactory.getTextIO();
    this.terminal = textIO.getTextTerminal();
  }

  public  void start() {
      accept(textIO, null);
  }


  @Override
  public void accept(TextIO textIO, RunnerData runnerData) {
    String initData = (runnerData == null) ? null : runnerData.getInitData();
    mainMenu();
  }

  private void mainMenu() {

    if (atm.getTotalAmount() == 0L) shutDownForMaintenance();
    setColor("aqua");
    terminal.setBookmark("HEADER");
    terminal.println("              Welcome to Kabasakalis Bank");
    terminal.println("              Main Menu");
    terminal.println("");

    terminal.setBookmark("MAINMENU");
    printAtmCashLoadInfo();
    terminal.println("");
    terminal.println("Please Select one of the following options");
    terminal.println("1. Withdraw amount");
    terminal.println("2. Exit.");
    terminal.println("");

    int mainMenuChoice =
        textIO
            .newIntInputReader()
            .withPropertiesConfigurator(props -> props.setPromptColor("lime"))
            .withInlinePossibleValues(1, 2)
            .read("Please Select option.");

    if (mainMenuChoice == 1) getValidStrategiesForRequestedAmount();
    if (mainMenuChoice == 2) exit();
  }

  private void getValidStrategiesForRequestedAmount() {

    setColor("lime");
    printAtmCashLoadInfo();
    // Custom Validator of amount, prints custom error messages
    InputReader.ValueChecker<Long> lessThanAtmCashChecker =
        (val, itemname) -> {
          Long atmTotalAmount = atm.getTotalAmount();
          ArrayList<String> errorMessages = new ArrayList<String>();
          if (val > atmTotalAmount)
            errorMessages.add(
                "You attempted to withdraw an amount greater"
                    + " than the current ATM's available cash load: $"
                    + atmTotalAmount);
          if (val <= TWENTY.get())
            errorMessages.add("Amount should be greater than $" + TWENTY.get());
          return errorMessages;
        };

    terminal.println("Please type the amount to withdraw.\n");
    terminal.setBookmark("AMOUNT_INPUT");

    // input amount to withdraw
    Long amount =
        textIO
            .newLongInputReader()
            .withPropertiesConfigurator(props -> props.setPromptColor("lime"))
            .withValueChecker(lessThanAtmCashChecker)
            .withMinVal(TWENTY.get())
            .withMaxVal(atm.getTotalAmount())
            .read("Amount:");

    List<BanknoteCombinationStrategy> validStrategies =
        BanknoteCombinationStrategy.getValidStrategiesForRequestedAmount(amount, atm);

    askForCombinationBasedOnSelectedBanknoteDistributionStrategy(validStrategies, amount);
  }

  private void askForCombinationBasedOnSelectedBanknoteDistributionStrategy(
      List<BanknoteCombinationStrategy> validStrategies, Long amount) {

    terminal.setBookmark("STRATEGY_INPUT");
    // Check if any valid strategies for this amount exist. Valid means that they provide at least
    // one combination.
    if (!validStrategies.isEmpty()) {

      // Select Strategy filter
      terminal.println();
      BanknoteCombinationStrategy banknoteCombinationStrategy =
          textIO
              .newEnumInputReader(BanknoteCombinationStrategy.class)
              .withNumberedPossibleValues(validStrategies)
              .withPropertiesConfigurator(props -> props.setPromptColor("lime"))
              .read("Choose your favorite distribution of banknotes:");

      // Compute combinations with limit and selected combination strategy filter
      Set<BanknoteBundle> combinations =
          atm.getPossibleBanknoteBundlesForAmount(
              amount, banknoteCombinationStrategy, Atm.COMBINATION_LIMIT);
      List<BanknoteBundle> combinationsList = new ArrayList<>(combinations);

      terminal.println();
      terminal.println(
          "These are available combinations of banknotes for the requested amount: \n");

      // print Combinations
      Consumer<Integer> printCombinationsListConsumer =
          i -> terminal.println(i + "." + combinationsList.get(i - 1).toString());
      IntStream.rangeClosed(1, combinationsList.size())
          .boxed()
          .forEach(printCombinationsListConsumer);

      terminal.println();
      terminal.setBookmark("COMBINATON_INPUT");
      terminal.println(
          "Please Select Combination of Banknotes based on your selected distribution: "
              + banknoteCombinationStrategy.toString());
      int combinationIndex =
          textIO
              .newIntInputReader()
              .withPropertiesConfigurator(props -> props.setPromptColor("lime"))
              .withMinVal(1)
              .withMaxVal(combinationsList.size())
              .read("Combination:");

      BanknoteBundle chosenBanknoteCombination = combinationsList.get(combinationIndex - 1);
      System.out.println("Chosen Combination: " + chosenBanknoteCombination.toString());

      // we can safely use get() on optional result of substraction because we used
      // atm.getPossibleBanknoteBundlesForAmount which guarantees the chosen combination is
      // not greater than the ATM's total amount.

      BanknoteBundle result =
          atm.getTotalBanknoteBundle().substract(chosenBanknoteCombination).get();

      // set Atm new total amount
      atm.setTotalBanknoteBundle(result);

      // print success message
      successfulWithdrawal(chosenBanknoteCombination);

      // return to main menu
      mainMenu();

    } else {
      // Valid strategies is empty, meaning no combination for the requested amount can be served
      // for the existing distribution of ATM's banknotes.
      terminal.resetToBookmark("HEADER");
      setColor("deeppink");
      terminal.println(
          "The amount you requested cannot be served with the machine's current"
              + " distribution of $20 and $50 banknotes. Please try again or exit.");
      setColor("lime");
      printAtmCashLoadInfo();
      terminal.println("");
      terminal.println("1. Try another amount");
      terminal.println("2. Exit.");
      terminal.println("");
      int mainMenuChoice =
          textIO
              .newIntInputReader()
              .withPropertiesConfigurator(props -> props.setPromptColor("lime"))
              .withInlinePossibleValues(1, 2)
              .read("Please Select option.");
      if (mainMenuChoice == 1) getValidStrategiesForRequestedAmount();
      if (mainMenuChoice == 2) exit();
    }
  }

  private void printAtmCashLoadInfo() {
    setColor("aquamarine");
    terminal.println("");
    terminal.println("Current available cash on this ATM is " + "$" + atm.getTotalAmount());
    terminal.println("Banknote distribution is " + atm.getTotalBanknoteBundle().toString());
    terminal.println("");
  }

  private void successfulWithdrawal(BanknoteBundle withDrawnBanknoteBundle) {
    setColor("lime");
    terminal.println("");
    terminal.println(
        "You have successfully withdrawn "
            + "$"
            + withDrawnBanknoteBundle.getAmount()
            + " ( "
            + withDrawnBanknoteBundle.toString()
            + " )");
    terminal.println("");

    textIO
        .newStringInputReader()
        .withPropertiesConfigurator(props -> props.setPromptColor("lime"))
        .withMinLength(0)
        .read("Press Enter for Main Menu.");

    terminal.resetToBookmark("HEADER");
    mainMenu();
  }

  private void exit() {
    terminal.resetToBookmark("HEADER");
    textIO
        .newStringInputReader()
        .withPropertiesConfigurator(props -> props.setPromptColor("aqua"))
        .withMinLength(0)
        .read("\nThank you for visiting Kabasakalis Bank. Press Enter to exit.");
    textIO.dispose();
  }

  private void shutDownForMaintenance() {
    terminal.resetToBookmark("HEADER");
    textIO
        .newStringInputReader()
        .withPropertiesConfigurator(props -> props.setPromptColor("gold"))
        .withMinLength(0)
        .read(
            "\nThe machine has ran out of banknote supplies.\nShutting down for maintenance."
                + "\nPress Enter to exit.");

    terminal.dispose();
    System.exit(0);
  }

  private void setColor(String color) {
    TerminalProperties<?> props = terminal.getProperties();
    props.setPromptColor(color);
  }

  @Override
  public String toString() {
    return getClass().getSimpleName()
        + ": reading personal data.\n"
        + "(Properties are initialized at start-up.\n"
        + "Properties file: "
        + getClass().getSimpleName()
        + ".properties.)";
  }
}
