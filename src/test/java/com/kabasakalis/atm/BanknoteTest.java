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
  public void values() throws Exception {
    assertEquals(FIFTY.get(), BigInteger.valueOf(50));
    assertEquals(TWENTY.get(), BigInteger.valueOf(20));

  }

  @Test
  public void symbols() throws Exception {
    assertEquals(FIFTY.getSymbol(), "$50");
    assertEquals(TWENTY.getSymbol(), "$20");
  }
}
