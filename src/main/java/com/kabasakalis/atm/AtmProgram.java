package com.kabasakalis.atm;

import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.beryx.textio.TextTerminal;
import org.beryx.textio.web.RunnerData;

import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.IntConsumer;
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

      terminal.setBookmark("START");
    terminal.println("              Welcome to Kabasakalis Bank");
    terminal.println("              Main Menu");
    terminal.println("");
    terminal.println("Please Select one of the following options");
    terminal.println("1. Inspect ATM Cash Load.");
    terminal.println("2. Withdraw amount");
    terminal.println("4.  Reset");

    int mainMenuChoice =
        textIO.newIntInputReader().withInlinePossibleValues(1,2,3).read("Please Select option");

    if (mainMenuChoice == 1) printAtmCashLoadInfo();
      if (mainMenuChoice == 2) withdrawMenu();
      if (mainMenuChoice == 3) reset();

      terminal.setBookmark("MAIN MENU");
  }


  private void printAtmCashLoadInfo() {
    terminal.println("Current available cash on this ATM is " + "$" + atm.getTotalAmount());
    terminal.println("Banknote distribution is " + atm.getTotalBanknoteBundle().toString());
  }


private  void reset(){

     terminal.resetToBookmark("START");
     mainMenu();

//    terminal.println("       Mljklasdjf; Menu");
}

private  void withdrawMenu(){

terminal.println("Please type the amount to withdraw.");

    Long amount =
        textIO.newLongInputReader()
                .withMinVal(20L)
                .read("Amount:");

    Set<BanknoteBundle> combinations = atm.getPossibleBanknoteBundlesForAmount(amount);
    List<BanknoteBundle> combinationsList = new ArrayList<>(combinations);
    Consumer<Integer> printCombinationsConsumer =
            i -> terminal.println( i + "." +  combinationsList.get(i).toString() );

    IntStream.range(0, combinations.size()-1).boxed().forEach( printCombinationsConsumer);

    terminal.println("Please Select Combination of Banknotes");
     int combinationIndex =
        textIO.newIntInputReader()
                .withMinVal(0)
                .withMaxVal(combinationsList.size() -1 )
                .read("Combination:");


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
