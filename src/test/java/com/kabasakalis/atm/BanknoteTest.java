package com.kabasakalis.atm;

import com.kabasakalis.atm.Banknote;

import org.junit.Test;

import java.math.BigInteger;
import java.util.Iterator;
import java.util.List;

import static com.kabasakalis.atm.Banknote.FIFTY;
import static com.kabasakalis.atm.Banknote.TWENTY;
import static org.junit.Assert.*;

public class BanknoteTest {

  @Test
  public void getShouldReturnBanknotevalues() throws Exception {
      assertEquals(50L, (long) FIFTY.get());
      assertEquals(20L, (long) TWENTY.get());
  }

  @Test
  public void getSymbolShouldReturnBanknoteSymbols() throws Exception {
    assertEquals(FIFTY.getSymbol(), "$50");
    assertEquals(TWENTY.getSymbol(), "$20");
  }
}
