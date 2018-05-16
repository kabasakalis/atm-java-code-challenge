package com.kabasakalis.atm;

import java.math.BigInteger;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import static com.kabasakalis.atm.Banknote.FIFTY;
import static com.kabasakalis.atm.Banknote.TWENTY;

public class WithdrawalTransaction implements Transaction {

  private Atm atm;
  private String errorMessage;
  private BanknoteBundle withdrawBanknoteBundle;

  public WithdrawalTransaction(Atm atm, BanknoteBundle withdrawBanknoteBundle) {
    this.atm = atm;
    this.withdrawBanknoteBundle = withdrawBanknoteBundle;
  }

  @Override
  public boolean execute() {
    // withdraw
    //    if (withdrawBanknoteBundle.getAmount() > atm.getTotalAmount()) {
    //      this.errorMessage =
    //          " Insufficient ATM cash supply, try an amount less or equal than " +
    // atm.getTotalAmount();
    //      return false;
    //    }

    Optional<BanknoteBundle> newTotalBanknoteBundle =
        atm.getTotalBanknoteBundle().substract(withdrawBanknoteBundle);
    newTotalBanknoteBundle.ifPresent(banknoteBundle -> atm.setTotalBanknoteBundle(banknoteBundle));
    return newTotalBanknoteBundle.isPresent();
  }


}
