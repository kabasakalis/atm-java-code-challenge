package com.kabasakalis.atm;

import com.kabasakalis.atm.Banknote;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

import java.math.BigInteger;
import java.util.Iterator;
import java.util.List;

import static com.kabasakalis.atm.Banknote.FIFTY;
import static com.kabasakalis.atm.Banknote.TWENTY;
import static org.junit.Assert.*;

public class BanknoteTest {

  @Rule
  public TestRule watcher =
      new TestWatcher() {
        protected void starting(Description description) {
          System.out.println("Starting test: " + description.getMethodName());
        }
      };

  @Test
  public void get() throws Exception {
    assertEquals(50L, (long) FIFTY.get());
    assertEquals(20L, (long) TWENTY.get());
  }
}
