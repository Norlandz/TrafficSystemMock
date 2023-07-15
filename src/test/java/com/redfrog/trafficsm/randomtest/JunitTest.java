package com.redfrog.trafficsm.randomtest;

import java.util.Random;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

//__________________________________________
class JunitTest {

  Random random = new Random(111);

  @Test
  public void demo_1() {
    System.out.println(">--<");

  }

  //________________________________________
  //
  //___________
  //____________________________________________________________________________________________________________

  @BeforeAll
  public static void beforeAll() {
    System.out.println("beforeAll()");

  }

  @BeforeEach
  public void beforeEach() {
    System.out.println("beforeEach()");

  }

  @Nested
  class TestNest01 {

    @BeforeAll
    public static void beforeAll() {
      System.out.println("nest01 beforeAll()");

    }

    @BeforeEach
    public void beforeEach() {
      System.out.println("nest01 beforeEach()");

    }

    @Test
    public void simpleCase() {
      System.out.println("nest01 simpleCase()");

    }

  }

  @Nested
  class TestNest02 {

    {
      System.out.println("nest02 instance block");
    }

    @BeforeAll
    public static void beforeAll() {
      System.out.println("nest02 beforeAll()");

    }

    @BeforeEach
    public void beforeEach() {
      System.out.println("nest02 beforeEach()");

    }

    @Test
    public void simpleCase() {
      System.out.println("nest02 simpleCase()");

    }

  }

  @Nested
  class TestNest03 {

    @Test
    public void each01() {
      System.out.println("nest03 each01()");

    }

    @Test
    public void each02() {
      System.out.println("nest03 each02()");

    }

  }

  @Nested
  class TestNest04 {

    @BeforeAll
    public static void beforeAll() {
      System.out.println("nest04 beforeAll()");

    }

    @BeforeEach
    public void beforeEach() {
      System.out.println("nest04 beforeEach()");

    }

    @Test
    public void each01() {
      System.out.println("nest04 each01()");

    }

    @Nested
    class TestNest04a1 {

      @Test
      public void each01() {
        System.out.println("nest04a1 each01()");

      }

      @Test
      public void each02() {
        System.out.println("nest04a1 each02()");

      }

    }

    @Test
    public void each02() {
      System.out.println("nest04 each02()");

    }

  }

}
