package com.redfrog.trafficsm.randomtest;

import java.time.Instant;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import com.redfrog.trafficsm.service.MapBuilder;
import com.redfrog.trafficsm.session.WindowSession;
import com.redfrog.trafficsm.test.javafxBoot.JfxAppSimpleSetup;
import com.redfrog.trafficsm.util.JavafxUtil;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.subjects.PublishSubject;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

class JavafxTest extends ApplicationTest {

  //__________________________________

  @Test
  public void demo_1() {
    System.out.println(">--<");

  }

  private AnchorPane panel_SemanticRoot;

  @Override
  public void start(Stage primaryStage) { panel_SemanticRoot = JfxAppSimpleSetup.startSetup(primaryStage); }

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
    public void javafx_color_blink() {
      JavafxUtil.javafx_platform_runLater_sync(() -> {
        Circle circle = new Circle();
        panel_SemanticRoot.getChildren().add(circle);
        circle.setRadius(10);
        circle.setFill(JavafxUtil.color_Yellow);
        circle.setCenterX(200);
        circle.setCenterY(200);
        circle.setViewOrder(-1);

        Paint paint_ori = circle.getFill();
        Paint paint_blink = JavafxUtil.color_Cyan;
        System.out.println("Timeline");
        Timeline flasher = new Timeline(
                                        new KeyFrame(javafx.util.Duration.millis(300), e -> {
                                          circle.setFill(paint_blink);
                                          System.out.println(Instant.now() + " :: " + "paint_blink");
                                        }),

                                        new KeyFrame(javafx.util.Duration.millis(1000), e -> {
                                          circle.setFill(paint_ori);
                                          System.out.println(Instant.now() + " :: " + "paint_ori");
                                        }));
        //____________________________________________________________
        flasher.setCycleCount(1);
        flasher.setAutoReverse(false);
        System.out.println(Instant.now() + " :: " + "play");
        flasher.play();
      });

      sleep(999999);
    }

  }

}
