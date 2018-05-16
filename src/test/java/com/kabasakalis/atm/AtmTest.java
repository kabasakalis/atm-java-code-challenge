package com.kabasakalis.atm;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Stream;

import static com.kabasakalis.atm.Banknote.FIFTY;
import static com.kabasakalis.atm.Banknote.TWENTY;
import static org.junit.Assert.*;

public class AtmTest {
  private Atm atm;
  Consumer<BanknoteBundle> printCombinationsConsumer = b -> System.out.println(b.toString());

  @Before
  public void setUp() throws Exception {
    BanknoteBundle atmInitialLoad = new BanknoteBundle(Map.of(TWENTY, 100L, FIFTY, 50L));
    atm = new Atm(atmInitialLoad);
  }

  @After
  public void tearDown() throws Exception {}

  @Test
  public void getTotalAmount() {
    assertEquals(atm.getTotalAmount(), (Long) 4500L);
  }

  @Test
  public void getTotalBanknotes() {
    assertEquals(atm.getTotalBanknotes(), Set.of(TWENTY, FIFTY));
  }

  @Test
  public void getPossibleBanknoteBundlesAmount() {

    Set<BanknoteBundle> combinations;

    System.out.println("Combinations For withdrawal of $10");
    combinations = atm.getPossibleBanknoteBundlesForAmount(10L);
    assertTrue(combinations.isEmpty());

    System.out.println("Combinations For withdrawal of $1219");
    combinations = atm.getPossibleBanknoteBundlesForAmount(1219L);
    assertTrue(combinations.isEmpty());

    System.out.println("Combinations For withdrawal of $20");
    combinations = atm.getPossibleBanknoteBundlesForAmount(20L);
    combinations.forEach(printCombinationsConsumer);
    assertEquals(1, combinations.size());

    System.out.println("Combinations For withdrawal of $50");
    combinations = atm.getPossibleBanknoteBundlesForAmount(50L);
    combinations.forEach(printCombinationsConsumer);
    assertEquals(1, combinations.size());

    System.out.println("Combinations For withdrawal of $300");
    combinations = atm.getPossibleBanknoteBundlesForAmount(300L);
    combinations.forEach(printCombinationsConsumer);
    assertEquals(4, combinations.size());

    System.out.println("Combinations For withdrawal of $1500");
    combinations = atm.getPossibleBanknoteBundlesForAmount(1500L);
    combinations.forEach(printCombinationsConsumer);
    assertEquals(16, combinations.size());

    System.out.println("Combinations For withdrawal of $1510");
    combinations = atm.getPossibleBanknoteBundlesForAmount(1510L);
    combinations.forEach(printCombinationsConsumer);
    assertEquals(15, combinations.size());
  }


}
