package com.kabasakalis.atm;

import com.kabasakalis.atm.Banknote;

import org.junit.BeforeClass;
import org.junit.Test;

import java.math.BigInteger;
import java.util.*;

import static com.kabasakalis.atm.Banknote.FIFTY;
import static com.kabasakalis.atm.Banknote.TWENTY;
import static com.kabasakalis.atm.Banknote.TEN;
import static org.junit.Assert.*;

public class BanknoteBundleTest {
  private BanknoteBundle fiveFiftiesTwoTwenties =
      new BanknoteBundle(Map.of(FIFTY, BigInteger.valueOf(5), TWENTY, BigInteger.valueOf(2)));
  private BanknoteBundle threeFiftiesFourTwenties =
      new BanknoteBundle(Map.of(FIFTY, BigInteger.valueOf(3), TWENTY, BigInteger.valueOf(4)));
  private BanknoteBundle eightFiftiesSixTwenties =
      new BanknoteBundle(Map.of(FIFTY, BigInteger.valueOf(8), TWENTY, BigInteger.valueOf(6)));
  private BanknoteBundle zeroFiftiesTwoTwenties =
      new BanknoteBundle(Map.of(FIFTY, BigInteger.valueOf(0), TWENTY, BigInteger.valueOf(2)));
  private BanknoteBundle fiveFiftiesZeroTwenties =
      new BanknoteBundle(Map.of(FIFTY, BigInteger.valueOf(5), TWENTY, BigInteger.valueOf(0)));
  private BanknoteBundle zeroFiftiesZeroTwenties =
      new BanknoteBundle(Map.of(FIFTY, BigInteger.valueOf(0), TWENTY, BigInteger.valueOf(0)));
  private BanknoteBundle threeTens = new BanknoteBundle(Map.of(TEN, BigInteger.valueOf(3)));
  private BanknoteBundle threeFiftiesOneTwenty =
      new BanknoteBundle(Map.of(FIFTY, BigInteger.valueOf(3), TWENTY, BigInteger.valueOf(1)));
  private BanknoteBundle twoFiftiesOneTwenty =
      new BanknoteBundle(Map.of(FIFTY, BigInteger.valueOf(2), TWENTY, BigInteger.valueOf(1)));

  private BanknoteBundle fiveFiftiesTwoTwentiesthreeTens =
      new BanknoteBundle(
          Map.of(
              FIFTY,
              BigInteger.valueOf(5),
              TWENTY,
              BigInteger.valueOf(2),
              TEN,
              BigInteger.valueOf(3)));

  @BeforeClass
  public static void setUp() {
    System.out.println("BanknoteBundleTest setup");
  }

  @Test
  public void equalsShouldCorrectlyCompare() throws Exception {
    assertNotEquals(threeTens, fiveFiftiesTwoTwentiesthreeTens);
    assertEquals(fiveFiftiesTwoTwenties, fiveFiftiesTwoTwenties);
    assertNotEquals(threeFiftiesFourTwenties, zeroFiftiesTwoTwenties);
  }

  @Test
  public void getAmountShouldEvaluateBanknote() throws Exception {
    assertEquals(fiveFiftiesTwoTwenties.getAmount(), BigInteger.valueOf(290));
    assertEquals(threeFiftiesFourTwenties.getAmount(), BigInteger.valueOf(230));
  }

  @Test
  public void addShouldAddBanknoteBundles() throws Exception {
    assertEquals(fiveFiftiesTwoTwenties.add(threeFiftiesFourTwenties), eightFiftiesSixTwenties);
    assertEquals(
        fiveFiftiesTwoTwenties.add(threeFiftiesFourTwenties).getAmount(),
        eightFiftiesSixTwenties.getAmount());
    assertEquals(fiveFiftiesTwoTwenties.add(threeTens), fiveFiftiesTwoTwentiesthreeTens);
  }

  @Test
  public void substractShouldSubstractBanknoteBundles() throws Exception {
    assertEquals(
        fiveFiftiesTwoTwenties.substract(threeFiftiesOneTwenty).get(), twoFiftiesOneTwenty);
    assertEquals(threeFiftiesOneTwenty.substract(fiveFiftiesTwoTwenties), Optional.empty());
  }

  @Test
  public void compareToShouldCompareBanknoteBundles() throws Exception {
    assertTrue(fiveFiftiesTwoTwenties.compareTo(threeFiftiesOneTwenty) > 0);
    assertEquals(0, new BanknoteBundle(Map.of(FIFTY,BigInteger.valueOf(3),TWENTY, BigInteger.valueOf(1))).compareTo(threeFiftiesOneTwenty));
    assertTrue(threeFiftiesOneTwenty.compareTo(eightFiftiesSixTwenties) < 0);
  }
}
