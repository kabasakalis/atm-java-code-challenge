package com.kabasakalis.atm;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

public class BanknoteBundle {
  private HashMap<Banknote, BigInteger> store;

  public BanknoteBundle(HashMap<Banknote, BigInteger> store) {

    this.store = store;
  }

  public BigInteger getAmount() {

    return store
        .entrySet()
        .stream()
        .map((a) -> a.getKey().get().multiply(a.getValue()))
        .reduce(BigInteger::add)
        .get();
  }
}
