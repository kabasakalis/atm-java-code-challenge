
package com.kabasakalis.atm;

import com.kabasakalis.atm.Banknote;

import org.junit.BeforeClass;
import org.junit.Test;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static com.kabasakalis.atm.Banknote.FIFTY;
import static com.kabasakalis.atm.Banknote.TWENTY;
import static org.junit.Assert.*;

public class BanknoteBundleTest {
    private  BanknoteBundle fiveFiftiesTwoTwenties = new BanknoteBundle( Map.of(FIFTY, BigInteger.valueOf(5), TWENTY, BigInteger.valueOf(2)));
    private  BanknoteBundle threeFiftiesFourTwenties = new BanknoteBundle( Map.of(FIFTY, BigInteger.valueOf(3), TWENTY, BigInteger.valueOf(4)));
    private  BanknoteBundle eightFiftiesSixTwenties = new BanknoteBundle( Map.of(FIFTY, BigInteger.valueOf(8), TWENTY, BigInteger.valueOf(6)));

   @BeforeClass
    public static void setUp() {
        System.out.println("BanknoteBundleTest setup");
    }

  @Test
  public void getAmountTest() throws Exception {
    assertEquals(fiveFiftiesTwoTwenties.getAmount(), BigInteger.valueOf(290));
    assertEquals(threeFiftiesFourTwenties.getAmount(), BigInteger.valueOf(230));
  }

  @Test
  public void addTest() throws Exception {
    assertEquals(fiveFiftiesTwoTwenties.add(threeFiftiesFourTwenties).getAmount(), eightFiftiesSixTwenties.getAmount());
  }


//  @Test
//  public void symbols() throws Exception {
//
//  }
}
