package com.kabasakalis.atm;

import java.math.BigInteger;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static com.kabasakalis.atm.Banknote.FIFTY;
import static com.kabasakalis.atm.Banknote.TWENTY;

public class Atm {

  private BanknoteBundle totalBanknoteBundle;

  public Atm(BanknoteBundle totalBanknoteBundle) {
    this.totalBanknoteBundle = totalBanknoteBundle;
  }

  public Long getTotalAmount() {
    return totalBanknoteBundle.getAmount();
  }

  public Set<Banknote> getTotalBanknotes() {
    return totalBanknoteBundle.getBanknotes();
  }

  public Long getFiftiesCount(){
      return  totalBanknoteBundle.getBanknoteCount(FIFTY);
  }

  public Long getTwentiesCount(){
      return  totalBanknoteBundle.getBanknoteCount(TWENTY);
  }

  public BanknoteBundle getTotalBanknoteBundle() {
    return totalBanknoteBundle;
  }

  public void setTotalBanknoteBundle(BanknoteBundle totalBanknoteBundle) {
    this.totalBanknoteBundle = totalBanknoteBundle;
  }


  public Set<BanknoteBundle> getPossibleBanknoteBundlesForAmount(Long amount) {
    final Long fiftyValue = FIFTY.get();
    final Long twentyValue = TWENTY.get();
    Set<BanknoteBundle> result = new HashSet<>();
    Long maximumNumberOfFiftiesCount = amount / fiftyValue;

    for (Long fiftiesCount = 0L; fiftiesCount <= maximumNumberOfFiftiesCount; fiftiesCount++) {
      if ((amount - fiftyValue * fiftiesCount) % twentyValue == 0) {
        Long twentiesCount = (amount - fiftyValue * fiftiesCount) / twentyValue;
        if (areAtmBanknotesEnoughForTransasction(twentiesCount, fiftiesCount)) {
          BanknoteBundle validCombinationOfBanknotes =
              new BanknoteBundle(Map.of(FIFTY, fiftiesCount, TWENTY, twentiesCount));
          result.add(validCombinationOfBanknotes);
        }
      }
    }
    return  result;
  }


 private boolean areAtmBanknotesEnoughForTransasction(BanknoteBundle withdrawal)  {
  return  this.getFiftiesCount() >=  withdrawal.getBanknoteCount(FIFTY) &&
          this.getTwentiesCount() >=  withdrawal.getBanknoteCount(TWENTY) ;
 }

 private boolean areAtmBanknotesEnoughForTransasction(Long twentiesCount, Long fiftiesCount)  {
  return  this.getFiftiesCount() >=  fiftiesCount && this.getTwentiesCount() >= twentiesCount ;
 }


}
