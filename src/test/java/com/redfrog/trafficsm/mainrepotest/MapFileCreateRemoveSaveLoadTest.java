package com.redfrog.trafficsm.mainrepotest;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.query.internal.NativeQueryImpl;
import org.hibernate.transform.AliasedTupleSubsetResultTransformer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.transaction.annotation.Transactional;
import org.testfx.framework.junit5.ApplicationTest;

import com.google.common.base.CaseFormat;
import com.redfrog.trafficsm.annotation.DuplicatedCode;
import com.redfrog.trafficsm.annotation.Hack;
import com.redfrog.trafficsm.annotation.Main;
import com.redfrog.trafficsm.annotation.Messy;
import com.redfrog.trafficsm.annotation.Todo;
import com.redfrog.trafficsm.exception.AlreadyExistedException;
import com.redfrog.trafficsm.exception.FoundNoItemWithIdException;
import com.redfrog.trafficsm.model.MapFile;
import com.redfrog.trafficsm.model.MapFile_;
import com.redfrog.trafficsm.model.roadway.fundamental.RoadwayAbstract;
import com.redfrog.trafficsm.model.roadway.fundamental.RoadwayAbstract_;
import com.redfrog.trafficsm.model.roadway.fundamental.RoadwayDirLinkerComponent;
import com.redfrog.trafficsm.model.roadway.fundamental.RoadwayDirLinkerComponent_;
import com.redfrog.trafficsm.model.roadway.fundamental.RoadwayPoint;
import com.redfrog.trafficsm.model.roadway.fundamental.RoadwayPoint_;
import com.redfrog.trafficsm.model.roadway.fundamental.RoadwaySegment;
import com.redfrog.trafficsm.model.roadway.main.RoadwayCrossPointConnectionDto;
import com.redfrog.trafficsm.model.roadway.main.RoadwaySolidRoad;
import com.redfrog.trafficsm.service.MapBuilder;
import com.redfrog.trafficsm.service.WindowSessionJavafx;
import com.redfrog.trafficsm.shape.Point;
import com.redfrog.trafficsm.test.javafxBoot.JfxAppSimpleSetup;
import com.redfrog.trafficsm.util.DbTableUtil;
import com.redfrog.trafficsm.util.MathUtil;
import com.redfrog.trafficsm.util.TestUtil;

import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lombok.extern.log4j.Log4j2;

@SpringBootTest
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
//____________
//______________________
//_________________________________
//______________________________________________________________________________________
//___________________________________________________________________________
//___________________________________________
//____________________________________________________
//________________________
@Log4j2
//______________________
class MapFileCreateRemoveSaveLoadTest extends ApplicationTest {

  //____________
  //________________________________________________________________________
  //______________________________________________________________________________________
  //________________________________
  //___

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

  //_____________
  //_______________________________________________________

  //_____

  //____________
  @PersistenceContext
  private EntityManager em;

  @Autowired
  private DbTableUtil dbTableUtil;

  //_____

  //_________________________________________________________
  private AnchorPane panel_SemanticRoot;

  @Override
  public void start(Stage primaryStage) {
    panel_SemanticRoot                     = JfxAppSimpleSetup.startSetup(primaryStage);
    windowSessionJavafx.panel_SemanticRoot = panel_SemanticRoot;
  }
  //_______

  @Nested
  class Test_01 {

    @Test
    @Transactional
    //____________________
    public void simpleCase__create_1_roadway__messy_todo_multi_mapFile() {
      MapFile mapFile_New = mapBuilder.newMapFile();

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
      long id_roadway_N = roadwaySolidRoad.getIdSql();

      RoadwayDirLinkerComponent roadwayDirLinkerComponent_N = roadwaySolidRoad.getArrRoadwayDirLinkerComponent().get(1);
      long id_linker_N = roadwayDirLinkerComponent_N.getIdSql();
      RoadwaySegment roadwaySegment_N = roadwayDirLinkerComponent_N.getRoadwaySegment();
      RoadwayPoint roadwayPoint_N = roadwayDirLinkerComponent_N.getRoadwayPoint();
      long id_segment_N = roadwaySegment_N.getIdSql();
      long id_point_N = roadwayPoint_N.getIdSql();

      @DuplicatedCode byte dmy820; //_
      {
        em.flush();
        em.clear();

        //
        RoadwaySolidRoad roadwaySolidRoad_FN = em.find(RoadwaySolidRoad.class, id_roadway_N);

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

      //_________

      //_____________________________________________________________________________________________________

      Set<RoadwaySolidRoad> gpRoadwaySolidRoad_New = mapFile_New.getGpRoadwaySolidRoad();
      TestUtil.assertEquals_NoStop(1, gpRoadwaySolidRoad_New.size());
      TestUtil.assertEquals_NoStop(roadwaySolidRoad, gpRoadwaySolidRoad_New.iterator().next());

      //_________

      //_____________________________

      MapFile mapFile_PSMG = mapBuilder.saveMapFile();
      long id_mapFile = mapFile_PSMG.getIdSql();

      em.flush();
      em.clear();

      @Main byte dmy829; //_
      System.out.println(">> System.out.println(querySomeTable_helper());");
      System.out.println(mapBuilder.querySomeTable_MapFile_helper());

      //______________________________________________________________________________________________________________________
      //___________________________________________________________________________________

      //_________
      //____________________________________________________________
      MapFile mapFile_FN = em.find(MapFile.class, id_mapFile);
      {
        //______________________________
        MapFile mapFile_ORFN;
        try {
          mapFile_ORFN = mapBuilder.loadMapFile(id_mapFile);
          Assertions.fail("above should throw");
        } catch (FoundNoItemWithIdException e) {
          throw new Error(e);
        } catch (AlreadyExistedException e) {
          mapFile_ORFN = (MapFile) e.getObjAlreadyExisted();
        }
        TestUtil.assertSame_NoStop(mapFile_New, mapFile_PSMG);
        TestUtil.assertSame_NoStop(mapFile_PSMG, mapFile_ORFN);
      }

      TestUtil.assertNotSame_NoStop(mapFile_PSMG, mapFile_FN);
      //___________________________________________________________________________________________________
      TestUtil.assertEquals_NoStop(mapFile_PSMG.getIdJava(), mapFile_FN.getIdJava());

      Set<RoadwaySolidRoad> gpRoadwaySolidRoad_FN = mapFile_FN.getGpRoadwaySolidRoad();
      TestUtil.assertEquals_NoStop(mapFile_PSMG.getGpRoadwaySolidRoad().size(), gpRoadwaySolidRoad_FN.size());
      TestUtil.assertEquals_NoStop(1, gpRoadwaySolidRoad_FN.size());
      TestUtil.assertNotSame_NoStop(roadwaySolidRoad, gpRoadwaySolidRoad_FN.iterator().next());
      TestUtil.assertEquals_NoStop(roadwaySolidRoad, gpRoadwaySolidRoad_FN.iterator().next());

      em.flush();
      em.clear();

      //_________

      //____
      @Todo
      @Main
      @Messy byte dmy456;
      System.out.println("//TODO said messy implemented -- cuz never wanted to deal with multi mapfile ..........................");
      //_______________________________________________________________
      //______________________________________________________________________________________________________
      //________________________________________
      //______________________________________________

      try {
        mapBuilder.removeMapFile(id_mapFile);
      } catch (FoundNoItemWithIdException e) {
        throw new Error(e);
      }

      em.flush();
      em.clear();

      System.out.println(mapBuilder.querySomeTable_MapFile_helper());

      //_______________
      MapFile                      mapFile_NotExist = em.find(MapFile.class, id_mapFile);
      RoadwayAbstract              roadway_NotExist = em.find(RoadwayAbstract.class, id_roadway_N);
      RoadwayDirLinkerComponent     linker_NotExist = em.find(RoadwayDirLinkerComponent.class, id_linker_N);
      RoadwaySegment               segment_NotExist = em.find(RoadwaySegment.class, id_segment_N);
      RoadwayPoint                   point_NotExist = em.find(RoadwayPoint.class, id_point_N);

      TestUtil.assertEquals_NoStop(null, mapFile_NotExist);
      TestUtil.assertEquals_NoStop(null, roadway_NotExist);
      TestUtil.assertEquals_NoStop(null, linker_NotExist);
      TestUtil.assertEquals_NoStop(null, segment_NotExist);
      TestUtil.assertEquals_NoStop(null, point_NotExist);

      TestUtil.assertEquals_NoStop(0, dbTableUtil.queryDbTable_resultList(MapFile                    .class).size());
      TestUtil.assertEquals_NoStop(0, dbTableUtil.queryDbTable_resultList(RoadwayAbstract            .class).size());
      TestUtil.assertEquals_NoStop(0, dbTableUtil.queryDbTable_resultList(RoadwayDirLinkerComponent  .class).size());
      TestUtil.assertEquals_NoStop(0, dbTableUtil.queryDbTable_resultList(RoadwaySegment             .class).size());
      TestUtil.assertEquals_NoStop(0, dbTableUtil.queryDbTable_resultList(RoadwayPoint               .class).size());
      //______________

      //____________________________________
    }

    //__________________________________
    //________________________________________

    //_____________
    //_____________
    //_____________

    @Test
    @Transactional
    public void case__create_2_roadway() {
      MapFile mapFile_New = mapBuilder.newMapFile();

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

      {
        //___________________________________________________________

        //_
        String nameRoadway_prepend = "Ro";

        RoadwaySolidRoad roadway_curr;
        ArrayList<RoadwaySolidRoad> arr_roadway = new ArrayList<>();

        //_________
        //_________

        //
        int i = 0;
        i++;
        roadway_curr = mapBuilder.createRoadway(nameRoadway_prepend + i, arr_pathArr.get(i - 1));
        RoadwaySolidRoad roadway_curr_01 = roadway_curr;
        long id_roadway_curr_01 = roadway_curr_01.getIdSql();
        arr_roadway.add(roadway_curr);
        i++;
        roadway_curr = mapBuilder.createRoadway(nameRoadway_prepend + i, arr_pathArr.get(i - 1));
        RoadwaySolidRoad roadway_curr_02 = roadway_curr;
        long id_roadway_curr_02 = roadway_curr_02.getIdSql();
        arr_roadway.add(roadway_curr);

        em.flush();
        em.clear();

        System.out.println(">> Before Add CrossPoint");
        System.out.println(mapBuilder.querySomeTable_MapFile_helper());

        TestUtil.assertEquals_NoStop(0, dbTableUtil.queryDbTable_resultList(MapFile.class).size());
        TestUtil.assertEquals_NoStop(2, dbTableUtil.queryDbTable_resultList(RoadwayAbstract.class).size());
        TestUtil.assertEquals_NoStop(2 + (2) * 2, dbTableUtil.queryDbTable_resultList(RoadwayDirLinkerComponent.class).size()); //_______________________________________________________
        TestUtil.assertEquals_NoStop(2 + (2), dbTableUtil.queryDbTable_resultList(RoadwaySegment.class).size()); //__________________
        TestUtil.assertEquals_NoStop(4, dbTableUtil.queryDbTable_resultList(RoadwayPoint.class).size());

        //_________
        //_________

        List<RoadwayCrossPointConnectionDto> arr_roadwayCrossPointConnection = mapBuilder.findAllCrossPoint(arr_roadway.get(0), arr_roadway.get(1));
        if (arr_roadwayCrossPointConnection.size() != 1) { throw new Error(); }
        mapBuilder.connectRoadwayAtCrossPointConnection(arr_roadwayCrossPointConnection);

        em.flush();
        em.clear();

        Set<RoadwaySolidRoad> gpRoadwaySolidRoad_New = mapFile_New.getGpRoadwaySolidRoad();
        TestUtil.assertEquals_NoStop(2, gpRoadwaySolidRoad_New.size());
        TestUtil.assertTrue_NoStop(gpRoadwaySolidRoad_New.contains(roadway_curr_01));
        TestUtil.assertTrue_NoStop(gpRoadwaySolidRoad_New.contains(roadway_curr_02));

        //_________

        MapFile mapFile_PSMG = mapBuilder.saveMapFile();
        long id_mapFile = mapFile_PSMG.getIdSql();

        em.flush();
        em.clear();

        @Main byte dmy829; //_
        System.out.println(">> After Add CrossPoint System.out.println(querySomeTable_helper());");
        System.out.println(mapBuilder.querySomeTable_MapFile_helper());

        TestUtil.assertEquals_NoStop(1, dbTableUtil.queryDbTable_resultList(MapFile.class).size());
        TestUtil.assertEquals_NoStop(2, dbTableUtil.queryDbTable_resultList(RoadwayAbstract.class).size());
        TestUtil.assertEquals_NoStop(2 + (2 - 2 + 4) * 2, dbTableUtil.queryDbTable_resultList(RoadwayDirLinkerComponent.class).size());
        TestUtil.assertEquals_NoStop(2 + (2 - 2 + 4), dbTableUtil.queryDbTable_resultList(RoadwaySegment.class).size());
        TestUtil.assertEquals_NoStop(4 + 1, dbTableUtil.queryDbTable_resultList(RoadwayPoint.class).size());

        //_________
        //_________

        //____________________________________________________________
        MapFile mapFile_FN = em.find(MapFile.class, id_mapFile);
        {
          //______________________________
          MapFile mapFile_ORFN;
          try {
            mapFile_ORFN = mapBuilder.loadMapFile(id_mapFile);
            Assertions.fail("above should throw");
          } catch (FoundNoItemWithIdException e) {
            throw new Error(e);
          } catch (AlreadyExistedException e) {
            mapFile_ORFN = (MapFile) e.getObjAlreadyExisted();
          }
          TestUtil.assertSame_NoStop(mapFile_New, mapFile_PSMG);
          TestUtil.assertSame_NoStop(mapFile_PSMG, mapFile_ORFN);
        }

        TestUtil.assertNotSame_NoStop(mapFile_PSMG, mapFile_FN);
        TestUtil.assertEquals_NoStop(mapFile_PSMG.getIdJava(), mapFile_FN.getIdJava());

        Set<RoadwaySolidRoad> gpRoadwaySolidRoad_FN = mapFile_FN.getGpRoadwaySolidRoad();
        TestUtil.assertEquals_NoStop(mapFile_PSMG.getGpRoadwaySolidRoad().size(), gpRoadwaySolidRoad_FN.size());
        TestUtil.assertEquals_NoStop(2, gpRoadwaySolidRoad_FN.size());
        TestUtil.assertTrue_NoStop(gpRoadwaySolidRoad_FN.contains(roadway_curr_01));
        TestUtil.assertTrue_NoStop(gpRoadwaySolidRoad_FN.contains(roadway_curr_02));

        em.flush();
        em.clear();

        //_________

        @Todo
        @Main
        @Messy byte dmy456;
        System.out.println("<see ^^>");
        try {
          mapBuilder.removeMapFile(id_mapFile);
        } catch (FoundNoItemWithIdException e) {
          throw new Error(e);
        }
        ;
        //__________________________________________________

        em.flush();
        em.clear();

        System.out.println(">> After Remove MapFile System.out.println(querySomeTable_helper());");
        System.out.println(mapBuilder.querySomeTable_MapFile_helper());

        MapFile mapFile_NotExist = em.find(MapFile.class, id_mapFile);
        RoadwayAbstract roadway_NotExist_01 = em.find(RoadwayAbstract.class, id_roadway_curr_01);
        RoadwayAbstract roadway_NotExist_02 = em.find(RoadwayAbstract.class, id_roadway_curr_02);

        TestUtil.assertEquals_NoStop(null, mapFile_NotExist);
        TestUtil.assertEquals_NoStop(null, roadway_NotExist_01);
        TestUtil.assertEquals_NoStop(null, roadway_NotExist_02);

        TestUtil.assertEquals_NoStop(0, dbTableUtil.queryDbTable_resultList(MapFile.class).size());
        TestUtil.assertEquals_NoStop(0, dbTableUtil.queryDbTable_resultList(RoadwayAbstract.class).size());
        TestUtil.assertEquals_NoStop(0, dbTableUtil.queryDbTable_resultList(RoadwayDirLinkerComponent.class).size());
        TestUtil.assertEquals_NoStop(0, dbTableUtil.queryDbTable_resultList(RoadwaySegment.class).size());
        TestUtil.assertEquals_NoStop(0, dbTableUtil.queryDbTable_resultList(RoadwayPoint.class).size());

        //____________________________________
      }

    }

    @Test
    @Transactional
    public void case__TShape() {
      MapFile mapFile_New = mapBuilder.newMapFile();

      //
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

      @DuplicatedCode byte dmy901; //_
      //________________________________
      {
        //___________________________________________________________

        //_
        String nameRoadway_prepend = "Ro";

        RoadwaySolidRoad roadway_curr;
        ArrayList<RoadwaySolidRoad> arr_roadway = new ArrayList<>();

        //_________
        //_________

        //
        int i = 0;
        i++;
        roadway_curr = mapBuilder.createRoadway(nameRoadway_prepend + i, arr_pathArr.get(i - 1));
        RoadwaySolidRoad roadway_curr_01 = roadway_curr;
        long id_roadway_curr_01 = roadway_curr_01.getIdSql();
        arr_roadway.add(roadway_curr);
        i++;
        roadway_curr = mapBuilder.createRoadway(nameRoadway_prepend + i, arr_pathArr.get(i - 1));
        RoadwaySolidRoad roadway_curr_02 = roadway_curr;
        long id_roadway_curr_02 = roadway_curr_02.getIdSql();
        arr_roadway.add(roadway_curr);

        em.flush();
        em.clear();

        System.out.println(">> Before Add CrossPoint");
        System.out.println(mapBuilder.querySomeTable_MapFile_helper());

        TestUtil.assertEquals_NoStop(0, dbTableUtil.queryDbTable_resultList(MapFile.class).size());
        TestUtil.assertEquals_NoStop(2, dbTableUtil.queryDbTable_resultList(RoadwayAbstract.class).size());
        TestUtil.assertEquals_NoStop(2 + (2) * 2, dbTableUtil.queryDbTable_resultList(RoadwayDirLinkerComponent.class).size()); //_______________________________________________________
        TestUtil.assertEquals_NoStop(2 + (2), dbTableUtil.queryDbTable_resultList(RoadwaySegment.class).size()); //__________________
        TestUtil.assertEquals_NoStop(4, dbTableUtil.queryDbTable_resultList(RoadwayPoint.class).size());

        //_________
        //_________

        List<RoadwayCrossPointConnectionDto> arr_roadwayCrossPointConnection = mapBuilder.findAllCrossPoint(arr_roadway.get(0), arr_roadway.get(1));
        if (arr_roadwayCrossPointConnection.size() != 1) { throw new Error(); }
        mapBuilder.connectRoadwayAtCrossPointConnection(arr_roadwayCrossPointConnection);

        em.flush();
        em.clear();

        Set<RoadwaySolidRoad> gpRoadwaySolidRoad_New = mapFile_New.getGpRoadwaySolidRoad();
        TestUtil.assertEquals_NoStop(2, gpRoadwaySolidRoad_New.size());
        TestUtil.assertTrue_NoStop(gpRoadwaySolidRoad_New.contains(roadway_curr_01));
        TestUtil.assertTrue_NoStop(gpRoadwaySolidRoad_New.contains(roadway_curr_02));

        //_________

        MapFile mapFile_PSMG = mapBuilder.saveMapFile();
        long id_mapFile = mapFile_PSMG.getIdSql();

        em.flush();
        em.clear();

        @Main byte dmy829; //_
        System.out.println(">> After Add CrossPoint System.out.println(querySomeTable_helper());");
        System.out.println(mapBuilder.querySomeTable_MapFile_helper());

        TestUtil.assertEquals_NoStop(1, dbTableUtil.queryDbTable_resultList(MapFile.class).size());
        TestUtil.assertEquals_NoStop(2, dbTableUtil.queryDbTable_resultList(RoadwayAbstract.class).size());
        TestUtil.assertEquals_NoStop(2 + (2 - 2 + 4 - 1) * 2, dbTableUtil.queryDbTable_resultList(RoadwayDirLinkerComponent.class).size());
        TestUtil.assertEquals_NoStop(2 + (2 - 2 + 4 - 1), dbTableUtil.queryDbTable_resultList(RoadwaySegment.class).size());
        TestUtil.assertEquals_NoStop(4 + 1 - 1, dbTableUtil.queryDbTable_resultList(RoadwayPoint.class).size());

        //_________
        //_________

        //___________________________________________________________________
        //___
        //_________________________________________________________________
        //________________________________________________________________________________________
        MapFile mapFile_FN = em.find(MapFile.class, id_mapFile);
        {
          //______________________________
          MapFile mapFile_ORFN;
          try {
            mapFile_ORFN = mapBuilder.loadMapFile(id_mapFile);
            Assertions.fail("above should throw");
          } catch (FoundNoItemWithIdException e) {
            throw new Error(e);
          } catch (AlreadyExistedException e) {
            mapFile_ORFN = (MapFile) e.getObjAlreadyExisted();
          }
          TestUtil.assertSame_NoStop(mapFile_New, mapFile_PSMG);
          TestUtil.assertSame_NoStop(mapFile_PSMG, mapFile_ORFN);
        }

        TestUtil.assertNotSame_NoStop(mapFile_PSMG, mapFile_FN);
        TestUtil.assertEquals_NoStop(mapFile_PSMG.getIdJava(), mapFile_FN.getIdJava());

        Set<RoadwaySolidRoad> gpRoadwaySolidRoad_FN = mapFile_FN.getGpRoadwaySolidRoad();
        TestUtil.assertEquals_NoStop(mapFile_PSMG.getGpRoadwaySolidRoad().size(), gpRoadwaySolidRoad_FN.size());
        TestUtil.assertEquals_NoStop(2, gpRoadwaySolidRoad_FN.size());
        TestUtil.assertTrue_NoStop(gpRoadwaySolidRoad_FN.contains(roadway_curr_01));
        TestUtil.assertTrue_NoStop(gpRoadwaySolidRoad_FN.contains(roadway_curr_02));

        em.flush();
        em.clear();

        //_________

        @Todo
        @Main
        @Messy byte dmy456;
        System.out.println("<see ^^>");
        try {
          mapBuilder.removeMapFile(id_mapFile);
        } catch (FoundNoItemWithIdException e) {
          throw new Error(e);
        }
        ;
        //__________________________________________________

        em.flush();
        em.clear();

        System.out.println(">> After Remove MapFile System.out.println(querySomeTable_helper());");
        System.out.println(mapBuilder.querySomeTable_MapFile_helper());

        MapFile mapFile_NotExist = em.find(MapFile.class, id_mapFile);
        RoadwayAbstract roadway_NotExist_01 = em.find(RoadwayAbstract.class, id_roadway_curr_01);
        RoadwayAbstract roadway_NotExist_02 = em.find(RoadwayAbstract.class, id_roadway_curr_02);

        TestUtil.assertEquals_NoStop(null, mapFile_NotExist);
        TestUtil.assertEquals_NoStop(null, roadway_NotExist_01);
        TestUtil.assertEquals_NoStop(null, roadway_NotExist_02);

        TestUtil.assertEquals_NoStop(0, dbTableUtil.queryDbTable_resultList(MapFile.class).size());
        TestUtil.assertEquals_NoStop(0, dbTableUtil.queryDbTable_resultList(RoadwayAbstract.class).size());
        TestUtil.assertEquals_NoStop(0, dbTableUtil.queryDbTable_resultList(RoadwayDirLinkerComponent.class).size());
        TestUtil.assertEquals_NoStop(0, dbTableUtil.queryDbTable_resultList(RoadwaySegment.class).size());
        TestUtil.assertEquals_NoStop(0, dbTableUtil.queryDbTable_resultList(RoadwayPoint.class).size());

        //____________________________________
      }
      //_________________________________

    }

    //__________________
    //__________________________________________________________________________________________________________

    //_____________
    //_____________
    //_____________

    @Test
    @Transactional
    public void case__2_mapFile() {

      long id_FirstMapFile_ToDelete;

      {
        //______________________________________________________
        //
        //__________
        //________________________________________
        //____________________________________________________________________
        //
        //___________________________________________
        //______________________________________________________________
        //______________________________________________________________
        //______________________________________________________________
        //________________________________________
        //
        //___________
        //__________________________________________
        //________________________________________________________________________________________________
        //________________________________________________________
        //
        //______________________________________________________
        //________________________________________________
        //
        //______________________________________________
        //
        //___________________
        //___________________
      }

      {
        MapFile mapFile_New = mapBuilder.newMapFile();

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

        {
          //_
          String nameRoadway_prepend = "Ro*UniquePb";

          RoadwaySolidRoad roadway_curr;
          ArrayList<RoadwaySolidRoad> arr_roadway = new ArrayList<>();

          //_________
          //_________

          //
          int i = 0;
          i++;
          roadway_curr = mapBuilder.createRoadway(nameRoadway_prepend + i, arr_pathArr.get(i - 1));
          RoadwaySolidRoad roadway_curr_01 = roadway_curr;
          long id_roadway_curr_01 = roadway_curr_01.getIdSql();
          arr_roadway.add(roadway_curr);
          i++;
          roadway_curr = mapBuilder.createRoadway(nameRoadway_prepend + i, arr_pathArr.get(i - 1));
          RoadwaySolidRoad roadway_curr_02 = roadway_curr;
          long id_roadway_curr_02 = roadway_curr_02.getIdSql();
          arr_roadway.add(roadway_curr);

          em.flush();
          em.clear();

          //_________
          //_________

          List<RoadwayCrossPointConnectionDto> arr_roadwayCrossPointConnection = mapBuilder.findAllCrossPoint(arr_roadway.get(0), arr_roadway.get(1));
          if (arr_roadwayCrossPointConnection.size() != 1) { throw new Error(); }
          mapBuilder.connectRoadwayAtCrossPointConnection(arr_roadwayCrossPointConnection);

          em.flush();
          em.clear();

          //_________

          MapFile mapFile_MG = mapBuilder.saveMapFile();
          long id_mapFile = mapFile_MG.getIdSql();

          em.flush();
          em.clear();

          //_________
          //_________

          id_FirstMapFile_ToDelete = id_mapFile;
        }
      }

      {
        MapFile mapFile_New = mapBuilder.newMapFile();

        //
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

        {
          //_
          String nameRoadway_prepend = "Ro";

          RoadwaySolidRoad roadway_curr;
          ArrayList<RoadwaySolidRoad> arr_roadway = new ArrayList<>();

          //_________
          //_________

          //
          int i = 0;
          i++;
          roadway_curr = mapBuilder.createRoadway(nameRoadway_prepend + i, arr_pathArr.get(i - 1));
          RoadwaySolidRoad roadway_curr_01 = roadway_curr;
          long id_roadway_curr_01 = roadway_curr_01.getIdSql();
          arr_roadway.add(roadway_curr);
          i++;
          roadway_curr = mapBuilder.createRoadway(nameRoadway_prepend + i, arr_pathArr.get(i - 1));
          RoadwaySolidRoad roadway_curr_02 = roadway_curr;
          long id_roadway_curr_02 = roadway_curr_02.getIdSql();
          arr_roadway.add(roadway_curr);

          em.flush();
          em.clear();

          //_________
          //_________

          List<RoadwayCrossPointConnectionDto> arr_roadwayCrossPointConnection = mapBuilder.findAllCrossPoint(arr_roadway.get(0), arr_roadway.get(1));
          if (arr_roadwayCrossPointConnection.size() != 1) { throw new Error(); }
          mapBuilder.connectRoadwayAtCrossPointConnection(arr_roadwayCrossPointConnection);

          em.flush();
          em.clear();

          //_________

          MapFile mapFile_MG = mapBuilder.saveMapFile();
          long id_mapFile = mapFile_MG.getIdSql();

          em.flush();
          em.clear();

          //_________
          //_________

        }
      }

      //_____________

      //_______________________________________________________________________________________

      @Main byte dmy138; //_
      System.out.println(">> After 2 MapFile");
      System.out.println(mapBuilder.querySomeTable_MapFile_helper());

      TestUtil.assertEquals_NoStop((1) + (1), dbTableUtil.queryDbTable_resultList(MapFile.class).size());
      TestUtil.assertEquals_NoStop((2) + (2), dbTableUtil.queryDbTable_resultList(RoadwayAbstract.class).size());
      TestUtil.assertEquals_NoStop((2 + (2 - 2 + 4) * 2) + (2 + (2 - 2 + 4 - 1) * 2), dbTableUtil.queryDbTable_resultList(RoadwayDirLinkerComponent.class).size());
      TestUtil.assertEquals_NoStop((2 + (2 - 2 + 4)) + (2 + (2 - 2 + 4 - 1)), dbTableUtil.queryDbTable_resultList(RoadwaySegment.class).size());
      TestUtil.assertEquals_NoStop((4 + 1) + (4 + 1 - 1), dbTableUtil.queryDbTable_resultList(RoadwayPoint.class).size());

      //_____________

      try {
        mapBuilder.removeMapFile(id_FirstMapFile_ToDelete);
      } catch (FoundNoItemWithIdException e) {
        throw new Error(e);
      }
      em.flush();
      em.clear();
      System.out.println(">> After Remove 1 MapFile");
      System.out.println(mapBuilder.querySomeTable_MapFile_helper());

      //_________________________________________________________________________________________________
      //_________________________________________________________________________________________________________
      //_____________________________________________________________________________________________________________________________________
      //______________________________________________________________________________________________________________________
      //__________________________________________________________________________________________________________

      TestUtil.assertEquals_NoStop(1, dbTableUtil.queryDbTable_resultList(MapFile.class).size());
      TestUtil.assertEquals_NoStop(2, dbTableUtil.queryDbTable_resultList(RoadwayAbstract.class).size());
      TestUtil.assertEquals_NoStop(2 + (2 - 2 + 4 - 1) * 2, dbTableUtil.queryDbTable_resultList(RoadwayDirLinkerComponent.class).size());
      TestUtil.assertEquals_NoStop(2 + (2 - 2 + 4 - 1), dbTableUtil.queryDbTable_resultList(RoadwaySegment.class).size());
      TestUtil.assertEquals_NoStop(4 + 1 - 1, dbTableUtil.queryDbTable_resultList(RoadwayPoint.class).size());

    }

  }

}
