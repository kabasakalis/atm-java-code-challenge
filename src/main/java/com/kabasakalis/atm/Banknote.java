package com.kabasakalis.atm;

import java.math.BigInteger;
import java.util.function.Supplier;

public enum Banknote implements Supplier<BigInteger> {
  FIFTY("$50", () -> BigInteger.valueOf(50)),
  TWENTY("$20", () -> BigInteger.valueOf(20)),
  TEN("$10", () -> BigInteger.valueOf(10));

  private final Supplier<BigInteger> supplier;
  private String symbol;

  private Banknote(String symbol, final Supplier<BigInteger> supplier) {
    this.symbol = symbol;
    this.supplier = supplier;
  }

  public String getSymbol() {
    return symbol;
  }

  @Override
  public BigInteger get() {
    return supplier.get();
  }
}
