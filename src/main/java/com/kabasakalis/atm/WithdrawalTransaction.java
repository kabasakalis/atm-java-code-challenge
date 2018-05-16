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
  private Long amount;

  public WithdrawalTransaction(Atm atm, Long amount) {
    this.atm = atm;
    this.amount = amount;
  }

  @Override
  public void execute() {
    // withdraw
    BanknoteBundle amountAsBanknoteBundle;
    BanknoteBundle resultBanknoteBundle;
    //    if (withDrawalIsLegal(amountAsBanknoteBundle))
    //      atm.getTotalBanknoteBundle().substract(amountAsBanknoteBundle);
  }

  private boolean withDrawalIsLegal(BanknoteBundle amountAsBanknoteBundle) {
    return (atm.getTotalBanknoteBundle().getAmount().compareTo(amountAsBanknoteBundle.getAmount())
            >= 0)
        && atm.getTotalBanknoteBundle().substract(amountAsBanknoteBundle).isPresent();
  }


}
