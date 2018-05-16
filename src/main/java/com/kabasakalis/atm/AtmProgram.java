package com.kabasakalis.atm;

import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.beryx.textio.TextTerminal;
import org.beryx.textio.web.RunnerData;

import java.time.Month;
import java.util.Map;
import java.util.function.BiConsumer;

import static com.kabasakalis.atm.Banknote.FIFTY;
import static com.kabasakalis.atm.Banknote.TWENTY;

/** A simple application illustrating the use of TextIO. */
public class AtmProgram implements BiConsumer<TextIO, RunnerData> {

  private Atm atm;
  private TextIO textIO;
  private TextTerminal<?> terminal;
  //  public static void start() {
  //
  //    TextIO textIO = TextIoFactory.getTextIO();
  //    new AtmProgram().accept(textIO, null);
  //  }

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

    terminal.println("              Welcome to Kabasakalis Bank");
    terminal.println("              Main Menu");
    terminal.println("");
    terminal.println("Please Select one of the following options");
    terminal.println("1. Inspect ATM Cash Load.");
    terminal.println("2. Withdraw amount");

    int mainMenuChoice =
        textIO.newIntInputReader().withPossibleValues(1,2).read("Please Select 1 or 2");

    if (mainMenuChoice == 1) printAtmCashLoadInfo();
  }

  private void printAtmCashLoadInfo() {
    terminal.println("Current available amount on this ATM is " + "$" + atm.getTotalAmount());
    terminal.println("Banknote distribution is " + atm.getTotalBanknoteBundle().toString());
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
