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
    //    if (withDrawalIsLegal(amountAsBanknoteBundle))
    //      atm.getTotalBanknoteBundle().substract(amountAsBanknoteBundle);
  }

  private boolean withDrawalIsLegal(BanknoteBundle amountAsBanknoteBundle) {
    return (atm.getTotalBanknoteBundle().getAmount().compareTo(amountAsBanknoteBundle.getAmount())
            >= 0)
        && atm.getTotalBanknoteBundle().substract(amountAsBanknoteBundle).isPresent();
  }

  private Set<BanknoteBundle> getPossibleBanknoteBundlesForAmountToWithdraw(int amount) {
    final int fiftyValue = FIFTY.getAsInt();
    final int twentyValue = TWENTY.getAsInt();
    Set<BanknoteBundle> result = new HashSet<>();
    int maximumNumberOfFiftiesCount = amount / fiftyValue;

    for (int fiftiesCount = 0; i <= maximumNumberOfFiftiesCount; i++) {

      if ((amount - fiftyValue * fiftiesCount) % twentyValue == 0) {

        int twentiesCount = (amount - fiftyValue * fiftiesCount) / twentyValue;
        BanknoteBundle validCombinationOfBanknotes =
            new BanknoteBundle(
                Map.of(
                    FIFTY,
                    BigInteger.valueOf(fiftiesCount),
                    TWENTY,
                    BigInteger.valueOf(twentiesCount)));
        result.add(validCombinationOfBanknotes);
      }
    }
    return  result;
  }
}
