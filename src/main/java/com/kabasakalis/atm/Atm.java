package com.kabasakalis.atm;

import java.util.HashSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.kabasakalis.atm.Banknote.FIFTY;
import static com.kabasakalis.atm.Banknote.TWENTY;
import  com.kabasakalis.atm.Banknote;

public class Atm {

  public static final Long COMBINATION_LIMIT = 15L;
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

  public Long getFiftiesCount() {
    return totalBanknoteBundle.getBanknoteCount(FIFTY);
  }

  public Long getTwentiesCount() {
    return totalBanknoteBundle.getBanknoteCount(TWENTY);
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
           Map<Banknote, Long>  notes_to_count = new HashMap<Banknote, Long>();        
           notes_to_count.put(FIFTY, fiftiesCount);
           notes_to_count.put(TWENTY, twentiesCount);
          BanknoteBundle validCombinationOfBanknotes =
              new BanknoteBundle(notes_to_count);
          result.add(validCombinationOfBanknotes);
        }
      }
    }
    return result;
  }

  public Set<BanknoteBundle> getPossibleBanknoteBundlesForAmount(
      Long amount, BanknoteCombinationStrategy strategy, Long limit) {
    return getPossibleBanknoteBundlesForAmount(amount)
        .stream()
        .filter(strategy)
        .limit(limit)
        .collect(Collectors.toSet());
  }

  private boolean areAtmBanknotesEnoughForTransasction(Long twentiesCount, Long fiftiesCount) {
    return this.getFiftiesCount() >= fiftiesCount && this.getTwentiesCount() >= twentiesCount;
  }
}
