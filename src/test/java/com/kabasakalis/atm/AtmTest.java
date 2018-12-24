package com.kabasakalis.atm;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;
import java.util.function.Consumer;

import static com.kabasakalis.atm.Banknote.FIFTY;
import static com.kabasakalis.atm.Banknote.TWENTY;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class AtmTest {
  private Atm atm;
  Consumer<BanknoteBundle> printCombinationsConsumer = b -> System.out.println(b.toString());

  @Rule
  public TestRule watcher =
      new TestWatcher() {
        protected void starting(Description description) {
          System.out.println("Starting test: " + description.getMethodName());
        }
      };

  @Before
  public void setUp() throws Exception {
         Map<Banknote, Long>  notes_to_count = new HashMap<Banknote, Long>();        
           notes_to_count.put(FIFTY, 50L);
           notes_to_count.put(TWENTY, 100L);

    BanknoteBundle atmInitialLoad = new BanknoteBundle(notes_to_count);
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
    Set<Banknote> set = new HashSet<>();
    set.add(TWENTY);
    set.add(FIFTY);
    assertEquals(atm.getTotalBanknotes(), set);
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
