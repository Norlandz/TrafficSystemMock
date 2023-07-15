package com.redfrog.trafficsm.service;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

import javax.imageio.ImageIO;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.logging.log4j.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.redfrog.trafficsm.annotation.BugPotential;
import com.redfrog.trafficsm.annotation.Config;
import com.redfrog.trafficsm.annotation.Debug;
import com.redfrog.trafficsm.annotation.DuplicatedCode;
import com.redfrog.trafficsm.annotation.JustForPerformance;
import com.redfrog.trafficsm.annotation.JustForTest;
import com.redfrog.trafficsm.annotation.Main;
import com.redfrog.trafficsm.annotation.MainImp;
import com.redfrog.trafficsm.annotation.Messy;
import com.redfrog.trafficsm.annotation.MultiThreadPb;
import com.redfrog.trafficsm.annotation.PerformancePotential;
import com.redfrog.trafficsm.annotation.Temporary;
import com.redfrog.trafficsm.annotation.ToUseEntry;
import com.redfrog.trafficsm.annotation.Todo;
import com.redfrog.trafficsm.annotation.UseWithoutSpring;
import com.redfrog.trafficsm.exception.AlreadyExistedException;
import com.redfrog.trafficsm.exception.DuplicatedIdException;
import com.redfrog.trafficsm.exception.FoundNoItemWithIdException;
import com.redfrog.trafficsm.model.MapFile;
import com.redfrog.trafficsm.model.MapFile_;
import com.redfrog.trafficsm.model.TrafficDetector;
import com.redfrog.trafficsm.model.Vehicle;
import com.redfrog.trafficsm.model.VehicleInventory;
import com.redfrog.trafficsm.model.roadway.fundamental.RoadwayAbstract;
import com.redfrog.trafficsm.model.roadway.fundamental.RoadwayAbstract_;
import com.redfrog.trafficsm.model.roadway.fundamental.RoadwayDirLinkerComponent;
import com.redfrog.trafficsm.model.roadway.fundamental.RoadwayDirLinkerComponent_;
import com.redfrog.trafficsm.model.roadway.fundamental.RoadwayPoint;
import com.redfrog.trafficsm.model.roadway.fundamental.RoadwayPoint_;
import com.redfrog.trafficsm.model.roadway.fundamental.RoadwaySegment;
import com.redfrog.trafficsm.model.roadway.fundamental.RoadwaySegment_;
import com.redfrog.trafficsm.model.roadway.fundamental.pseudo.RoadwaySegmentPseudoBegin;
import com.redfrog.trafficsm.model.roadway.main.RoadwayCrossPoint;
import com.redfrog.trafficsm.model.roadway.main.RoadwayCrossPointConnectionDto;
import com.redfrog.trafficsm.model.roadway.main.RoadwayNormalPoint;
import com.redfrog.trafficsm.model.roadway.main.RoadwayNormalSegment;
import com.redfrog.trafficsm.model.roadway.main.RoadwaySolidRoad;
import com.redfrog.trafficsm.repository.FakeEntityManager;
import com.redfrog.trafficsm.root.EntityGeneral_;
import com.redfrog.trafficsm.service.exception.RoadwayDoesNotContainThisSegmentException;
import com.redfrog.trafficsm.session.WindowSession;
import com.redfrog.trafficsm.shape.Point;
import com.redfrog.trafficsm.util.DbSqlUtil;
import com.redfrog.trafficsm.util.DbTableUtil;
import com.redfrog.trafficsm.util.JavafxUtil;
import com.redfrog.trafficsm.util.MathUtil;

import ch.qos.logback.core.pattern.color.BoldGreenCompositeConverter;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.PathElement;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class MapBuilder {

  @UseWithoutSpring
  private final WindowSession windowSession_corr;

  @UseWithoutSpring
  public MapBuilder(WindowSession windowSession_corr) {
    this.windowSession_corr       = windowSession_corr;
    this.em                       = new FakeEntityManager();
    this.windowSessionJavafx      = windowSession_corr.windowSessionJavafx;
    this.mode_NoSpring_NoDatabase = true;
    //_________________________________________________________________________________

    //_________________
    //__________________________________________
    //______________________________________________________________________________________________
    //_
    //_______________________________________________________________________
    //____________________________________________________________________________________________________________________________
    //_____________________________________________________________________________________
    //______________________________________________________________________
    //________________________________________________________________
    //_____________________________
    //_________________________________________________________________________________________________________________________________________________________________________________
    //
    //_________
    //______
    //______________________________________________________________________
    //_______________________________________________________________________________________________________
    //_______________________________________________________________________________________________________
  }

  //___

  private final WindowSessionJavafx windowSessionJavafx;

  //________________________________________________________________________________________
  //___________________________________________________________________

  @Autowired
  public MapBuilder(WindowSessionJavafx windowSessionJavafx) {
    this.windowSession_corr       = null;
    this.windowSessionJavafx      = windowSessionJavafx;
    this.mode_NoSpring_NoDatabase = false;
    //______________________________________________________________

    //_________________
  }

  //___

  //____________
  //________________________________________________
  //
  //____________
  //______________________________________________________

  @PersistenceContext
  private EntityManager em;

  //_______________________________________________
  //_______________________
  //_________________________________________________________________________________________________________________________
  //_____________________________________________________________________________________________________________________

  @Autowired(required = false)
  private DbTableUtil dbTableUtil;

  //____________________________________________________________
  private final boolean mode_NoSpring_NoDatabase;

  //_________
  //______________________________
  //______________________________
  //______________________________
  //______________________________
  //______________________________

  @Transactional //_____________________________________________________
  public LinkedHashMap<RoadwaySolidRoad, List<Point>> createMapDemo01() { return createMapDemo01("D1"); }

  @Transactional
  public LinkedHashMap<RoadwaySolidRoad, List<Point>> createMapDemo01(String nameMap_prepend) {
    newMapFile();

    List<List<Point>> arr_pathArr = new ArrayList<>();
    arr_pathArr.add(Arrays.asList(new Point(100.000000, 90.000000), new Point(190.000000, 160.000000), new Point(900.000000, 160.000000)));
    arr_pathArr.add(Arrays.asList(new Point(860.000000, 60.000000), new Point(800.000000, 160.000000), new Point(800.000000, 650.000000), new Point(800.000000, 700.000000)));
    arr_pathArr.add(Arrays.asList(new Point(920.000000, 630.000000), new Point(610.000000, 630.000000), new Point(530.000000, 660.000000), new Point(80.000000, 400.000000)));
    arr_pathArr.add(Arrays.asList(new Point(100.000000, 500.000000), new Point(370.000000, 130.000000), new Point(500.000000, 40.000000)));
    arr_pathArr.add(Arrays.asList(new Point(240.000000, 70.000000), new Point(940.000000, 420.000000)));
    arr_pathArr.add(Arrays.asList(new Point(930.000000, 350.000000), new Point(50.000000, 350.000000)));
    arr_pathArr.add(Arrays.asList(new Point(850.000000, 40.000000), new Point(500.000000, 500.000000), new Point(500.000000, 650.000000)));
    arr_pathArr.add(Arrays.asList(new Point(540.000000, 490.000000), new Point(280.000000, 40.000000)));
    arr_pathArr.add(Arrays.asList(new Point(880.000000, 240.000000), new Point(350.000000, 240.000000)));
    arr_pathArr.add(Arrays.asList(new Point(830.000000, 410.000000), new Point(140.000000, 400.000000)));
    arr_pathArr.add(Arrays.asList(new Point(160.000000, 30.000000), new Point(90.000000, 200.000000), new Point(50.000000, 360.000000)));
    arr_pathArr.add(Arrays.asList(new Point(900.000000, 535.000000), new Point(450.000000, 530.000000)));
    arr_pathArr.add(Arrays.asList(new Point(280.000000, 550.000000), new Point(280.000000, 130.000000)));
    arr_pathArr.add(Arrays.asList(new Point(670.000000, 300.000000), new Point(670.000000, 650.000000)));
    arr_pathArr.add(Arrays.asList(new Point(360.000000, 230.000000), new Point(360.000000, 430.000000)));
    arr_pathArr.add(Arrays.asList(new Point(520.000000, 420.000000), new Point(520.000000, 230.000000)));

    LinkedHashMap<RoadwaySolidRoad, List<Point>> mpp__roadway_vs_pathArr_point = new LinkedHashMap<>();
    String nameRoadway_prepend = nameMap_prepend + "Ro";
    int i = 0;

    for (List<Point> pathArr_curr : arr_pathArr) {
      i++;
      log.printf(Level.DEBUG, ">> createMapDemo01 %s %3d", nameRoadway_prepend, i);
      RoadwaySolidRoad roadway_curr = this.createRoadway(nameRoadway_prepend + i, pathArr_curr);
      mpp__roadway_vs_pathArr_point.put(roadway_curr, pathArr_curr);
      Platform.runLater(() -> {
        Path path = JavafxUtil.convert_arrPath2pathJfx(pathArr_curr);
        //_______________________________________________________________________
        mapFile.getPaneForRoadway().getChildren().add(path);
      });
    }

    log.debug(">> 2nd createMapDemo lead to find Path is null .. ");
    LinkedList<LinkedList<Entry<RoadwaySolidRoad, List<Point>>>> comb = MathUtil.get_Combination(mpp__roadway_vs_pathArr_point.entrySet(), 2);
    for (LinkedList<Entry<RoadwaySolidRoad, List<Point>>> arr_Group_curr : comb) {
      if (arr_Group_curr.size() != 2) { throw new Error("Comb specified 2 per group, so should be 2"); }
      RoadwaySolidRoad roadway_prev = arr_Group_curr.get(0).getKey();
      RoadwaySolidRoad roadway_curr = arr_Group_curr.get(1).getKey();

      List<RoadwayCrossPointConnectionDto> arr_roadwayCrossPointConnection = this.findAllCrossPoint(roadway_prev, roadway_curr);
      log.debug(arr_roadwayCrossPointConnection);
      //_______________________________
      //____________________________________________________________________________________________________________________________________________________________________________________________________________________________
      //_________
      this.connectRoadwayAtCrossPointConnection(arr_roadwayCrossPointConnection);
    }

    return mpp__roadway_vs_pathArr_point;
  }

  public String getMapImg() {
    //
    SnapshotParameters snapshotParameters = new SnapshotParameters();
    snapshotParameters.setFill(Color.TRANSPARENT);
    //
    FutureTask<WritableImage> future = new FutureTask<>(() -> {
      return windowSessionJavafx.panel_SemanticRoot.snapshot(snapshotParameters, null);
    });
    Platform.runLater(future);
    WritableImage writableImage;
    try {
      writableImage = future.get();
    } catch (InterruptedException e) {
      throw new Error(e);
    } catch (ExecutionException e) {
      throw new Error(e);
    }
    //
    BufferedImage bufferedImage = SwingFXUtils.fromFXImage(writableImage, null);
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    try {
      ImageIO.write(bufferedImage, "png", byteArrayOutputStream);
    } catch (IOException e) {
      throw new Error(e);
    }
    byte[] imgByte = byteArrayOutputStream.toByteArray();
    String imgStr = Base64.getEncoder().encodeToString(imgByte);
    return imgStr;
  }

  //___

  //_________

  @Todo
  @Getter
  private MapFile mapFile; //__________________

  //________________________________
  //_________
  //___________________________________________________________________________

  //_________________________________________________________________________________________________
  @Getter
  private VehicleInventory vehicleInventory = new VehicleInventory();

  //_________
  //_________________________________________________________________________________________________

  //_________

  @Transactional
  public Collection<Vehicle> getGpVehicleInVehicleInventory() { return vehicleInventory.getMppVehicle().values(); }

  @Transactional
  public Collection<Vehicle> getMppVehicleInMap() { return mapFile.getMppVehicleInMap().values(); }

  @Transactional
  public Set<TrafficDetector> getGpTrafficDetector() { return mapFile.getGpTrafficDetector(); }

  //_________

  @Debug
  @Getter
  @Setter
  private Vehicle vehicleSelected;
  //_________
  //______________________________

  public void listen_SelectVehicle(Vehicle vehicle) {
    //____________________________
    vehicle.getPaneWrap().addEventHandler(MouseEvent.MOUSE_PRESSED, (event) -> {
      if (!event.isConsumed()) {
        if (!event.isControlDown() && !event.isAltDown() && !event.isShiftDown() && event.isPrimaryButtonDown()) {
          event.consume();
          log.debug(">> listen_SelectVehicle() :: " + vehicle);
          @Messy byte dmy970; //_
          vehicleSelected = vehicle;
          //___________________________________________________________________________________
          //___________________________________________________________________________________________________________________________
          //________________________
        }
      }
    });

  }

  //_________

  public MapFile newMapFile() {
    closeCurrentFile();

    //_________________________________________________
    //_________________________
    //___________
    MapFile mapFile_L = new MapFile();
    mapFile = mapFile_L;

    @MultiThreadPb AnchorPane paneWrap = mapFile_L.getPaneWrap();
    Pane panel_SemanticRoot_L = windowSessionJavafx.panel_SemanticRoot;
    if (panel_SemanticRoot_L == null) { throw new Error(); }
    if (paneWrap == null) { throw new Error(); }
    Platform.runLater(() -> {
      panel_SemanticRoot_L.getChildren().add(paneWrap); //____________
    });

    return mapFile_L;
  }

  @Todo
  @Transactional
  public MapFile loadMapFile(long idSql) throws FoundNoItemWithIdException, AlreadyExistedException {
    log.debug(">> loadMapFile({})", idSql);
    //_______________________________________________________________________
    if (mapFile.getIdSql() == null) {
      //_____________
    }
    else if (mapFile.getIdSql() == idSql) {
      //_________________________________________________________________________________________________________
      //____________________________
      throw new AlreadyExistedException(mapFile);
    }

    MapFile mapFile_L = em.find(MapFile.class, idSql);
    if (mapFile_L == null) {
      log.info(">> loadMapFile({}) -- no such MapFile", idSql);
      //__________________
      throw new FoundNoItemWithIdException("No such MapFile");
    }

    closeCurrentFile();
    mapFile = mapFile_L;
    //_______________________________________________________________________________________________________________________
    //_____________________________________________________
    //_____________________________________________________________________________
    //_________________________________________________________________________
    //________________________________

    @MultiThreadPb AnchorPane paneWrap = mapFile_L.getPaneWrap();
    Pane panel_SemanticRoot_L = windowSessionJavafx.panel_SemanticRoot;

    //__________
    Set<RoadwaySolidRoad> gpRoadwaySolidRoad = mapFile_L.getGpRoadwaySolidRoad();
    for (RoadwaySolidRoad roadwaySolidRoad_curr : gpRoadwaySolidRoad) {
      List<RoadwayDirLinkerComponent> arrRoadwayDirLinkerComponent_curr = roadwaySolidRoad_curr.getArrRoadwayDirLinkerComponent();
      //___________________________________________________
      Path path = convert_arrPath2pathJfx(arrRoadwayDirLinkerComponent_curr);
      Platform.runLater(() -> {
        mapFile.getPaneForRoadway().getChildren().add(path);
      });
    }

    //_______________
    Set<TrafficDetector> gpTrafficDetector = mapFile_L.getGpTrafficDetector();
    for (TrafficDetector trafficDetector_curr : gpTrafficDetector) {
      Platform.runLater(() -> {
        mapFile.getPaneForTrafficDetector().getChildren().add(trafficDetector_curr.getPaneWrap());
      });
    }

    //____________________
    //__________________________________________________________________
    Map<Long, Vehicle> mppVehicleInMap = mapFile_L.getMppVehicleInMap();
    for (Vehicle vehicle_curr : mppVehicleInMap.values()) {
      Platform.runLater(() -> {
        mapFile.getPaneForVehicle().getChildren().add(vehicle_curr.getPaneWrap());
        listen_SelectVehicle(vehicle_curr); //__________________________________________________________
      });
    }

    Platform.runLater(() -> {
      panel_SemanticRoot_L.getChildren().add(paneWrap);
    });

    //____________________________________________________________________________
    return mapFile_L;
  }

  //_____
  //_________________________________________
  //______________________________________________________________________
  //___________________________________________________________________
  //_______________________________________________________

  @JustForPerformance
  @DuplicatedCode
  private static Path convert_arrPath2pathJfx(List<RoadwayDirLinkerComponent> arrRoadwayDirLinkerComponent_curr) {
    Path path = new Path();
    path.setStrokeWidth(1.0);
    path.setStroke(JavafxUtil.color_Blue);

    ObservableList<PathElement> pele = path.getElements();
    int j = 0;
    for (RoadwayDirLinkerComponent linker_Goto_curr : arrRoadwayDirLinkerComponent_curr) {
      j++;
      Point point_Goto_curr = linker_Goto_curr.getRoadwayPoint().getPointLocation();
      if (j == 1) {
        pele.add(new MoveTo(point_Goto_curr.getX(), point_Goto_curr.getY()));
      }
      else {
        pele.add(new LineTo(point_Goto_curr.getX(), point_Goto_curr.getY()));
      }
    }

    return path;
  }

  @MultiThreadPb //_______________________________________
  @Transactional
  public MapFile saveMapFile() {
    //________________________
    //______________________________________________________________________________________________________________
    //_____________________
    //____________________
    //________________________________
    //_____________________________________________________________________________________________________________________________
    //________________________________
    //_____________________________________________________________________________________________________________________________________________________________________________________
    @Messy byte dmy179; //_________________________
    if (mapFile.getIdSql() == null) {
      em.persist(mapFile);
    }
    else {
      em.merge(mapFile);
    }
    return mapFile;
  }

  //________________________________________________________________________
  //_______________________________________________________________________________________________________________
  //_________________________________________________
  //_______________________________________________________
  //__________________________________________________________________
  //__________________
  public void closeCurrentFile() {
    log.debug(">> closeCurrentFile()");

    //_________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________
    //_______________________________________________________________________________________________
    //_______________________________________________
    //_____________________________________________________________________________________
    //_________________________________________________________
    //_____________________________________________________________________________
    //___________________________________________
    //___________________________________________
    //__________________________
    //___
    //_________________________________________________________
    //______________________________________________________________________________________________________________________________________________________
    if (mapFile != null) {
      @MultiThreadPb AnchorPane paneWrap = mapFile.getPaneWrap();
      Pane panel_SemanticRoot_L = windowSessionJavafx.panel_SemanticRoot;
      Platform.runLater(() -> {
        panel_SemanticRoot_L.getChildren().remove(paneWrap);
      });
      mapFile = null;
    }
    else {
      log.debug(">> closeCurrentFile() :: mapFile == null");
    }
  }
  //___________________________________________________________________________________________________________

  @Transactional
  public int removeMapFile(long idSql) throws FoundNoItemWithIdException {
    //___________________________________________
    //__________________________

    //____________________
    //_________________________________________________________________________________________________________________
    //_______________________________________
    //_________________________________

    @Messy byte dmy774; //_____________________________________________
    int amount_RowAffected = 0;

    //_____________________________________________________________________________________________________________________________________

    //________________________________________________________
    //______________________________________________
    //____________________________________________
    //_____________________________________________________________________________
    //__________________________________________________________________________________________
    //____________________________________________________________________________________
    //
    //________________________________
    //___________________________
    //____________________________
    //
    //____________________________________________________________________________________________
    //_____________________________________________
    //
    //____________________________________
    //_________________________________________________________________
    //__________________________________________
    //__
    //__________________________________________________________________
    //____________________________________________
    //_______________________________________________________________________________________

    String vN_idSql = EntityGeneral_.ID_SQL;
    //_______________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________
    String queryStr = "select "
                      + "" + "MM." + vN_idSql + ""
                      + "" + ", RR." + vN_idSql + ""
                      + "" + ", DD." + vN_idSql + "" //_____________________________________________________________________________________________
                      + "" + ", PTD." + vN_idSql + ""
                      + "" + ", DD." + RoadwayDirLinkerComponent_.ROADWAY_SEGMENT + "." + vN_idSql + ""
                      + "" + ", PP." + vN_idSql + ""
                      + "" + " "
                      + "\n" + "from " + MapFile.class.getSimpleName() + " MM "
                      + "\n" + "inner join MM." + MapFile_.GP_ROADWAY_SOLID_ROAD + " RR "
                      + "\n" + "inner join RR." + RoadwayAbstract_.ARR_ROADWAY_DIR_LINKER_COMPONENT + " DD "
                      + "\n" + "inner join DD." + RoadwayDirLinkerComponent_.ROADWAY_POINT + " PP "
                      + "\n" + "inner join PP." + RoadwayPoint_.MPP_ROADWAY_DIR_LINKER_COMPONENT + " PTD "
                      + "\n" + "where MM." + vN_idSql + " = :jp_idSql " //________________________________________________
                      + "";
    List resultList = em.createQuery(queryStr)
                        .setParameter("jp_idSql", idSql)
                        .getResultList();

    if (resultList.isEmpty()) {
      throw new FoundNoItemWithIdException("Didnt find any row, is the MapFile id correct? :: " + idSql);
    }
    else {
      List<Class> arr_ClassParam = new ArrayList<>();
      arr_ClassParam.add(MapFile.class);
      arr_ClassParam.add(RoadwayAbstract.class);
      arr_ClassParam.add(RoadwayDirLinkerComponent.class); //_______
      arr_ClassParam.add(RoadwayDirLinkerComponent.class);
      arr_ClassParam.add(RoadwaySegment.class);
      arr_ClassParam.add(RoadwayPoint.class);

      Object[][] array = (Object[][]) resultList.toArray(new Object[0][]);
      List<List<Long>> arrayTranspose = MathUtil.transposeAndConvertToLong(array);       //_____________________________________________________________________
      //__________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________

      //_____________________________________________________________________________________

      //_____________________________________________________________
      //______________________________________________________________________________________
      //___________________________________________________________________________________________________________________________________________________________________
      //_______________________________________________________________

      int sn_table = 0;
      for (List<Long> arr_idSql : arrayTranspose) {
        sn_table++;
        String qq = "delete from " + arr_ClassParam.get(sn_table - 1).getSimpleName() + " EE "
                    + "\n" + "where EE." + vN_idSql + " in (:jp_arr_idSql)";

        System.out.println(arr_idSql);
        System.out.println(qq);
        if (dbTableUtil != null) {
          System.out.println(dbTableUtil.queryDbTableAsStr_selectAll(RoadwayDirLinkerComponent.class));
          System.out.println(dbTableUtil.queryDbTableAsStr_selectAll(RoadwaySegment.class));
        }
        else {
          System.err.println(">> dbTableUtil is null");
        }

        //_____________
        //________________________________________________________
        //_________________________
        //_________________________
        //_______________________________________________________________________________________________________________
        //________________________________________________________________________

        amount_RowAffected += em.createQuery(qq)
                                .setParameter("jp_arr_idSql", arr_idSql)
                                .executeUpdate();
      }

      //___________________________________________________________________________________
      //___________________________________________________________________________________________________________________________________________________________
      //______________________________________________________________________________________
      //___________________________________________
      //_______________________

      //_________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________
      //______________________________________________________________________________________________
      //___________________________________________________

      //____________________________
      //_________________________
      //__________________________________________________________________________________
      //____________________________________

    }

    return amount_RowAffected;

  }

  //_____

  //_______________________________
  @Transactional //________________________________________
  @JustForTest
  @Debug
  public String querySomeTable_MapFile_helper() {
    //_____
    String result = "";
    result += "\n" + dbTableUtil.queryDbTableAsStr_selectAll(MapFile.class);
    result += "\n" + dbTableUtil.queryDbTableAsStr_selectAll(RoadwayAbstract.class);
    result += "\n" + dbTableUtil.queryDbTableAsStr_selectAll(RoadwayDirLinkerComponent.class);
    result += "\n" + dbTableUtil.queryDbTableAsStr_selectAll(RoadwaySegment.class);
    result += "\n" + dbTableUtil.queryDbTableAsStr_selectAll(RoadwayPoint.class);

    //_____________________________________________________________________________________
    //________________________________________________________________________________________________________
    //__________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________
    //________________________________________________
    //____________________________________________________________________________________________________________________________________________
    try {
      result += "\n" + dbTableUtil.queryDbTableAsStr_selectAll(DbTableUtil.ca2sn(MapFile.class.getSimpleName() + "_" + MapFile_.GP_ROADWAY_SOLID_ROAD));
      result += "\n" + dbTableUtil.queryDbTableAsStr_selectAll(DbTableUtil.ca2sn(RoadwayAbstract.class.getSimpleName() + "_" + RoadwayAbstract_.ARR_ROADWAY_DIR_LINKER_COMPONENT));
    } catch (Exception e) {
      result += "\n" + dbTableUtil.queryDbTableAsStr_selectAll(DbTableUtil.ca2sn(MapFile.class.getSimpleName() + "_" + RoadwayAbstract.class.getSimpleName()));
      result += "\n" + dbTableUtil.queryDbTableAsStr_selectAll(DbTableUtil.ca2sn(RoadwayAbstract.class.getSimpleName() + "_" + RoadwayDirLinkerComponent.class.getSimpleName()));
      throw e;
    }

    @Messy byte dmy881; //_
    //___
    //___________________________________________
    //__________________________________________________________________________________________________________
    //____________________________________________________________________________________________________________________________________________________________________________
    //______________________________________________________________________________________________________________________________________________________________________________
    //______________________________________________________________________________________________________________________________________________
    //
    //___
    //____________________________________________________________________________________________________________
    //________________________________________________________________________________
    //_____

    //_____
    //___________________________________________________________________________________________________________________________________________________________________
    //_______________________________
    //__________________________________________________________
    //_____________________________
    //_____________________________________________________________________________________________________________________
    //__________________________________________________________________________________________________
    //___________________________________________________________________________________________________
    //______________________________________________________________________________________________
    //____________________________________________________________________________________________________________
    //________________________________________________________________________________
    //__________
    //__________
    //________________________________________________
    //____________________________________________________________________________________
    //________________________________________________________________
    //_______________________________________________________________________
    //___________
    //
    //_________________________________________________________
    //______
    //_______________________________________________________
    //_____________________________________
    //_______________________________________________
    //_________________________________________________
    //________________________________________________________________

    result += "\n" + dbTableUtil.queryDbTableAsStr("SELECT MM, RR FROM " + MapFile.class.getSimpleName() + " MM INNER JOIN MM." + MapFile_.GP_ROADWAY_SOLID_ROAD + " RR", false);
    result += "\n" + dbTableUtil.queryDbTableAsStr("SELECT RR, DD FROM " + RoadwayAbstract.class.getSimpleName() + " RR INNER JOIN RR." + RoadwayAbstract_.ARR_ROADWAY_DIR_LINKER_COMPONENT + " DD", false);
    result += "\n" + dbTableUtil.queryDbTableAsStr("SELECT DD, SS FROM " + RoadwayDirLinkerComponent.class.getSimpleName() + " DD INNER JOIN DD." + RoadwayDirLinkerComponent_.ROADWAY_SEGMENT + " SS", false);
    result += "\n" + dbTableUtil.queryDbTableAsStr("SELECT DD, PP FROM " + RoadwayDirLinkerComponent.class.getSimpleName() + " DD INNER JOIN DD." + RoadwayDirLinkerComponent_.ROADWAY_POINT + " PP", false);

    @Main byte dmy798; //_

    //_____________________________________________________________________________
    //__________________________________________________________________________________________________________
    //____________________________________________________________________________________________________________________
    //______________________________________________________________________________________________________________________________________
    //________________
    //__________________________________________________________
    //_______________________________________________________________________________________________________________________________
    //_____________________________________________________________
    //
    //_____________________________________________________________________________
    //__________________________________________________________________________________________________________
    //____________________________________________________________________________________________________________________
    //_____________________________________________________________________________________________________________________________________________________________________________
    //________________
    //__________________________________________________________
    //________________________________________________________________________________________________________________________________________________________________________________________________________
    //______________
    //
    //_________________________________________________________
    //__________________________________________________________________________________________________
    //____________________________________________________________________________________________________________
    //______________________________________________________________________________________________________________________________
    //________
    //__________________________________________________
    //_________________________________________________________________________________________________________________________
    //
    //_________________________________________________________________
    //__________________________________________________________________________________________________________
    //____________________________________________________________________________________________________________________
    //______________________________________________________________________________________________________________________________________
    //________________
    //__________________________________________________________
    //______________________________________________________________________________________________________________________________________________
    //______________________________________________
    //__________________________________________________________________
    //___________________________________
    //____________________________________________
    //
    //_____________________________________________________________________
    //__________________________________________________________________________________________________________
    //____________________________________________________________________________________________________________________
    //______________________________________________________________________________________________________________________________________
    //________________
    //__________________________________________________________
    //______________________________________________________
    //________
    //_____________________________________________________________________
    //__________________________________________________________________________________________________________
    //____________________________________________________________________________________________________________________
    //______________________________________________________________________________________________________________________________________
    //_______________________________________________________________________________________________________________________________
    //________________
    //__________________________________________________________
    //___________________________________________________________
    //________
    //_____________________________________________________________________
    //__________________________________________________________________________________________________________
    //____________________________________________________________________________________________________________________
    //______________________________________________________________________________________________________________________________________
    //_______________________________________________________________________________________________________________________________
    //________________
    //__________________________________________________________
    //________
    //_____________________________________________________________________
    //__________________________________________________________________________________________________________
    //____________________________________________________________________________________________________________________
    //______________________________________________________________________________________________________________________________________
    //_____________________________________________________________________________________________________________________________
    //________________
    //__________________________________________________________
    //________
    //______________________________________________________________________________________
    //_____________________________________________________________________________
    //
    //_________________________________________________________________________________________________________________________________________________________________________
    //__________________________________________________________________________________________________
    //____________________________________________________________________________________________________________
    //_______________________________________________________________________________________________________________________________
    //________
    //__________________________________________________
    //
    //________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________

    //___________________________________________________________________________________________________________________________________________________________
    //_________________________________________________________________________________________________
    //__________________
    //______________________________

    //__________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________
    //______________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________

    result += "\n" + dbTableUtil.queryDbTableAsStr("select MM, RR, DD, PTD, DD." + RoadwayDirLinkerComponent_.ROADWAY_SEGMENT + ", PP "
                                                   + "\n" + "from " + MapFile.class.getSimpleName() + " MM "
                                                   + "\n" + "inner join MM." + MapFile_.GP_ROADWAY_SOLID_ROAD + " RR "
                                                   + "\n" + "inner join RR." + RoadwayAbstract_.ARR_ROADWAY_DIR_LINKER_COMPONENT + " DD "
                                                   + "\n" + "inner join DD." + RoadwayDirLinkerComponent_.ROADWAY_POINT + " PP "
                                                   + "\n" + "inner join PP." + RoadwayPoint_.MPP_ROADWAY_DIR_LINKER_COMPONENT + " PTD " //__________________________________________________
    //
                                                   , false);

    //_________________________________________________________________________

    return result.replaceAll("(?m)^", "    ");
  }

  //_____________________________________
  //_____________________________________
  //_____________________________________
  //_____________________________________
  //_____________________________________
  //_________

  @Config
  public static final double marginOfError_UserFuzzyLocate = 15; //___

  //_____________________________________________________________________________________
  @Config
  public static final double marginOfError_ProgramAccurateLocate = 1E-4; //______________________________

  //___________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________

  //_______________________________________________________________________________________________________________________
  //_________________________________________________________

  @ToUseEntry
  @Main
  @Transactional
  //___________________________________________________
  public RoadwaySolidRoad createRoadway(String idBsi, List<Point> pathArr_point) {
    if (pathArr_point == null) { throw new Error(); }
    if (pathArr_point.size() < 2) { throw new Error(); }

    RoadwaySolidRoad roadway = new RoadwaySolidRoad(idBsi);
    List<RoadwayDirLinkerComponent> arr_roadwayLinkerComponent = roadway.getArrRoadwayDirLinkerComponent();

    int i = 0;
    RoadwayNormalPoint roadwayNormalPoint_prev = null;
    for (Point point_curr : pathArr_point) {
      i++;
      log.printf(Level.DEBUG, ">> createRoadway %30s %3d", point_curr, i);
      if (point_curr == null) { throw new Error(); }
      RoadwayNormalPoint roadwayNormalPoint_curr = new RoadwayNormalPoint(idBsi + "-" + i, point_curr);
      em.persist(roadwayNormalPoint_curr);
      if (i == 1) {
        //____________________________________________________________________________________________________________________________________________________________________________________
        //____________________________________________________________________________________________________________________________________________________________
        //_______________________________________________________________________________________
        //__________________________________________
        //_____________________________________________________________________________________________________________________________________________________________________________________________________________________________________
        //_________________________________________________________________________________________________________________________________________

        RoadwaySegmentPseudoBegin roadwaySegment_btPrevCurr = new RoadwaySegmentPseudoBegin(roadwayNormalPoint_curr, roadwayNormalPoint_curr);
        em.persist(roadwaySegment_btPrevCurr);

        RoadwayDirLinkerComponent roadwayLinkerComponent_btPrevToCurr = new RoadwayDirLinkerComponent(roadwaySegment_btPrevCurr, roadwayNormalPoint_curr);
        em.persist(roadwayLinkerComponent_btPrevToCurr);

        arr_roadwayLinkerComponent.add(roadwayLinkerComponent_btPrevToCurr);
      }
      else {
        RoadwayNormalSegment roadwaySegment_btPrevCurr = new RoadwayNormalSegment(roadwayNormalPoint_prev, roadwayNormalPoint_curr);
        em.persist(roadwaySegment_btPrevCurr);

        RoadwayDirLinkerComponent roadwayLinkerComponent_btPrevToCurr = new RoadwayDirLinkerComponent(roadwaySegment_btPrevCurr, roadwayNormalPoint_curr);
        RoadwayDirLinkerComponent roadwayLinkerComponent_btCurrToPrev = new RoadwayDirLinkerComponent(roadwaySegment_btPrevCurr, roadwayNormalPoint_prev);
        em.persist(roadwayLinkerComponent_btPrevToCurr);
        em.persist(roadwayLinkerComponent_btCurrToPrev);

        roadwayNormalPoint_prev.getMppRoadwayDirLinkerComponent().put(roadwayNormalPoint_curr, roadwayLinkerComponent_btPrevToCurr);
        roadwayNormalPoint_curr.getMppRoadwayDirLinkerComponent().put(roadwayNormalPoint_prev, roadwayLinkerComponent_btCurrToPrev);

        //________________________________
        arr_roadwayLinkerComponent.add(roadwayLinkerComponent_btPrevToCurr);
      }
      roadwayNormalPoint_prev = roadwayNormalPoint_curr;
    }

    log.printf(Level.DEBUG, ">> em.persist(roadway) %30s", idBsi);
    em.persist(roadway);
    //____________________________________________________________________________
    //____________________

    //___________________________________________________

    if (!mapFile.getGpRoadwaySolidRoad().add(roadway)) { throw new DuplicatedIdException("Duplicate road id detected"); }

    return roadway;
  }

  //________________________________________________________________
  //____________________________________________

  //____________________________________________________________

  //_______________________________________________________________________________
  //_____________________________

  //________________________________________________________________________________________________________
  //________________________________
  //______________________________________________________________________________________

  //____________________________________________

  //_______________________

  @Main
  @Messy //__________________________________
  public List<RoadwayCrossPointConnectionDto> findAllCrossPoint(RoadwaySolidRoad roadway_AA, RoadwaySolidRoad roadway_BB) {
    ArrayList<RoadwayCrossPointConnectionDto> arr_AllCrossPoint = new ArrayList<>();

    List<RoadwayDirLinkerComponent> arr_roadwayDirLinkerComponent_AA = roadway_AA.getArrRoadwayDirLinkerComponent();
    List<RoadwayDirLinkerComponent> arr_roadwayDirLinkerComponent_BB = roadway_BB.getArrRoadwayDirLinkerComponent();

    HashSet<Point> arr_FoundedCrossPoint__dedup_when_2SegmentEsEnd_AlreadyAtACrossPoint = new HashSet<>();

    //
    int i = 0;
    for (RoadwayDirLinkerComponent roadwayDirLinkerComponent_AA_curr : arr_roadwayDirLinkerComponent_AA) {
      i++;
      @Messy //______________________________________________________________________________________________________
      RoadwaySegment roadwaySegment_AA_curr_cast = roadwayDirLinkerComponent_AA_curr.getRoadwaySegment();
      if (i == 1) {
        //____________
        if (!(roadwaySegment_AA_curr_cast instanceof RoadwaySegmentPseudoBegin)) { throw new Error("Should be RoadwaySegmentPseudoBegin"); }
        continue;
      }
      RoadwayNormalSegment roadwaySegment_AA_curr = (RoadwayNormalSegment) roadwaySegment_AA_curr_cast;
      Point point_AA = roadwaySegment_AA_curr.getRoadwayPointSp().getPointLocation();
      Point point_BB = roadwaySegment_AA_curr.getRoadwayPointNp().getPointLocation();

      //
      int j = 0;
      for (RoadwayDirLinkerComponent roadwayDirLinkerComponent_BB_curr : arr_roadwayDirLinkerComponent_BB) {
        j++;
        RoadwaySegment roadwaySegment_BB_curr_cast = roadwayDirLinkerComponent_BB_curr.getRoadwaySegment();
        if (j == 1) {
          //____________
          if (!(roadwaySegment_BB_curr_cast instanceof RoadwaySegmentPseudoBegin)) { throw new Error("Should be RoadwaySegmentPseudoBegin"); }
          continue;
        }
        RoadwayNormalSegment roadwaySegment_BB_curr = (RoadwayNormalSegment) roadwayDirLinkerComponent_BB_curr.getRoadwaySegment();
        Point point_CC = roadwaySegment_BB_curr.getRoadwayPointSp().getPointLocation();
        Point point_DD = roadwaySegment_BB_curr.getRoadwayPointNp().getPointLocation();

        //_________________________________________________________________________________________________

        //________
        Point point_MM = MathUtil.findPointAtTwoLineIntersection(point_AA, point_BB, point_CC, point_DD);
        if (point_MM != null) {

          //___
          //____________
          //____________________________________________________________________________________________________________________________________________
          //_______________________________________________________________________________
          //_______________________________________________________________________________
          //_____________________________________________________________________________________________________

          //___________________________________________________
          if (arr_FoundedCrossPoint__dedup_when_2SegmentEsEnd_AlreadyAtACrossPoint.add(point_MM)) {
            RoadwayCrossPointConnectionDto roadwayCrossPointConnection;

            boolean det_ExistingCrossPoint = false;
            RoadwayCrossPoint roadwayCrossPoint_existing = null;
            @BugPotential //_______________________________________________________________________________________________________________________________________
            @MultiThreadPb byte multi_threading;
            for (RoadwayCrossPoint roadwayCrossPoint_curr : mapFile.getGRoadwayCrossPoint()) {
              //_________________________________________________________________________________
              if (roadwayCrossPoint_curr.getPointLocation().distance(point_MM) <= marginOfError_ProgramAccurateLocate) {
                det_ExistingCrossPoint     = true;
                roadwayCrossPoint_existing = roadwayCrossPoint_curr;
                break;
              }
            }

            if (det_ExistingCrossPoint) {
              roadwayCrossPointConnection = new RoadwayCrossPointConnectionDto(roadwayCrossPoint_existing, roadway_AA, roadway_BB);
            }
            else {
              roadwayCrossPointConnection = new RoadwayCrossPointConnectionDto(point_MM, roadwaySegment_AA_curr, roadwaySegment_BB_curr, roadway_AA, roadway_BB);
            }

            arr_AllCrossPoint.add(roadwayCrossPointConnection);
          }

        }

      }
    }

    return arr_AllCrossPoint;
  }

  //_________________________________________
  /**
____________________________________________________________________________________________
__*/
  //________________________________________
  //______________________________________________________________________________________________________________________________________________________________________________________________________
  public List<RoadwayCrossPointConnectionDto> connectRoadwayAtCrossPointConnection(List<RoadwayCrossPointConnectionDto> arr_CrossPointToConnect) {
    log.debug(">> connectRoadwayAtCrossPointConnection()");
    log.debug(arr_CrossPointToConnect);
    ArrayList<RoadwayCrossPointConnectionDto> arr_roadwayCrossPointConnectionDto_Existing = new ArrayList<>();
    for (RoadwayCrossPointConnectionDto roadwayCrossPointConnectionDto_curr : arr_CrossPointToConnect) {
      @Messy byte messy; //___________________________________________________________________
      if (roadwayCrossPointConnectionDto_curr.getRoadwayCrossPoint() == null) {
        //__________________________________________________
        try {
          connectRoadwayAtExactSegment(roadwayCrossPointConnectionDto_curr);
        } catch (RoadwayDoesNotContainThisSegmentException e) {
          //_______________________________________
          //___________________________________________________________________________________________________
          List<RoadwayCrossPointConnectionDto> arr_AllCrossPoint = findAllCrossPoint(roadwayCrossPointConnectionDto_curr.getRoadwayAp(), roadwayCrossPointConnectionDto_curr.getRoadwayBp());
          boolean found = false;
          for (RoadwayCrossPointConnectionDto roadwayCrossPointConnectionDto_02_curr : arr_AllCrossPoint) {
            //__________________________________________________
            //_________________________________________________________________________________________________________________________________________________________________________________________________________
            if (roadwayCrossPointConnectionDto_02_curr.getPointLocationEventually().equals(roadwayCrossPointConnectionDto_curr.getPointLocation())) {
              if (roadwayCrossPointConnectionDto_02_curr.getRoadwayCrossPoint() == null) {
                found = true;
                try {
                  connectRoadwayAtExactSegment(roadwayCrossPointConnectionDto_02_curr);
                } catch (RoadwayDoesNotContainThisSegmentException e1) {
                  throw new RuntimeException("2nd try update & connect still fail? why? ... should this / how to make this atomic ope?");
                }
              }
              else {
                throw new Error("Found, but its already connected?");
              }
            }
          }
          if (!found) { throw new RuntimeException("Not found? why..."); }
        }
      }
      else {
        //____________________________________________________________________________
        //_____________________________________________________________________________________________________
        arr_roadwayCrossPointConnectionDto_Existing.add(roadwayCrossPointConnectionDto_curr);
      }
    }
    return arr_roadwayCrossPointConnectionDto_Existing;
  }

  @Debug
  HashSet<String> gp_idBsiCrossPoint_debug = new HashSet<String>();

  //________________________________________________________________________
  //______________________________________________________

  //________________________________

  //______________________________________________________
  //__________________
  //__________________________________________________________________________________________________________________________
  @MultiThreadPb
  //____________________________________
  //________________________________________________
  //___________________________________________________________________

  @Main
  @MainImp
  @Transactional //______________________________
  //_______________________________________________________________________________________________________________________________
  //__________________________________________________________________________________________________________________________________________________________________
  public void connectRoadwayAtExactSegment(RoadwayCrossPointConnectionDto roadwayCrossPointConnection) throws RoadwayDoesNotContainThisSegmentException {
    log.debug(">> connectRoadwayAtExactSegment()");
    log.debug(roadwayCrossPointConnection);

    //____________________________________________________

    //_____
    RoadwaySolidRoad roadway_AA = roadwayCrossPointConnection.getRoadwayAp();
    RoadwaySolidRoad roadway_BB = roadwayCrossPointConnection.getRoadwayBp();
    //______________________________________________
    //______________________________________________

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
    //________________________________________
    for (RoadwayDirLinkerComponent roadwayDirLinkerComponent_BB_curr : arr_roadwayDirLinkerComponent_BB) {
      //________________________________________________________________________________________
      if (roadwayDirLinkerComponent_BB_curr.getRoadwaySegment() == roadwaySegment_BB) { break; }
      ind_roadwaySegment_BB++;
    }
    if (ind_roadwaySegment_BB == arr_roadwayDirLinkerComponent_BB.size()) { throw new RoadwayDoesNotContainThisSegmentException("Maybe this RoadwayCrossPointConnectionDto is stale (after an prev cross connection) :: " + roadwayCrossPointConnection); }
    //__________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________

    //_____

    RoadwayPoint point_Seg_AA_Pt_A = roadwaySegment_AA.getRoadwayPointSp();
    RoadwayPoint point_Seg_AA_Pt_B = roadwaySegment_AA.getRoadwayPointNp();
    RoadwayPoint point_Seg_BB_Pt_A = roadwaySegment_BB.getRoadwayPointSp();
    RoadwayPoint point_Seg_BB_Pt_B = roadwaySegment_BB.getRoadwayPointNp();

    //______________________________________________
    //_________________________________________
    //____________________________________________
    //______________________________________________
    //___________________________________________________________
    //___________
    //_______

    Map<RoadwayPoint, RoadwayDirLinkerComponent> mpp_roadwayDirLinkerComponent_Seg_AA_Pt_A = point_Seg_AA_Pt_A.getMppRoadwayDirLinkerComponent();
    Map<RoadwayPoint, RoadwayDirLinkerComponent> mpp_roadwayDirLinkerComponent_Seg_AA_Pt_B = point_Seg_AA_Pt_B.getMppRoadwayDirLinkerComponent();
    Map<RoadwayPoint, RoadwayDirLinkerComponent> mpp_roadwayDirLinkerComponent_Seg_BB_Pt_A = point_Seg_BB_Pt_A.getMppRoadwayDirLinkerComponent();
    Map<RoadwayPoint, RoadwayDirLinkerComponent> mpp_roadwayDirLinkerComponent_Seg_BB_Pt_B = point_Seg_BB_Pt_B.getMppRoadwayDirLinkerComponent();

    RoadwayDirLinkerComponent roadwayDirLinkerComponent_Seg_AA_btAtoB = mpp_roadwayDirLinkerComponent_Seg_AA_Pt_A.remove(point_Seg_AA_Pt_B);
    RoadwayDirLinkerComponent roadwayDirLinkerComponent_Seg_AA_btBtoA = mpp_roadwayDirLinkerComponent_Seg_AA_Pt_B.remove(point_Seg_AA_Pt_A);
    RoadwayDirLinkerComponent roadwayDirLinkerComponent_Seg_BB_btAtoB = mpp_roadwayDirLinkerComponent_Seg_BB_Pt_A.remove(point_Seg_BB_Pt_B);
    RoadwayDirLinkerComponent roadwayDirLinkerComponent_Seg_BB_btBtoA = mpp_roadwayDirLinkerComponent_Seg_BB_Pt_B.remove(point_Seg_BB_Pt_A);

    //____________________________________________________________________
    //____________________________________________
    //_________________________________________________
    //______________________________________________________________________________________________________________________________
    //__________________________
    //______________________________________________________
    //______________________________________________________
    //________________________________
    //_________________________________________________________________________________________________
    //___________________________________________________________________________________________________________________________________________________
    //_______________________________________________
    //_________________________________________________________________________________________________________________________
    //_____________________________________________________________________________________________________________________________
    //_________________________________________________________________________________________________________________________
    //_____________________________________________________________________________________________________________________________
    //_____________________________________________________________
    if (roadwayDirLinkerComponent_Seg_AA_btAtoB == null) { throw new Error("that must be its nearby point & must exist || the stale update pb aga?.. "); }
    if (roadwayDirLinkerComponent_Seg_AA_btBtoA == null) { throw new Error("that must be its nearby point & must exist || the stale update pb aga?.. "); }
    if (roadwayDirLinkerComponent_Seg_BB_btAtoB == null) { throw new Error("that must be its nearby point & must exist || the stale update pb aga?.. "); }
    if (roadwayDirLinkerComponent_Seg_BB_btBtoA == null) {

      //___________________________________________________________________________
      //___________________________________________________________________________
      //___________________________________________________________________________
      //___________________________________________________________________________
      //___________________________________________________
      //___________________________________________________
      //___________________________________________________
      //___________________________________________________
      //________
      //__________________________________________________________________
      //__________________________________________________________________
      //________
      //_____________________________________________________________
      //__________________________________________________________________________________
      //________________________________________________________________________________________________________________________________________________________________
      //____________________________________________________________________________________
      //____________________________________________________________________________________________________________________________________________________________________________________________________________________________
      //_____________________________________________________________________________________
      //________________________________________________________________________________
      //________________
      //_____________________________________________________________
      //_____________________________________________________________________________________________
      //_____________________________________________________________________________________________
      //_____________________________________________________________________________________________
      //______________________________________________________________________________________________
      //______________________________________________________________________________________
      //________________
      //________
      //__________________
      //____________________________________
      //_______________________________________________
      //_________________________________________________
      //___________________________________
      //__________________________________
      //______________
      //________
      //___________________________________________________________
      //_________________________________________________
      //__________________
      //_________________________________________________________________________________________________________________________________________
      //___________________________________________________________________
      //___________________________________________________________________________________________________________________________________________________
      //_______________________________________________________________________________________
      //________
      //________________________________________________________
      //___________________________________________________
      //_____________________________________________________________________
      //__________________________________________________________________________________________
      //______________________________
      //_____________________________________________________________________________________________________________________________________________
      //_________________________
      //_________________________
      //___________________________________________________________________________
      //_______________
      //______________________________________________________________________________________________________________________________________________

      throw new Error("that must be its nearby point & must exist || the stale update pb aga?..  Segment should be removed already during update, should not go here -- can be sth is wrong with arr_roadwayDirLinkerComponent didnt remove the stale segment.."
                      + "\n" + "-> not just remove, need reset the segment on both linked side");
    }
    //_________________________________________________________
    //____________________________________________________________________________
    //____________________________________________________________________________
    //____________________________________________________________________________
    //____________________________________________________________________________
    //___________________________________________________________________
    //_________________________________________________________________________________________________________________________________
    //______________________________________________________
    //______________________________________________________
    //______________________________________________________
    //______________________________________________________
    //_________________________________________________________________________________________________
    //_____________________________________________________
    //_____________________________________________________________________
    //_________________________________________________________________
    //_________________________________________
    //____________________________________________________________________________________________
    //____________________________________________________________________________________________
    //____________________________________________________________________________________________
    //_____________________________________________________________________________________________
    //________________________________________________________________________
    //______________________________________________________
    //_________________________________________
    //______________________________________________________________________
    //______________________________________________________________________
    //______________________________________________________________________
    //_______________________________________________________________________
    //_____________________________________________________________
    //____________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________
    //_______________________________________________________________________________________________________________________________________
    //____________________

    //_____

    //____________________________
    //_______________________________
    //_______________________________________________________________________________________________________
    Point point_MM = roadwayCrossPointConnection.getPointLocation();

    //_______________________________________________________________________________________________________________
    //________________________________________________________________________________________________________________
    //________________________________________________________
    @BugPotential

    //_____________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________
    //_____________________________________________________________________
    //______________________________________________________________________________________________
    //______________________________________________________________________________________________
    //______________________________________________________________________________________________
    //______________________________________________________________________________________________
    //________________________________________________________________________________
    //__________________________________________________________________
    //__________________________________________________________________________

    //_______________________________________________________________________________________
    //_____________________________________________________________________________
    //_____________________________________________________________________________________________________________________________________________________
    //_________________________________________________________________________________________________________________________________
    //________________________________________________________________
    @Todo //____________________________________________________________________________________
    String idBsi_CrossPoint = roadway_AA.getIdBsi() + "-" + (arr_roadwayDirLinkerComponent_AA.size() + 1) + "x"
                              + roadway_BB.getIdBsi() + "-" + (arr_roadwayDirLinkerComponent_BB.size() + 1);
    if (!gp_idBsiCrossPoint_debug.add(idBsi_CrossPoint)) { throw new Error("Not unique, it could happen in test where the Spring Context is not refreshed?... or just Wrong?  \n" + gp_idBsiCrossPoint_debug + "\n" + idBsi_CrossPoint); }
    RoadwayCrossPoint roadwayCrossPoint = new RoadwayCrossPoint(idBsi_CrossPoint, point_MM);
    em.persist(roadwayCrossPoint);

    mapFile.getGRoadwayCrossPoint().add(roadwayCrossPoint);
    //______________________________________________

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

    //_________________________________________________
    //____________________________________________________________________________________________________________

    int ind_Linker_Seg_AA_btAtoB = arr_roadwayDirLinkerComponent_AA.indexOf(roadwayDirLinkerComponent_Seg_AA_btAtoB);
    int ind_Linker_Seg_BB_btAtoB = arr_roadwayDirLinkerComponent_BB.indexOf(roadwayDirLinkerComponent_Seg_BB_btAtoB);
    if (ind_Linker_Seg_AA_btAtoB == -1 || ind_Linker_Seg_BB_btAtoB == -1) { throw new Error("Can be messy order pb (for now)"); }
    if (ind_Linker_Seg_AA_btAtoB != ind_roadwaySegment_AA) { throw new Error("Should equal" + " :: " + ind_Linker_Seg_AA_btAtoB + " :: " + ind_roadwaySegment_AA); }
    if (ind_Linker_Seg_BB_btAtoB != ind_roadwaySegment_BB) { throw new Error("Should equal" + " :: " + ind_Linker_Seg_BB_btAtoB + " :: " + ind_roadwaySegment_BB); }

    @Messy byte messy; //_________________________________________________________________________________
    //_______________________________________________________________
    arr_roadwayDirLinkerComponent_AA.set(ind_Linker_Seg_AA_btAtoB, roadwayDirLinkerComponent_Seg_AA_btAToCross);
    arr_roadwayDirLinkerComponent_AA.add(ind_Linker_Seg_AA_btAtoB + 1, roadwayDirLinkerComponent_Seg_AA_btCrossToB);
    arr_roadwayDirLinkerComponent_BB.set(ind_Linker_Seg_BB_btAtoB, roadwayDirLinkerComponent_Seg_BB_btAToCross);
    arr_roadwayDirLinkerComponent_BB.add(ind_Linker_Seg_BB_btAtoB + 1, roadwayDirLinkerComponent_Seg_BB_btCrossToB);

    //________________________________________________________________________________________________________

    //_________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________
    //_____________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________
    //________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________

    em.merge(roadway_AA);
    em.merge(roadway_BB);
    //_______________________________________________________________________________
    if (!mode_NoSpring_NoDatabase) {
      DbSqlUtil.deleteByArrId(RoadwayDirLinkerComponent.class,
                              List.of(roadwayDirLinkerComponent_Seg_AA_btAtoB.getIdSql(),
                                      roadwayDirLinkerComponent_Seg_AA_btBtoA.getIdSql(),
                                      roadwayDirLinkerComponent_Seg_BB_btAtoB.getIdSql(),
                                      roadwayDirLinkerComponent_Seg_BB_btBtoA.getIdSql()),
                              em, RoadwayDirLinkerComponent_.ID_SQL);
      DbSqlUtil.deleteByArrId(RoadwaySegment.class,
                              List.of(roadwaySegment_AA.getIdSql(),
                                      roadwaySegment_BB.getIdSql()),
                              em, RoadwaySegment_.ID_SQL);
    }

    //_____
    //________________________________________
    {
      //___________________________________________________________________________________________
      //______________________________________________________________________________________________________________________________
      //____________________

      //_____________________________________________________________________________________________________

      //__________________________________________________________________________________________

      //_________________________________________________________________________________

      //______________________________________________________

      double distance;
      distance = point_MM.distance(point_Seg_AA_Pt_A.getPointLocation());
      log.debug(point_MM);
      log.debug(point_Seg_AA_Pt_A);
      log.debug(point_Seg_AA_Pt_B);
      log.debug(point_Seg_BB_Pt_A);
      log.debug(point_Seg_BB_Pt_B);
      if (distance <= marginOfError_ProgramAccurateLocate) {
        if (!(roadwayDirLinkerComponent_Seg_AA_btAToCross.getRoadwaySegment().length() == distance)) { throw new Error(); }

        removeRoadwayPointFromRoadway_helper_TShapeSplit3Segment(point_Seg_AA_Pt_A, roadwayCrossPoint,
                                                                 arr_roadwayDirLinkerComponent_AA, ind_Linker_Seg_AA_btAtoB, roadwayDirLinkerComponent_Seg_AA_btAToCross,
                                                                 roadway_AA);
      }
      distance = point_MM.distance(point_Seg_AA_Pt_B.getPointLocation());
      if (distance <= marginOfError_ProgramAccurateLocate) {
        if (!(roadwayDirLinkerComponent_Seg_AA_btCrossToB.getRoadwaySegment().length() == distance)) { throw new Error(); }

        removeRoadwayPointFromRoadway_helper_TShapeSplit3Segment(point_Seg_AA_Pt_B, roadwayCrossPoint,
                                                                 arr_roadwayDirLinkerComponent_AA, ind_Linker_Seg_AA_btAtoB + 1, roadwayDirLinkerComponent_Seg_AA_btCrossToB,
                                                                 roadway_AA);
      }
      distance = point_MM.distance(point_Seg_BB_Pt_A.getPointLocation());
      if (distance <= marginOfError_ProgramAccurateLocate) {
        if (!(roadwayDirLinkerComponent_Seg_BB_btAToCross.getRoadwaySegment().length() == distance)) { throw new Error(); }

        removeRoadwayPointFromRoadway_helper_TShapeSplit3Segment(point_Seg_BB_Pt_A, roadwayCrossPoint,
                                                                 arr_roadwayDirLinkerComponent_BB, ind_Linker_Seg_BB_btAtoB, roadwayDirLinkerComponent_Seg_BB_btAToCross,
                                                                 roadway_BB);
      }
      distance = point_MM.distance(point_Seg_BB_Pt_B.getPointLocation());
      if (distance <= marginOfError_ProgramAccurateLocate) {
        if (!(roadwayDirLinkerComponent_Seg_BB_btCrossToB.getRoadwaySegment().length() == distance)) { throw new Error(); }

        removeRoadwayPointFromRoadway_helper_TShapeSplit3Segment(point_Seg_BB_Pt_B, roadwayCrossPoint,
                                                                 arr_roadwayDirLinkerComponent_BB, ind_Linker_Seg_BB_btAtoB + 1, roadwayDirLinkerComponent_Seg_BB_btCrossToB,
                                                                 roadway_BB);
      }
    }

    //____________________________________________________________________________________________________________________________________________
    //_______________________________________________________________
    //_______________________________________________________________
    //________________________________________________________________________________________________
    //________________________________
    //__________________________________________________________________________________________
    //_____________________________________________________________________
  }

  @Messy //_______________________________________________________________
  @Main
  @Temporary //_____________________________________________________________________________________________________________
  @Transactional
  private void removeRoadwayPointFromRoadway_helper_TShapeSplit3Segment(RoadwayPoint point_Seg_AA_Pt_A, RoadwayCrossPoint roadwayCrossPoint,
                                                                        List<RoadwayDirLinkerComponent> arr_roadwayDirLinkerComponent_AA, int ind_Linker_Seg_AA_btAtoB, RoadwayDirLinkerComponent roadwayDirLinkerComponent_Seg_AA_btAToCross,
                                                                        RoadwaySolidRoad roadway_AA) {
    //________________________________________
    //_________________________________________________
    Map<RoadwayPoint, RoadwayDirLinkerComponent> mpp_roadwayDirLinkerComponent = point_Seg_AA_Pt_A.getMppRoadwayDirLinkerComponent();
    //____________________________________________________________________________________
    //_____________________________________________________________________
    //_______________________________________________________________
    //______________________

    if (ind_Linker_Seg_AA_btAtoB == 0) { throw new Error("Shouldnt, cuz this index is the link bt 2 (real) element -- but the 0 ind is 1 ele with pseduo link to begin"); }

    //_________________________________________
    if (mpp_roadwayDirLinkerComponent.size() == 1) {
      Iterator<Entry<RoadwayPoint, RoadwayDirLinkerComponent>> itr = mpp_roadwayDirLinkerComponent.entrySet().iterator();
      RoadwayPoint point_YY = itr.next().getKey();
      if (!(point_YY == roadwayCrossPoint)) { throw new Error("Must be the CrossPoint."); }

      //________________________________________________________________________
      RoadwayDirLinkerComponent roadwayDirLinkerComponent_YYToA = point_YY.getMppRoadwayDirLinkerComponent().remove(point_Seg_AA_Pt_A);
      em.merge(point_YY);
      //___________________________________________________
      @Messy byte messy; //____________________________
      //___________________________________________________________________________________________________________________________________________________________________________________________________________
      //________________________________________________________________________
      //________________________________________________________
      //__________________________________________________________________________________
      //_________________________________________________________________________________________________________________________________

      //___________________
      if (ind_Linker_Seg_AA_btAtoB == 1) { //____________________________________________________________________________
        RoadwaySegmentPseudoBegin roadwaySegment_btPrevCurr = new RoadwaySegmentPseudoBegin(point_YY, point_YY);
        em.persist(roadwaySegment_btPrevCurr);
        RoadwayDirLinkerComponent roadwayLinkerComponent_btPrevToCurr = new RoadwayDirLinkerComponent(roadwaySegment_btPrevCurr, point_YY);
        em.persist(roadwayLinkerComponent_btPrevToCurr);

        RoadwayDirLinkerComponent roadwayDirLinkerComponent_Removed_FirstPseudo = arr_roadwayDirLinkerComponent_AA.remove(0);
        //___________________________________________________________________________________________________________________________________
        //________________________________________________
        RoadwayDirLinkerComponent roadwayDirLinkerComponent_AToYY = arr_roadwayDirLinkerComponent_AA.get(0);
        arr_roadwayDirLinkerComponent_AA.set(0, roadwayLinkerComponent_btPrevToCurr);
        {
          em.merge(roadway_AA); //______________________________
          @PerformancePotential byte dmy193; //______________________________________________________________
          if (!mode_NoSpring_NoDatabase) {
            DbSqlUtil.deleteByArrId(RoadwayDirLinkerComponent.class,
                                    List.of(
                                            roadwayDirLinkerComponent_Removed_FirstPseudo.getIdSql(),
                                            roadwayDirLinkerComponent_YYToA.getIdSql(),
                                            roadwayDirLinkerComponent_AToYY.getIdSql()), //____________________________________
                                    em, RoadwayDirLinkerComponent_.ID_SQL);
            DbSqlUtil.deleteByArrId(RoadwaySegment.class,
                                    List.of(
                                            roadwayDirLinkerComponent_Removed_FirstPseudo.getRoadwaySegment().getIdSql(),
                                            roadwayDirLinkerComponent_YYToA.getRoadwaySegment().getIdSql()),
                                    em, RoadwaySegment_.ID_SQL);
            DbSqlUtil.deleteByArrId(RoadwayPoint.class,
                                    List.of(
                                            point_Seg_AA_Pt_A.getIdSql()),
                                    em, RoadwayPoint_.ID_SQL);
          }
        }
      }
      //_________________
      else if (ind_Linker_Seg_AA_btAtoB == arr_roadwayDirLinkerComponent_AA.size() - 1) {
        //_____________________________________________________________________________________________________________________________
        //____________________________________________________________________
        RoadwayDirLinkerComponent roadwayDirLinkerComponent_Removed_Last_YYToA = arr_roadwayDirLinkerComponent_AA.remove(arr_roadwayDirLinkerComponent_AA.size() - 1);
        {
          RoadwayDirLinkerComponent roadwayDirLinkerComponent_AToYY = point_Seg_AA_Pt_A.getMppRoadwayDirLinkerComponent().remove(point_YY);
          em.merge(point_YY);

          em.merge(roadway_AA);
          if (!mode_NoSpring_NoDatabase) {
            DbSqlUtil.deleteByArrId(RoadwayDirLinkerComponent.class,
                                    List.of(
                                            roadwayDirLinkerComponent_Removed_Last_YYToA.getIdSql(),
                                            roadwayDirLinkerComponent_AToYY.getIdSql()),
                                    em, RoadwayDirLinkerComponent_.ID_SQL);
            DbSqlUtil.deleteByArrId(RoadwaySegment.class,
                                    List.of(
                                            roadwayDirLinkerComponent_Removed_Last_YYToA.getRoadwaySegment().getIdSql()),
                                    em, RoadwaySegment_.ID_SQL);
            DbSqlUtil.deleteByArrId(RoadwayPoint.class,
                                    List.of(
                                            point_Seg_AA_Pt_A.getIdSql()),
                                    em, RoadwayPoint_.ID_SQL);
          }
        }
      }
      else {
        System.out.println(mpp_roadwayDirLinkerComponent);
        throw new Error("ind" + " :: " + ind_Linker_Seg_AA_btAtoB);
      }
    }
    //___________________________________
    else if (mpp_roadwayDirLinkerComponent.size() == 2) {
      Iterator<Entry<RoadwayPoint, RoadwayDirLinkerComponent>> itr = mpp_roadwayDirLinkerComponent.entrySet().iterator();
      RoadwayPoint point_XX = itr.next().getKey();
      RoadwayPoint point_YY = itr.next().getKey();
      if (!(point_XX == roadwayCrossPoint || point_YY == roadwayCrossPoint)) { throw new Error("One side must be the CrossPoint."); }

      //_____________________________________________________________________________
      //_____________________________________________________________________________________________________
      //_________
      //__________________________________________________________________________________
      //_________________________________________________________________________________________________________________________________________________
      //____________________________________________________________________________________________________________________________________________________________________________________________________________________
      //__________________________________________________________________________________
      //__________________________________________________________________
      //____________________________________________________________________________________________
      //___________________________________________________________________________________________________________________________________________
      //_______________
      //_________________________________________________________________________________________________________
      //_____________________________________________________________________________________________________
      //_________
      //__________________________________________________________________________________
      //_________________________________________________________________________________________________________________________________________________
      //____________________________________________________________________________________________________________________________________________________________________________________________________________________
      //__________________________________________________________________________________
      //__________________________________________________________________
      //____________________________________________________________________________________________
      //___________________________________________________________________________________________________________________________________________
      //_______________
      //____________________

      Map<RoadwayPoint, RoadwayDirLinkerComponent> mppRoadwayDirLinkerComponent_XX = point_XX.getMppRoadwayDirLinkerComponent();
      Map<RoadwayPoint, RoadwayDirLinkerComponent> mppRoadwayDirLinkerComponent_YY = point_YY.getMppRoadwayDirLinkerComponent();
      int size_XX_prev = mppRoadwayDirLinkerComponent_XX.size();
      int size_YY_prev = mppRoadwayDirLinkerComponent_YY.size();

      RoadwayDirLinkerComponent roadwayDirLinkerComponent_XXToA = mppRoadwayDirLinkerComponent_XX.remove(point_Seg_AA_Pt_A);
      RoadwayDirLinkerComponent roadwayDirLinkerComponent_YYToA = mppRoadwayDirLinkerComponent_YY.remove(point_Seg_AA_Pt_A);

      @Messy //_________________________________
      boolean det_PlaceXXAtSpDir = roadwayDirLinkerComponent_XXToA.getRoadwaySegment().getRoadwayPointSp() == point_XX;
      boolean det_PlaceYYAtSpDir = roadwayDirLinkerComponent_YYToA.getRoadwaySegment().getRoadwayPointSp() == point_YY;
      if (det_PlaceXXAtSpDir == det_PlaceYYAtSpDir) { throw new Error("The Segment Sp Np direction is messed up some where? or this code check is wrong ..?"); }

      RoadwayNormalSegment roadwayNormalSegment_btXXYY;
      if (det_PlaceXXAtSpDir) {
        roadwayNormalSegment_btXXYY = new RoadwayNormalSegment(point_XX, point_YY);
      }
      else {
        roadwayNormalSegment_btXXYY = new RoadwayNormalSegment(point_YY, point_XX);
      }
      em.persist(roadwayNormalSegment_btXXYY);

      RoadwayDirLinkerComponent roadwayDirLinkerComponent_btXXtoYY = new RoadwayDirLinkerComponent(roadwayNormalSegment_btXXYY, point_YY);
      RoadwayDirLinkerComponent roadwayDirLinkerComponent_btYYtoXX = new RoadwayDirLinkerComponent(roadwayNormalSegment_btXXYY, point_XX);
      em.persist(roadwayDirLinkerComponent_btXXtoYY);
      em.persist(roadwayDirLinkerComponent_btYYtoXX);
      mppRoadwayDirLinkerComponent_XX.put(point_YY, roadwayDirLinkerComponent_btXXtoYY);
      mppRoadwayDirLinkerComponent_YY.put(point_XX, roadwayDirLinkerComponent_btYYtoXX);
      em.merge(point_XX);
      em.merge(point_YY);

      {
        int size_XX_curr = mppRoadwayDirLinkerComponent_XX.size();
        int size_YY_curr = mppRoadwayDirLinkerComponent_YY.size();
        if (size_XX_curr != size_XX_prev) { throw new Error(); }
        if (size_YY_curr != size_YY_prev) { throw new Error(); }
      }

      //____________________________________________________
      //_______________________________________________
      //_________________________________________________________________
      //______________________________________________________________________________________
      //__________________________
      //_________________________________________________________________________________________________________________________________________
      //_____________________
      //_____________________
      //_______________________________________________________________________
      //______________________________________________________________________________________________________________________________________________________________________________
      //_________________________________________________________________________________________________________________________________________________________________________________________________________________________________
      //________________________________________________________________________________________________
      //__________________________________________________________________________________________________________________________________________________
      //___________________________________________________________________________________________________
      //_______________________________________________________________________________________________________
      //__________________________________________________________________________________________________________________________
      //________________________________________________________________________________________
      //______________________________________________________________________________________________________________________
      //______________________________________________________________________________________________________________________
      //_______________________________________________________________________
      //__________________
      //_______________________
      //___________________________
      //__________________________________________________________________________________________________________________________________________
      //_________________________________________________________________________________________________________________________________________
      //__________________________________________________________________________________________________________________________________________
      //_____________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________
      //___________________________________________________________________________________________________________________________________________________________________________________________________________________________
      //______________________________________________________
      //_____________________
      //__________________________________________________________________________________________________________________________________________
      //
      //______________________________________________________________________________________________________________________________________________________________________________________

      //________________________________
      {
        if (arr_roadwayDirLinkerComponent_AA.remove(ind_Linker_Seg_AA_btAtoB) != roadwayDirLinkerComponent_Seg_AA_btAToCross) { throw new Error("Should match code above.. unless Changed code to avoid shared ref"); }
        if (roadwayDirLinkerComponent_Seg_AA_btAToCross.getRoadwaySegment() instanceof RoadwaySegmentPseudoBegin) { throw new Error(); }
        RoadwayDirLinkerComponent prev = roadwayDirLinkerComponent_Seg_AA_btAToCross;
        RoadwayDirLinkerComponent curr = arr_roadwayDirLinkerComponent_AA.get(ind_Linker_Seg_AA_btAtoB);
        //____________________
        if (det_PlaceXXAtSpDir) {
          if (!(prev.getRoadwaySegment().getRoadwayPointSp() == point_XX)) { throw new Error(); }
          if (!(curr.getRoadwayPoint() == point_YY)) { throw new Error(); }
          arr_roadwayDirLinkerComponent_AA.set(ind_Linker_Seg_AA_btAtoB, roadwayDirLinkerComponent_btXXtoYY);
        }
        else {
          if (!(prev.getRoadwaySegment().getRoadwayPointSp() == point_YY)) { throw new Error(); }
          if (!(curr.getRoadwayPoint() == point_XX)) { throw new Error(); }
          arr_roadwayDirLinkerComponent_AA.set(ind_Linker_Seg_AA_btAtoB, roadwayDirLinkerComponent_btYYtoXX);
        }
        //_________________________________________________________________________________________________________________________________________________________
      }

      {
        em.merge(roadway_AA);

        RoadwayDirLinkerComponent roadwayDirLinkerComponent_AToXX = point_Seg_AA_Pt_A.getMppRoadwayDirLinkerComponent().remove(point_XX);
        RoadwayDirLinkerComponent roadwayDirLinkerComponent_AToYY = point_Seg_AA_Pt_A.getMppRoadwayDirLinkerComponent().remove(point_YY);
        em.merge(point_Seg_AA_Pt_A);

        if (!mode_NoSpring_NoDatabase) {
          DbSqlUtil.deleteByArrId(RoadwayDirLinkerComponent.class,
                                  List.of(
                                          roadwayDirLinkerComponent_XXToA.getIdSql(),
                                          roadwayDirLinkerComponent_AToXX.getIdSql(),
                                          roadwayDirLinkerComponent_YYToA.getIdSql(),
                                          roadwayDirLinkerComponent_AToYY.getIdSql()),
                                  em, RoadwayDirLinkerComponent_.ID_SQL);
          DbSqlUtil.deleteByArrId(RoadwaySegment.class,
                                  List.of(
                                          roadwayDirLinkerComponent_XXToA.getRoadwaySegment().getIdSql(),
                                          roadwayDirLinkerComponent_YYToA.getRoadwaySegment().getIdSql()),
                                  em, RoadwaySegment_.ID_SQL);
          DbSqlUtil.deleteByArrId(RoadwayPoint.class,
                                  List.of(
                                          point_Seg_AA_Pt_A.getIdSql()),
                                  em, RoadwayPoint_.ID_SQL);
          //_____________________________
        }
      }
    }
    else {
      throw new Error("Is this not a linear point? this is a cross point? ... not supported yet :: " + mpp_roadwayDirLinkerComponent.entrySet());
    }

  }

}
