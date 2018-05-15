package com.kabasakalis.atm;

import java.math.BigInteger;
import java.util.Set;

public class Atm {

  private BanknoteBundle totalBanknoteBundle;
  private AtmProgram program;

  public Atm(BanknoteBundle totalBanknoteBundle, AtmProgram program) {
    this.totalBanknoteBundle = totalBanknoteBundle;
    this.program = program;
  }

  public BigInteger getTotalAmount() {
    return totalBanknoteBundle.getAmount();
  }

  public Set<Banknote> getTotalBanknotes() {
    return totalBanknoteBundle.getBanknotes();
  }

  public BanknoteBundle getTotalBanknoteBundle() {
    return totalBanknoteBundle;
  }

  public void setTotalBanknoteBundle(BanknoteBundle totalBanknoteBundle) {
    this.totalBanknoteBundle = totalBanknoteBundle;
  }
}
