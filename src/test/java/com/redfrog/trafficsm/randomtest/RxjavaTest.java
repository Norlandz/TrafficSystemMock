package com.redfrog.trafficsm.randomtest;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.subjects.PublishSubject;

class RxjavaTest {

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

    @Test
    public void demo_01() {

      //____________________________________________________________________________

      Observable<Integer> observable = Observable.create(
                                                         new ObservableOnSubscribe<Integer>()
                                                           {
                                                             @Override
                                                             public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                                                               emitter.onNext(1);
                                                               emitter.onNext(2);
                                                               emitter.onNext(3);
                                                               emitter.onComplete();
                                                             }
                                                           });

      Observer<Integer> observer = new Observer<Integer>()
        {

          @Override
          public void onSubscribe(Disposable d) {
            //________________________
          }

          @Override
          public void onNext(Integer value) {
            System.out.println(value);

          }

          @Override
          public void onError(Throwable e) {
            e.printStackTrace();
            throw new RuntimeException(e);
          }

          @Override
          public void onComplete() {
            //_________________________

          }
        };

      observable.subscribe(observer);

    }

    @Test
    public void demo_02() {

      PublishSubject<Integer> publishSubject = PublishSubject.create();

      publishSubject.onNext(1);

      Observer<Integer> observer = new Observer<Integer>()
        {

          @Override
          public void onSubscribe(Disposable d) {
            //________________________
          }

          @Override
          public void onNext(Integer value) {
            System.out.println(value);
            try {
              Thread.sleep(500);
            } catch (InterruptedException e) {
              e.printStackTrace();
              throw new Error(e);
            }
            System.out.println("BB " + Thread.currentThread().getName());

          }

          @Override
          public void onError(Throwable e) {
            e.printStackTrace();
            throw new RuntimeException(e);
          }

          @Override
          public void onComplete() {
            //_________________________
          }
        };

      publishSubject.subscribe(observer);

      Thread t1 = new Thread(() -> {

        Observer<Integer> observer_t1 = new Observer<Integer>()
          {

            @Override
            public void onSubscribe(Disposable d) {}

            @Override
            public void onNext(Integer value) {
              System.out.println(value);
              try {
                Thread.sleep(500);
              } catch (InterruptedException e) {
                e.printStackTrace();
                throw new Error(e);
              }
              //_____________________________________________
              System.out.println("BB_t1 " + Thread.currentThread().getName());

            }

            @Override
            public void onError(Throwable e) {}

            @Override
            public void onComplete() {}
          };

        publishSubject.subscribe(observer_t1);

      });
      t1.start();
      try {
        t1.join();
      } catch (InterruptedException e1) {
        e1.printStackTrace();
        throw new Error(e1);
      }

      System.out.println("AA " + Thread.currentThread().getName());
      publishSubject.onNext(2);
      System.out.println("CC " + Thread.currentThread().getName());

      //____________________________________________________________________________________________________________________________________________________________
      //_____________________________________________________________________________________________________________
      //________________________________________________________________________________________________________________

    }

  }

}
