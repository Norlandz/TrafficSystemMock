package com.redfrog.trafficsm.util;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.junit.jupiter.api.Assertions;
import org.opentest4j.AssertionFailedError;

public class TestUtil {

  public static void assertEquals_NoStop(Object AA, Object BB) { assertions_NoStop(() -> Assertions.assertEquals(AA, BB)); }

  public static void assertSame_NoStop(Object AA, Object BB) { assertions_NoStop(() -> Assertions.assertSame(AA, BB)); }

  public static void assertTrue_NoStop(boolean AA) { assertions_NoStop(() -> Assertions.assertTrue(AA)); }

  public static void assertNotEquals_NoStop(Object AA, Object BB) { assertions_NoStop(() -> Assertions.assertNotEquals(AA, BB)); }

  public static void assertNotSame_NoStop(Object AA, Object BB) { assertions_NoStop(() -> Assertions.assertNotSame(AA, BB)); }

  public static void assertFalse_NoStop(boolean AA) { assertions_NoStop(() -> Assertions.assertFalse(AA)); }
  
  public static void fail_NoStop(String AA) { assertions_NoStop(() -> Assertions.fail(AA)); }

  //

  public static void assertIterableEquals_NoStop(Iterable<?> AA, Iterable<?> BB) { assertions_NoStop(() -> Assertions.assertIterableEquals(AA, BB)); }

  public static void assertEquals_withDelta_NoStop(double AA, double BB, double delta) { assertions_NoStop(() -> Assertions.assertEquals(AA, BB, delta)); }

  private static void assertions_NoStop(Runnable runnable) {
    //__________________________________
    //______________________________________
    //_______
    //_______________
    //_________
    //________________
    //______________________________________
    //__________________________
    //_________________________
    //_____

    //_____________________________________________________________
    //______________________________________
    //_____________
    //
    //___________________________________
    //
    //_________
    //_______________________
    //______________________________________
    //__________________________
    //_________________________
    //____________________________________
    //____________________________________________________________
    //___________
    //___________________________
    //_____________________________________
    //_________________
    //______________________________
    //______________________________________
    //______________________________
    //___________________________________________________________________________________
    //_______
    //_____

    try {
      runnable.run();
    } catch (AssertionFailedError e) {
      //________________________
      Thread t1 = new Thread(() -> { throw e; });
      t1.start();
      try {
        t1.join();
      } catch (InterruptedException e1) {
        e1.printStackTrace();
        throw new Error(e1);
      }
      //_______________________________
      //__________________________
    }

  }

  //_____________

  public static void thread_sleep(long milliSecond) {
    try {
      Thread.sleep(milliSecond);
    } catch (InterruptedException e) {
      e.printStackTrace();
      throw new Error(e);
    }
  }

  public static <T> T future_get(Future<T> future) {
    try {
      return future.get();
    } catch (InterruptedException e) {
      throw new Error(e);
    } catch (ExecutionException e) {
      try {
        throw e.getCause();
      } catch (RuntimeException e2) {
        throw e2;
      } catch (Error e2) {
        throw e2;
      } catch (Throwable e2) {
        throw new Error(e2);
      }
    }
  }

}
