package com.redfrog.trafficsm.maincoretest;

import java.time.Instant;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;
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
import com.redfrog.trafficsm.model.TrafficDetector;
import com.redfrog.trafficsm.model.Vehicle;
import com.redfrog.trafficsm.model.VehicleInfoTrafficDetectorDto;
import com.redfrog.trafficsm.model.roadway.main.RoadwaySolidRoad;
import com.redfrog.trafficsm.service.MapBuilder;
import com.redfrog.trafficsm.service.MoveController;
import com.redfrog.trafficsm.service.MoveController.FutureMovement;
import com.redfrog.trafficsm.service.TrafficAnalyzerBuilder;
import com.redfrog.trafficsm.service.WindowSessionJavafx;
import com.redfrog.trafficsm.session.WindowSession;
import com.redfrog.trafficsm.shape.Point;
import com.redfrog.trafficsm.test.javafxBoot.JfxAppSimpleSetup;
import com.redfrog.trafficsm.util.JavafxUtil;
import com.redfrog.trafficsm.util.MathUtil;
import com.redfrog.trafficsm.util.TestUtil;

import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Path;
import javafx.stage.Stage;

class TrafficDetectorTest extends ApplicationTest {

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

  //__________
  //_____________________________________________________________________________________________
  //___

  //_____________

  //__________________________________________________________________________________________
  //______________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________
  //__________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________

  private static final Logger logger = LogManager.getLogger(TrafficDetectorTest.class);

  private static final Marker mk_Test = MarkerManager.getMarker("Test");

  //_____________
  //_____________

  /**
_____________
___*/
  @Config
  //___________________________________________________________
  private static final double factor_SpeedUpUnitTest = 3;

  /**
_____________
___*/
  @Config
  private static final double factor_Delta = 1;

  @Config
  @Debug
  /**
_____________
__*/
  private static final double factor_Delta_ForProblematicOnes = 1.5; //________________________________________________________________________________

  private static final String name_TD_prepend = "TD";

  @Nested
  class Test__TrafficDetector {

    //_________________________________________________________
    /**
_____________________
__________________________
_____________________
________________________
________________________________________________________________________________________________________________________________________
_________________________________________________________
____
___________________________________
____
______________________
____
______________________________________________________________
______________________________________________________________
__________________________________________________________________________
____
__________________________________________________________________________________
_____
_____________________________________________________________________________________________________
____
_________________________________________
____
____________________
____
____________________________________
_______________
_______________________________
____________________________________________________________________________________________
___________________________________________________________________________________________________________________________________________________________________________
__________________________________________________________________________________________________________________________________________
____
_____*/
    //___________________________________________________________
    private static final double factor_radiusDetection = 4; //________________________________________________________________________

    @Test
    @NeedVisuallyCheck
    public void simpleCase() {
      //______
      int sn_td = 1;

      Point point_TrafficDetector;

      point_TrafficDetector = new Point(500, 200);
      TrafficDetector trafficDetector = trafficAnalyzerBuilder_corr.createTrafficDetector(name_TD_prepend + ++sn_td, Instant.ofEpochSecond(0), point_TrafficDetector, 20 * factor_radiusDetection);
      JavafxUtil.javafx_platform_runLater_sync(() -> {
        panel_SemanticRoot.getChildren().add(trafficDetector.getPaneWrap());
      });

      //_________________
      //_________________

      @DuplicatedCode byte dmy; //_

      //_____
      Vehicle vehicle = new Vehicle();
      vehicle.setIdSql((long) ++sn_td);
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

      TestUtil.assertEquals_NoStop(point_Target, vehicle.getPosActual());
      double delta = 80 * factor_Delta * factor_Delta_ForProblematicOnes; //______________________________________________________________________
      TestUtil.assertEquals_withDelta_NoStop(distance / speed_Actual, timeGap_GetToTarget, delta);

      //____________________

      //_________________
      //_________________

      List<VehicleInfoTrafficDetectorDto> arr_VehicleInfoTrafficDetectorDto_history = trafficDetector.getArrVehicleInfoTrafficDetectorDtoHistory();

      //_______________________________________________________________________
      //______________________________________________________________________________
      logger.trace(mk_Test, arr_VehicleInfoTrafficDetectorDto_history.get(0).getSpeedMeasuredByTrafficDetector());

      TestUtil.assertEquals_NoStop(1, arr_VehicleInfoTrafficDetectorDto_history.size());

      delta = 0.2 * factor_SpeedUpUnitTest;
      TestUtil.assertEquals_withDelta_NoStop(speed_Actual, arr_VehicleInfoTrafficDetectorDto_history.get(0).getSpeedMeasuredByTrafficDetector(), delta);

      //____________________

    }

    //____________________________________________________

    @Test
    public void multi_Detector_Vehicle() {
      //______
      int sn_td = 1;

      Point point_TrafficDetector;

      point_TrafficDetector = new Point(500, 200);
      TrafficDetector trafficDetector = trafficAnalyzerBuilder_corr.createTrafficDetector(name_TD_prepend + ++sn_td, Instant.ofEpochSecond(0), point_TrafficDetector, 20 * factor_radiusDetection);
      JavafxUtil.javafx_platform_runLater_sync(() -> {
        panel_SemanticRoot.getChildren().add(trafficDetector.getPaneWrap());
      });

      point_TrafficDetector = new Point(600, 400);
      TrafficDetector trafficDetector_02 = trafficAnalyzerBuilder_corr.createTrafficDetector(name_TD_prepend + ++sn_td, Instant.ofEpochSecond(0), point_TrafficDetector, 20 * factor_radiusDetection);
      JavafxUtil.javafx_platform_runLater_sync(() -> {
        panel_SemanticRoot.getChildren().add(trafficDetector_02.getPaneWrap());
      });

      //_________________
      //_________________
      //_________________
      //_________________

      @DuplicatedCode byte dmy944; //_

      //_________________
      //_________________

      //_____
      //__________________________________________________________________
      //__________________________________________________________________________________________
      Vehicle vehicle = new Vehicle();
      vehicle.setIdSql((long) ++sn_td); //___________________________________________________________
      System.out.println(vehicle);
      try {
        trafficAnalyzerBuilder_corr.placeVehicleInMap(vehicle);
      } catch (AlreadyExistedException e) {
        throw new Error(e);
      }
      JavafxUtil.javafx_platform_runLater_sync(() -> {
        panel_SemanticRoot.getChildren().add(vehicle.getPaneWrap());
      });

      //_____
      Point point_Begin = new Point(180, 180);
      //_______________________________________________

      ArrayList<Point> pathArr_point_TargetSegment = new ArrayList<>();
      pathArr_point_TargetSegment.add(new Point(200.000000, 200.000000));
      pathArr_point_TargetSegment.add(new Point(800.000000, 200.000000));
      pathArr_point_TargetSegment.add(new Point(400.000000, 500.000000));

      Path path = JavafxUtil.convert_arrPath2pathJfx(pathArr_point_TargetSegment);
      JavafxUtil.javafx_platform_runLater_sync(() -> {
        panel_SemanticRoot.getChildren().add(path);
      });

      double speed_Actual = 0.3 * factor_SpeedUpUnitTest;

      vehicle.setPosActual(point_Begin);
      vehicle.setSpeedActual(speed_Actual);

      //_____
      //_____________________________________________________________________
      //________________________________________________________________________________________________
      Vehicle vehicle_02 = new Vehicle();
      vehicle_02.setIdSql((long) ++sn_td);
      System.out.println(vehicle_02);
      try {
        trafficAnalyzerBuilder_corr.placeVehicleInMap(vehicle_02);
      } catch (AlreadyExistedException e) {
        throw new Error(e);
      }
      JavafxUtil.javafx_platform_runLater_sync(() -> {
        panel_SemanticRoot.getChildren().add(vehicle_02.getPaneWrap());
      });

      //_____
      Point point_Begin_02 = new Point(150, 550);
      //_______________________________________________

      ArrayList<Point> pathArr_point_TargetSegment_02 = new ArrayList<>();
      pathArr_point_TargetSegment_02.add(new Point(200.000000, 600.000000));
      pathArr_point_TargetSegment_02.add(new Point(400.000000, 100.000000));
      pathArr_point_TargetSegment_02.add(new Point(700.000000, 500.000000));

      Path path_02 = JavafxUtil.convert_arrPath2pathJfx(pathArr_point_TargetSegment_02);
      JavafxUtil.javafx_platform_runLater_sync(() -> {
        panel_SemanticRoot.getChildren().add(path_02);
      });

      double speed_Actual_02 = 0.6 * factor_SpeedUpUnitTest;

      vehicle_02.setPosActual(point_Begin_02);
      vehicle_02.setSpeedActual(speed_Actual_02);

      //_________________
      //_________________

      //______
      long time_Start = System.currentTimeMillis();
      FutureMovement<Boolean> futureMovement = moveController_corr.gotoTarget(pathArr_point_TargetSegment, vehicle);

      //______
      Thread t1 = new Thread(() -> {
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
        TestUtil.assertEquals_withDelta_NoStop(distance_total / speed_Actual, timeGap_GetToTarget, delta);
      });

      //_________________
      //_________________

      //______
      long time_Start_02 = System.currentTimeMillis();
      FutureMovement<Boolean> futureMovement_02 = moveController_corr.gotoTarget(pathArr_point_TargetSegment_02, vehicle_02);

      //______
      Thread t2 = new Thread(() -> {
        double distance_first_02 = point_Begin_02.distance(pathArr_point_TargetSegment_02.get(0));
        double distance_total_02 = distance_first_02 + MathUtil.calc_LengthOfArrPath(pathArr_point_TargetSegment_02);

        Long timeGap_GetToTarget_02;
        try {
          futureMovement_02.getFuture().get();
          long time_End = System.currentTimeMillis();
          timeGap_GetToTarget_02 = time_End - time_Start_02;
        } catch (InterruptedException e) {
          throw new Error(e);
        } catch (ExecutionException e) {
          throw new Error(e);
        }
        TestUtil.assertEquals_NoStop(true, futureMovement_02.detmReachTarget());

        TestUtil.assertEquals_NoStop(pathArr_point_TargetSegment_02.get(pathArr_point_TargetSegment_02.size() - 1), vehicle_02.getPosActual());
        double delta_02 = 80 * factor_Delta * factor_Delta_ForProblematicOnes;
        TestUtil.assertEquals_withDelta_NoStop(distance_total_02 / speed_Actual_02, timeGap_GetToTarget_02, delta_02);
      });

      t1.start();
      t2.start();
      try {
        t1.join();
        t2.join();
      } catch (InterruptedException e) {
        throw new Error(e);
      }

      //_________________
      //_________________
      //_________________
      //_________________

      //________________________________________
      //____________________________________________________________________________________________
      //______________________________________________________________________
      //___________________________________
      //___________________
      //____
      //________________________________________
      //___
      //_____________________________________________________________________________
      //_______________________________________________________
      //_________________________________________
      //___________________________________________________________

      List<VehicleInfoTrafficDetectorDto> arr_VehicleInfoTrafficDetectorDto_history = trafficDetector.getArrVehicleInfoTrafficDetectorDtoHistory();
      List<VehicleInfoTrafficDetectorDto> arr_VehicleInfoTrafficDetectorDto_history_02 = trafficDetector_02.getArrVehicleInfoTrafficDetectorDtoHistory();

      TestUtil.assertEquals_NoStop(2, arr_VehicleInfoTrafficDetectorDto_history.size());
      TestUtil.assertEquals_NoStop(2, arr_VehicleInfoTrafficDetectorDto_history_02.size());

      //__________________________________________________
      logger.trace(mk_Test, arr_VehicleInfoTrafficDetectorDto_history.get(0).getSpeedMeasuredByTrafficDetector());
      logger.trace(mk_Test, arr_VehicleInfoTrafficDetectorDto_history.get(1).getSpeedMeasuredByTrafficDetector());
      logger.trace(mk_Test, arr_VehicleInfoTrafficDetectorDto_history_02.get(0).getSpeedMeasuredByTrafficDetector());
      logger.trace(mk_Test, arr_VehicleInfoTrafficDetectorDto_history_02.get(1).getSpeedMeasuredByTrafficDetector());

      double delta = 0.2 * factor_SpeedUpUnitTest;

      for (VehicleInfoTrafficDetectorDto vehicleInfoTrafficDetectorDto : arr_VehicleInfoTrafficDetectorDto_history) {
        if (vehicleInfoTrafficDetectorDto.getVehicleCreationTime() == vehicle.getVehicleCreationTime()) {
          TestUtil.assertEquals_withDelta_NoStop(speed_Actual, vehicleInfoTrafficDetectorDto.getSpeedMeasuredByTrafficDetector(), delta);
        }
        else if (vehicleInfoTrafficDetectorDto.getVehicleCreationTime() == vehicle_02.getVehicleCreationTime()) {
          TestUtil.assertEquals_withDelta_NoStop(speed_Actual_02, vehicleInfoTrafficDetectorDto.getSpeedMeasuredByTrafficDetector(), delta);
        }
        else {
          throw new Error();
        }
      }

      for (VehicleInfoTrafficDetectorDto vehicleInfoTrafficDetectorDto : arr_VehicleInfoTrafficDetectorDto_history_02) {
        if (vehicleInfoTrafficDetectorDto.getVehicleCreationTime() == vehicle.getVehicleCreationTime()) {
          TestUtil.assertEquals_withDelta_NoStop(speed_Actual, vehicleInfoTrafficDetectorDto.getSpeedMeasuredByTrafficDetector(), delta);
        }
        else if (vehicleInfoTrafficDetectorDto.getVehicleCreationTime() == vehicle_02.getVehicleCreationTime()) {
          TestUtil.assertEquals_withDelta_NoStop(speed_Actual_02, vehicleInfoTrafficDetectorDto.getSpeedMeasuredByTrafficDetector(), delta);
        }
        else {
          throw new Error();
        }
      }

      //____________________

    }

  }

  //_____________

  //_____________
  //_____________
  //_____________

  @DuplicatedCode
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

}
