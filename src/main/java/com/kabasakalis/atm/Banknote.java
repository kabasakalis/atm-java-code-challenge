package com.kabasakalis.atm;

import java.util.function.Supplier;

public enum Banknote implements Supplier<Long> {
  FIFTY("$50", () -> 50L),
  TWENTY("$20", () -> 20L),
  TEN("$10", () -> 10L); // Tens are never used in the application

  private final Supplier<Long> supplier;
  private String symbol;

  private Banknote(String symbol, final Supplier<Long> supplier) {
    this.symbol = symbol;
    this.supplier = supplier;
  }

  public String getSymbol() {
    return symbol;
  }


  @Override
  public Long get() {
    return supplier.get();
  }

}
