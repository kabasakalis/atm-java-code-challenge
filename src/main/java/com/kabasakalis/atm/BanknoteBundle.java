package com.kabasakalis.atm;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static com.kabasakalis.atm.Banknote.FIFTY;
import static com.kabasakalis.atm.Banknote.TWENTY;

public class BanknoteBundle implements Comparable<BanknoteBundle> {
  private Map<Banknote, Long> store;

  public BanknoteBundle(Map<Banknote, Long> store) {
    this.store = store;
  }

  public BanknoteBundle add(BanknoteBundle banknoteBundle) {
    HashMap<Banknote, Long> result = new HashMap<Banknote, Long>();
    this.getBanknotes()
        .forEach(
            (Banknote banknote) -> {
              Long value = getBanknoteCount(banknote) + banknoteBundle.getBanknoteCount(banknote);
              result.put(banknote, value);
            });

    banknoteBundle
        .getBanknotes()
        .stream()
        .filter(banknote -> !store.keySet().contains(banknote))
        .forEach(banknote -> result.put(banknote, banknoteBundle.getBanknoteCount(banknote)));

    return new BanknoteBundle(result);
  };

  public static BanknoteBundle add(BanknoteBundle left, BanknoteBundle right) {
    return left.add(right);
  }

  public Optional<BanknoteBundle> substract(BanknoteBundle banknoteBundle) {
    HashMap<Banknote, Long> result = new HashMap<Banknote, Long>();
    this.getBanknotes()
        .forEach(
            (Banknote banknote) -> {
              Long leftCount = getBanknoteCount(banknote);
              Long rightCount = banknoteBundle.getBanknoteCount(banknote);
              Long value = leftCount - rightCount;
              result.put(banknote, value);
            });
    boolean existsNegativeCount = result.values().stream().anyMatch((count) -> count < 0);
    return existsNegativeCount ? Optional.empty() : Optional.of(new BanknoteBundle(result));
  }

  public Long getAmount() {
    return store
        .entrySet()
        .stream()
        .map((a) -> a.getKey().get() * (a.getValue()))
        .reduce(Long::sum)
        .get();
  }

  public Long getBanknoteCount(Banknote banknote) {
    return store.getOrDefault(banknote, 0L);
  }

  public Set<Banknote> getBanknotes() {
    return store.keySet();
  }

  @Override
  public int compareTo(BanknoteBundle banknoteBundle) {
    return this.getAmount().compareTo(banknoteBundle.getAmount());
  }

  @Override
  public boolean equals(Object o) {

    if (o == this) return true;
    if (!(o instanceof BanknoteBundle)) {
      return false;
    }
    BanknoteBundle banknoteBundle = (BanknoteBundle) o;
    return this.getBanknotes()
        .stream()
        .allMatch(
            (banknote) ->
                getBanknoteCount(banknote).equals(banknoteBundle.getBanknoteCount(banknote))
                    && getBanknotes().size() == banknoteBundle.getBanknotes().size());
  }

  @Override
  public String toString() {
    return "[ "
        + getBanknoteCount(TWENTY)
        + " "
        + TWENTY.getSymbol()
        + " | "
        + getBanknoteCount(FIFTY)
        + " "
        + FIFTY.getSymbol()
        + " ]";
  }
}
