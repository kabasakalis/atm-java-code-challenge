package com.kabasakalis.atm;

import com.kabasakalis.atm.AtmProgram;

import java.util.Map;

import static com.kabasakalis.atm.Banknote.FIFTY;
import static com.kabasakalis.atm.Banknote.TWENTY;

public class AtmApplication {
  public static void main(String[] args) {
    // Load ATM with 100 twenties and 50 fifties.
    BanknoteBundle atmInitialLoad = new BanknoteBundle(Map.of(TWENTY, 100L, FIFTY, 50L));
    Atm atm = new Atm(atmInitialLoad);
    // start ATM software
    new AtmProgram(atm).start();
  }
}
