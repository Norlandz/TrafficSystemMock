package com.redfrog.trafficsm.mathtest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import com.redfrog.trafficsm.annotation.Warn;
import com.redfrog.trafficsm.shape.Point;
import com.redfrog.trafficsm.test.javafxBoot.JfxAppSimpleSetup;
import com.redfrog.trafficsm.util.JavafxUtil;
import com.redfrog.trafficsm.util.MathUtil;

import javafx.application.Platform;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

class MathUtilGraphTest extends ApplicationTest {

  Random random = new Random(111);

  @Test
  public void demo_1() {
    System.out.println(">--<");

  }

  private AnchorPane panel_SemanticRoot;

  @Override
  public void start(Stage primaryStage) { panel_SemanticRoot = JfxAppSimpleSetup.startSetup(primaryStage); }

  //_____________

  //_______
  //____________________________________________
  //______________________
  //
  //___________________________________
  //________________
  //________________
  //____________________________________________
  //____________________________________________
  //__________________________________________
  //________________________________________________
  //
  //____________________________________________
  //____________________________________________
  //__________________________________________
  //________________________________________________
  //_______________________________________________________________
  //
  //____________________________________________
  //____________________________________________
  //__________________________________________
  //________________________________________________
  //
  //_______________________________________________________________________________________________
  //__________________________________________________________________________________________
  //
  //___________________________
  //__________________________________
  //____________________________________
  //
  //____________________
  //________________________________________________________________
  //_____________________________________________________________
  //________________________________________________________________
  //_____________________________________________________________
  //______________________________________________________________________________________________
  //______________________________________________________________________________________________
  //_____
  //
  //___

  //_____________

  @Warn
  //__________________________

  @Nested
  //_______________________________________________
  class Test_findPointAtTwoLineIntersection {

    Point point_AA;
    Point point_BB;
    Point point_CC;
    Point point_DD;
    Point point_MF;
    Point point_MM;
    Point point_EP;

    //_________________________________________________________________
    //______________
    //_____________________________________________________________
    //
    //_____________
    //___________________________________________

    @Test
    public void normal_intersect() {
      Platform.runLater(() -> {

        point_AA = new Point(300, 500);
        point_BB = new Point(0, 200);
        point_CC = new Point(200, 600);
        point_DD = new Point(400, 0);
        point_MF = MathUtil.findPointAtTwoLineIntersection(point_AA, point_BB, point_CC, point_DD, true);
        point_MM = MathUtil.findPointAtTwoLineIntersection(point_AA, point_BB, point_CC, point_DD);
        JavafxUtil.visualize_Point(new ArrayList<Point>(Arrays.asList(point_AA, point_BB, point_CC, point_DD, point_MF)), panel_SemanticRoot);

        point_EP = new Point(250, 450);
        Assertions.assertEquals(point_EP, point_MF);
        Assertions.assertEquals(point_EP, point_MM);

        panel_SemanticRoot.getChildren().clear();
        point_AA = new Point(100, 200);
        point_BB = new Point(500, 200);
        point_CC = new Point(300, 100);
        point_DD = new Point(300, 600);
        point_MF = MathUtil.findPointAtTwoLineIntersection(point_AA, point_BB, point_CC, point_DD, true);
        point_MM = MathUtil.findPointAtTwoLineIntersection(point_AA, point_BB, point_CC, point_DD);
        JavafxUtil.visualize_Point(new ArrayList<Point>(Arrays.asList(point_AA, point_BB, point_CC, point_DD, point_MF)), panel_SemanticRoot);

        //_____________________________________
        //_____________________________________
        point_EP = new Point(300, 200);
        Assertions.assertEquals(point_EP, point_MF);
        Assertions.assertEquals(point_EP, point_MM);

        panel_SemanticRoot.getChildren().clear();
        point_AA = new Point(100, 200);
        point_BB = new Point(500, 300);
        point_CC = new Point(400, 600);
        point_DD = new Point(300, 100);
        point_MF = MathUtil.findPointAtTwoLineIntersection(point_AA, point_BB, point_CC, point_DD, true);
        point_MM = MathUtil.findPointAtTwoLineIntersection(point_AA, point_BB, point_CC, point_DD);
        JavafxUtil.visualize_Point(new ArrayList<Point>(Arrays.asList(point_AA, point_BB, point_CC, point_DD, point_MF)), panel_SemanticRoot);

        //_____________________________________
        //_____________________________________
        point_EP = new Point(331.578947, 257.894737);
        Assertions.assertEquals(point_EP, point_MF);
        Assertions.assertEquals(point_EP, point_MM);

      });

      //____________________
    }

    @Test
    public void intersect_InfLength__no_intersect_FixLength() {
      Platform.runLater(() -> {

        point_AA = new Point(300, 500);
        point_BB = new Point(0, 200);
        point_CC = new Point(100, 200);
        point_DD = new Point(400, 0);
        point_MF = MathUtil.findPointAtTwoLineIntersection(point_AA, point_BB, point_CC, point_DD, true);
        point_MM = MathUtil.findPointAtTwoLineIntersection(point_AA, point_BB, point_CC, point_DD);
        JavafxUtil.visualize_Point(new ArrayList<Point>(Arrays.asList(point_AA, point_BB, point_CC, point_DD, point_MF)), panel_SemanticRoot);

        //_____________________________________
        //_____________________________________
        point_EP = new Point(40, 240);
        Assertions.assertEquals(point_EP, point_MF);
        Assertions.assertTrue(point_MM == null); //_____________

        panel_SemanticRoot.getChildren().clear();
        point_AA = new Point(100, 200);
        point_BB = new Point(500, 300);
        point_CC = new Point(400, 600);
        point_DD = new Point(300, 400);
        point_MF = MathUtil.findPointAtTwoLineIntersection(point_AA, point_BB, point_CC, point_DD, true);
        point_MM = MathUtil.findPointAtTwoLineIntersection(point_AA, point_BB, point_CC, point_DD);
        JavafxUtil.visualize_Point(new ArrayList<Point>(Arrays.asList(point_AA, point_BB, point_CC, point_DD, point_MF)), panel_SemanticRoot);

        point_EP = new Point(214.285714, 228.571429);
        Assertions.assertEquals(point_EP, point_MF);
        Assertions.assertEquals(null, point_MM); //_____________

      });

      //____________________
    }

    @Test
    public void no_intersect_parallel() {
      Platform.runLater(() -> {

        point_AA = new Point(100, 100);
        point_BB = new Point(200, 200);
        point_CC = new Point(150, 100);
        point_DD = new Point(250, 200);
        point_MF = MathUtil.findPointAtTwoLineIntersection(point_AA, point_BB, point_CC, point_DD, true);
        point_MM = MathUtil.findPointAtTwoLineIntersection(point_AA, point_BB, point_CC, point_DD);
        JavafxUtil.visualize_Point(new ArrayList<Point>(Arrays.asList(point_AA, point_BB, point_CC, point_DD, point_MF)), panel_SemanticRoot, true);

        point_EP = null;
        Assertions.assertEquals(point_EP, point_MF); //_____________
        Assertions.assertEquals(point_EP, point_MM); //_____________

        panel_SemanticRoot.getChildren().clear();
        point_AA = new Point(100, 200);
        point_BB = new Point(100, 300);
        point_CC = new Point(200, 600);
        point_DD = new Point(200, 400);
        point_MF = MathUtil.findPointAtTwoLineIntersection(point_AA, point_BB, point_CC, point_DD, true);
        point_MM = MathUtil.findPointAtTwoLineIntersection(point_AA, point_BB, point_CC, point_DD);
        JavafxUtil.visualize_Point(new ArrayList<Point>(Arrays.asList(point_AA, point_BB, point_CC, point_DD, point_MF)), panel_SemanticRoot, true);

        point_EP = null;
        Assertions.assertEquals(point_EP, point_MF); //_____________
        Assertions.assertEquals(point_EP, point_MM); //_____________

      });

      //____________________
    }

    @Test
    public void intersect_at_end_point() {
      Platform.runLater(() -> {

        point_AA = new Point(300, 500);
        point_BB = new Point(0, 200);
        point_CC = new Point(100, 200);
        point_DD = new Point(0, 200);
        point_MF = MathUtil.findPointAtTwoLineIntersection(point_AA, point_BB, point_CC, point_DD, true);
        point_MM = MathUtil.findPointAtTwoLineIntersection(point_AA, point_BB, point_CC, point_DD);
        JavafxUtil.visualize_Point(new ArrayList<Point>(Arrays.asList(point_AA, point_BB, point_CC, point_DD, point_MF)), panel_SemanticRoot);

        //______________________________________________________
        //_____________________________________
        //_____________________________________
        //_____________________________________
        //________________________________________
        //___
        //_____________________________________________________________________________________________________________________________________________________
        //___
        //_______________________________________________________________________________________________________
        point_EP = new Point(0, 200);
        Assertions.assertEquals(point_EP, point_MF);
        Assertions.assertEquals(point_EP, point_MM);

        point_AA = new Point(0, 0);
        point_BB = new Point(0, 200);
        point_CC = new Point(0, 0);
        point_DD = new Point(200, 0);
        point_MF = MathUtil.findPointAtTwoLineIntersection(point_AA, point_BB, point_CC, point_DD, true);
        point_MM = MathUtil.findPointAtTwoLineIntersection(point_AA, point_BB, point_CC, point_DD);
        JavafxUtil.visualize_Point(new ArrayList<Point>(Arrays.asList(point_AA, point_BB, point_CC, point_DD, point_MF)), panel_SemanticRoot, true);

        point_EP = new Point(0, 0);
        Assertions.assertEquals(point_EP, point_MF);
        Assertions.assertEquals(point_EP, point_MM);

      });
    }

    @Test
    public void on_same_line() {
      Platform.runLater(() -> {

        panel_SemanticRoot.getChildren().clear();
        point_AA = new Point(100, 100);
        point_BB = new Point(100, 300);
        point_CC = new Point(100, 150);
        point_DD = new Point(100, 350);
        point_MF = MathUtil.findPointAtTwoLineIntersection(point_AA, point_BB, point_CC, point_DD, true);
        point_MM = MathUtil.findPointAtTwoLineIntersection(point_AA, point_BB, point_CC, point_DD);
        JavafxUtil.visualize_Point(new ArrayList<Point>(Arrays.asList(point_AA, point_BB, point_CC, point_DD, point_MF)), panel_SemanticRoot, true);

        Assertions.assertTrue(point_MF == null);
        Assertions.assertTrue(point_MM == null);

      });
    }

    @Test
    public void same_line() {
      Platform.runLater(() -> {

        point_AA = new Point(0, 0);
        point_BB = new Point(0, 200);
        point_CC = new Point(0, 0);
        point_DD = new Point(0, 200);
        point_MF = MathUtil.findPointAtTwoLineIntersection(point_AA, point_BB, point_CC, point_DD, true);
        point_MM = MathUtil.findPointAtTwoLineIntersection(point_AA, point_BB, point_CC, point_DD);
        JavafxUtil.visualize_Point(new ArrayList<Point>(Arrays.asList(point_AA, point_BB, point_CC, point_DD, point_MF)), panel_SemanticRoot, true);

        Assertions.assertTrue(point_MF == null);
        Assertions.assertTrue(point_MM == null);

        panel_SemanticRoot.getChildren().clear();
        point_AA = new Point(100, 100);
        point_BB = new Point(100, 300);
        point_CC = new Point(100, 150);
        point_DD = new Point(100, 350);
        point_MF = MathUtil.findPointAtTwoLineIntersection(point_AA, point_BB, point_CC, point_DD, true);
        point_MM = MathUtil.findPointAtTwoLineIntersection(point_AA, point_BB, point_CC, point_DD);
        JavafxUtil.visualize_Point(new ArrayList<Point>(Arrays.asList(point_AA, point_BB, point_CC, point_DD, point_MF)), panel_SemanticRoot, true);

        Assertions.assertTrue(point_MF == null);
        Assertions.assertTrue(point_MM == null);

      });
    }

    @Test
    public void same_point_no_line() {
      Platform.runLater(() -> {

        point_AA = new Point(0, 0);
        point_BB = new Point(0, 0);
        point_CC = new Point(0, 0);
        point_DD = new Point(0, 0);
        point_MF = MathUtil.findPointAtTwoLineIntersection(point_AA, point_BB, point_CC, point_DD, true);
        point_MM = MathUtil.findPointAtTwoLineIntersection(point_AA, point_BB, point_CC, point_DD);
        JavafxUtil.visualize_Point(new ArrayList<Point>(Arrays.asList(point_AA, point_BB, point_CC, point_DD, point_MF)), panel_SemanticRoot, true);

        Assertions.assertTrue(point_MF == null);
        Assertions.assertTrue(point_MM == null);

      });

    }

  }

}
