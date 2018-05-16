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
      new BanknoteBundle(Map.of(FIFTY, 5L, TWENTY, 2L));
  private BanknoteBundle threeFiftiesFourTwenties =
      new BanknoteBundle(Map.of(FIFTY, 3L, TWENTY,4L));
  private BanknoteBundle eightFiftiesSixTwenties =
      new BanknoteBundle(Map.of(FIFTY, 8L, TWENTY, 6L));
  private BanknoteBundle zeroFiftiesTwoTwenties =
      new BanknoteBundle(Map.of(FIFTY, 0L, TWENTY, 2L));
  private BanknoteBundle fiveFiftiesZeroTwenties =
      new BanknoteBundle(Map.of(FIFTY, 5L, TWENTY, 0L));
  private BanknoteBundle zeroFiftiesZeroTwenties =
      new BanknoteBundle(Map.of(FIFTY, 0L, TWENTY, 0L));
  private BanknoteBundle threeTens = new BanknoteBundle(Map.of(TEN, 3L));
  private BanknoteBundle threeFiftiesOneTwenty =
      new BanknoteBundle(Map.of(FIFTY, 3L, TWENTY, 1L));
  private BanknoteBundle twoFiftiesOneTwenty =
      new BanknoteBundle(Map.of(FIFTY,2L, TWENTY, 1L));

  private BanknoteBundle fiveFiftiesTwoTwentiesthreeTens =
      new BanknoteBundle(
          Map.of(
              FIFTY,
              5L,
              TWENTY,
              2L,
              TEN,
              3L));

  @BeforeClass
  public static void setUp() {
    System.out.println("BanknoteBundleTest setup");
  }

  @Test
  public void equals() throws Exception {
    assertNotEquals(threeTens, fiveFiftiesTwoTwentiesthreeTens);
    assertEquals(fiveFiftiesTwoTwenties, fiveFiftiesTwoTwenties);
    assertNotEquals(threeFiftiesFourTwenties, zeroFiftiesTwoTwenties);
  }

  @Test
  public void getAmount() throws Exception {
    assertEquals((long)fiveFiftiesTwoTwenties.getAmount(), 290L);
    assertEquals((long)threeFiftiesFourTwenties.getAmount(), 230L);
  }

  @Test
  public void add() throws Exception {
    assertEquals(fiveFiftiesTwoTwenties.add(threeFiftiesFourTwenties), eightFiftiesSixTwenties);
    assertEquals(
        fiveFiftiesTwoTwenties.add(threeFiftiesFourTwenties).getAmount(),
        eightFiftiesSixTwenties.getAmount());
    assertEquals(fiveFiftiesTwoTwenties.add(threeTens), fiveFiftiesTwoTwentiesthreeTens);
  }

  @Test
  public void substract() throws Exception {
    assertEquals(
        fiveFiftiesTwoTwenties.substract(threeFiftiesOneTwenty).get(), twoFiftiesOneTwenty);
    assertEquals(threeFiftiesOneTwenty.substract(fiveFiftiesTwoTwenties), Optional.empty());
  }

  @Test
  public void compareTo() throws Exception {
    assertTrue(fiveFiftiesTwoTwenties.compareTo(threeFiftiesOneTwenty) > 0);
    assertEquals(0, new BanknoteBundle(Map.of(FIFTY,3L,TWENTY, 1L)).compareTo(threeFiftiesOneTwenty));
    assertTrue(threeFiftiesOneTwenty.compareTo(eightFiftiesSixTwenties) < 0);
  }
}
