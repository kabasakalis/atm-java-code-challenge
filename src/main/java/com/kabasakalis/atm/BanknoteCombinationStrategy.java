package com.kabasakalis.atm;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static com.kabasakalis.atm.Banknote.FIFTY;
import static com.kabasakalis.atm.Banknote.TWENTY;

public enum BanknoteCombinationStrategy implements Predicate<BanknoteBundle> {
  ANY((b) -> true),
  ONLY_FIFTIES((b) -> b.getBanknoteCount(TWENTY).equals(0L)),
  ONLY_TWENTIES((b) -> b.getBanknoteCount(FIFTY).equals(0L)),
  MORE_FIFTIES((b) -> b.getBanknoteCount(FIFTY) >= b.getBanknoteCount(TWENTY)),
  MORE_TWENTIES((b) -> b.getBanknoteCount(TWENTY) >= b.getBanknoteCount(FIFTY));

  private final Predicate<BanknoteBundle> filter;

  private BanknoteCombinationStrategy(final Predicate<BanknoteBundle> filter) {
    this.filter = filter;
  }

  public static List<BanknoteCombinationStrategy> getValidStrategiesForRequestedAmount(
      Long amount, Atm atm) {
    Predicate<BanknoteCombinationStrategy> emptyCombinationsChecker =
        (strategy) -> {
          Set<BanknoteBundle> combinations =
              atm.getPossibleBanknoteBundlesForAmount(amount, strategy, Atm.COMBINATION_LIMIT);
          return !combinations.isEmpty();
        };
    return Arrays.stream(BanknoteCombinationStrategy.values())
        .filter(emptyCombinationsChecker)
        .collect(Collectors.toList());
  }

  @Override
  public boolean test(BanknoteBundle b) {
    return filter.test(b);
  }
}
