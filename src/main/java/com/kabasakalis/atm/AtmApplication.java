package com.kabasakalis.atm;

import com.kabasakalis.atm.AtmProgram;
import com.kabasakalis.atm.Banknote;

import java.util.Map;
import java.lang.Long;
import java.util.HashMap;

import static com.kabasakalis.atm.Banknote.FIFTY;
import static com.kabasakalis.atm.Banknote.TWENTY;

public class AtmApplication {
  public static void main(String[] args) {
    // Load ATM with 100 twenties and 50 fifties.
     Map  notes_to_count = new HashMap<Banknote, Long>();        
           notes_to_count.put(TWENTY, 100L);
           notes_to_count.put(FIFTY, 50L);

    BanknoteBundle atmInitialLoad = new BanknoteBundle(notes_to_count);
    Atm atm = new Atm(atmInitialLoad);
    // start ATM software
    new AtmProgram(atm).start();
  }
}
