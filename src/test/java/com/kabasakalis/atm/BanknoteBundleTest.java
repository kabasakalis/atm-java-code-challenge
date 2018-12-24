package com.kabasakalis.atm;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

import java.util.Map;
import java.util.HashMap;
import java.util.Optional;

import static com.kabasakalis.atm.Banknote.*;
import static org.junit.Assert.*;

public class BanknoteBundleTest {

  private BanknoteBundle fiveFiftiesTwoTwenties = new BanknoteBundle(mapFactory(5L,2L,0L));
  private BanknoteBundle threeFiftiesFourTwenties =
      new BanknoteBundle(mapFactory(3L, 4L, 0L));
  private BanknoteBundle eightFiftiesSixTwenties =
      new BanknoteBundle(mapFactory(8L, 6L, 0L));
  private BanknoteBundle zeroFiftiesTwoTwenties = new BanknoteBundle(mapFactory(0L, 2L, 0L));
  private BanknoteBundle fiveFiftiesZeroTwenties =
      new BanknoteBundle(mapFactory(5L, 0L, 0L));
  private BanknoteBundle zeroFiftiesZeroTwenties =
      new BanknoteBundle(mapFactory(0L, 0L, 0L));
  private BanknoteBundle threeTens = new BanknoteBundle(mapFactory(0L, 0L, 3L));
  private BanknoteBundle threeFiftiesOneTwenty = new BanknoteBundle(mapFactory(3L, 1L, 0L));
  private BanknoteBundle twoFiftiesOneTwenty = new BanknoteBundle(mapFactory(2L, 1L, 0L));

  private BanknoteBundle fiveFiftiesTwoTwentiesthreeTens =
      new BanknoteBundle(mapFactory(5L, 2L, 3L));

  @Rule
  public TestRule watcher =
      new TestWatcher() {
        protected void starting(Description description) {
          System.out.println("Starting test: " + description.getMethodName());
        }
      };

  @BeforeClass
  public static void setUp() {
    System.out.println("BanknoteBundleTest setup");
  }

  @Test
  public void equals() throws Exception {
    assertNotEquals(threeTens, fiveFiftiesTwoTwentiesthreeTens);
    assertEquals(fiveFiftiesTwoTwenties, new BanknoteBundle(mapFactory(5L, 2L, 0L)));
    assertNotEquals(threeFiftiesFourTwenties, zeroFiftiesTwoTwenties);
  }

  @Test
  public void getAmount() throws Exception {
    assertEquals((long) fiveFiftiesTwoTwenties.getAmount(), 290L);
    assertEquals((long) threeFiftiesFourTwenties.getAmount(), 230L);
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
    assertEquals(
        0, new BanknoteBundle(mapFactory(3L, 1L, 0L)).compareTo(threeFiftiesOneTwenty));
    assertTrue(threeFiftiesOneTwenty.compareTo(eightFiftiesSixTwenties) < 0);
  }
  private Map<Banknote, Long> mapFactory(Long fifties, Long twenties, Long tens) {
      Map<Banknote, Long>  notes_to_count = new HashMap<Banknote, Long>();        
      notes_to_count.put(FIFTY, fifties);
      notes_to_count.put(TWENTY, twenties);
      notes_to_count.put(TEN, tens);
      return notes_to_count;
  }
}
