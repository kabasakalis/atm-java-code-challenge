package com.kabasakalis.atm;

import java.math.BigInteger;
import java.util.*;
import java.util.function.UnaryOperator;
import com.kabasakalis.atm.exception.IllegalBanknoteSubstractionException;

public class BanknoteBundle implements Comparable<BanknoteBundle> {
  private Map<Banknote, BigInteger> store;

  public BanknoteBundle(Map<Banknote, BigInteger> store) {
    this.store = store;
  }

  public BanknoteBundle add(BanknoteBundle banknoteBundle) {

    HashMap<Banknote, BigInteger> result = new HashMap<Banknote, BigInteger>();

    this.getBanknotes()
        .forEach(
            (Banknote banknote) -> {
              BigInteger value =
                  getBanknoteCount(banknote).add(banknoteBundle.getBanknoteCount(banknote));
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

    HashMap<Banknote, BigInteger> result = new HashMap<Banknote, BigInteger>();
    this.getBanknotes()
        .forEach(
            (Banknote banknote) -> {
              BigInteger leftCount = getBanknoteCount(banknote);
              BigInteger rightCount = banknoteBundle.getBanknoteCount(banknote);
              BigInteger value = leftCount.subtract(rightCount);
              result.put(banknote, value);
            });
    boolean existsNegativeCount =
        result.values().stream().anyMatch((count) -> count.compareTo(BigInteger.ZERO) < 0);
    return existsNegativeCount ? Optional.empty() : Optional.of(new BanknoteBundle(result));
  }

  public BigInteger getAmount() {

    return store
        .entrySet()
        .stream()
        .map((a) -> a.getKey().get().multiply(a.getValue()))
        .reduce(BigInteger::add)
        .get();
  }

  public BigInteger getBanknoteCount(Banknote banknote) {
    return store.getOrDefault(banknote, BigInteger.valueOf(0));
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
    return this.getBanknotes().stream().allMatch( (banknote) ->
              getBanknoteCount(banknote).equals(banknoteBundle.getBanknoteCount(banknote))
                      && getBanknotes().size() == banknoteBundle.getBanknotes().size());
  }
}
