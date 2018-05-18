package com.kabasakalis.atm;

import org.beryx.textio.*;
import org.beryx.textio.web.RunnerData;

import java.time.Month;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.IntConsumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static com.kabasakalis.atm.Banknote.FIFTY;
import static com.kabasakalis.atm.Banknote.TWENTY;
import static java.awt.Color.orange;
import static java.awt.Color.yellow;

/** A simple application illustrating the use of TextIO. */
public class AtmProgram implements BiConsumer<TextIO, RunnerData> {

  private Atm atm;
  private TextIO textIO;
  private TextTerminal<?> terminal;

  public AtmProgram(Atm atm) {
    this.atm = atm;
    this.textIO = TextIoFactory.getTextIO();

    this.terminal = textIO.getTextTerminal();
    accept(textIO, null);
  }

  @Override
  public void accept(TextIO textIO, RunnerData runnerData) {
    //    TextTerminal<?> terminal = textIO.getTextTerminal();
    String initData = (runnerData == null) ? null : runnerData.getInitData();

    mainMenu();

    // AppUtil.printGsonMessage(terminal, initData);

    //        String user = textIO.newStringInputReader()
    //                .withDefaultValue("admin")
    //                .read("Username");
    //
    //        String password = textIO.newStringInputReader()
    //                .withMinLength(6)
    //                .withInputMasking(true)
    //                .read("Password");
    //
    //        int age = textIO.newIntInputReader()
    //                .withMinVal(13)
    //                .read("Age");
    //
    //        Month month = textIO.newEnumInputReader(Month.class)
    //                .read("What month were you born in?");
    //
    //        terminal.printf("\nUser %s is %d years old, was born in %s and has the password
    // %s.\n", user, age, month, password);
    //
    //        textIO.newStringInputReader().withMinLength(0).read("\nPress enter to terminate...");
    //        textIO.dispose("User '" + user + "' has left the building.");
  }

  private void mainMenu() {
        TerminalProperties<?> properties = terminal.getProperties();
        properties.setPromptColor("aqua");
    terminal.setBookmark("MAIN");
    terminal.println("              Welcome to Kabasakalian Bank");
    terminal.println("              Main Menu");
    terminal.println("");
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

    if (mainMenuChoice == 1) withdrawMenu();
    if (mainMenuChoice == 2) exit();
  }

  private void printAtmCashLoadInfo() {
      terminal.println("");
    terminal.println("Current available cash on this ATM is " + "$" + atm.getTotalAmount());
    terminal.println("Banknote distribution is " + atm.getTotalBanknoteBundle().toString());
    terminal.println("");
  }

  private void exit() {
    terminal.resetToBookmark("MAIN");
    textIO
        .newStringInputReader()
        .withPropertiesConfigurator(props -> props.setPromptColor("white"))
        .withMinLength(0)
        .read("\nThank you for visiting Kabasakalian Bank. Press Enter to exit.");
    textIO.dispose();
  }

  private void withdrawMenu() {

       TerminalProperties<?> properties = terminal.getProperties();
        properties.setPromptColor("lime");

    terminal.setBookmark("WITHDRAW");
//    terminal.resetToBookmark("MAIN");
    printAtmCashLoadInfo();
    InputReader.ValueChecker<Long> lessThanAtmCashChecker =
        (val, itemname) -> {
          Long atmTotalAmount = atm.getTotalAmount();
          ArrayList<String> errorMessages = new ArrayList<String>();
          if (val > atmTotalAmount)
            errorMessages.add(
                "You attempted to withdraw an amount bigger"
                    + " than the current ATM's available cash load: $"
                    + atmTotalAmount);
          if (val <= TWENTY.get())
            errorMessages.add("Amount should be greater than $" + TWENTY.get());
          return errorMessages;
        };

    terminal.println("Please type the amount to withdraw.");
    Long amount =
        textIO
            .newLongInputReader()
            .withPropertiesConfigurator(props -> props.setPromptColor("lime"))
            .withValueChecker(lessThanAtmCashChecker)
            .withMinVal(TWENTY.get())
            .withMaxVal(atm.getTotalAmount())
            .read("Amount:");

    List<BanknoteCombinationStrategy> strategies =
        Arrays.asList(BanknoteCombinationStrategy.values());
    // Validator
    Predicate<BanknoteCombinationStrategy> emptyCombinationsChecker =
        (strategy) -> {
          ArrayList<String> errorMessages = new ArrayList<String>();
          Set<BanknoteBundle> combinations =
              atm.getPossibleBanknoteBundlesForAmount(amount)
                  .stream()
                  .filter(strategy)
                  .limit(Atm.COMBINATION_LIMIT)
                  .collect(Collectors.toSet());
          return !combinations.isEmpty();
        };


    List<BanknoteCombinationStrategy> validStrategies =
        strategies.stream().filter(emptyCombinationsChecker).collect(Collectors.toList());

    withDraw2(validStrategies, amount);
    }





private void withDraw2(List<BanknoteCombinationStrategy> validStrategies, Long amount) {

    if (!validStrategies.isEmpty()){
        // Select Combination Strategy filter
        BanknoteCombinationStrategy banknoteCombinationStrategy =
            textIO
                .newEnumInputReader(BanknoteCombinationStrategy.class)
                .withNumberedPossibleValues(validStrategies)
                .withPropertiesConfigurator(props -> props.setPromptColor("green"))
                .read("Choose your favorite distribution of banknotes:");

        // Compute combinations with limit and selected combination strategy filter
        Set<BanknoteBundle> combinations =
            atm.getPossibleBanknoteBundlesForAmount(amount)
                .stream()
                .filter(banknoteCombinationStrategy)
                .limit(Atm.COMBINATION_LIMIT)
                .collect(Collectors.toSet());
        List<BanknoteBundle> combinationsList = new ArrayList<>(combinations);

        // print Combinations
        Consumer<Integer> printCombinationsListConsumer =
            i -> terminal.println(i + "." + combinationsList.get(i - 1).toString());
        IntStream.rangeClosed(1, combinationsList.size())
            .boxed()
            .forEach(printCombinationsListConsumer);

        terminal.println(
            "Please Select Combination of Banknotes based on your selected distribution: "
                + banknoteCombinationStrategy.toString());
        int combinationIndex =
            textIO
                .newIntInputReader()
                .withPropertiesConfigurator(props -> props.setPromptColor("green"))
                .withMinVal(1)
                .withMaxVal(combinationsList.size())
                .read("Combination:");

        BanknoteBundle chosenBanknoteCombination = combinationsList.get(combinationIndex - 1);
        System.out.println("Chosen:");
        System.out.println(chosenBanknoteCombination.toString());

        BanknoteBundle result = atm.getTotalBanknoteBundle().substract(chosenBanknoteCombination).get();
        atm.setTotalBanknoteBundle(result);
        mainMenu();

    } else {
        TerminalProperties<?> props = terminal.getProperties();
        props.setPromptColor("red");

            terminal.println("The amount you requested cannot be formed with $20 and $50.");


        withdrawMenu();
    }
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
