package com.redfrog.trafficsm.maincoretest;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import com.redfrog.trafficsm.annotation.Config;
import com.redfrog.trafficsm.annotation.Debug;
import com.redfrog.trafficsm.annotation.DuplicatedCode;
import com.redfrog.trafficsm.annotation.Main;
import com.redfrog.trafficsm.annotation.NeedVisuallyCheck;
import com.redfrog.trafficsm.exception.AlreadyExistedException;
import com.redfrog.trafficsm.model.Vehicle;
import com.redfrog.trafficsm.model.roadway.fundamental.RoadwayDirLinkerComponent;
import com.redfrog.trafficsm.model.roadway.fundamental.RoadwayPoint;
import com.redfrog.trafficsm.model.roadway.fundamental.RoadwaySegment;
import com.redfrog.trafficsm.model.roadway.fundamental.pseudo.RoadwaySegmentPseudoBegin;
import com.redfrog.trafficsm.model.roadway.main.RoadwayCrossPointConnectionDto;
import com.redfrog.trafficsm.model.roadway.main.RoadwayNormalSegment;
import com.redfrog.trafficsm.model.roadway.main.RoadwaySolidRoad;
import com.redfrog.trafficsm.service.MapBuilder;
import com.redfrog.trafficsm.service.MoveController;
import com.redfrog.trafficsm.service.MoveController.FutureMovement;
import com.redfrog.trafficsm.service.PathFinder.FindPathAlgorithm;
import com.redfrog.trafficsm.service.TrafficAnalyzerBuilder;
import com.redfrog.trafficsm.service.WindowSessionJavafx;
import com.redfrog.trafficsm.service.exception.PointIsNotNearAnyRoadwayException;
import com.redfrog.trafficsm.session.WindowSession;
import com.redfrog.trafficsm.shape.Point;
import com.redfrog.trafficsm.test.javafxBoot.JfxAppSimpleSetup;
import com.redfrog.trafficsm.util.JavafxUtil;
import com.redfrog.trafficsm.util.MathUtil;
import com.redfrog.trafficsm.util.TestUtil;

import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Path;
import javafx.stage.Stage;

class MoveAnimationTest extends ApplicationTest {

  Random random = new Random(111);

  @BeforeAll
  public static void beforeAll() {
    System.out.println(">--< beforeAll");

  }

  private AnchorPane panel_SemanticRoot;

  private WindowSession windowSession;
  private WindowSessionJavafx windowSessionJavafx_corr;
  private MapBuilder mapBuilder_corr;
  private TrafficAnalyzerBuilder trafficAnalyzerBuilder_corr;
  private MoveController moveController_corr;

  @Override
  public void start(Stage primaryStage) {
    panel_SemanticRoot                          = JfxAppSimpleSetup.startSetup(primaryStage);

    windowSession                               = new WindowSession();
    mapBuilder_corr                             = windowSession.mapBuilder;
    moveController_corr                         = windowSession.moveController;
    trafficAnalyzerBuilder_corr                 = windowSession.trafficAnalyzerBuilder;
    windowSessionJavafx_corr                    = windowSession.windowSessionJavafx;
    windowSessionJavafx_corr.panel_SemanticRoot = panel_SemanticRoot;
    windowSessionJavafx_corr.javafxStage        = primaryStage;
    mapBuilder_corr.newMapFile();
  }

  //_____________

  static {
    @Debug byte dmy064; //_
    TrafficAnalyzerBuilder.mode_Use_SeqNum_as_IdSql_ifItsNull_for_NoDatabase_TestMode = true;
  }

  //_____________
  //_____________
  //_____________

  /**
___________________________________________________________________
_____________________________________________________________________
____
_____________________________________________________________________
___*/
  @Config
  //____________________________________________________________________________________________________________________________________________
  private static final double factor_SpeedUpUnitTest = 3;

  /**
______________________________________________________________________________________________
_________________________________________________________________________________________________
_____
_______________________________________________________________________________________________________________________________
___________________________________________
___________________________________________________________________________________________
___*/
  @Config
  private static final double factor_Delta = 1;

  @Config
  @Debug
  /**
______________________________________________________________
__*/
  private static final double factor_Delta_ForProblematicOnes = 1.5;

  @Nested
  class Test__createVehicle__Move_gotoTarget {

    //__________________________________
    @Test
    @NeedVisuallyCheck
    public void simpleCase() {
      //_____
      Vehicle vehicle = new Vehicle();
      try {
        trafficAnalyzerBuilder_corr.placeVehicleInMap(vehicle);
      } catch (AlreadyExistedException e) {
        throw new Error(e);
      }
      JavafxUtil.javafx_platform_runLater_sync(() -> {
        panel_SemanticRoot.getChildren().add(vehicle.getPaneWrap());
      });

      //_____
      Point point_Begin = new Point(100, 100);
      Point point_Target = new Point(800, 250);
      double speed_Actual = 0.6 * factor_SpeedUpUnitTest; //______________________________________________________________________________________________________

      vehicle.setPosActual(point_Begin);
      vehicle.setSpeedActual(speed_Actual);

      //______
      long time_Start = System.currentTimeMillis();
      @Main FutureMovement<Boolean> futureMovement = moveController_corr.gotoTarget(point_Target, vehicle);

      //______
      double distance = point_Target.distance(point_Begin);
      Long timeGap_GetToTarget;
      try {
        futureMovement.getFuture().get();
        long time_End = System.currentTimeMillis();
        timeGap_GetToTarget = time_End - time_Start;
      } catch (InterruptedException e) {
        throw new Error(e);
      } catch (ExecutionException e) {
        throw new Error(e);
      }
      TestUtil.assertEquals_NoStop(true, futureMovement.detmReachTarget());

      //_________________________________________________________________________
      //____________________________________________________________________________________________
      //___________________________________________________

      TestUtil.assertEquals_NoStop(point_Target, vehicle.getPosActual());
      double delta = 80 * factor_Delta * factor_Delta_ForProblematicOnes; //______________________________________________________________________
      //__________________________________________
      TestUtil.assertEquals_withDelta_NoStop(distance / speed_Actual, timeGap_GetToTarget, delta);

      //____________________
    }

    @Test
    public void gotoTarget_with_pause() throws InterruptedException {

      //_____
      Vehicle vehicle = new Vehicle();
      try {
        trafficAnalyzerBuilder_corr.placeVehicleInMap(vehicle);
      } catch (AlreadyExistedException e) {
        throw new Error(e);
      }
      JavafxUtil.javafx_platform_runLater_sync(() -> {
        panel_SemanticRoot.getChildren().add(vehicle.getPaneWrap());
      });

      //_____
      Point point_Begin = new Point(100, 100);
      Point point_Target = new Point(800, 250);
      double speed_Actual = 0.6 * factor_SpeedUpUnitTest;

      vehicle.setPosActual(point_Begin);
      vehicle.setSpeedActual(speed_Actual);

      //______
      long time_Start = System.currentTimeMillis();
      FutureMovement<Boolean> futureMovement = moveController_corr.gotoTarget(point_Target, vehicle);

      long timeGap_Pause_cum = 0;
      long timeGap_Pause_curr;

      Point posActual_afterPause_curr;
      Point posActual_beforeResume_curr;

      @Main byte dmy; //_
      thread_sleep((long) (100 / factor_SpeedUpUnitTest));
      timeGap_Pause_cum         += futureMovement.pauseSync();
      posActual_afterPause_curr  = vehicle.getPosActual();
      timeGap_Pause_curr         = (long) (300 / factor_SpeedUpUnitTest);
      timeGap_Pause_cum         += timeGap_Pause_curr;
      thread_sleep(timeGap_Pause_curr);
      posActual_beforeResume_curr = vehicle.getPosActual();
      TestUtil.assertEquals_NoStop(posActual_afterPause_curr, posActual_beforeResume_curr);
      futureMovement.resume();

      thread_sleep((long) (200 / factor_SpeedUpUnitTest));
      timeGap_Pause_cum         += futureMovement.pauseSync();
      posActual_afterPause_curr  = vehicle.getPosActual();
      timeGap_Pause_curr         = (long) (500 / factor_SpeedUpUnitTest);
      timeGap_Pause_cum         += timeGap_Pause_curr;
      thread_sleep(timeGap_Pause_curr);
      posActual_beforeResume_curr = vehicle.getPosActual();
      TestUtil.assertEquals_NoStop(posActual_afterPause_curr, posActual_beforeResume_curr);
      futureMovement.resume();

      thread_sleep((long) (100 / factor_SpeedUpUnitTest));
      timeGap_Pause_cum         += futureMovement.pauseSync();
      posActual_afterPause_curr  = vehicle.getPosActual();
      timeGap_Pause_curr         = (long) (30 / factor_SpeedUpUnitTest); //_________________
      timeGap_Pause_cum         += timeGap_Pause_curr;
      thread_sleep(timeGap_Pause_curr);
      posActual_beforeResume_curr = vehicle.getPosActual();
      TestUtil.assertEquals_NoStop(posActual_afterPause_curr, posActual_beforeResume_curr);
      futureMovement.resume();

      //______
      double distance = point_Target.distance(point_Begin);
      Long timeGap_GetToTarget;
      try {
        futureMovement.getFuture().get();
        long time_End = System.currentTimeMillis();
        timeGap_GetToTarget = time_End - time_Start;
      } catch (InterruptedException e) {
        throw new Error(e);
      } catch (ExecutionException e) {
        throw new Error(e);
      }
      TestUtil.assertEquals_NoStop(true, futureMovement.detmReachTarget());

      //_________________________________________________________________________
      //____________________________________________________________________________________________
      //___________________________________________________

      //_________________________________________________________________
      //____________________________________________________________________________________________________________________
      //____________________________________________
      TestUtil.assertEquals_NoStop(point_Target, vehicle.getPosActual());
      double delta = 80 * factor_Delta * factor_Delta_ForProblematicOnes;
      TestUtil.assertEquals_withDelta_NoStop((distance / speed_Actual) + timeGap_Pause_cum, timeGap_GetToTarget, delta);

      //____________________

    }

    @Test
    public void gotoTarget_with_cancel() throws InterruptedException {

      //_____
      Vehicle vehicle = new Vehicle();
      try {
        trafficAnalyzerBuilder_corr.placeVehicleInMap(vehicle);
      } catch (AlreadyExistedException e) {
        throw new Error(e);
      }
      ;
      JavafxUtil.javafx_platform_runLater_sync(() -> {
        panel_SemanticRoot.getChildren().add(vehicle.getPaneWrap());
      });

      //_____
      Point point_Begin = new Point(100, 100);
      Point point_Target = new Point(800, 250);
      double speed_Actual = 0.6 * factor_SpeedUpUnitTest;

      vehicle.setPosActual(point_Begin);
      vehicle.setSpeedActual(speed_Actual);

      //______
      long time_Start = System.currentTimeMillis();
      FutureMovement<Boolean> futureMovement = moveController_corr.gotoTarget(point_Target, vehicle);

      //_____
      long timeGap_Running_curr;
      long timeGap_Pause_cum = 0;
      long timeGap_Pause_curr;

      Point posActual_afterPause_curr;
      Point posActual_beforeResume_curr;

      timeGap_Running_curr = (long) (100 / factor_SpeedUpUnitTest);
      thread_sleep(timeGap_Running_curr);
      //_________________________________________________________________________

      timeGap_Pause_cum         += futureMovement.pauseSync();
      posActual_afterPause_curr  = vehicle.getPosActual();
      timeGap_Pause_curr         = (long) (300 / factor_SpeedUpUnitTest);
      timeGap_Pause_cum         += timeGap_Pause_curr;
      thread_sleep(timeGap_Pause_curr);
      posActual_beforeResume_curr = vehicle.getPosActual();
      TestUtil.assertEquals_NoStop(posActual_afterPause_curr, posActual_beforeResume_curr);
      futureMovement.resume();

      //__________________________________________________________
      //______________________________________________________________
      //__________________________________________________________
      //_________________________________________________________________________
      //______________________________________________________
      //_______________________________________
      //___________________________________________________________
      //___________________________________________________________________________________________
      //______________________________

      timeGap_Running_curr = (long) (100 / factor_SpeedUpUnitTest);
      thread_sleep(timeGap_Running_curr);
      //_________________________________________________________________________

      @Main Future<Boolean> future = futureMovement.getFuture();
      System.out.println(">> cancel");
      future.cancel(true);
      //______________________________________________
      //___________________________________________________________________________________________________________
      //__________________________________________________________
      //______________________________________
      //_____________________
      //_____________________________________________________________________________________________________
      futureMovement.waitUntilEnded();
      TestUtil.assertEquals_NoStop(false, futureMovement.detmReachTarget());
      System.out.println("#<< cancel");

      Long timeGap_UntilCancel;
      long time_End = System.currentTimeMillis();
      timeGap_UntilCancel = time_End - time_Start;

      //_____________________________________
      Point posActual_afterCancel_01 = vehicle.getPosActual();
      thread_sleep((long) (100 / factor_SpeedUpUnitTest));
      Point posActual_afterCancel_02 = vehicle.getPosActual();
      TestUtil.assertEquals_NoStop(posActual_afterCancel_01, posActual_afterCancel_02);
      System.out.println(posActual_afterCancel_01);
      //________________________________________________________________________
      //________________________________________________________________________________________________

      //______
      TestUtil.assertNotEquals_NoStop(point_Target, vehicle.getPosActual());

      //__________________________________________
      double distance_Moved_assertExpect = speed_Actual * (timeGap_UntilCancel - timeGap_Pause_cum);
      double distance_BC_assertCheck = posActual_afterCancel_01.distance(point_Begin);
      double delta = 40 * factor_Delta * factor_Delta_ForProblematicOnes;
      TestUtil.assertEquals_withDelta_NoStop(distance_Moved_assertExpect, distance_BC_assertCheck, delta);

      //____________________

    }

  }

  //_____________

  @DuplicatedCode

  @Nested
  class Test__gotoTarget__pathArr {

    @Test
    @NeedVisuallyCheck
    public void simpleCase() {
      //_____
      Vehicle vehicle = new Vehicle();
      try {
        trafficAnalyzerBuilder_corr.placeVehicleInMap(vehicle);
      } catch (AlreadyExistedException e) {
        throw new Error(e);
      }
      ;
      JavafxUtil.javafx_platform_runLater_sync(() -> {
        panel_SemanticRoot.getChildren().add(vehicle.getPaneWrap());
      });

      //_____
      Point point_Begin = new Point(150, 150);
      //_______________________________________________

      ArrayList<Point> pathArr_point_TargetSegment = new ArrayList<>();
      pathArr_point_TargetSegment.add(new Point(200.000000, 200.000000));
      pathArr_point_TargetSegment.add(new Point(300.000000, 200.000000));
      pathArr_point_TargetSegment.add(new Point(400.000000, 150.000000));
      pathArr_point_TargetSegment.add(new Point(350.000000, 300.000000));

      Path path = JavafxUtil.convert_arrPath2pathJfx(pathArr_point_TargetSegment);
      JavafxUtil.javafx_platform_runLater_sync(() -> {
        panel_SemanticRoot.getChildren().add(path);
      });

      double speed_Actual = 0.6 * factor_SpeedUpUnitTest; //______________________________________________________________________________________________________

      vehicle.setPosActual(point_Begin);
      vehicle.setSpeedActual(speed_Actual);

      //______
      long time_Start = System.currentTimeMillis();
      @Main FutureMovement<Boolean> futureMovement = moveController_corr.gotoTarget(pathArr_point_TargetSegment, vehicle);

      //______
      double distance_first = point_Begin.distance(pathArr_point_TargetSegment.get(0));
      double distance_total = distance_first + MathUtil.calc_LengthOfArrPath(pathArr_point_TargetSegment);

      Long timeGap_GetToTarget;
      try {
        futureMovement.getFuture().get();
        long time_End = System.currentTimeMillis();
        timeGap_GetToTarget = time_End - time_Start;
      } catch (InterruptedException e) {
        throw new Error(e);
      } catch (ExecutionException e) {
        throw new Error(e);
      }
      TestUtil.assertEquals_NoStop(true, futureMovement.detmReachTarget());

      TestUtil.assertEquals_NoStop(pathArr_point_TargetSegment.get(pathArr_point_TargetSegment.size() - 1), vehicle.getPosActual());
      double delta = 80 * factor_Delta;
      TestUtil.assertEquals_withDelta_NoStop(distance_total / speed_Actual, timeGap_GetToTarget, delta);

      //____________________
    }

    @Test
    public void gotoTarget_with_pause() throws InterruptedException {

      //_____
      Vehicle vehicle = new Vehicle();
      try {
        trafficAnalyzerBuilder_corr.placeVehicleInMap(vehicle);
      } catch (AlreadyExistedException e) {
        throw new Error(e);
      }
      ;
      JavafxUtil.javafx_platform_runLater_sync(() -> {
        panel_SemanticRoot.getChildren().add(vehicle.getPaneWrap());
      });

      //_____
      Point point_Begin = new Point(150, 150);
      //_______________________________________________

      ArrayList<Point> pathArr_point_TargetSegment = new ArrayList<>();
      pathArr_point_TargetSegment.add(new Point(200.000000, 200.000000));
      pathArr_point_TargetSegment.add(new Point(300.000000, 200.000000));
      pathArr_point_TargetSegment.add(new Point(400.000000, 150.000000));
      pathArr_point_TargetSegment.add(new Point(350.000000, 300.000000));

      Path path = JavafxUtil.convert_arrPath2pathJfx(pathArr_point_TargetSegment);
      JavafxUtil.javafx_platform_runLater_sync(() -> {
        panel_SemanticRoot.getChildren().add(path);
      });

      double speed_Actual = 0.6 * factor_SpeedUpUnitTest;

      vehicle.setPosActual(point_Begin);
      vehicle.setSpeedActual(speed_Actual);

      //______
      long time_Start = System.currentTimeMillis();
      FutureMovement<Boolean> futureMovement = moveController_corr.gotoTarget(pathArr_point_TargetSegment, vehicle);

      //_____
      long timeGap_Pause_cum = 0;
      long timeGap_Pause_curr;

      Point posActual_afterPause_curr;
      Point posActual_beforeResume_curr;

      @Main byte dmy; //_
      thread_sleep((long) (100 / factor_SpeedUpUnitTest));
      timeGap_Pause_cum         += futureMovement.pauseSync();
      posActual_afterPause_curr  = vehicle.getPosActual();
      timeGap_Pause_curr         = (long) (300 / factor_SpeedUpUnitTest);
      timeGap_Pause_cum         += timeGap_Pause_curr;
      thread_sleep(timeGap_Pause_curr);
      posActual_beforeResume_curr = vehicle.getPosActual();
      TestUtil.assertEquals_NoStop(posActual_afterPause_curr, posActual_beforeResume_curr);
      futureMovement.resume();

      thread_sleep((long) (200 / factor_SpeedUpUnitTest));
      timeGap_Pause_cum         += futureMovement.pauseSync();
      posActual_afterPause_curr  = vehicle.getPosActual();
      timeGap_Pause_curr         = (long) (500 / factor_SpeedUpUnitTest);
      timeGap_Pause_cum         += timeGap_Pause_curr;
      thread_sleep(timeGap_Pause_curr);
      posActual_beforeResume_curr = vehicle.getPosActual();
      TestUtil.assertEquals_NoStop(posActual_afterPause_curr, posActual_beforeResume_curr);
      futureMovement.resume();

      thread_sleep((long) (100 / factor_SpeedUpUnitTest));
      timeGap_Pause_cum         += futureMovement.pauseSync();
      posActual_afterPause_curr  = vehicle.getPosActual();
      timeGap_Pause_curr         = (long) (30 / factor_SpeedUpUnitTest); //_________________
      timeGap_Pause_cum         += timeGap_Pause_curr;
      thread_sleep(timeGap_Pause_curr);
      posActual_beforeResume_curr = vehicle.getPosActual();
      TestUtil.assertEquals_NoStop(posActual_afterPause_curr, posActual_beforeResume_curr);
      futureMovement.resume();

      //______
      double distance_first = point_Begin.distance(pathArr_point_TargetSegment.get(0));
      double distance_total = distance_first + MathUtil.calc_LengthOfArrPath(pathArr_point_TargetSegment);

      Long timeGap_GetToTarget;
      try {
        futureMovement.getFuture().get();
        long time_End = System.currentTimeMillis();
        timeGap_GetToTarget = time_End - time_Start;
      } catch (InterruptedException e) {
        throw new Error(e);
      } catch (ExecutionException e) {
        throw new Error(e);
      }
      TestUtil.assertEquals_NoStop(true, futureMovement.detmReachTarget());

      TestUtil.assertEquals_NoStop(pathArr_point_TargetSegment.get(pathArr_point_TargetSegment.size() - 1), vehicle.getPosActual());
      double delta = 80 * factor_Delta * factor_Delta_ForProblematicOnes;
      TestUtil.assertEquals_withDelta_NoStop((distance_total / speed_Actual) + timeGap_Pause_cum, timeGap_GetToTarget, delta);

      //____________________

    }

    @Test
    public void gotoTarget_with_cancel() throws InterruptedException {

      //_____
      Vehicle vehicle = new Vehicle();
      try {
        trafficAnalyzerBuilder_corr.placeVehicleInMap(vehicle);
      } catch (AlreadyExistedException e) {
        throw new Error(e);
      }
      ;
      JavafxUtil.javafx_platform_runLater_sync(() -> {
        panel_SemanticRoot.getChildren().add(vehicle.getPaneWrap());
      });

      //_____
      Point point_Begin = new Point(150, 150);
      //_______________________________________________

      ArrayList<Point> pathArr_point_TargetSegment = new ArrayList<>();
      pathArr_point_TargetSegment.add(new Point(200.000000, 200.000000));
      pathArr_point_TargetSegment.add(new Point(300.000000, 200.000000));
      pathArr_point_TargetSegment.add(new Point(400.000000, 150.000000));
      pathArr_point_TargetSegment.add(new Point(350.000000, 300.000000));

      Path path = JavafxUtil.convert_arrPath2pathJfx(pathArr_point_TargetSegment);
      JavafxUtil.javafx_platform_runLater_sync(() -> {
        panel_SemanticRoot.getChildren().add(path);
      });

      double speed_Actual = 0.6 * factor_SpeedUpUnitTest;

      vehicle.setPosActual(point_Begin);
      vehicle.setSpeedActual(speed_Actual);

      //______
      long time_Start = System.currentTimeMillis();
      FutureMovement<Boolean> futureMovement = moveController_corr.gotoTarget(pathArr_point_TargetSegment, vehicle);

      //_____
      long timeGap_Running_curr;
      long timeGap_Pause_cum = 0;
      long timeGap_Pause_curr;

      Point posActual_afterPause_curr;
      Point posActual_beforeResume_curr;

      timeGap_Running_curr = (long) (100 / factor_SpeedUpUnitTest);
      thread_sleep(timeGap_Running_curr);

      timeGap_Pause_cum         += futureMovement.pauseSync();
      posActual_afterPause_curr  = vehicle.getPosActual();
      timeGap_Pause_curr         = (long) (150 / factor_SpeedUpUnitTest);
      timeGap_Pause_cum         += timeGap_Pause_curr;
      thread_sleep(timeGap_Pause_curr);
      posActual_beforeResume_curr = vehicle.getPosActual();
      TestUtil.assertEquals_NoStop(posActual_afterPause_curr, posActual_beforeResume_curr);
      futureMovement.resume();

      timeGap_Running_curr = (long) (250 / factor_SpeedUpUnitTest);
      thread_sleep(timeGap_Running_curr);

      @Main Future<Boolean> future = futureMovement.getFuture();
      System.out.println(">> cancel");
      future.cancel(true);
      futureMovement.waitUntilEnded();
      TestUtil.assertEquals_NoStop(false, futureMovement.detmReachTarget());
      System.out.println("#<< cancel");

      Long timeGap_UntilCancel;
      long time_End = System.currentTimeMillis();
      timeGap_UntilCancel = time_End - time_Start;

      //_____________________________________
      Point posActual_afterCancel_01 = vehicle.getPosActual();
      thread_sleep((long) (100 / factor_SpeedUpUnitTest));
      Point posActual_afterCancel_02 = vehicle.getPosActual();
      TestUtil.assertEquals_NoStop(posActual_afterCancel_01, posActual_afterCancel_02);
      System.out.println(posActual_afterCancel_01);

      //______
      TestUtil.assertNotEquals_NoStop(pathArr_point_TargetSegment.get(pathArr_point_TargetSegment.size() - 1), vehicle.getPosActual());

      //__________________________________________
      double distance_Moved_assertExpect = speed_Actual * (timeGap_UntilCancel - timeGap_Pause_cum);
      //______________________________________________________________________________________
      //______________________________________
      //______________________________________________________
      //_________________________________________________________________________________________________
      //
      //__________________________

      //____________________

    }

  }

  //_____________

  @Nested
  class Test__gotoTarget__dynamicSpeed {

    @Test
    @NeedVisuallyCheck
    public void simpleCase() {
      //_____
      Vehicle vehicle = new Vehicle();
      try {
        trafficAnalyzerBuilder_corr.placeVehicleInMap(vehicle);
      } catch (AlreadyExistedException e) {
        throw new Error(e);
      }
      ;
      JavafxUtil.javafx_platform_runLater_sync(() -> {
        panel_SemanticRoot.getChildren().add(vehicle.getPaneWrap());
      });

      //_____
      Point point_Begin = new Point(100, 100);
      Point point_Target = new Point(800, 250);
      double speed_Actual = 0.6 * factor_SpeedUpUnitTest;

      vehicle.setPosActual(point_Begin);
      vehicle.setSpeedActual(speed_Actual);

      //______
      long time_Start = System.currentTimeMillis();
      @Main FutureMovement<Boolean> futureMovement = moveController_corr.gotoTarget(point_Target, vehicle);

      //_____
      long timeGap_Running_curr; //_________________________________
      double speed_curr;
      double distance_assertExpect = 0;
      Point posActual_before_curr;
      Point posActual_after_curr;
      double delta;

      //__________________________________________________________________________________________________

      posActual_before_curr = vehicle.getPosActual();
      speed_curr            = speed_Actual;
      //____________________________________
      timeGap_Running_curr  = (long) (200 / factor_SpeedUpUnitTest);
      thread_sleep(timeGap_Running_curr);
      posActual_after_curr  = vehicle.getPosActual();
      distance_assertExpect = timeGap_Running_curr * speed_curr;
      delta                 = 40 * factor_Delta;
      TestUtil.assertEquals_withDelta_NoStop(distance_assertExpect, posActual_before_curr.distance(posActual_after_curr), delta);

      posActual_before_curr = vehicle.getPosActual();
      speed_curr            = 0.1 * factor_SpeedUpUnitTest;       //_________________________________________________________
      vehicle.setSpeedActual(speed_curr);
      timeGap_Running_curr = (long) (500 / factor_SpeedUpUnitTest);
      thread_sleep(timeGap_Running_curr);
      posActual_after_curr  = vehicle.getPosActual();
      distance_assertExpect = timeGap_Running_curr * speed_curr;
      delta                 = 40 * factor_Delta;
      TestUtil.assertEquals_withDelta_NoStop(distance_assertExpect, posActual_before_curr.distance(posActual_after_curr), delta);

      posActual_before_curr = vehicle.getPosActual();
      speed_curr            = 0.5 * factor_SpeedUpUnitTest; //___________________________________
      vehicle.setSpeedActual(speed_curr);
      timeGap_Running_curr = (long) (150 / factor_SpeedUpUnitTest);
      thread_sleep(timeGap_Running_curr);
      posActual_after_curr  = vehicle.getPosActual();
      distance_assertExpect = timeGap_Running_curr * speed_curr;
      delta                 = 40 * factor_Delta;
      TestUtil.assertEquals_withDelta_NoStop(distance_assertExpect, posActual_before_curr.distance(posActual_after_curr), delta);

      //________________________________

      //______
      double distance = point_Target.distance(point_Begin);
      Long timeGap_GetToTarget;
      try {
        futureMovement.getFuture().get();
        long time_End = System.currentTimeMillis();
        timeGap_GetToTarget = time_End - time_Start;
      } catch (InterruptedException e) {
        throw new Error(e);
      } catch (ExecutionException e) {
        throw new Error(e);
      }
      TestUtil.assertEquals_NoStop(true, futureMovement.detmReachTarget());

      TestUtil.assertEquals_NoStop(point_Target, vehicle.getPosActual());
      //_______________________________________
      //__________________________________________________________________________________________________

      //____________________
    }

  }

  //_____________________________________

  @Nested
  class Test__gotoTarget__findPath {

    @DuplicatedCode
    public LinkedHashMap<RoadwaySolidRoad, ArrayList<Point>> setup__3_roadway__simple() { //__________
      ArrayList<Point> pathArr_curr_L;
      ArrayList<ArrayList<Point>> arr_pathArr = new ArrayList<>();

      pathArr_curr_L = new ArrayList<>();
      pathArr_curr_L.add(new Point(220.000000, 470.000000));
      pathArr_curr_L.add(new Point(900.000000, 500.000000));
      pathArr_curr_L.add(new Point(930.000000, 420.000000));
      arr_pathArr.add(pathArr_curr_L);

      pathArr_curr_L = new ArrayList<>();
      pathArr_curr_L.add(new Point(100.000000, 200.000000));
      pathArr_curr_L.add(new Point(850.000000, 200.000000));
      pathArr_curr_L.add(new Point(760.000000, 580.000000));
      arr_pathArr.add(pathArr_curr_L);

      pathArr_curr_L = new ArrayList<>();
      pathArr_curr_L.add(new Point(200.000000, 110.000000));
      pathArr_curr_L.add(new Point(300.000000, 400.000000));
      pathArr_curr_L.add(new Point(500.000000, 400.000000));
      pathArr_curr_L.add(new Point(500.000000, 550.000000));
      arr_pathArr.add(pathArr_curr_L);

      return createRoadway_ForTestSetup(arr_pathArr);
    }

    @Test
    @NeedVisuallyCheck //________________________________________
    public void simpleCase() {

      @DuplicatedCode
      //_________________

      //____

      LinkedHashMap<RoadwaySolidRoad, ArrayList<Point>> mpp__roadway_vs_pathArr_point = setup__3_roadway__simple();

      //
      HashSet<Point> gp_AllRoadwayCross_assertCheck = new HashSet<>();

      //
      LinkedList<LinkedList<Entry<RoadwaySolidRoad, ArrayList<Point>>>> comb = MathUtil.get_Combination(mpp__roadway_vs_pathArr_point.entrySet(), 2);
      //_____________________________________________________________________________
      for (LinkedList<Entry<RoadwaySolidRoad, ArrayList<Point>>> arr_Group_curr : comb) {
        if (arr_Group_curr.size() != 2) { throw new Error("Comb specified 2 per group, so should be 2"); }

        RoadwaySolidRoad roadway_prev = arr_Group_curr.get(0).getKey();
        RoadwaySolidRoad roadway_curr = arr_Group_curr.get(1).getKey();

        List<RoadwayCrossPointConnectionDto> arr_roadwayCrossPointConnection = mapBuilder_corr.findAllCrossPoint(roadway_prev, roadway_curr);
        //_______________________________________________________________________
        for (RoadwayCrossPointConnectionDto roadwayCrossPointConnectionDto_curr : arr_roadwayCrossPointConnection) {
          if (!gp_AllRoadwayCross_assertCheck.add(roadwayCrossPointConnectionDto_curr.getPointLocation())) { throw new Error(); }
        }
        //________________________________________________________
        //______________________________________________________________________________________________________________________________________________________________________________________________________________________________
        //___________
        mapBuilder_corr.connectRoadwayAtCrossPointConnection(arr_roadwayCrossPointConnection);
      }

      //
      System.out.println(gp_AllRoadwayCross_assertCheck);
      //______________________________________________________
      //_________________________________________________________________________________________________________________
      //_________

      //____

      HashSet<RoadwayPoint> gp_AllPoint = new HashSet<>();
      HashSet<RoadwaySegment> gp_AllSegment = new HashSet<>();
      mapBuilder_corr.getMapFile().getGpRoadwaySolidRoad().forEach(e -> {
        e.getArrRoadwayDirLinkerComponent().forEach(f -> {
          gp_AllPoint.add(f.getRoadwayPoint());
          gp_AllSegment.add(f.getRoadwaySegment());
        });
      });
      gp_AllSegment.remove(RoadwaySegment.roadwaySegmentPseudoBegin);
      //______________________________________________________
      //________________________________________________________________________________________________________________________________________________
      //_________

      TestUtil.assertEquals_NoStop(13, gp_AllPoint.size());
      TestUtil.assertEquals_NoStop(13, gp_AllSegment.size());

      //____

      Point point_Self = new Point(646.000000, 487.000000);
      Point point_Target = new Point(260.000000, 200.000000);
      @Main Triple<LinkedList<RoadwayDirLinkerComponent>, Pair<RoadwayNormalSegment, Point>, Pair<RoadwayNormalSegment, Point>> result_findPath;
      try {
        result_findPath = windowSession.pathFinder.findPath(point_Self, point_Target, false, FindPathAlgorithm.ClosestPoint);
      } catch (PointIsNotNearAnyRoadwayException e1) {
        throw new Error(e1);
      }
      //______________________________________________________________________
      //_______________________________________________________________________
      LinkedList<RoadwayDirLinkerComponent> pathArr_dirLinker_assertCheck = result_findPath.getLeft();
      LinkedList<List<Point>> arr_Vis = new LinkedList<>();
      for (RoadwayDirLinkerComponent roadwayDirLinkerComponent_curr : pathArr_dirLinker_assertCheck) {
        RoadwaySegment roadwaySegment_curr = roadwayDirLinkerComponent_curr.getRoadwaySegment();
        if (roadwaySegment_curr instanceof RoadwaySegmentPseudoBegin) { continue; }
        arr_Vis.add(Arrays.asList(roadwaySegment_curr.getRoadwayPointSp().getPointLocation(), roadwaySegment_curr.getRoadwayPointNp().getPointLocation()));
      }
      List<Point> pathArr_point_Go_assertCheck = pathArr_dirLinker_assertCheck.stream().map(t -> t.getRoadwayPoint().getPointLocation()).toList();
      JavafxUtil.javafx_platform_runLater_sync(() -> {
        JavafxUtil.visualize_Point(Arrays.asList(point_Self, point_Target), panel_SemanticRoot, "G-", true, false, 10, 10);
        JavafxUtil.visualize_Line(arr_Vis, panel_SemanticRoot);
        JavafxUtil.visualize_Point(pathArr_point_Go_assertCheck, panel_SemanticRoot, "F-", true, false, 10, 10);
      });
      System.out.println("pathArr_point_Go_assertCheck" + " :: " + pathArr_point_Go_assertCheck);

      TestUtil.assertIterableEquals_NoStop(new ArrayList<Point>(Arrays.asList(
                                                                              new Point(645.92100150444680000000, 488.79063249557277000000), //___________________
                                                                              new Point(780.19915700000000000000, 494.71466900000000000000),
                                                                              new Point(850.00000000000000000000, 200.00000000000000000000),
                                                                              new Point(260.00000000000000000000, 200.00000000000000000000))),
                                           pathArr_point_Go_assertCheck);

      //____________________________________________________________
      //______________________________________________________________

      @DuplicatedCode

      //_____________________________________________________

      //_____
      Vehicle vehicle = new Vehicle();
      try {
        trafficAnalyzerBuilder_corr.placeVehicleInMap(vehicle);
      } catch (AlreadyExistedException e) {
        throw new Error(e);
      }
      ;
      JavafxUtil.javafx_platform_runLater_sync(() -> {
        panel_SemanticRoot.getChildren().add(vehicle.getPaneWrap());
      });

      //_____
      Point point_Begin = point_Self;
      //______________________________________________
      //_______________________________________________

      //_______________________________________________________________________
      //_________________________________________________________________________
      //_________________________________________________________________________
      //_________________________________________________________________________
      //_________________________________________________________________________

      @Main List<Point> pathArr_point_TargetSegment = pathArr_point_Go_assertCheck;

      Path path = JavafxUtil.convert_arrPath2pathJfx(pathArr_point_TargetSegment);
      JavafxUtil.javafx_platform_runLater_sync(() -> {
        panel_SemanticRoot.getChildren().add(path);
      });

      double speed_Actual = 0.6 * factor_SpeedUpUnitTest;

      vehicle.setPosActual(point_Begin);
      vehicle.setSpeedActual(speed_Actual);

      //______
      long time_Start = System.currentTimeMillis();
      @Main FutureMovement<Boolean> futureMovement = moveController_corr.gotoTarget(pathArr_point_TargetSegment, vehicle);

      //______
      double distance_first = point_Begin.distance(pathArr_point_TargetSegment.get(0));
      double distance_total = distance_first + MathUtil.calc_LengthOfArrPath(pathArr_point_TargetSegment);

      Long timeGap_GetToTarget;
      try {
        futureMovement.getFuture().get();
        long time_End = System.currentTimeMillis();
        timeGap_GetToTarget = time_End - time_Start;
      } catch (InterruptedException e) {
        throw new Error(e);
      } catch (ExecutionException e) {
        throw new Error(e);
      }
      TestUtil.assertEquals_NoStop(true, futureMovement.detmReachTarget());

      TestUtil.assertEquals_NoStop(pathArr_point_TargetSegment.get(pathArr_point_TargetSegment.size() - 1), vehicle.getPosActual());
      double delta = 80 * factor_Delta;
      TestUtil.assertEquals_withDelta_NoStop(distance_total / speed_Actual, timeGap_GetToTarget, delta);
      //___________________________

      //____________________
    }

  }

  //_____________

  //_____________
  //_____________
  //_____________

  private LinkedHashMap<RoadwaySolidRoad, ArrayList<Point>> createRoadway_ForTestSetup(ArrayList<ArrayList<Point>> arr_pathArr) {
    LinkedHashMap<RoadwaySolidRoad, ArrayList<Point>> mpp__roadway_vs_pathArr_point = new LinkedHashMap<>();
    String nameRoadway_prepend = "Ro";
    int i = 0;

    for (ArrayList<Point> pathArr_curr : arr_pathArr) {
      i++;
      RoadwaySolidRoad roadway_curr = mapBuilder_corr.createRoadway(nameRoadway_prepend + i, pathArr_curr);
      mpp__roadway_vs_pathArr_point.put(roadway_curr, pathArr_curr);
      JavafxUtil.javafx_platform_runLater_sync(() -> {
        Path path = JavafxUtil.convert_arrPath2pathJfx(pathArr_curr);
        panel_SemanticRoot.getChildren().add(path);
      });

    }

    return mpp__roadway_vs_pathArr_point;
  }

  public void thread_sleep(long milliSecond) {
    try {
      Thread.sleep(milliSecond);
    } catch (InterruptedException e) {
      e.printStackTrace();
      throw new Error(e);
    }
  }

}
