package com.redfrog.trafficsm.randomtest;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.subjects.PublishSubject;
import lombok.EqualsAndHashCode;

class HashcodeTest {

  //__________________________________

  @Test
  public void demo_1() {
    System.out.println(">--<");

  }

  @Nested
  class TestNest01 {

    //______________
    //____________________________________
    //_______________________________________________
    //
    //_____
    //
    //_______________
    //______________________________
    //________________________________________________
    //
    //_____

    @EqualsAndHashCode
    public static class Ball {
      Material material;

    }

    @EqualsAndHashCode
    public static class Material {
      String name;

      double weight;

      public Material(String name, double weight) {
        super();
        this.name   = name;
        this.weight = weight;
      }

    }

    @Test
    public void demo_01() {
      //____________________________________
      //________
      //____________________________________
      //______________________________________________________
      //_________________________________
      //________
      //_____________________________________
      //_____________________________________________________
      //_________________________________
      //________
      //____________________________________________________
      //____________________________________________________
      //________
      //____________________________________________________
      //_________________________________________________________
      //________
      //________________________________________
      //_____________
      //_____________________________________________________________
      //_____________________________________________________________
      //________
      //______________________________________________________________________
      //___________________________________________________________________________
      //________
      //___________________________________________________
      //___________________________________________________

      Ball ball = new Ball();

      ball.material = new Material("Rock", 10);
      System.out.println(ball.hashCode());

      ball.material = new Material("Paper", 5);
      System.out.println(ball.hashCode());

    }

    @Test
    public void demo_02() {
      Ball ball;
      ball          = new Ball();
      ball.material = new Material("Rock", 10);
      Ball ball_01 = ball;

      ball          = new Ball();
      ball.material = new Material("Rock", 10);
      Ball ball_02 = ball;

      System.out.println(ball_01.hashCode());
      System.out.println(ball_02.hashCode());

      System.out.println(ball_01 == ball_02);
      System.out.println(ball_01.equals(ball_02));

    }

  }

}
