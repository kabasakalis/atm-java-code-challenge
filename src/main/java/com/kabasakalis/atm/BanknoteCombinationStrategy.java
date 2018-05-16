package com.kabasakalis.atm;

import java.util.function.Predicate;

import static com.kabasakalis.atm.Banknote.FIFTY;
import static com.kabasakalis.atm.Banknote.TWENTY;

public enum BanknoteCombinationStrategy implements   Predicate<BanknoteBundle>{
    ALL( (b) -> true),
    ONLY_FIFTIES( (b) -> b.getBanknoteCount(TWENTY) == 0),
    ONLY_TWENTIES( (b) -> b.getBanknoteCount(FIFTY) == 0),
    MORE_FIFTIES( (b) -> b.getBanknoteCount(FIFTY) >= b.getBanknoteCount(TWENTY) ),
    MORE_TWENTIES( (b) -> b.getBanknoteCount(TWENTY) >= b.getBanknoteCount(FIFTY) );


    private final Predicate<BanknoteBundle> filter;

    private BanknoteCombinationStrategy(final Predicate<BanknoteBundle> filter) {
    this.filter = filter;
  }

    @Override
  public boolean test(BanknoteBundle b) {
    return filter.test(b);
  }

}




