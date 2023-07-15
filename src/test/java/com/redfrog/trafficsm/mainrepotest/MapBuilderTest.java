package com.redfrog.trafficsm.mainrepotest;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.testfx.framework.junit5.ApplicationTest;

import com.redfrog.trafficsm.TrafficSystemMockAppSpringBoot;
import com.redfrog.trafficsm.annotation.BugPotential;
import com.redfrog.trafficsm.annotation.DuplicatedCode;
import com.redfrog.trafficsm.annotation.Main;
import com.redfrog.trafficsm.annotation.Messy;
import com.redfrog.trafficsm.model.roadway.fundamental.RoadwayDirLinkerComponent;
import com.redfrog.trafficsm.model.roadway.fundamental.RoadwayPoint;
import com.redfrog.trafficsm.model.roadway.main.RoadwayCrossPoint;
import com.redfrog.trafficsm.model.roadway.main.RoadwayCrossPointConnectionDto;
import com.redfrog.trafficsm.model.roadway.main.RoadwayNormalPoint;
import com.redfrog.trafficsm.model.roadway.main.RoadwayNormalSegment;
import com.redfrog.trafficsm.model.roadway.main.RoadwaySolidRoad;
import com.redfrog.trafficsm.service.MapBuilder;
import com.redfrog.trafficsm.service.WindowSessionJavafx;
import com.redfrog.trafficsm.service.exception.RoadwayDoesNotContainThisSegmentException;
import com.redfrog.trafficsm.shape.Point;
import com.redfrog.trafficsm.test.javafxBoot.JfxAppSimpleSetup;
import com.redfrog.trafficsm.util.JavafxUtil;
import com.redfrog.trafficsm.util.TestUtil;

import javafx.application.Platform;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Path;
import javafx.stage.Stage;
import lombok.extern.log4j.Log4j2;

//_______________
@DataJpaTest
@ContextConfiguration(
                      classes = {
                          TrafficSystemMockAppSpringBoot.class, //____________________
                          //_______________________________________________
                          MapBuilder.class,
                          WindowSessionJavafx.class,
                      })
@Log4j2
//______________________
class MapBuilderTest extends ApplicationTest {

  //______________
  //__________________________________________________________________________
  //________________________________________________________________________________________
  //__________________________________
  //_____

  //_____

  //______________________________________
  //________________________________
  //
  //_____________
  //____________________________
  //________________________________________
  //_____________________________________________
  //___

  //_____

  @Autowired
  private MapBuilder mapBuilder;

  @Messy
  @Autowired
  private WindowSessionJavafx windowSessionJavafx;

  //____________________________________________

  @BeforeEach
  public void beforeEach() { mapBuilder.newMapFile(); }

  //_____

  //____________
  @PersistenceContext
  private EntityManager em;

  //_____

  //_________________________________________________________
  private AnchorPane panel_SemanticRoot;

  @Override
  public void start(Stage primaryStage) {
    panel_SemanticRoot                     = JfxAppSimpleSetup.startSetup(primaryStage);
    windowSessionJavafx.panel_SemanticRoot = panel_SemanticRoot;
  }

  //_______________________________________________
  //____________________________________________
  //________________________________________________
  //_____________________________________________________
  //_________________________________
  //______________________________________________________________

  //_______

  //_____

  //_________________
  //____________________________________________________________________________________________________
  //____________________________________
  //________________________________________________________________
  //
  //_______________________________________
  //__________________________________________________________
  //__________________________________________________________
  //__________________________________________________________
  //____________________________________
  //
  //_______________________________________
  //__________________________________________________________
  //__________________________________________________________
  //__________________________________________________________
  //____________________________________
  //
  //_______________________________________
  //__________________________________________________________
  //__________________________________________________________
  //__________________________________________________________
  //__________________________________________________________
  //____________________________________
  //
  //___________________________________________________
  //___

  @Nested
  class MapBuilder_createRoadway_Repo_MethodWholeUnitTest {

    @Test
    @Transactional
    public void simpleCase__create_1_roadway() {
      //
      ArrayList<Point> pathArr_curr_L;
      ArrayList<ArrayList<Point>> arr_pathArr = new ArrayList<>();

      pathArr_curr_L = new ArrayList<>();
      pathArr_curr_L.add(new Point(220.000000, 470.000000));
      pathArr_curr_L.add(new Point(900.000000, 500.000000));
      pathArr_curr_L.add(new Point(930.000000, 420.000000));
      arr_pathArr.add(pathArr_curr_L);

      //_
      String idRoadway = "Ro1";
      RoadwaySolidRoad roadwaySolidRoad = mapBuilder.createRoadway(idRoadway, pathArr_curr_L);

      log.debug(roadwaySolidRoad.getIdSql());
      TestUtil.assertTrue_NoStop(roadwaySolidRoad.getIdSql() != null); //__________________________

      //_____________________________________________________________________________________________________________
      TestUtil.assertTrue_NoStop(em.contains(roadwaySolidRoad));

      em.flush();
      em.clear();

      //
      RoadwaySolidRoad roadwaySolidRoad_FN = em.find(RoadwaySolidRoad.class, roadwaySolidRoad.getIdSql());

      TestUtil.assertFalse_NoStop(em.contains(roadwaySolidRoad));
      TestUtil.assertTrue_NoStop(em.contains(roadwaySolidRoad_FN));
      TestUtil.assertNotSame_NoStop(roadwaySolidRoad, roadwaySolidRoad_FN);
      TestUtil.assertEquals_NoStop(roadwaySolidRoad, roadwaySolidRoad_FN); //_____________________________________________
      TestUtil.assertEquals_NoStop(roadwaySolidRoad.getArrRoadwayDirLinkerComponent().size(), roadwaySolidRoad_FN.getArrRoadwayDirLinkerComponent().size());
      TestUtil.assertEquals_NoStop(pathArr_curr_L.size(), roadwaySolidRoad_FN.getArrRoadwayDirLinkerComponent().size());

      //
      List<RoadwayDirLinkerComponent> arrRoadwayDirLinkerComponent = roadwaySolidRoad.getArrRoadwayDirLinkerComponent();
      List<RoadwayDirLinkerComponent> arrRoadwayDirLinkerComponent_FN = roadwaySolidRoad_FN.getArrRoadwayDirLinkerComponent();

      int i = -1;
      for (RoadwayDirLinkerComponent roadwayDirLinkerComponent_curr__FN : arrRoadwayDirLinkerComponent_FN) {
        i++;
        TestUtil.assertEquals_NoStop(arrRoadwayDirLinkerComponent.get(i), roadwayDirLinkerComponent_curr__FN);
      }

    }
  }

  @Nested
  class MapBuilder_connectRoadwayAtExactSegment_Repo_MethodSubUnitTest {

    @Test
    @Transactional
    public void create_2_roadway_a_cross() {
      //
      ArrayList<Point> pathArr_curr_L;
      ArrayList<ArrayList<Point>> arr_pathArr = new ArrayList<>();

      pathArr_curr_L = new ArrayList<>();
      pathArr_curr_L.add(new Point(200.000000, 200.000000));
      pathArr_curr_L.add(new Point(600.000000, 200.000000));
      arr_pathArr.add(pathArr_curr_L);

      pathArr_curr_L = new ArrayList<>();
      pathArr_curr_L.add(new Point(400.000000, 100.000000));
      pathArr_curr_L.add(new Point(400.000000, 300.000000));
      arr_pathArr.add(pathArr_curr_L);

      for (ArrayList<Point> pathArr_curr : arr_pathArr) {
        Platform.runLater(() -> {
          Path path = JavafxUtil.convert_arrPath2pathJfx(pathArr_curr);
          panel_SemanticRoot.getChildren().add(path);
        });
      }

      //_
      String nameRoadway_prepend = "Ro";

      RoadwaySolidRoad roadway_curr;
      RoadwaySolidRoad roadway_curr_FN;
      ArrayList<RoadwaySolidRoad> arr_roadway = new ArrayList<>();
      ArrayList<RoadwaySolidRoad> arr_roadway_FN = new ArrayList<RoadwaySolidRoad>();

      //
      int i = 0;
      i++;
      roadway_curr = mapBuilder.createRoadway(nameRoadway_prepend + i, arr_pathArr.get(i - 1));
      RoadwaySolidRoad roadway_curr_01 = roadway_curr;
      arr_roadway.add(roadway_curr);
      i++;
      roadway_curr = mapBuilder.createRoadway(nameRoadway_prepend + i, arr_pathArr.get(i - 1));
      RoadwaySolidRoad roadway_curr_02 = roadway_curr;
      arr_roadway.add(roadway_curr);

      em.flush();
      em.clear();

      //
      i = 0;
      i++;
      roadway_curr_FN = em.find(RoadwaySolidRoad.class, arr_roadway.get(i - 1).getIdSql());
      RoadwaySolidRoad roadway_curr_01_FN = roadway_curr_FN;
      arr_roadway_FN.add(roadway_curr_FN);
      i++;
      roadway_curr_FN = em.find(RoadwaySolidRoad.class, arr_roadway.get(i - 1).getIdSql());
      RoadwaySolidRoad roadway_curr_02_FN = roadway_curr_FN;
      arr_roadway_FN.add(roadway_curr_FN);

      i = 0;
      for (RoadwaySolidRoad roadway_curr_D : arr_roadway) {
        i++;
        TestUtil.assertNotSame_NoStop(arr_roadway.get(i - 1), arr_roadway_FN.get(i - 1));
        TestUtil.assertEquals_NoStop(arr_roadway.get(i - 1), arr_roadway_FN.get(i - 1));
        TestUtil.assertEquals_NoStop(arr_roadway.get(i - 1).getArrRoadwayDirLinkerComponent().size(), arr_roadway_FN.get(i - 1).getArrRoadwayDirLinkerComponent().size());
        TestUtil.assertEquals_NoStop(arr_pathArr.get(i - 1).size(), arr_roadway_FN.get(i - 1).getArrRoadwayDirLinkerComponent().size());

        List<RoadwayDirLinkerComponent> arrRoadwayDirLinkerComponent = arr_roadway.get(i - 1).getArrRoadwayDirLinkerComponent();
        List<RoadwayDirLinkerComponent> arrRoadwayDirLinkerComponent_FN = arr_roadway_FN.get(i - 1).getArrRoadwayDirLinkerComponent();

        int j = -1;
        for (RoadwayDirLinkerComponent roadwayDirLinkerComponent_curr__FN : arrRoadwayDirLinkerComponent_FN) {
          j++;
          TestUtil.assertEquals_NoStop(arrRoadwayDirLinkerComponent.get(j), roadwayDirLinkerComponent_curr__FN);
        }
      }

      em.flush();
      em.clear();

      //_________
      //_________

      @Main
      @Messy //_______________________________________________________
      List<RoadwayCrossPointConnectionDto> arr_roadwayCrossPointConnection = mapBuilder.findAllCrossPoint(arr_roadway.get(0), arr_roadway.get(1));
      if (arr_roadwayCrossPointConnection.size() != 1) { throw new Error(); }
      RoadwayCrossPointConnectionDto roadwayCrossPointConnection = arr_roadwayCrossPointConnection.get(0);

      //_______________________________________________________________________________________
      //__________
      //_________________________________________________________________________________________

      try {
        connectRoadwayAtExactSegment(roadwayCrossPointConnection);
      } catch (RoadwayDoesNotContainThisSegmentException e) {
        throw new Error(e);
      }

      em.flush();
      em.clear();

      //_________
      //_________

      roadway_curr_FN = em.find(RoadwaySolidRoad.class, roadway_curr_01.getIdSql());
      RoadwaySolidRoad roadway_curr_01_FN2 = roadway_curr_FN;
      roadway_curr_FN = em.find(RoadwaySolidRoad.class, roadway_curr_02.getIdSql());
      RoadwaySolidRoad roadway_curr_02_FN2 = roadway_curr_FN;

      //
      TestUtil.assertEquals_NoStop(roadway_curr_01, roadway_curr_01_FN2);
      TestUtil.assertEquals_NoStop(roadway_curr_02, roadway_curr_02_FN2);
      TestUtil.assertEquals_NoStop(3, roadway_curr_01_FN2.getArrRoadwayDirLinkerComponent().size());
      TestUtil.assertEquals_NoStop(3, roadway_curr_02_FN2.getArrRoadwayDirLinkerComponent().size());
      TestUtil.assertEquals_NoStop(roadway_curr_01.getArrRoadwayDirLinkerComponent(), roadway_curr_01_FN2.getArrRoadwayDirLinkerComponent());
      TestUtil.assertEquals_NoStop(roadway_curr_02.getArrRoadwayDirLinkerComponent(), roadway_curr_02_FN2.getArrRoadwayDirLinkerComponent());

      //
      RoadwayDirLinkerComponent roadwayDirLinkerComponent_roadway01_sn02 = roadway_curr_01.getArrRoadwayDirLinkerComponent().get(1);
      RoadwayDirLinkerComponent roadwayDirLinkerComponent_roadway02_sn02 = roadway_curr_02.getArrRoadwayDirLinkerComponent().get(1);
      RoadwayDirLinkerComponent roadwayDirLinkerComponent_roadway01_sn02_FN2 = roadway_curr_01_FN2.getArrRoadwayDirLinkerComponent().get(1);
      RoadwayDirLinkerComponent roadwayDirLinkerComponent_roadway02_sn02_FN2 = roadway_curr_02_FN2.getArrRoadwayDirLinkerComponent().get(1);

      TestUtil.assertEquals_NoStop(roadwayDirLinkerComponent_roadway01_sn02, roadwayDirLinkerComponent_roadway01_sn02_FN2);
      TestUtil.assertEquals_NoStop(roadwayDirLinkerComponent_roadway02_sn02, roadwayDirLinkerComponent_roadway02_sn02_FN2);
      TestUtil.assertNotSame_NoStop(roadwayDirLinkerComponent_roadway01_sn02, roadwayDirLinkerComponent_roadway01_sn02_FN2);
      TestUtil.assertNotSame_NoStop(roadwayDirLinkerComponent_roadway02_sn02, roadwayDirLinkerComponent_roadway02_sn02_FN2);

      @Main //
      RoadwayPoint roadwayPoint_CrossPoint_roadway01_FN2 = roadwayDirLinkerComponent_roadway01_sn02_FN2.getRoadwayPoint();
      RoadwayPoint roadwayPoint_CrossPoint_roadway02_FN2 = roadwayDirLinkerComponent_roadway02_sn02_FN2.getRoadwayPoint();
      TestUtil.assertTrue_NoStop(roadwayPoint_CrossPoint_roadway01_FN2 instanceof RoadwayCrossPoint);
      TestUtil.assertTrue_NoStop(roadwayPoint_CrossPoint_roadway01_FN2 == roadwayPoint_CrossPoint_roadway02_FN2);
      TestUtil.assertEquals_NoStop(4, roadwayPoint_CrossPoint_roadway01_FN2.getMppRoadwayDirLinkerComponent().size());
      TestUtil.assertEquals_NoStop(new Point(400, 200), roadwayPoint_CrossPoint_roadway01_FN2.getPointLocation());

      //
      RoadwayDirLinkerComponent roadwayDirLinkerComponent_roadway01_sn01 = roadway_curr_01.getArrRoadwayDirLinkerComponent().get(0);
      RoadwayDirLinkerComponent roadwayDirLinkerComponent_roadway02_sn01 = roadway_curr_02.getArrRoadwayDirLinkerComponent().get(0);
      RoadwayDirLinkerComponent roadwayDirLinkerComponent_roadway01_sn01_FN2 = roadway_curr_01_FN2.getArrRoadwayDirLinkerComponent().get(0);
      RoadwayDirLinkerComponent roadwayDirLinkerComponent_roadway02_sn01_FN2 = roadway_curr_02_FN2.getArrRoadwayDirLinkerComponent().get(0);

      TestUtil.assertEquals_NoStop(roadwayDirLinkerComponent_roadway01_sn01, roadwayDirLinkerComponent_roadway01_sn01_FN2);
      TestUtil.assertEquals_NoStop(roadwayDirLinkerComponent_roadway02_sn01, roadwayDirLinkerComponent_roadway02_sn01_FN2);
      TestUtil.assertNotSame_NoStop(roadwayDirLinkerComponent_roadway01_sn01, roadwayDirLinkerComponent_roadway01_sn01_FN2);
      TestUtil.assertNotSame_NoStop(roadwayDirLinkerComponent_roadway02_sn01, roadwayDirLinkerComponent_roadway02_sn01_FN2);

      RoadwayPoint roadwayPoint_FirstPoint_roadway01_FN2 = roadwayDirLinkerComponent_roadway01_sn01_FN2.getRoadwayPoint();
      RoadwayPoint roadwayPoint_FirstPoint_roadway02_FN2 = roadwayDirLinkerComponent_roadway02_sn01_FN2.getRoadwayPoint();
      TestUtil.assertTrue_NoStop(roadwayPoint_FirstPoint_roadway01_FN2 instanceof RoadwayNormalPoint);
      TestUtil.assertTrue_NoStop(roadwayPoint_FirstPoint_roadway02_FN2 instanceof RoadwayNormalPoint);
      TestUtil.assertEquals_NoStop(1, roadwayPoint_FirstPoint_roadway01_FN2.getMppRoadwayDirLinkerComponent().size());
      TestUtil.assertEquals_NoStop(1, roadwayPoint_FirstPoint_roadway02_FN2.getMppRoadwayDirLinkerComponent().size());
      TestUtil.assertEquals_NoStop(roadwayPoint_CrossPoint_roadway01_FN2, roadwayPoint_FirstPoint_roadway01_FN2.getMppRoadwayDirLinkerComponent().values().iterator().next().getRoadwayPoint());
      TestUtil.assertEquals_NoStop(roadwayPoint_CrossPoint_roadway02_FN2, roadwayPoint_FirstPoint_roadway02_FN2.getMppRoadwayDirLinkerComponent().values().iterator().next().getRoadwayPoint());
      TestUtil.assertEquals_NoStop(arr_pathArr.get(0).get(0), roadwayPoint_FirstPoint_roadway01_FN2.getPointLocation());
      TestUtil.assertEquals_NoStop(arr_pathArr.get(1).get(0), roadwayPoint_FirstPoint_roadway02_FN2.getPointLocation());

    }

    @DuplicatedCode //________________________________________
    private void connectRoadwayAtExactSegment(RoadwayCrossPointConnectionDto roadwayCrossPointConnection) throws RoadwayDoesNotContainThisSegmentException {
      //_____
      RoadwaySolidRoad roadway_AA = roadwayCrossPointConnection.getRoadwayAp();
      RoadwaySolidRoad roadway_BB = roadwayCrossPointConnection.getRoadwayBp();

      RoadwayNormalSegment roadwaySegment_AA = roadwayCrossPointConnection.getRoadwaySegmentA();
      RoadwayNormalSegment roadwaySegment_BB = roadwayCrossPointConnection.getRoadwaySegmentB();

      List<RoadwayDirLinkerComponent> arr_roadwayDirLinkerComponent_AA = roadway_AA.getArrRoadwayDirLinkerComponent();
      List<RoadwayDirLinkerComponent> arr_roadwayDirLinkerComponent_BB = roadway_BB.getArrRoadwayDirLinkerComponent();

      //__________________________________________________________
      int ind_roadwaySegment_AA = 0;
      for (RoadwayDirLinkerComponent roadwayDirLinkerComponent_AA_curr : arr_roadwayDirLinkerComponent_AA) {
        if (roadwayDirLinkerComponent_AA_curr.getRoadwaySegment() == roadwaySegment_AA) { break; }
        ind_roadwaySegment_AA++;
      }
      if (ind_roadwaySegment_AA == arr_roadwayDirLinkerComponent_AA.size()) { throw new RoadwayDoesNotContainThisSegmentException("Maybe this RoadwayCrossPointConnectionDto is stale (after an prev cross connection) :: " + roadwayCrossPointConnection); }

      int ind_roadwaySegment_BB = 0;
      for (RoadwayDirLinkerComponent roadwayDirLinkerComponent_BB_curr : arr_roadwayDirLinkerComponent_BB) {
        if (roadwayDirLinkerComponent_BB_curr.getRoadwaySegment() == roadwaySegment_BB) { break; }
        ind_roadwaySegment_BB++;
      }
      if (ind_roadwaySegment_BB == arr_roadwayDirLinkerComponent_BB.size()) { throw new RoadwayDoesNotContainThisSegmentException("Maybe this RoadwayCrossPointConnectionDto is stale (after an prev cross connection) :: " + roadwayCrossPointConnection); }

      //_____
      RoadwayPoint point_Seg_AA_Pt_A = roadwaySegment_AA.getRoadwayPointSp();
      RoadwayPoint point_Seg_AA_Pt_B = roadwaySegment_AA.getRoadwayPointNp();
      RoadwayPoint point_Seg_BB_Pt_A = roadwaySegment_BB.getRoadwayPointSp();
      RoadwayPoint point_Seg_BB_Pt_B = roadwaySegment_BB.getRoadwayPointNp();

      Map<RoadwayPoint, RoadwayDirLinkerComponent> mpp_roadwayDirLinkerComponent_Seg_AA_Pt_A = point_Seg_AA_Pt_A.getMppRoadwayDirLinkerComponent();
      Map<RoadwayPoint, RoadwayDirLinkerComponent> mpp_roadwayDirLinkerComponent_Seg_AA_Pt_B = point_Seg_AA_Pt_B.getMppRoadwayDirLinkerComponent();
      Map<RoadwayPoint, RoadwayDirLinkerComponent> mpp_roadwayDirLinkerComponent_Seg_BB_Pt_A = point_Seg_BB_Pt_A.getMppRoadwayDirLinkerComponent();
      Map<RoadwayPoint, RoadwayDirLinkerComponent> mpp_roadwayDirLinkerComponent_Seg_BB_Pt_B = point_Seg_BB_Pt_B.getMppRoadwayDirLinkerComponent();

      RoadwayDirLinkerComponent roadwayDirLinkerComponent_Seg_AA_btAtoB = mpp_roadwayDirLinkerComponent_Seg_AA_Pt_A.remove(point_Seg_AA_Pt_B);
      RoadwayDirLinkerComponent roadwayDirLinkerComponent_Seg_AA_btBtoA = mpp_roadwayDirLinkerComponent_Seg_AA_Pt_B.remove(point_Seg_AA_Pt_A);
      RoadwayDirLinkerComponent roadwayDirLinkerComponent_Seg_BB_btAtoB = mpp_roadwayDirLinkerComponent_Seg_BB_Pt_A.remove(point_Seg_BB_Pt_B);
      RoadwayDirLinkerComponent roadwayDirLinkerComponent_Seg_BB_btBtoA = mpp_roadwayDirLinkerComponent_Seg_BB_Pt_B.remove(point_Seg_BB_Pt_A);
      //________________________________________

      if (roadwayDirLinkerComponent_Seg_AA_btAtoB == null) { throw new Error("that must be its nearby point & must exist || the stale update pb aga?.. "); }
      if (roadwayDirLinkerComponent_Seg_AA_btBtoA == null) { throw new Error("that must be its nearby point & must exist || the stale update pb aga?.. "); }
      if (roadwayDirLinkerComponent_Seg_BB_btAtoB == null) { throw new Error("that must be its nearby point & must exist || the stale update pb aga?.. "); }
      if (roadwayDirLinkerComponent_Seg_BB_btBtoA == null) { throw new Error("that must be its nearby point & must exist || the stale update pb aga?..  Segment should be removed already during update, should not go here -- can be sth is wrong with arr_roadwayDirLinkerComponent didnt remove the stale segment.." + "\n" + "-> not just remove, need reset the segment on both linked side"); }

      //_____
      Point point_MM = roadwayCrossPointConnection.getPointLocation();
      @BugPotential RoadwayCrossPoint roadwayCrossPoint = new RoadwayCrossPoint("Cs-" + point_Seg_AA_Pt_A.getIdBsi() + point_Seg_AA_Pt_B.getIdBsi() + point_Seg_BB_Pt_A.getIdBsi() + point_Seg_BB_Pt_B.getIdBsi() + Instant.now(), point_MM);
      em.persist(roadwayCrossPoint);
      //________________________________________________

      RoadwayNormalSegment roadwaySegment_Seg_AA_btLCross = new RoadwayNormalSegment(point_Seg_AA_Pt_A, roadwayCrossPoint);
      RoadwayNormalSegment roadwaySegment_Seg_AA_btCrossR = new RoadwayNormalSegment(roadwayCrossPoint, point_Seg_AA_Pt_B);
      RoadwayNormalSegment roadwaySegment_Seg_BB_btLCross = new RoadwayNormalSegment(point_Seg_BB_Pt_A, roadwayCrossPoint);
      RoadwayNormalSegment roadwaySegment_Seg_BB_btCrossR = new RoadwayNormalSegment(roadwayCrossPoint, point_Seg_BB_Pt_B);
      em.persist(roadwaySegment_Seg_AA_btLCross);
      em.persist(roadwaySegment_Seg_AA_btCrossR);
      em.persist(roadwaySegment_Seg_BB_btLCross);
      em.persist(roadwaySegment_Seg_BB_btCrossR);

      RoadwayDirLinkerComponent roadwayDirLinkerComponent_Seg_AA_btAToCross = new RoadwayDirLinkerComponent(roadwaySegment_Seg_AA_btLCross, roadwayCrossPoint);
      RoadwayDirLinkerComponent roadwayDirLinkerComponent_Seg_AA_btCrossToA = new RoadwayDirLinkerComponent(roadwaySegment_Seg_AA_btLCross, point_Seg_AA_Pt_A);
      RoadwayDirLinkerComponent roadwayDirLinkerComponent_Seg_AA_btCrossToB = new RoadwayDirLinkerComponent(roadwaySegment_Seg_AA_btCrossR, point_Seg_AA_Pt_B);
      RoadwayDirLinkerComponent roadwayDirLinkerComponent_Seg_AA_btBToCross = new RoadwayDirLinkerComponent(roadwaySegment_Seg_AA_btCrossR, roadwayCrossPoint);
      RoadwayDirLinkerComponent roadwayDirLinkerComponent_Seg_BB_btAToCross = new RoadwayDirLinkerComponent(roadwaySegment_Seg_BB_btLCross, roadwayCrossPoint);
      RoadwayDirLinkerComponent roadwayDirLinkerComponent_Seg_BB_btCrossToA = new RoadwayDirLinkerComponent(roadwaySegment_Seg_BB_btLCross, point_Seg_BB_Pt_A);
      RoadwayDirLinkerComponent roadwayDirLinkerComponent_Seg_BB_btCrossToB = new RoadwayDirLinkerComponent(roadwaySegment_Seg_BB_btCrossR, point_Seg_BB_Pt_B);
      RoadwayDirLinkerComponent roadwayDirLinkerComponent_Seg_BB_btBToCross = new RoadwayDirLinkerComponent(roadwaySegment_Seg_BB_btCrossR, roadwayCrossPoint);
      em.persist(roadwayDirLinkerComponent_Seg_AA_btAToCross);
      em.persist(roadwayDirLinkerComponent_Seg_AA_btCrossToA);
      em.persist(roadwayDirLinkerComponent_Seg_AA_btCrossToB);
      em.persist(roadwayDirLinkerComponent_Seg_AA_btBToCross);
      em.persist(roadwayDirLinkerComponent_Seg_BB_btAToCross);
      em.persist(roadwayDirLinkerComponent_Seg_BB_btCrossToA);
      em.persist(roadwayDirLinkerComponent_Seg_BB_btCrossToB);
      em.persist(roadwayDirLinkerComponent_Seg_BB_btBToCross);

      Map<RoadwayPoint, RoadwayDirLinkerComponent> mpp_roadwayDirLinkerComponent_Cross = roadwayCrossPoint.getMppRoadwayDirLinkerComponent();

      //_____

      mpp_roadwayDirLinkerComponent_Seg_AA_Pt_A.put(roadwayCrossPoint, roadwayDirLinkerComponent_Seg_AA_btAToCross);
      mpp_roadwayDirLinkerComponent_Seg_AA_Pt_B.put(roadwayCrossPoint, roadwayDirLinkerComponent_Seg_AA_btBToCross);
      mpp_roadwayDirLinkerComponent_Seg_BB_Pt_A.put(roadwayCrossPoint, roadwayDirLinkerComponent_Seg_BB_btAToCross);
      mpp_roadwayDirLinkerComponent_Seg_BB_Pt_B.put(roadwayCrossPoint, roadwayDirLinkerComponent_Seg_BB_btBToCross);

      mpp_roadwayDirLinkerComponent_Cross.put(point_Seg_AA_Pt_A, roadwayDirLinkerComponent_Seg_AA_btCrossToA);
      mpp_roadwayDirLinkerComponent_Cross.put(point_Seg_AA_Pt_B, roadwayDirLinkerComponent_Seg_AA_btCrossToB);
      mpp_roadwayDirLinkerComponent_Cross.put(point_Seg_BB_Pt_A, roadwayDirLinkerComponent_Seg_BB_btCrossToA);
      mpp_roadwayDirLinkerComponent_Cross.put(point_Seg_BB_Pt_B, roadwayDirLinkerComponent_Seg_BB_btCrossToB);

      //_______________________________________________________________
      //__________________________________________________________________________________________________________________________________
      //_________________________________________________________________________________________
      RoadwayPoint point_Seg_AA_Pt_A_MG = em.merge(point_Seg_AA_Pt_A);
      RoadwayPoint point_Seg_AA_Pt_B_MG = em.merge(point_Seg_AA_Pt_B);
      RoadwayPoint point_Seg_BB_Pt_A_MG = em.merge(point_Seg_BB_Pt_A);
      RoadwayPoint point_Seg_BB_Pt_B_MG = em.merge(point_Seg_BB_Pt_B);
      //_____________________________________________________

      //_____
      int ind_Linker_Seg_AA_btAtoB = arr_roadwayDirLinkerComponent_AA.indexOf(roadwayDirLinkerComponent_Seg_AA_btAtoB);
      int ind_Linker_Seg_BB_btAtoB = arr_roadwayDirLinkerComponent_BB.indexOf(roadwayDirLinkerComponent_Seg_BB_btAtoB);
      if (ind_Linker_Seg_AA_btAtoB == -1 || ind_Linker_Seg_BB_btAtoB == -1) { throw new Error("Can be messy order pb (for now)"); }
      if (ind_Linker_Seg_AA_btAtoB != ind_roadwaySegment_AA) { throw new Error("Should equal" + " :: " + ind_Linker_Seg_AA_btAtoB + " :: " + ind_roadwaySegment_AA); }
      if (ind_Linker_Seg_BB_btAtoB != ind_roadwaySegment_BB) { throw new Error("Should equal" + " :: " + ind_Linker_Seg_BB_btAtoB + " :: " + ind_roadwaySegment_BB); }

      @Messy byte messy;
      //________________________________________________________________
      arr_roadwayDirLinkerComponent_AA.set(ind_Linker_Seg_AA_btAtoB, roadwayDirLinkerComponent_Seg_AA_btAToCross);
      arr_roadwayDirLinkerComponent_AA.add(ind_Linker_Seg_AA_btAtoB + 1, roadwayDirLinkerComponent_Seg_AA_btCrossToB);
      arr_roadwayDirLinkerComponent_BB.set(ind_Linker_Seg_BB_btAtoB, roadwayDirLinkerComponent_Seg_BB_btAToCross);
      arr_roadwayDirLinkerComponent_BB.add(ind_Linker_Seg_BB_btAtoB + 1, roadwayDirLinkerComponent_Seg_BB_btCrossToB);

      //_________________________________________________________________________________________________________________________________
      RoadwaySolidRoad roadway_AA_MG = em.merge(roadway_AA);
      RoadwaySolidRoad roadway_BB_MG = em.merge(roadway_BB);
    }
  }

  @Nested
  class MapBuilder_connectRoadwayAtExactSegment_Repo_MethodWholeUnitTest {

    @Test
    @Transactional
    //_________________________________________________________________________________________________
    public void case__TShape_need_connect_and_remove() {
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

      for (ArrayList<Point> pathArr_curr : arr_pathArr) {
        Platform.runLater(() -> {
          Path path = JavafxUtil.convert_arrPath2pathJfx(pathArr_curr);
          panel_SemanticRoot.getChildren().add(path);
        });
      }

      //_
      String nameRoadway_prepend = "Ro";

      RoadwaySolidRoad roadway_curr;
      RoadwaySolidRoad roadway_curr_FN;
      ArrayList<RoadwaySolidRoad> arr_roadway = new ArrayList<>();
      ArrayList<RoadwaySolidRoad> arr_roadway_FN = new ArrayList<RoadwaySolidRoad>();

      //
      int i = 0;
      i++;
      roadway_curr = mapBuilder.createRoadway(nameRoadway_prepend + i, arr_pathArr.get(i - 1));
      RoadwaySolidRoad roadway_curr_01 = roadway_curr;
      arr_roadway.add(roadway_curr);
      i++;
      roadway_curr = mapBuilder.createRoadway(nameRoadway_prepend + i, arr_pathArr.get(i - 1));
      RoadwaySolidRoad roadway_curr_02 = roadway_curr;
      arr_roadway.add(roadway_curr);

      em.flush(); //_____________________________________
      em.clear();

      //_________
      //_________

      List<RoadwayCrossPointConnectionDto> arr_roadwayCrossPointConnection = mapBuilder.findAllCrossPoint(arr_roadway.get(0), arr_roadway.get(1));
      if (arr_roadwayCrossPointConnection.size() != 1) { throw new Error(); }
      @Main byte dmy134; //_
      mapBuilder.connectRoadwayAtCrossPointConnection(arr_roadwayCrossPointConnection);

      //_________

      roadway_curr_FN = em.find(RoadwaySolidRoad.class, roadway_curr_01.getIdSql());
      RoadwaySolidRoad roadway_curr_01_FN = roadway_curr_FN;
      roadway_curr_FN = em.find(RoadwaySolidRoad.class, roadway_curr_02.getIdSql());
      RoadwaySolidRoad roadway_curr_02_FN = roadway_curr_FN;

      List<RoadwayDirLinkerComponent> arrRoadwayDirLinkerComponent_01 = roadway_curr_01.getArrRoadwayDirLinkerComponent();
      List<RoadwayDirLinkerComponent> arrRoadwayDirLinkerComponent_02 = roadway_curr_02.getArrRoadwayDirLinkerComponent();
      List<RoadwayDirLinkerComponent> arrRoadwayDirLinkerComponent_01_FN = roadway_curr_01_FN.getArrRoadwayDirLinkerComponent();
      List<RoadwayDirLinkerComponent> arrRoadwayDirLinkerComponent_02_FN = roadway_curr_02_FN.getArrRoadwayDirLinkerComponent();

      TestUtil.assertEquals_NoStop(3, arrRoadwayDirLinkerComponent_01.size());
      TestUtil.assertEquals_NoStop(2, arrRoadwayDirLinkerComponent_02.size());
      TestUtil.assertEquals_NoStop(3, arrRoadwayDirLinkerComponent_01_FN.size());
      TestUtil.assertEquals_NoStop(2, arrRoadwayDirLinkerComponent_02_FN.size());

      TestUtil.assertEquals_NoStop(arrRoadwayDirLinkerComponent_01, arrRoadwayDirLinkerComponent_01_FN);
      TestUtil.assertEquals_NoStop(arrRoadwayDirLinkerComponent_02, arrRoadwayDirLinkerComponent_02_FN);

      //
      @Main //
      RoadwayDirLinkerComponent roadwayDirLinkerComponent_roadway01_sn02 = roadway_curr_01.getArrRoadwayDirLinkerComponent().get(1);
      RoadwayDirLinkerComponent roadwayDirLinkerComponent_roadway02_sn02 = roadway_curr_02.getArrRoadwayDirLinkerComponent().get(0);
      RoadwayDirLinkerComponent roadwayDirLinkerComponent_roadway01_sn02_FN = roadway_curr_01_FN.getArrRoadwayDirLinkerComponent().get(1);
      RoadwayDirLinkerComponent roadwayDirLinkerComponent_roadway02_sn02_FN = roadway_curr_02_FN.getArrRoadwayDirLinkerComponent().get(0); //_____

      //
      @Main //
      RoadwayPoint roadwayPoint_CrossPoint_roadway01_FN = roadwayDirLinkerComponent_roadway01_sn02_FN.getRoadwayPoint();
      RoadwayPoint roadwayPoint_CrossPoint_roadway02_FN = roadwayDirLinkerComponent_roadway02_sn02_FN.getRoadwayPoint();
      TestUtil.assertTrue_NoStop(roadwayPoint_CrossPoint_roadway01_FN instanceof RoadwayCrossPoint);
      TestUtil.assertTrue_NoStop(roadwayPoint_CrossPoint_roadway01_FN == roadwayPoint_CrossPoint_roadway02_FN);
      TestUtil.assertEquals_NoStop(3, roadwayPoint_CrossPoint_roadway01_FN.getMppRoadwayDirLinkerComponent().size());
      TestUtil.assertEquals_NoStop(new Point(500, 200), roadwayPoint_CrossPoint_roadway01_FN.getPointLocation());

    }

  }

  //_____
  //___________________________________________________________________________
  //__________________________________________
  @Nested
  class CheckerTest_ResetRepo_beforeEach {

  }

  //_____________
  //_____________
  //_____________

  //_________________
  //_________________________________________________________________________________________________________________________________
  //____________________________________________________________________________________________________________
  //______________________________________
  //______________
  //
  //_______________________________________________________
  //__________
  //______________________________________________________________________________________________________
  //____________________________________________________________________
  //_______________________________________
  //_____________________________________________________________________________
  //___________________________________________________________
  //_________________
  //
  //_____
  //
  //_________________________________________
  //___

}
