package com.kabasakalis.atm;

import java.math.BigInteger;
import java.util.Optional;

public class WithdrawalTransaction implements Transaction {

  private Atm atm;
  private BigInteger amount;

  public WithdrawalTransaction(Atm atm, BigInteger amount) {
    this.atm = atm;
    this.amount = amount;
  }

  @Override
  public void execute() {
    // withdraw
    BanknoteBundle amountAsBanknoteBundle;
    BanknoteBundle resultBanknoteBundle;
    if (withDrawalIsLegal(amountAsBanknoteBundle))
      atm.getTotalBanknoteBundle().substract(amountAsBanknoteBundle);
  }

  private boolean withDrawalIsLegal(BanknoteBundle amountAsBanknoteBundle) {
    return (atm.getTotalBanknoteBundle().getAmount().compareTo(amountAsBanknoteBundle.getAmount())
            >= 0)
        && atm.getTotalBanknoteBundle().substract(amountAsBanknoteBundle).isPresent();
  }
}
