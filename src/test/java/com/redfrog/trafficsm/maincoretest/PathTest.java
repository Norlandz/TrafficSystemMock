package com.redfrog.trafficsm.maincoretest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import com.redfrog.trafficsm.annotation.BugPotential;
import com.redfrog.trafficsm.annotation.DuplicatedCode;
import com.redfrog.trafficsm.annotation.Main;
import com.redfrog.trafficsm.annotation.Messy;
import com.redfrog.trafficsm.annotation.NeedVisuallyCheck;
import com.redfrog.trafficsm.model.roadway.fundamental.RoadwayDirLinkerComponent;
import com.redfrog.trafficsm.model.roadway.fundamental.RoadwayPoint;
import com.redfrog.trafficsm.model.roadway.fundamental.RoadwaySegment;
import com.redfrog.trafficsm.model.roadway.fundamental.pseudo.RoadwaySegmentPseudoBegin;
import com.redfrog.trafficsm.model.roadway.main.RoadwayCrossPointConnectionDto;
import com.redfrog.trafficsm.model.roadway.main.RoadwayNormalSegment;
import com.redfrog.trafficsm.model.roadway.main.RoadwaySolidRoad;
import com.redfrog.trafficsm.service.MapBuilder;
import com.redfrog.trafficsm.service.PathFinder.FindPathAlgorithm;
import com.redfrog.trafficsm.service.WindowSessionJavafx;
import com.redfrog.trafficsm.service.exception.PointIsNotNearAnyRoadwayException;
import com.redfrog.trafficsm.session.WindowSession;
import com.redfrog.trafficsm.shape.Point;
import com.redfrog.trafficsm.test.javafxBoot.JfxAppSimpleSetup;
import com.redfrog.trafficsm.util.JavafxUtil;
import com.redfrog.trafficsm.util.MathUtil;
import com.redfrog.trafficsm.util.TestUtil;

import javafx.application.Platform;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Path;
import javafx.stage.Stage;

//__________________
class PathTest extends ApplicationTest {

  Random random = new Random(111);

  @BeforeAll
  public static void beforeAll() {
    System.out.println(">--< beforeAll");

  }

  //____________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________

  private AnchorPane panel_SemanticRoot;

  private WindowSession windowSession;
  private WindowSessionJavafx windowSessionJavafx_corr;
  private MapBuilder mapBuilder_corr;

  /**
_________________________________________________________________________________
__*/
  @Override
  public void start(Stage primaryStage) {
    panel_SemanticRoot                          = JfxAppSimpleSetup.startSetup(primaryStage);

    windowSession                               = new WindowSession();
    mapBuilder_corr                             = windowSession.mapBuilder; //_____________________________________________
    windowSessionJavafx_corr                    = windowSession.windowSessionJavafx;
    windowSessionJavafx_corr.panel_SemanticRoot = panel_SemanticRoot;
    windowSessionJavafx_corr.javafxStage        = primaryStage;
    @Messy byte dmy137; //_
    //___________________________________________________________________________________________________________________________________________________________________________
    mapBuilder_corr.newMapFile();

    //_____________
    //_____________________________
    //__________________________________________
    //______________________________
    //_____________________________
    //_________

    //________________________________
    //___________________________________
    //___________________________________________________________
    //___________________________________________________________
  }

  //_____________
  //________________________________________________________
  //
  //____________
  //_______________________________________________________

  //_____________

  @Nested
  class Test__createRoadway__findPath {

    @Test
    @NeedVisuallyCheck
    public void simpleCase__3_roadway_each_single_intersect_X_permutation_alg() {
      //_______________________________
      JavafxUtil.javafx_platform_runLater_sync(() -> {
        //_______________________________

        //_________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________
        //_____________________________________________________________________________________________

        //______________________________________________________________
        //_____________________________________________________________
        //__________________________________________________________________

        ArrayList<Point> pathArr_point;
        Path path;

        RoadwaySolidRoad roadway_curr;
        RoadwaySolidRoad roadway_prev;
        RoadwaySolidRoad roadway_first_messy;

        List<RoadwayCrossPointConnectionDto> arr_roadwayCrossPointConnection;

        ArrayList<Point> arr_AllRoadwayPoint_assertNeed = new ArrayList<>();
        ArrayList<Point> arr_AllRoadwayCross_assertNeed = new ArrayList<>();

        //___________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________

        pathArr_point = new ArrayList<>();
        pathArr_point.add(new Point(220.000000, 470.000000));
        pathArr_point.add(new Point(900.000000, 500.000000));
        pathArr_point.add(new Point(930.000000, 420.000000));
        arr_AllRoadwayPoint_assertNeed.addAll(pathArr_point);
        roadway_curr = mapBuilder_corr.createRoadway("Ro1", pathArr_point);
        path         = JavafxUtil.convert_arrPath2pathJfx(pathArr_point);
        panel_SemanticRoot.getChildren().add(path);
        roadway_prev        = roadway_curr;
        roadway_first_messy = roadway_curr;

        pathArr_point       = new ArrayList<>();
        pathArr_point.add(new Point(100.000000, 200.000000));
        pathArr_point.add(new Point(850.000000, 200.000000));
        pathArr_point.add(new Point(760.000000, 580.000000));
        arr_AllRoadwayPoint_assertNeed.addAll(pathArr_point);
        roadway_curr = mapBuilder_corr.createRoadway("Ro2", pathArr_point);
        path         = JavafxUtil.convert_arrPath2pathJfx(pathArr_point);
        panel_SemanticRoot.getChildren().add(path);
        arr_roadwayCrossPointConnection = mapBuilder_corr.findAllCrossPoint(roadway_prev, roadway_curr);
        if (arr_roadwayCrossPointConnection.size() != 1) { throw new Error(); }
        arr_AllRoadwayCross_assertNeed.add(arr_roadwayCrossPointConnection.get(0).getPointLocation());
        JavafxUtil.visualize_Point(arr_roadwayCrossPointConnection.stream().map(t -> t.getPointLocation()).toList(), panel_SemanticRoot, "CsRo1Ro2-");
        //_____________________________________________________________________________________________________________________
        mapBuilder_corr.connectRoadwayAtCrossPointConnection(arr_roadwayCrossPointConnection);
        roadway_prev  = roadway_curr;

        pathArr_point = new ArrayList<>();
        pathArr_point.add(new Point(200.000000, 110.000000));
        pathArr_point.add(new Point(300.000000, 400.000000));
        pathArr_point.add(new Point(500.000000, 400.000000));
        pathArr_point.add(new Point(500.000000, 550.000000));
        arr_AllRoadwayPoint_assertNeed.addAll(pathArr_point);
        roadway_curr = mapBuilder_corr.createRoadway("Ro3", pathArr_point);
        path         = JavafxUtil.convert_arrPath2pathJfx(pathArr_point);
        panel_SemanticRoot.getChildren().add(path);
        arr_roadwayCrossPointConnection = mapBuilder_corr.findAllCrossPoint(roadway_prev, roadway_curr);
        if (arr_roadwayCrossPointConnection.size() != 1) { throw new Error(); }
        arr_AllRoadwayCross_assertNeed.add(arr_roadwayCrossPointConnection.get(0).getPointLocation());
        JavafxUtil.visualize_Point(arr_roadwayCrossPointConnection.stream().map(t -> t.getPointLocation()).toList(), panel_SemanticRoot, "CsRo2Ro3-");
        mapBuilder_corr.connectRoadwayAtCrossPointConnection(arr_roadwayCrossPointConnection);
        roadway_prev                    = roadway_curr;

        //_________________________
        arr_roadwayCrossPointConnection = mapBuilder_corr.findAllCrossPoint(roadway_prev, roadway_first_messy);
        if (arr_roadwayCrossPointConnection.size() != 1) { throw new Error(); }
        arr_AllRoadwayCross_assertNeed.add(arr_roadwayCrossPointConnection.get(0).getPointLocation());
        JavafxUtil.visualize_Point(arr_roadwayCrossPointConnection.stream().map(t -> t.getPointLocation()).toList(), panel_SemanticRoot, "CsRo3Ro1-");
        //______________________________________________________________________________________________________________________________________________________________________________________________________
        mapBuilder_corr.connectRoadwayAtCrossPointConnection(arr_roadwayCrossPointConnection); //_______________________________________________________________________

        //_____

        Point point_Self = new Point(646.000000, 487.000000);
        Point point_Target = new Point(260.000000, 200.000000);
        JavafxUtil.visualize_Point(Arrays.asList(point_Self, point_Target), panel_SemanticRoot);
        Triple<LinkedList<RoadwayDirLinkerComponent>, Pair<RoadwayNormalSegment, Point>, Pair<RoadwayNormalSegment, Point>> result_findPath;
        try {
          result_findPath = windowSession.pathFinder.findPath(point_Self, point_Target, false, FindPathAlgorithm.Permutation);
        } catch (PointIsNotNearAnyRoadwayException e) {
          throw new Error(e);
        }
        Point point_SelfOnPath = result_findPath.getMiddle().getRight();
        Point point_TargetOnPath = result_findPath.getRight().getRight();
        @Main LinkedList<RoadwayDirLinkerComponent> pathArr_Shortest_assertCheck = result_findPath.getLeft();
        //___________________________________________________________
        //______________________________________________________________
        //___________________________________________________________________________________________________________________________________________
        //_______________________________________
        //______________________________________________________
        //_____________________________________________________________________________________
        //_______________________________________________
        //______________________________________________________________________________________________________________________________________________________
        //__________________________________________________________________________________________________________
        //________________________________________________________________________________________________
        //___________________________________
        //___________________
        //________________________
        //_______________________________________________________________________________________________________________________________________________________________________
        //___________________
        //______________________________
        //__________________________________________________
        //________________________________________
        //________________________________________
        //________________________________________
        LinkedList<List<Point>> arr_Vis = new LinkedList<>();
        for (RoadwayDirLinkerComponent roadwayDirLinkerComponent_curr : pathArr_Shortest_assertCheck) {
          RoadwaySegment roadwaySegment_curr = roadwayDirLinkerComponent_curr.getRoadwaySegment();
          if (roadwaySegment_curr instanceof RoadwaySegmentPseudoBegin) { continue; }
          arr_Vis.add(Arrays.asList(roadwaySegment_curr.getRoadwayPointSp().getPointLocation(), roadwaySegment_curr.getRoadwayPointNp().getPointLocation()));
        }
        JavafxUtil.visualize_Line(arr_Vis, panel_SemanticRoot);
        JavafxUtil.visualize_Point(pathArr_Shortest_assertCheck.stream().map(t -> t.getRoadwayPoint().getPointLocation()).toList(), panel_SemanticRoot, "F-", true, false, 10, 10);
        //________________________________________________________________________________________________________________________________________

        Assertions.assertTrue(!pathArr_Shortest_assertCheck.isEmpty());

        //_____

        //_____________________
        LinkedList<Point> pathArr_point_assertExpect = new LinkedList<>();
        pathArr_point_assertExpect.add(point_SelfOnPath);
        pathArr_point_assertExpect.add(arr_AllRoadwayCross_assertNeed.get(3 - 1));
        pathArr_point_assertExpect.add(arr_AllRoadwayPoint_assertNeed.get(3 + 3 + 3 - 1));
        pathArr_point_assertExpect.add(arr_AllRoadwayPoint_assertNeed.get(3 + 3 + 2 - 1));
        pathArr_point_assertExpect.add(arr_AllRoadwayCross_assertNeed.get(2 - 1));
        pathArr_point_assertExpect.add(point_TargetOnPath);
        JavafxUtil.visualize_Point(pathArr_point_assertExpect, panel_SemanticRoot, "E-", true, false, -15, -15);
        //_____________________________________________________________________________

        int i = 0;
        //___________________________________________
        //_________________________________________
        RoadwayPoint roadwayPoint_prev = null;
        for (RoadwayDirLinkerComponent roadwayDirLinkerComponent_curr : pathArr_Shortest_assertCheck) {
          i++;
          //______________________________

          RoadwaySegment roadwaySegment_curr = roadwayDirLinkerComponent_curr.getRoadwaySegment();

          RoadwayPoint roadwayPoint_curr = roadwayDirLinkerComponent_curr.getRoadwayPoint();

          @Main Point pointLocation_curr = roadwayPoint_curr.getPointLocation();
          Assertions.assertSame(pointLocation_curr, pathArr_point_assertExpect.get(i - 1));

          if (i == 1) {
            //_______________________________
            //_______________________________________________________________
            Assertions.assertTrue(roadwaySegment_curr instanceof RoadwaySegmentPseudoBegin);
          }
          else {
            //_______________________________

            RoadwayPoint roadwayPointSp_curr = roadwaySegment_curr.getRoadwayPointSp();
            RoadwayPoint roadwayPointNp_curr = roadwaySegment_curr.getRoadwayPointNp();

            if (roadwayPointSp_curr == roadwayPoint_prev) {
              Assertions.assertSame(roadwayPointNp_curr, roadwayPoint_curr);
            }
            else if (roadwayPointNp_curr == roadwayPoint_prev) {
              Assertions.assertSame(roadwayPointSp_curr, roadwayPoint_curr);
            }
            else {
              throw new Error("Not_Reachable unless segment wrong");
            }
          }

          //__________________________________________________
          roadwayPoint_prev = roadwayPoint_curr;
        }

      });

      //__________________________________________________________________________________________________________
      //____________________________________________
      //________________________________________________________________________________________
      //_______________________________________________________________________________
      //________________________________

      //_____________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________
      //__________________

    }

    //_____________________________________

    //_____

    public LinkedHashMap<RoadwaySolidRoad, ArrayList<Point>> setup__multi_roadway_intersect__complexLvX() {

      ArrayList<Point> pathArr_curr_L;
      ArrayList<ArrayList<Point>> arr_pathArr = new ArrayList<>();

      //

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

      //

      pathArr_curr_L = new ArrayList<>();
      pathArr_curr_L.add(new Point(200.000000, 200.000000));
      pathArr_curr_L.add(new Point(300.000000, 300.000000));
      pathArr_curr_L.add(new Point(800.000000, 300.000000));
      pathArr_curr_L.add(new Point(900.000000, 200.000000));
      arr_pathArr.add(pathArr_curr_L);

      pathArr_curr_L = new ArrayList<>();
      pathArr_curr_L.add(new Point(500.000000, 100.000000));
      pathArr_curr_L.add(new Point(500.000000, 400.000000));
      pathArr_curr_L.add(new Point(600.000000, 400.000000));
      pathArr_curr_L.add(new Point(650.000000, 100.000000));
      pathArr_curr_L.add(new Point(700.000000, 100.000000));
      pathArr_curr_L.add(new Point(700.000000, 500.000000));
      pathArr_curr_L.add(new Point(750.000000, 150.000000));
      arr_pathArr.add(pathArr_curr_L);

      pathArr_curr_L = new ArrayList<>();
      pathArr_curr_L.add(new Point(100.000000, 600.000000));
      pathArr_curr_L.add(new Point(400.000000, 200.000000));
      pathArr_curr_L.add(new Point(700.000000, 600.000000));
      arr_pathArr.add(pathArr_curr_L);

      pathArr_curr_L = new ArrayList<>();
      pathArr_curr_L.add(new Point(250.000000, 200.000000));
      pathArr_curr_L.add(new Point(550.000000, 500.000000));
      arr_pathArr.add(pathArr_curr_L);

      return createRoadway_ForTestSetup(arr_pathArr);

    }

    //_________

    //___________
    //__________________________________________________________________

    @Test
    public void multi_roadway_intersect_X_ClosestPoint_alg__complexLv2() {
      //______________________________________

      //____

      LinkedHashMap<RoadwaySolidRoad, ArrayList<Point>> mpp__roadway_vs_pathArr_point = setup__multi_roadway_intersect__complexLvX();

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

      //__________________________________________________________________________
      //______________________________________________________
      TestUtil.assertEquals_NoStop(30, gp_AllRoadwayCross_assertCheck.size());
      TestUtil.assertEquals_NoStop(new HashSet<>(Arrays.asList(
                                                               new Point(700.000000, 491.176471),
                                                               new Point(500.000000, 482.352941),
                                                               new Point(700.000000, 200.000000),
                                                               new Point(742.857143, 200.000000),
                                                               new Point(450.000000, 400.000000),
                                                               new Point(780.199157, 494.714669),
                                                               new Point(500.000000, 200.000000),
                                                               new Point(633.333333, 200.000000),
                                                               new Point(335.714286, 285.714286),
                                                               new Point(533.846154, 483.846154),
                                                               new Point(834.482759, 265.517241),
                                                               new Point(200.000000, 200.000000),
                                                               new Point(231.034483, 200.000000),
                                                               new Point(550.000000, 400.000000),
                                                               new Point(284.251968, 354.330709),
                                                               new Point(325.000000, 300.000000),
                                                               new Point(247.368421, 247.368421),
                                                               new Point(500.000000, 333.333333),
                                                               new Point(400.000000, 200.000000),
                                                               new Point(350.000000, 300.000000),
                                                               new Point(728.571429, 300.000000),
                                                               new Point(616.666667, 300.000000),
                                                               new Point(475.000000, 300.000000),
                                                               new Point(500.000000, 450.000000),
                                                               new Point(701.252610, 491.231733),
                                                               new Point(250.000000, 200.000000),
                                                               new Point(500.000000, 400.000000),
                                                               new Point(700.000000, 300.000000),
                                                               new Point(500.000000, 300.000000),
                                                               new Point(615.589354, 487.452472))),
                                   gp_AllRoadwayCross_assertCheck);

      //____

      //___________________________________
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

      TestUtil.assertEquals_NoStop(51, gp_AllPoint.size());
      TestUtil.assertEquals_NoStop(74, gp_AllSegment.size());

      //____

      Point point_Self = new Point(646.000000, 487.000000);
      Point point_Target = new Point(260.000000, 200.000000);
      Triple<LinkedList<RoadwayDirLinkerComponent>, Pair<RoadwayNormalSegment, Point>, Pair<RoadwayNormalSegment, Point>> result_findPath;
      try {
        result_findPath = windowSession.pathFinder.findPath(point_Self, point_Target, false, FindPathAlgorithm.ClosestPoint);
      } catch (PointIsNotNearAnyRoadwayException e1) {
        throw new Error(e1);
      }
      //______________________________________________________________________
      //_______________________________________________________________________
      @Main LinkedList<RoadwayDirLinkerComponent> pathArr_dirLinker_assertCheck = result_findPath.getLeft();
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

      //_____
      //___________________________________________________________________
      //__________________________________________________________________________________________________________
      TestUtil.assertIterableEquals_NoStop(new ArrayList<Point>(Arrays.asList(
                                                                              new Point(645.92100149214830000000, 488.79063288706527000000), //_____________________________________________
                                                                              new Point(615.58935400000000000000, 487.45247200000000000000),
                                                                              new Point(550.00000000000000000000, 400.00000000000000000000),
                                                                              new Point(500.00000000000000000000, 333.33333300000000000000),
                                                                              new Point(475.00000000000000000000, 300.00000000000000000000),
                                                                              new Point(400.00000000000000000000, 200.00000000000000000000),
                                                                              new Point(260.00000000000000000000, 200.00000000000000000000))),
                                           pathArr_point_Go_assertCheck);

      //____________________
    }

  }

  //_____________

  private LinkedHashMap<RoadwaySolidRoad, ArrayList<Point>> createRoadway_ForTestSetup(ArrayList<ArrayList<Point>> arr_pathArr) {
    LinkedHashMap<RoadwaySolidRoad, ArrayList<Point>> mpp__roadway_vs_pathArr_point = new LinkedHashMap<>();
    String nameRoadway_prepend = "Ro";
    int i = 0;

    for (ArrayList<Point> pathArr_curr : arr_pathArr) {
      i++;
      RoadwaySolidRoad roadway_curr = mapBuilder_corr.createRoadway(nameRoadway_prepend + i, pathArr_curr);
      mpp__roadway_vs_pathArr_point.put(roadway_curr, pathArr_curr);
      //______________________________________________________
      Platform.runLater(() -> { //___________________________________________________________________
        Path path = JavafxUtil.convert_arrPath2pathJfx(pathArr_curr);
        panel_SemanticRoot.getChildren().add(path);
      });

    }

    return mpp__roadway_vs_pathArr_point;
  }

  //_____________

  @Nested
  class Test__findAllCrossPoint__connectRoadwayAtCrossPointConnection__multi_intersect_on_same_segment {

    //_________________________________________________________________________________________________
    //_______________
    //___________________________
    //________________________________________________________
    //_______________________________________________________
    //____________________________________________________________
    //_____

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

    public LinkedHashMap<RoadwaySolidRoad, ArrayList<Point>> setup__2_roadway_X_multi_intersect_on_same_segment() {
      ArrayList<Point> pathArr_curr_L;
      ArrayList<ArrayList<Point>> arr_pathArr = new ArrayList<>();

      pathArr_curr_L = new ArrayList<>();
      pathArr_curr_L.add(new Point(200.000000, 200.000000));
      pathArr_curr_L.add(new Point(300.000000, 300.000000));
      pathArr_curr_L.add(new Point(800.000000, 300.000000));
      pathArr_curr_L.add(new Point(900.000000, 200.000000));
      arr_pathArr.add(pathArr_curr_L);

      pathArr_curr_L = new ArrayList<>();
      pathArr_curr_L.add(new Point(500.000000, 100.000000));
      pathArr_curr_L.add(new Point(500.000000, 400.000000));
      pathArr_curr_L.add(new Point(600.000000, 400.000000));
      pathArr_curr_L.add(new Point(650.000000, 100.000000));
      arr_pathArr.add(pathArr_curr_L);

      return createRoadway_ForTestSetup(arr_pathArr);
    }

    public LinkedHashMap<RoadwaySolidRoad, ArrayList<Point>> setup__multi_roadway_X_multi_intersect_on_same_segment__complexLv2() {
      ArrayList<Point> pathArr_curr_L;
      ArrayList<ArrayList<Point>> arr_pathArr = new ArrayList<>();

      pathArr_curr_L = new ArrayList<>();
      pathArr_curr_L.add(new Point(200.000000, 200.000000));
      pathArr_curr_L.add(new Point(300.000000, 300.000000));
      pathArr_curr_L.add(new Point(800.000000, 300.000000));
      pathArr_curr_L.add(new Point(900.000000, 200.000000));
      arr_pathArr.add(pathArr_curr_L);

      pathArr_curr_L = new ArrayList<>();
      pathArr_curr_L.add(new Point(500.000000, 100.000000));
      pathArr_curr_L.add(new Point(500.000000, 400.000000));
      pathArr_curr_L.add(new Point(600.000000, 400.000000));
      pathArr_curr_L.add(new Point(650.000000, 100.000000));
      pathArr_curr_L.add(new Point(700.000000, 100.000000));
      pathArr_curr_L.add(new Point(700.000000, 500.000000));
      pathArr_curr_L.add(new Point(750.000000, 150.000000));
      arr_pathArr.add(pathArr_curr_L);

      return createRoadway_ForTestSetup(arr_pathArr);
    }

    public LinkedHashMap<RoadwaySolidRoad, ArrayList<Point>> setup__multi_roadway_X_multi_intersect_on_same_segment__complexLv3() {
      ArrayList<Point> pathArr_curr_L;
      ArrayList<ArrayList<Point>> arr_pathArr = new ArrayList<>();

      pathArr_curr_L = new ArrayList<>();
      pathArr_curr_L.add(new Point(200.000000, 200.000000));
      pathArr_curr_L.add(new Point(300.000000, 300.000000));
      pathArr_curr_L.add(new Point(800.000000, 300.000000));
      pathArr_curr_L.add(new Point(900.000000, 200.000000));
      arr_pathArr.add(pathArr_curr_L);

      pathArr_curr_L = new ArrayList<>();
      pathArr_curr_L.add(new Point(500.000000, 100.000000));
      pathArr_curr_L.add(new Point(500.000000, 400.000000));
      pathArr_curr_L.add(new Point(600.000000, 400.000000));
      pathArr_curr_L.add(new Point(650.000000, 100.000000));
      pathArr_curr_L.add(new Point(700.000000, 100.000000));
      pathArr_curr_L.add(new Point(700.000000, 500.000000));
      pathArr_curr_L.add(new Point(750.000000, 150.000000));
      arr_pathArr.add(pathArr_curr_L);

      pathArr_curr_L = new ArrayList<>();
      pathArr_curr_L.add(new Point(100.000000, 600.000000));
      pathArr_curr_L.add(new Point(400.000000, 200.000000));
      pathArr_curr_L.add(new Point(700.000000, 600.000000));
      arr_pathArr.add(pathArr_curr_L);

      pathArr_curr_L = new ArrayList<>();
      pathArr_curr_L.add(new Point(250.000000, 200.000000));
      pathArr_curr_L.add(new Point(550.000000, 500.000000));
      arr_pathArr.add(pathArr_curr_L);

      return createRoadway_ForTestSetup(arr_pathArr);
    }

    //_____________

    @Test
    public void simpleCase() {
      LinkedHashMap<RoadwaySolidRoad, ArrayList<Point>> mpp__roadway_vs_pathArr_point = setup__3_roadway__simple();

      //
      ArrayList<Point> arr_AllRoadwayCross_assertCheck = new ArrayList<>();

      //
      LinkedList<LinkedList<Entry<RoadwaySolidRoad, ArrayList<Point>>>> comb = MathUtil.get_Combination(mpp__roadway_vs_pathArr_point.entrySet(), 2);
      for (LinkedList<Entry<RoadwaySolidRoad, ArrayList<Point>>> arr_Group_curr : comb) {
        //_______________________________________________________________________________________________________
        //_________________________________________________________________________________
        //_________________________________________________________
        //_________

        if (arr_Group_curr.size() != 2) { throw new Error("Comb specified 2 per group, so should be 2"); }

        RoadwaySolidRoad roadway_prev = arr_Group_curr.get(0).getKey();
        RoadwaySolidRoad roadway_curr = arr_Group_curr.get(1).getKey();

        List<RoadwayCrossPointConnectionDto> arr_roadwayCrossPointConnection = mapBuilder_corr.findAllCrossPoint(roadway_prev, roadway_curr);
        if (arr_roadwayCrossPointConnection.size() != 1) { throw new Error(); }
        arr_AllRoadwayCross_assertCheck.add(arr_roadwayCrossPointConnection.get(0).getPointLocation());
        JavafxUtil.javafx_platform_runLater_sync(() -> {
          JavafxUtil.visualize_Point(arr_roadwayCrossPointConnection.stream().map(t -> t.getPointLocation()).toList(), panel_SemanticRoot, "Cs" + roadway_prev.getIdBsi() + roadway_curr.getIdBsi() + "-", true, false, 0, 0);
        });
        mapBuilder_corr.connectRoadwayAtCrossPointConnection(arr_roadwayCrossPointConnection);
      }

      //
      //______________________________________________________
      Assertions.assertEquals(new ArrayList<>(Arrays.asList(
                                                            new Point(780.199157, 494.714669),
                                                            new Point(500.000000, 482.352941),
                                                            new Point(231.034483, 200.000000))),
                              arr_AllRoadwayCross_assertCheck);

      //_________________________
    }

    @Test
    public void find_X_connect_X_find_2nd_time() {
      LinkedHashMap<RoadwaySolidRoad, ArrayList<Point>> mpp__roadway_vs_pathArr_point = setup__3_roadway__simple();

      //
      LinkedList<LinkedList<Entry<RoadwaySolidRoad, ArrayList<Point>>>> comb = MathUtil.get_Combination(mpp__roadway_vs_pathArr_point.entrySet(), 2);

      //_____
      for (LinkedList<Entry<RoadwaySolidRoad, ArrayList<Point>>> arr_Group_curr : comb) {
        if (arr_Group_curr.size() != 2) { throw new Error("Comb specified 2 per group, so should be 2"); }

        RoadwaySolidRoad roadway_prev = arr_Group_curr.get(0).getKey();
        RoadwaySolidRoad roadway_curr = arr_Group_curr.get(1).getKey();

        //__
        List<RoadwayCrossPointConnectionDto> arr_roadwayCrossPointConnection = mapBuilder_corr.findAllCrossPoint(roadway_prev, roadway_curr);
        Assertions.assertEquals(1, arr_roadwayCrossPointConnection.size());
        Assertions.assertEquals(new Point(780.199157, 494.714669), arr_roadwayCrossPointConnection.get(0).getPointLocation());
        Assertions.assertEquals(null, arr_roadwayCrossPointConnection.get(0).getRoadwayCrossPoint());
        List<RoadwayCrossPointConnectionDto> arr_roadwayCrossPointConnectionDto_Existing = mapBuilder_corr.connectRoadwayAtCrossPointConnection(arr_roadwayCrossPointConnection);
        Assertions.assertEquals(0, arr_roadwayCrossPointConnectionDto_Existing.size());

        //_
        List<RoadwayCrossPointConnectionDto> arr_roadwayCrossPointConnection_02 = mapBuilder_corr.findAllCrossPoint(roadway_prev, roadway_curr);
        Assertions.assertEquals(1, arr_roadwayCrossPointConnection_02.size());
        //____________________________________________________________________
        Assertions.assertEquals(null, arr_roadwayCrossPointConnection_02.get(0).getPointLocation());
        Assertions.assertEquals(new Point(780.199157, 494.714669), arr_roadwayCrossPointConnection_02.get(0).getRoadwayCrossPoint().getPointLocation());
        //______________________________________________________________
        //_______________________________________________________________
        //__________________________________________________________________________________________________________________________
        //___________
        List<RoadwayCrossPointConnectionDto> arr_roadwayCrossPointConnectionDto_Existing_02 = mapBuilder_corr.connectRoadwayAtCrossPointConnection(arr_roadwayCrossPointConnection_02);
        Assertions.assertEquals(1, arr_roadwayCrossPointConnectionDto_Existing_02.size());

        break;
        //____________________
      }

      //_________________________________________________

      //______________________
      int i = 0;
      ArrayList<Point> arr_AllRoadwayCross_assertCheck = new ArrayList<>();
      for (LinkedList<Entry<RoadwaySolidRoad, ArrayList<Point>>> arr_Group_curr : comb) {
        i++;
        if (arr_Group_curr.size() != 2) { throw new Error("Comb specified 2 per group, so should be 2"); }

        RoadwaySolidRoad roadway_prev = arr_Group_curr.get(0).getKey();
        RoadwaySolidRoad roadway_curr = arr_Group_curr.get(1).getKey();

        List<RoadwayCrossPointConnectionDto> arr_roadwayCrossPointConnection = mapBuilder_corr.findAllCrossPoint(roadway_prev, roadway_curr);
        Assertions.assertEquals(1, arr_roadwayCrossPointConnection.size());
        arr_AllRoadwayCross_assertCheck.add(arr_roadwayCrossPointConnection.get(0).getPointLocationEventually());
        //____________________________________________________________
        JavafxUtil.javafx_platform_runLater_sync(() -> {
          //___________________
          JavafxUtil.visualize_Point(arr_roadwayCrossPointConnection.stream().map(t -> t.getPointLocationEventually()).toList(), panel_SemanticRoot, "Cs" + roadway_prev.getIdBsi() + roadway_curr.getIdBsi() + "-", true, false, 0, 0);
        });
        List<RoadwayCrossPointConnectionDto> arr_roadwayCrossPointConnectionDto_Existing = mapBuilder_corr.connectRoadwayAtCrossPointConnection(arr_roadwayCrossPointConnection);

        if (i == 1) {
          Assertions.assertSame(arr_roadwayCrossPointConnection.get(0), arr_roadwayCrossPointConnectionDto_Existing.get(0));
          Assertions.assertEquals(1, arr_roadwayCrossPointConnectionDto_Existing.size());
        }
        else {
          Assertions.assertEquals(0, arr_roadwayCrossPointConnectionDto_Existing.size());
        }

      }

      //
      Assertions.assertEquals(new ArrayList<>(Arrays.asList(
                                                            new Point(780.199157, 494.714669),
                                                            new Point(500.000000, 482.352941),
                                                            new Point(231.034483, 200.000000))),
                              arr_AllRoadwayCross_assertCheck);

      //_______________________________________________
      //__________________________________________________________________________________________________________
      //_________________________________________________________________________________________________
      //______________________________________________________________
      //________________

      ArrayList<Point> arr_AllPoint_withDirRedundant = new ArrayList<>();
      HashSet<RoadwayPoint> gp_AllPoint = new HashSet<>();
      HashSet<RoadwaySegment> gp_AllSegment = new HashSet<>();
      //_________________________________
      mapBuilder_corr.getMapFile().getGpRoadwaySolidRoad().forEach(e -> {
        e.getArrRoadwayDirLinkerComponent().forEach(f -> {
          arr_AllPoint_withDirRedundant.add(f.getRoadwayPoint().getPointLocation());
          gp_AllPoint.add(f.getRoadwayPoint());
          gp_AllSegment.add(f.getRoadwaySegment());
          //__________________
        });
      });
      gp_AllSegment.remove(RoadwaySegment.roadwaySegmentPseudoBegin);
      JavafxUtil.javafx_platform_runLater_sync(() -> {
        JavafxUtil.visualize_Point(arr_AllPoint_withDirRedundant, panel_SemanticRoot, "P-", true, false, 20, 20);
      });

      //______________________________________________________________
      //______________________
      Assertions.assertEquals(16, arr_AllPoint_withDirRedundant.size());
      Assertions.assertEquals(13, gp_AllPoint.size());
      Assertions.assertEquals(13, gp_AllSegment.size());

      //___________________

    }

    @Test
    public void find_X_multi_intersect_same_segment() {
      //____

      LinkedHashMap<RoadwaySolidRoad, ArrayList<Point>> mpp__roadway_vs_pathArr_point = setup__2_roadway_X_multi_intersect_on_same_segment();

      //
      ArrayList<Point> arr_AllRoadwayCross_assertCheck = new ArrayList<>();

      //
      LinkedList<LinkedList<Entry<RoadwaySolidRoad, ArrayList<Point>>>> comb = MathUtil.get_Combination(mpp__roadway_vs_pathArr_point.entrySet(), 2);
      for (LinkedList<Entry<RoadwaySolidRoad, ArrayList<Point>>> arr_Group_curr : comb) {
        if (arr_Group_curr.size() != 2) { throw new Error("Comb specified 2 per group, so should be 2"); }

        RoadwaySolidRoad roadway_prev = arr_Group_curr.get(0).getKey();
        RoadwaySolidRoad roadway_curr = arr_Group_curr.get(1).getKey();

        List<RoadwayCrossPointConnectionDto> arr_roadwayCrossPointConnection = mapBuilder_corr.findAllCrossPoint(roadway_prev, roadway_curr);
        Assertions.assertEquals(2, arr_roadwayCrossPointConnection.size());
        arr_AllRoadwayCross_assertCheck.add(arr_roadwayCrossPointConnection.get(0).getPointLocation());
        arr_AllRoadwayCross_assertCheck.add(arr_roadwayCrossPointConnection.get(1).getPointLocation());
        JavafxUtil.javafx_platform_runLater_sync(() -> {
          JavafxUtil.visualize_Point(arr_roadwayCrossPointConnection.stream().map(t -> t.getPointLocation()).toList(), panel_SemanticRoot, "Cs" + roadway_prev.getIdBsi() + roadway_curr.getIdBsi() + "-", true, false, 0, 0);
        });
        mapBuilder_corr.connectRoadwayAtCrossPointConnection(arr_roadwayCrossPointConnection);
      }

      //
      //__________________________________________________________

      Assertions.assertEquals(new ArrayList<>(Arrays.asList(
                                                            new Point(500.000000, 300.000000),
                                                            new Point(616.666667, 300.000000))),
                              arr_AllRoadwayCross_assertCheck);

      //____

      HashSet<RoadwayPoint> gp_AllPoint = new HashSet<>();
      HashSet<RoadwaySegment> gp_AllSegment = new HashSet<>();
      mapBuilder_corr.getMapFile().getGpRoadwaySolidRoad().forEach(e -> {
        e.getArrRoadwayDirLinkerComponent().forEach(f -> {
          gp_AllPoint.add(f.getRoadwayPoint());
          gp_AllSegment.add(f.getRoadwaySegment());
        });
      });
      //________________________________________________________________________________
      gp_AllSegment.remove(RoadwaySegment.roadwaySegmentPseudoBegin);
      JavafxUtil.javafx_platform_runLater_sync(() -> {
        JavafxUtil.visualize_Point(gp_AllPoint.stream().map(t -> t.getPointLocation()).toList(), panel_SemanticRoot, "P-", true, false, 10, 10);
      });

      //_________________________

      Assertions.assertEquals(10, gp_AllPoint.size());
      Assertions.assertEquals(10, gp_AllSegment.size());

    }

    @Test
    public void find_X_multi_intersect_same_segment_complexLv2() {
      //____

      LinkedHashMap<RoadwaySolidRoad, ArrayList<Point>> mpp__roadway_vs_pathArr_point = setup__multi_roadway_X_multi_intersect_on_same_segment__complexLv2();

      //
      ArrayList<Point> arr_AllRoadwayCross_assertCheck = new ArrayList<>();

      //
      LinkedList<LinkedList<Entry<RoadwaySolidRoad, ArrayList<Point>>>> comb = MathUtil.get_Combination(mpp__roadway_vs_pathArr_point.entrySet(), 2);
      if (comb.size() != 1) { throw new Error("There are only 2 Road ..."); }
      for (LinkedList<Entry<RoadwaySolidRoad, ArrayList<Point>>> arr_Group_curr : comb) {
        if (arr_Group_curr.size() != 2) { throw new Error("Comb specified 2 per group, so should be 2"); }

        RoadwaySolidRoad roadway_prev = arr_Group_curr.get(0).getKey();
        RoadwaySolidRoad roadway_curr = arr_Group_curr.get(1).getKey();

        List<RoadwayCrossPointConnectionDto> arr_roadwayCrossPointConnection = mapBuilder_corr.findAllCrossPoint(roadway_prev, roadway_curr);
        TestUtil.assertEquals_NoStop(4, arr_roadwayCrossPointConnection.size());
        arr_AllRoadwayCross_assertCheck.add(arr_roadwayCrossPointConnection.get(0).getPointLocation());
        arr_AllRoadwayCross_assertCheck.add(arr_roadwayCrossPointConnection.get(1).getPointLocation());
        arr_AllRoadwayCross_assertCheck.add(arr_roadwayCrossPointConnection.get(2).getPointLocation());
        arr_AllRoadwayCross_assertCheck.add(arr_roadwayCrossPointConnection.get(3).getPointLocation());
        JavafxUtil.javafx_platform_runLater_sync(() -> {
          JavafxUtil.visualize_Point(arr_roadwayCrossPointConnection.stream().map(t -> t.getPointLocation()).toList(), panel_SemanticRoot, "Cs" + roadway_prev.getIdBsi() + roadway_curr.getIdBsi() + "-", true, false, 0, 0);
        });
        mapBuilder_corr.connectRoadwayAtCrossPointConnection(arr_roadwayCrossPointConnection);
      }

      //
      System.out.println(arr_AllRoadwayCross_assertCheck);

      //_________________________________________
      TestUtil.assertEquals_NoStop(new ArrayList<>(Arrays.asList(
                                                                 new Point(500.000000, 300.000000),
                                                                 new Point(616.666667, 300.000000),
                                                                 new Point(700.000000, 300.000000),
                                                                 new Point(728.571429, 300.000000))),
                                   arr_AllRoadwayCross_assertCheck);

      //____

      HashSet<RoadwayPoint> gp_AllPoint = new HashSet<>();
      HashSet<RoadwaySegment> gp_AllSegment = new HashSet<>();
      mapBuilder_corr.getMapFile().getGpRoadwaySolidRoad().forEach(e -> {
        e.getArrRoadwayDirLinkerComponent().forEach(f -> {
          gp_AllPoint.add(f.getRoadwayPoint());
          gp_AllSegment.add(f.getRoadwaySegment());
        });
      });
      //________________________________________________________________________________
      gp_AllSegment.remove(RoadwaySegment.roadwaySegmentPseudoBegin);
      JavafxUtil.javafx_platform_runLater_sync(() -> {
        JavafxUtil.visualize_Point(gp_AllPoint.stream().map(t -> t.getPointLocation()).toList(), panel_SemanticRoot, "P-", true, false, 10, 10);
      });

      TestUtil.assertEquals_NoStop(15, gp_AllPoint.size());
      TestUtil.assertEquals_NoStop(17, gp_AllSegment.size());

      //_________________________
    }

    @Test
    public void find_X_multi_intersect_same_segment_complexLv3() {
      //____

      LinkedHashMap<RoadwaySolidRoad, ArrayList<Point>> mpp__roadway_vs_pathArr_point = setup__multi_roadway_X_multi_intersect_on_same_segment__complexLv3();

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
        JavafxUtil.javafx_platform_runLater_sync(() -> {
          JavafxUtil.visualize_Point(arr_roadwayCrossPointConnection.stream().map(t -> t.getPointLocation()).toList(), panel_SemanticRoot, "Cs" + roadway_prev.getIdBsi() + roadway_curr.getIdBsi() + "-", true, false, 0, 0);
        });
        mapBuilder_corr.connectRoadwayAtCrossPointConnection(arr_roadwayCrossPointConnection);
      }

      //
      //_________________________________________________________

      //_________________________________________
      TestUtil.assertEquals_NoStop(new HashSet<>(Arrays.asList(
                                                               new Point(616.666667, 300.000000),
                                                               new Point(325.000000, 300.000000),
                                                               new Point(500.000000, 333.333333),
                                                               new Point(335.714286, 285.714286),
                                                               new Point(728.571429, 300.000000),
                                                               new Point(475.000000, 300.000000),
                                                               new Point(700.000000, 300.000000),
                                                               new Point(350.000000, 300.000000),
                                                               new Point(500.000000, 300.000000),
                                                               new Point(550.000000, 400.000000))),
                                   gp_AllRoadwayCross_assertCheck);

      //____

      HashSet<RoadwayPoint> gp_AllPoint = new HashSet<>();
      HashSet<RoadwaySegment> gp_AllSegment = new HashSet<>();
      mapBuilder_corr.getMapFile().getGpRoadwaySolidRoad().forEach(e -> {
        e.getArrRoadwayDirLinkerComponent().forEach(f -> {
          gp_AllPoint.add(f.getRoadwayPoint());
          gp_AllSegment.add(f.getRoadwaySegment());
        });
      });
      //________________________________________________________________________________
      gp_AllSegment.remove(RoadwaySegment.roadwaySegmentPseudoBegin);
      //______________________________________________________
      //________________________________________________________________________________________________________________________________________________
      //_________

      TestUtil.assertEquals_NoStop(26, gp_AllPoint.size());
      TestUtil.assertEquals_NoStop(32, gp_AllSegment.size());

      //_________________________
    }

  }

  //_____________

  @Nested
  class Test__connectRoadwayAtCrossPointConnection__removeRoadwayPointFromRoadway__T_shape_split_3_segment {

    public LinkedHashMap<RoadwaySolidRoad, ArrayList<Point>> setup__T_shape_connect_split_to_3_segment() {
      ArrayList<Point> pathArr_curr_L;
      ArrayList<ArrayList<Point>> arr_pathArr = new ArrayList<>();

      pathArr_curr_L = new ArrayList<>();
      pathArr_curr_L.add(new Point(200.000000, 200.000000));
      pathArr_curr_L.add(new Point(900.000000, 200.000000));
      arr_pathArr.add(pathArr_curr_L);

      pathArr_curr_L = new ArrayList<>();
      pathArr_curr_L.add(new Point(500.000000, 200.000000));
      pathArr_curr_L.add(new Point(500.000000, 600.000000));
      arr_pathArr.add(pathArr_curr_L);

      return createRoadway_ForTestSetup(arr_pathArr);
    }

    public LinkedHashMap<RoadwaySolidRoad, ArrayList<Point>> setup__T_Shape_swap_end() {
      ArrayList<Point> pathArr_curr_L;
      ArrayList<ArrayList<Point>> arr_pathArr = new ArrayList<>();

      pathArr_curr_L = new ArrayList<>();
      pathArr_curr_L.add(new Point(200.000000, 200.000000));
      pathArr_curr_L.add(new Point(900.000000, 200.000000));
      arr_pathArr.add(pathArr_curr_L);

      pathArr_curr_L = new ArrayList<>();
      pathArr_curr_L.add(new Point(500.000000, 600.000000));
      pathArr_curr_L.add(new Point(500.000000, 200.000000));
      arr_pathArr.add(pathArr_curr_L);

      return createRoadway_ForTestSetup(arr_pathArr);
    }

    public LinkedHashMap<RoadwaySolidRoad, ArrayList<Point>> setup__intersect_V_shape() {
      ArrayList<Point> pathArr_curr_L;
      ArrayList<ArrayList<Point>> arr_pathArr = new ArrayList<>();

      pathArr_curr_L = new ArrayList<>();
      pathArr_curr_L.add(new Point(200.000000, 200.000000));
      pathArr_curr_L.add(new Point(900.000000, 200.000000));
      arr_pathArr.add(pathArr_curr_L);

      pathArr_curr_L = new ArrayList<>();
      pathArr_curr_L.add(new Point(500.000000, 600.000000));
      pathArr_curr_L.add(new Point(500.000000, 200.000000));
      arr_pathArr.add(pathArr_curr_L);

      pathArr_curr_L = new ArrayList<>();
      pathArr_curr_L.add(new Point(310.000000, 600.000000));
      pathArr_curr_L.add(new Point(310.000000, 100.000000));
      arr_pathArr.add(pathArr_curr_L);

      pathArr_curr_L = new ArrayList<>();
      pathArr_curr_L.add(new Point(700.000000, 600.000000));
      pathArr_curr_L.add(new Point(700.000000, 100.000000));
      arr_pathArr.add(pathArr_curr_L);

      pathArr_curr_L = new ArrayList<>();
      pathArr_curr_L.add(new Point(300.000000, 600.000000));
      pathArr_curr_L.add(new Point(350.000000, 200.000000));
      pathArr_curr_L.add(new Point(800.000000, 600.000000));
      arr_pathArr.add(pathArr_curr_L);

      return createRoadway_ForTestSetup(arr_pathArr);
    }

    public LinkedHashMap<RoadwaySolidRoad, ArrayList<Point>> setup__multi_T_shape() {
      ArrayList<Point> pathArr_curr_L;
      ArrayList<ArrayList<Point>> arr_pathArr = new ArrayList<>();

      pathArr_curr_L = new ArrayList<>();
      pathArr_curr_L.add(new Point(200.000000, 200.000000));
      pathArr_curr_L.add(new Point(900.000000, 200.000000));
      arr_pathArr.add(pathArr_curr_L);

      pathArr_curr_L = new ArrayList<>();
      pathArr_curr_L.add(new Point(500.000000, 200.000000));
      pathArr_curr_L.add(new Point(500.000000, 600.000000));
      arr_pathArr.add(pathArr_curr_L);

      pathArr_curr_L = new ArrayList<>();
      pathArr_curr_L.add(new Point(500.000000, 400.000000));
      pathArr_curr_L.add(new Point(800.000000, 500.000000));
      arr_pathArr.add(pathArr_curr_L);

      pathArr_curr_L = new ArrayList<>();
      pathArr_curr_L.add(new Point(800.000000, 700.000000));
      pathArr_curr_L.add(new Point(700.000000, 200.000000));
      arr_pathArr.add(pathArr_curr_L);

      return createRoadway_ForTestSetup(arr_pathArr);
    }

    public LinkedHashMap<RoadwaySolidRoad, ArrayList<Point>> setup__split_V_shape_multi_T_shape() {
      ArrayList<Point> pathArr_curr_L;
      ArrayList<ArrayList<Point>> arr_pathArr = new ArrayList<>();

      pathArr_curr_L = new ArrayList<>();
      pathArr_curr_L.add(new Point(200.000000, 200.000000));
      pathArr_curr_L.add(new Point(900.000000, 200.000000));
      arr_pathArr.add(pathArr_curr_L);

      pathArr_curr_L = new ArrayList<>();
      pathArr_curr_L.add(new Point(500.000000, 200.000000));
      pathArr_curr_L.add(new Point(500.000000, 600.000000));
      arr_pathArr.add(pathArr_curr_L);

      pathArr_curr_L = new ArrayList<>();
      pathArr_curr_L.add(new Point(500.000000, 400.000000));
      pathArr_curr_L.add(new Point(800.000000, 500.000000));
      arr_pathArr.add(pathArr_curr_L);

      pathArr_curr_L = new ArrayList<>();
      pathArr_curr_L.add(new Point(800.000000, 700.000000));
      pathArr_curr_L.add(new Point(700.000000, 200.000000));
      arr_pathArr.add(pathArr_curr_L);

      pathArr_curr_L = new ArrayList<>();
      pathArr_curr_L.add(new Point(300.000000, 100.000000));
      pathArr_curr_L.add(new Point(350.000000, 200.000000));
      pathArr_curr_L.add(new Point(400.000000, 100.000000));
      arr_pathArr.add(pathArr_curr_L);

      return createRoadway_ForTestSetup(arr_pathArr);
    }

    public LinkedHashMap<RoadwaySolidRoad, ArrayList<Point>> setup__split_V_shape_multi_T_shape__Lv2() {
      ArrayList<Point> pathArr_curr_L;
      ArrayList<ArrayList<Point>> arr_pathArr = new ArrayList<>();

      pathArr_curr_L = new ArrayList<>();
      pathArr_curr_L.add(new Point(200.000000, 200.000000));
      pathArr_curr_L.add(new Point(900.000000, 200.000000));
      arr_pathArr.add(pathArr_curr_L);

      pathArr_curr_L = new ArrayList<>();
      pathArr_curr_L.add(new Point(500.000000, 200.000000));
      pathArr_curr_L.add(new Point(500.000000, 600.000000));
      arr_pathArr.add(pathArr_curr_L);

      pathArr_curr_L = new ArrayList<>();
      pathArr_curr_L.add(new Point(500.000000, 400.000000));
      pathArr_curr_L.add(new Point(800.000000, 500.000000));
      arr_pathArr.add(pathArr_curr_L);

      pathArr_curr_L = new ArrayList<>();
      pathArr_curr_L.add(new Point(800.000000, 700.000000));
      pathArr_curr_L.add(new Point(700.000000, 200.000000));
      arr_pathArr.add(pathArr_curr_L);

      pathArr_curr_L = new ArrayList<>();
      pathArr_curr_L.add(new Point(300.000000, 600.000000));
      pathArr_curr_L.add(new Point(350.000000, 200.000000));
      pathArr_curr_L.add(new Point(800.000000, 600.000000));
      arr_pathArr.add(pathArr_curr_L);

      return createRoadway_ForTestSetup(arr_pathArr);
    }

    private void simpleCase_combine(LinkedHashMap<RoadwaySolidRoad, ArrayList<Point>> mpp__roadway_vs_pathArr_point) {

      //
      HashSet<Point> gp_AllRoadwayCross_assertCheck = new HashSet<>();

      //
      LinkedList<LinkedList<Entry<RoadwaySolidRoad, ArrayList<Point>>>> comb = MathUtil.get_Combination(mpp__roadway_vs_pathArr_point.entrySet(), 2);
      if (comb.size() != 1) { throw new Error("There are only 2 Road ..."); }
      for (LinkedList<Entry<RoadwaySolidRoad, ArrayList<Point>>> arr_Group_curr : comb) {
        if (arr_Group_curr.size() != 2) { throw new Error("Comb specified 2 per group, so should be 2"); }

        RoadwaySolidRoad roadway_prev = arr_Group_curr.get(0).getKey();
        RoadwaySolidRoad roadway_curr = arr_Group_curr.get(1).getKey();

        List<RoadwayCrossPointConnectionDto> arr_roadwayCrossPointConnection = mapBuilder_corr.findAllCrossPoint(roadway_prev, roadway_curr);
        //_______________________________________________________________________
        for (RoadwayCrossPointConnectionDto roadwayCrossPointConnectionDto_curr : arr_roadwayCrossPointConnection) {
          if (!gp_AllRoadwayCross_assertCheck.add(roadwayCrossPointConnectionDto_curr.getPointLocation())) { throw new Error(); }
        }
        mapBuilder_corr.connectRoadwayAtCrossPointConnection(arr_roadwayCrossPointConnection);
      }

      //
      //____________________________________________________
      JavafxUtil.javafx_platform_runLater_sync(() -> {
        JavafxUtil.visualize_Point(gp_AllRoadwayCross_assertCheck, panel_SemanticRoot, "Cs-", true, false, 0, 0);
      });

      TestUtil.assertEquals_NoStop(1, gp_AllRoadwayCross_assertCheck.size());
      TestUtil.assertEquals_NoStop(new HashSet<>(Arrays.asList(
                                                               new Point(500.000000, 200.000000))),
                                   gp_AllRoadwayCross_assertCheck);

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

      TestUtil.assertEquals_NoStop(4, gp_AllPoint.size());
      TestUtil.assertEquals_NoStop(3, gp_AllSegment.size());

      //___________________
    }

    @Test
    public void simpleCase() {
      //____
      LinkedHashMap<RoadwaySolidRoad, ArrayList<Point>> mpp__roadway_vs_pathArr_point = setup__T_shape_connect_split_to_3_segment();
      simpleCase_combine(mpp__roadway_vs_pathArr_point);
    }

    @Test
    public void simpleCase_T_Shape_swap_end() {
      //____
      LinkedHashMap<RoadwaySolidRoad, ArrayList<Point>> mpp__roadway_vs_pathArr_point = setup__T_Shape_swap_end();
      simpleCase_combine(mpp__roadway_vs_pathArr_point);
    }

    @Test
    public void multi_T_shape() {
      //____

      LinkedHashMap<RoadwaySolidRoad, ArrayList<Point>> mpp__roadway_vs_pathArr_point = setup__multi_T_shape();

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
        mapBuilder_corr.connectRoadwayAtCrossPointConnection(arr_roadwayCrossPointConnection);
      }

      //
      //____________________________________________________
      JavafxUtil.javafx_platform_runLater_sync(() -> {
        JavafxUtil.visualize_Point(gp_AllRoadwayCross_assertCheck, panel_SemanticRoot, "Cs-", true, false, 0, 0);
      });

      //____________________________________________________________________
      TestUtil.assertEquals_NoStop(new HashSet<>(Arrays.asList(
                                                               new Point(700.000000, 200.000000),
                                                               new Point(500.000000, 200.000000),
                                                               new Point(500.000000, 400.000000),
                                                               new Point(757.142857, 485.714286))),
                                   gp_AllRoadwayCross_assertCheck);

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

      TestUtil.assertEquals_NoStop(9, gp_AllPoint.size());
      TestUtil.assertEquals_NoStop(9, gp_AllSegment.size());

      //____________________
    }

    @Test
    public void intersect_V_shape() {
      //____

      LinkedHashMap<RoadwaySolidRoad, ArrayList<Point>> mpp__roadway_vs_pathArr_point = setup__intersect_V_shape();

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
        mapBuilder_corr.connectRoadwayAtCrossPointConnection(arr_roadwayCrossPointConnection);
      }

      //
      System.out.println(gp_AllRoadwayCross_assertCheck);
      JavafxUtil.javafx_platform_runLater_sync(() -> {
        JavafxUtil.visualize_Point(gp_AllRoadwayCross_assertCheck, panel_SemanticRoot, "Cs-", true, false, 0, 0);
      });

      //____________________________________________________________________
      TestUtil.assertEquals_NoStop(new HashSet<>(Arrays.asList(
                                                               new Point(310.000000, 200.000000),
                                                               new Point(700.000000, 200.000000),
                                                               new Point(350.000000, 200.000000),
                                                               new Point(310.000000, 520.000000),
                                                               new Point(700.000000, 511.111111),
                                                               new Point(500.000000, 200.000000),
                                                               new Point(500.000000, 333.333333))),
                                   gp_AllRoadwayCross_assertCheck);

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

      TestUtil.assertEquals_NoStop(16, gp_AllPoint.size());
      TestUtil.assertEquals_NoStop(18, gp_AllSegment.size());

      //____________________
    }

    @Test
    public void dup__split_V_shape_multi_T_shape() {
      //____

      LinkedHashMap<RoadwaySolidRoad, ArrayList<Point>> mpp__roadway_vs_pathArr_point = setup__split_V_shape_multi_T_shape();

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
        mapBuilder_corr.connectRoadwayAtCrossPointConnection(arr_roadwayCrossPointConnection);
      }

      //
      //____________________________________________________
      JavafxUtil.javafx_platform_runLater_sync(() -> {
        JavafxUtil.visualize_Point(gp_AllRoadwayCross_assertCheck, panel_SemanticRoot, "Cs-", true, false, 0, 0);
      });

      //____________________________________________________________________
      TestUtil.assertEquals_NoStop(new HashSet<>(Arrays.asList(
                                                               new Point(700.000000, 200.000000),
                                                               new Point(350.000000, 200.000000),
                                                               new Point(500.000000, 200.000000),
                                                               new Point(500.000000, 400.000000),
                                                               new Point(757.142857, 485.714286))),
                                   gp_AllRoadwayCross_assertCheck);

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

      //______________________________________
      TestUtil.assertEquals_NoStop(12, gp_AllPoint.size());
      TestUtil.assertEquals_NoStop(12, gp_AllSegment.size());

      //____________________
    }

    @Test
    public void split_V_shape_multi_T_shape__Lv2() {
      //____

      LinkedHashMap<RoadwaySolidRoad, ArrayList<Point>> mpp__roadway_vs_pathArr_point = setup__split_V_shape_multi_T_shape__Lv2();

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
        mapBuilder_corr.connectRoadwayAtCrossPointConnection(arr_roadwayCrossPointConnection);
      }

      //
      System.out.println(gp_AllRoadwayCross_assertCheck);
      JavafxUtil.javafx_platform_runLater_sync(() -> {
        JavafxUtil.visualize_Point(gp_AllRoadwayCross_assertCheck, panel_SemanticRoot, "Cs-", true, false, 0, 0);
      });

      //____________________________________________________________________
      TestUtil.assertEquals_NoStop(new HashSet<>(Arrays.asList(
                                                               new Point(775.675676, 578.378378),
                                                               new Point(757.142857, 485.714286),
                                                               new Point(700.000000, 200.000000),
                                                               new Point(350.000000, 200.000000),
                                                               new Point(500.000000, 200.000000),
                                                               new Point(500.000000, 400.000000),
                                                               new Point(500.000000, 333.333333),
                                                               new Point(620.000001, 440.000000))),
                                   gp_AllRoadwayCross_assertCheck);

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

      TestUtil.assertEquals_NoStop(15, gp_AllPoint.size());
      TestUtil.assertEquals_NoStop(18, gp_AllSegment.size());

      //____________________
    }

    //_____________
    //_____________

    private static int seqNumDebugRerun = 0;

    public void rerun__base_multi_T_shape() {
      System.out.println(">> rerun__base_multi_T_shape()");
      //____
      LinkedHashMap<RoadwaySolidRoad, ArrayList<Point>> mpp__roadway_vs_pathArr_point = setup__multi_T_shape();

      //
      LinkedList<LinkedList<Entry<RoadwaySolidRoad, ArrayList<Point>>>> comb = MathUtil.get_Combination(mpp__roadway_vs_pathArr_point.entrySet(), 2);
      for (LinkedList<Entry<RoadwaySolidRoad, ArrayList<Point>>> arr_Group_curr : comb) {
        RoadwaySolidRoad roadway_prev = arr_Group_curr.get(0).getKey();
        RoadwaySolidRoad roadway_curr = arr_Group_curr.get(1).getKey();

        List<RoadwayCrossPointConnectionDto> arr_roadwayCrossPointConnection = mapBuilder_corr.findAllCrossPoint(roadway_prev, roadway_curr);
        mapBuilder_corr.connectRoadwayAtCrossPointConnection(arr_roadwayCrossPointConnection);
      }

      //____
      final int[] seqNumDebugRerun_L = new int[] { 0 };

      HashSet<RoadwayPoint> gp_AllPoint = new HashSet<>();
      HashSet<RoadwaySegment> gp_AllSegment = new HashSet<>();
      mapBuilder_corr.getMapFile().getGpRoadwaySolidRoad().forEach(e -> {
        e.getArrRoadwayDirLinkerComponent().forEach(f -> {
          seqNumDebugRerun_L[0] = seqNumDebugRerun_L[0] + 1;
          System.out.printf("%2d %2d %s%n", seqNumDebugRerun_L[0], ++seqNumDebugRerun, f.getRoadwayPoint());
          gp_AllPoint.add(f.getRoadwayPoint());
          gp_AllSegment.add(f.getRoadwaySegment());
        });
      });
      gp_AllSegment.remove(RoadwaySegment.roadwaySegmentPseudoBegin);

      Assertions.assertEquals(9, gp_AllPoint.size()); //________________________
      Assertions.assertEquals(9, gp_AllSegment.size());
    }

    //________________________________________________________________________________________________________________
    @BugPotential
    @Test
    public void rerun_brust_point_all_size_bug__base_multi_T_shape() {
      //_____________________________________

      //______________________________________________________________________________________________________________________
      //_______________________________________________________________________________________________________________________
      //________________________________________________________________
      //__________________________________________________________________________________________________________________________________________________

      //__________________________________

      rerun__base_multi_T_shape();

      windowSession                               = new WindowSession();
      mapBuilder_corr                             = windowSession.mapBuilder; //_____________________________________________
      windowSessionJavafx_corr                    = windowSession.windowSessionJavafx;
      windowSessionJavafx_corr.panel_SemanticRoot = panel_SemanticRoot;
      mapBuilder_corr.newMapFile();
      rerun__base_multi_T_shape();

      windowSession                               = new WindowSession();
      mapBuilder_corr                             = windowSession.mapBuilder; //_____________________________________________
      windowSessionJavafx_corr                    = windowSession.windowSessionJavafx;
      windowSessionJavafx_corr.panel_SemanticRoot = panel_SemanticRoot;
      mapBuilder_corr.newMapFile();
      rerun__base_multi_T_shape();

      windowSession                               = new WindowSession();
      mapBuilder_corr                             = windowSession.mapBuilder; //_____________________________________________
      windowSessionJavafx_corr                    = windowSession.windowSessionJavafx;
      windowSessionJavafx_corr.panel_SemanticRoot = panel_SemanticRoot;
      mapBuilder_corr.newMapFile();
      rerun__base_multi_T_shape();

      windowSession                               = new WindowSession();
      mapBuilder_corr                             = windowSession.mapBuilder; //_____________________________________________
      windowSessionJavafx_corr                    = windowSession.windowSessionJavafx;
      windowSessionJavafx_corr.panel_SemanticRoot = panel_SemanticRoot;
      mapBuilder_corr.newMapFile();
      rerun__base_multi_T_shape();

    }

    //_________

    @DuplicatedCode
    public LinkedHashMap<RoadwaySolidRoad, ArrayList<Point>> setup__multi_roadway_X_multi_intersect_on_same_segment__complexLv2() {
      ArrayList<Point> pathArr_curr_L;
      ArrayList<ArrayList<Point>> arr_pathArr = new ArrayList<>();

      pathArr_curr_L = new ArrayList<>();
      pathArr_curr_L.add(new Point(200.000000, 200.000000));
      pathArr_curr_L.add(new Point(300.000000, 300.000000));
      pathArr_curr_L.add(new Point(800.000000, 300.000000));
      pathArr_curr_L.add(new Point(900.000000, 200.000000));
      arr_pathArr.add(pathArr_curr_L);

      pathArr_curr_L = new ArrayList<>();
      pathArr_curr_L.add(new Point(500.000000, 100.000000));
      pathArr_curr_L.add(new Point(500.000000, 400.000000));
      pathArr_curr_L.add(new Point(600.000000, 400.000000));
      pathArr_curr_L.add(new Point(650.000000, 100.000000));
      pathArr_curr_L.add(new Point(700.000000, 100.000000));
      pathArr_curr_L.add(new Point(700.000000, 500.000000));
      pathArr_curr_L.add(new Point(750.000000, 150.000000));
      arr_pathArr.add(pathArr_curr_L);

      return createRoadway_ForTestSetup(arr_pathArr);
    }

    public void rerun__base_find_X_multi_intersect_same_segment_complexLv2() {
      //____
      LinkedHashMap<RoadwaySolidRoad, ArrayList<Point>> mpp__roadway_vs_pathArr_point = setup__multi_roadway_X_multi_intersect_on_same_segment__complexLv2();

      LinkedList<LinkedList<Entry<RoadwaySolidRoad, ArrayList<Point>>>> comb = MathUtil.get_Combination(mpp__roadway_vs_pathArr_point.entrySet(), 2);
      for (LinkedList<Entry<RoadwaySolidRoad, ArrayList<Point>>> arr_Group_curr : comb) {
        RoadwaySolidRoad roadway_prev = arr_Group_curr.get(0).getKey();
        RoadwaySolidRoad roadway_curr = arr_Group_curr.get(1).getKey();
        List<RoadwayCrossPointConnectionDto> arr_roadwayCrossPointConnection = mapBuilder_corr.findAllCrossPoint(roadway_prev, roadway_curr);
        mapBuilder_corr.connectRoadwayAtCrossPointConnection(arr_roadwayCrossPointConnection);
      }

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

      TestUtil.assertEquals_NoStop(15, gp_AllPoint.size()); //________________________
      TestUtil.assertEquals_NoStop(17, gp_AllSegment.size());
    }

    @Test
    public void rerun_brust_point_all_size_bug__base_find_X_multi_intersect_same_segment_complexLv2() {
      rerun__base_find_X_multi_intersect_same_segment_complexLv2();

      for (int i = 0; i < 20; i++) {
        windowSession                               = new WindowSession();
        mapBuilder_corr                             = windowSession.mapBuilder; //_____________________________________________
        windowSessionJavafx_corr                    = windowSession.windowSessionJavafx;
        windowSessionJavafx_corr.panel_SemanticRoot = panel_SemanticRoot;
        mapBuilder_corr.newMapFile();
        rerun__base_find_X_multi_intersect_same_segment_complexLv2();
      }

    }

  }

  //_____________
  //_____________
  //_____________

}
