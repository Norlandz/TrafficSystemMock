package com.redfrog.trafficsm.randomtest;

import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class RandomTest {

  //__________________________________

  private static final Logger logger = LogManager.getLogger(RandomTest.class);

  private static final Marker mk_Test = MarkerManager.getMarker("Test");

  @Test
  public void demo_1() {
    System.out.println(">--<");

  }

  @Nested
  class TestNest01 {

    @Test
    public void demo_01() {
      System.out.println(TimeUnit.NANOSECONDS.toMillis(1_000_100));
      System.out.println(10E6 == 1_000_000);
      System.out.println(1_000_100 * 1E-6);
    }

    @Test
    public void demo_02() {
      System.out.println(logger.isTraceEnabled());
      logger.trace(mk_Test, "SSSSSS");
    }

  }

}
