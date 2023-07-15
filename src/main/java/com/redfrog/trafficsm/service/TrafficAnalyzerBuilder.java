package com.redfrog.trafficsm.service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.redfrog.trafficsm.annotation.BugPotential;
import com.redfrog.trafficsm.annotation.Config;
import com.redfrog.trafficsm.annotation.Debug;
import com.redfrog.trafficsm.annotation.Main;
import com.redfrog.trafficsm.annotation.Messy;
import com.redfrog.trafficsm.annotation.PerformancePotential;
import com.redfrog.trafficsm.annotation.Todo;
import com.redfrog.trafficsm.annotation.UseWithoutSpring;
import com.redfrog.trafficsm.annotation.Warn;
import com.redfrog.trafficsm.controller.TrafficInfoPublisher;
import com.redfrog.trafficsm.exception.AlreadyExistedException;
import com.redfrog.trafficsm.exception.FoundNoItemWithIdException;
import com.redfrog.trafficsm.model.MapFile;
import com.redfrog.trafficsm.model.TrafficDetector;
import com.redfrog.trafficsm.model.Vehicle;
import com.redfrog.trafficsm.model.Vehicle.VehicleBrand;
import com.redfrog.trafficsm.model.Vehicle.VehicleType;
import com.redfrog.trafficsm.model.VehicleInfoTrafficDetectorDto;
import com.redfrog.trafficsm.model.VehicleInventory;
import com.redfrog.trafficsm.model.roadway.fundamental.RoadwayDirLinkerComponent;
import com.redfrog.trafficsm.model.roadway.main.RoadwayNormalSegment;
import com.redfrog.trafficsm.model.roadway.main.RoadwaySolidRoad;
import com.redfrog.trafficsm.repository.FakeEntityManager;
import com.redfrog.trafficsm.repository.dao.EntityManagerWithTransactionScopeProxy;
import com.redfrog.trafficsm.service.MoveController.FutureMovement;
import com.redfrog.trafficsm.service.exception.PointIsNotNearAnyRoadwayException;
import com.redfrog.trafficsm.session.WindowSession;
import com.redfrog.trafficsm.shape.Point;
import com.redfrog.trafficsm.shape.PointAndTime;
import com.redfrog.trafficsm.util.JavafxUtil;
import com.redfrog.trafficsm.util.MathUtil;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Path;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class TrafficAnalyzerBuilder {

  @UseWithoutSpring
  private final WindowSession windowSession_corr;

  private final MapBuilder mapBuilder_corr;

  @UseWithoutSpring
  public TrafficAnalyzerBuilder(WindowSession windowSession_corr) {
    this.windowSession_corr   = windowSession_corr;
    this.mapBuilder_corr      = windowSession_corr.mapBuilder;
    this.pathFinder           = null;
    this.moveController       = null;
    this.windowSessionJavafx  = null;
    this.em                   = new FakeEntityManager();
    this.trafficInfoPublisher = trafficInfoPublisher_pseudoNoSpring;
    this.emTx                 = emTx_pseudoNoSpring;
  }

  //___

  private final PathFinder pathFinder;

  private final MoveController moveController;

  private final WindowSessionJavafx windowSessionJavafx;

  private final TrafficInfoPublisher trafficInfoPublisher;
  private static final TrafficInfoPublisher trafficInfoPublisher_pseudoNoSpring = new TrafficInfoPublisher();

  @Autowired
  public TrafficAnalyzerBuilder(MapBuilder mapBuilder, PathFinder pathFinder, MoveController moveController, WindowSessionJavafx windowSessionJavafx, TrafficInfoPublisher trafficInfoPublisher) {
    this.windowSession_corr   = null;
    this.mapBuilder_corr      = mapBuilder;
    this.pathFinder           = pathFinder;
    this.moveController       = moveController;
    this.windowSessionJavafx  = windowSessionJavafx;
    this.trafficInfoPublisher = trafficInfoPublisher;
  }

  //_________

  //_______________________________________________________________________________________

  private static final Marker mk_TD = MarkerManager.getMarker("TrafficDetector");

  //_________

  @PersistenceContext
  private EntityManager em;

  @Autowired
  private EntityManagerWithTransactionScopeProxy emTx;

  private static final EntityManagerWithTransactionScopeProxy emTx_pseudoNoSpring = new EntityManagerWithTransactionScopeProxy();

  //___________________________________
  //___________________________________
  //___________________________________
  //___________________________________
  //___________________________________

  @Transactional
  public void createVehicleDemo01() {
    int sn = 0;
    ArrayList<Vehicle> arr_vehicle = new ArrayList<>();
    arr_vehicle.add(new Vehicle(String.format("AC%03d", ++sn), 0.5, VehicleType.Car, VehicleBrand.BMW));
    arr_vehicle.add(new Vehicle(String.format("AC%03d", ++sn), 0.5, VehicleType.Car, VehicleBrand.BYD));
    arr_vehicle.add(new Vehicle(String.format("AC%03d", ++sn), 0.5, VehicleType.Truck, VehicleBrand.Jeep));
    arr_vehicle.add(new Vehicle(String.format("AC%03d", ++sn), 0.5, VehicleType.Motorbike, VehicleBrand.Ford));
    persistVehicleBatch(arr_vehicle);
  }

  //_________
  //_________
  //_________

  //_________
  //_________________________________________________________________________

  @Config
  /**
____________________________________________________________________________________________________________________________
_________________________________________
__*/
  //___________________________
  //__________________________________________________________________
  private static final int numOfSamplesNeededToMeasureTheVehicleSpeed = 50;

  //_________
  //_________
  //_________

  @Transactional
  public Vehicle createVehicle(double speed, VehicleType vehicleType, VehicleBrand brand) {
    Vehicle vehicle = new Vehicle();
    vehicle.setSpeedActual(speed);
    if (vehicleType != null) { vehicle.setVehicleType(vehicleType); }
    if (brand != null) { vehicle.setBrandVehicle(brand); }    //_____________________________________________________________________
    em.persist(vehicle);
    if (vehicle.getIdSql() == null) { throw new Error("Pre-Insert Id required"); }
    //_______________________

    //___________________________________________________
    //_______________________________
    //_____________________________________________________________
    VehicleInventory vehicleInventory = mapBuilder_corr.getVehicleInventory();
    //_________________________
    vehicleInventory.getMppVehicle().put(vehicle.getIdSql(), vehicle);
    em.merge(vehicleInventory);
    return vehicle;
  }

  @Transactional
  public void persistVehicleBatch(List<Vehicle> arr_vehicle) {
    VehicleInventory vehicleInventory = mapBuilder_corr.getVehicleInventory();

    for (Vehicle vehicle : arr_vehicle) {
      em.persist(vehicle);
      if (vehicle.getIdSql() == null) { throw new Error("Pre-Insert Id required"); }
      vehicleInventory.getMppVehicle().put(vehicle.getIdSql(), vehicle);
    }

    em.merge(vehicleInventory);
  }

  @Main
  @Transactional
  public TrafficDetector createTrafficDetector(String idBsi, Instant trafficDetectorCreationTime, Point pointLocation, double radiusDetection) {
    TrafficDetector trafficDetector = new TrafficDetector(idBsi, trafficDetectorCreationTime, pointLocation, radiusDetection);
    MapFile mapFile = mapBuilder_corr.getMapFile();
    mapFile.getGpTrafficDetector().add(trafficDetector);
    em.persist(trafficDetector);
    //_______________________________________________________________________________________
    return trafficDetector;
  }

  //_____

  @Messy //____________________________________________________________________________________
  @Transactional
  //_______________________________________________________________________________________________________________________________
  public Vehicle placeVehicleInMap(long idSql) throws FoundNoItemWithIdException, AlreadyExistedException {
    //____________________
    //_____________________________________________
    //______________________________________________________
    //__________________________________________

    VehicleInventory vehicleInventory = mapBuilder_corr.getVehicleInventory();
    Vehicle vehicle = vehicleInventory.getMppVehicle().get(idSql);
    if (vehicle == null) { throw new FoundNoItemWithIdException("No such Vehicle"); }
    try {
      placeVehicleInMap(vehicle);
    } catch (AlreadyExistedException e) {
      throw e; //____________________
    }
    //____________________________________________________________________________________________________________________________________________________________________
    em.merge(vehicle);

    //__________________________________________________________________________________________________________
    MapFile mapFile_L = mapBuilder_corr.getMapFile();
    Platform.runLater(() -> {
      //______________________________________________________________________________________
      mapFile_L.getPaneForVehicle().getChildren().add(vehicle.getPaneWrap());
    });

    return vehicle;
  }

  @Transactional
  public Vehicle setPosSelfOfVehicleInMap(long idSql, double x, double y) throws FoundNoItemWithIdException {
    VehicleInventory vehicleInventory = mapBuilder_corr.getVehicleInventory();
    Vehicle vehicle = vehicleInventory.getMppVehicle().get(idSql);
    if (vehicle == null) { throw new FoundNoItemWithIdException("No such Vehicle"); }
    vehicle.setPosActual(new Point(x, y));
    em.merge(vehicle);
    return vehicle;
  }

  //_____
  //_____

  private static final String name_TD_prepend = "TD";
  private static final double factor_radiusDetection = 4;

  //_____________________________________________________________________________
  //________________________________________
  @Transactional
  public MapFile createMapDemo02() {
    LinkedHashMap<RoadwaySolidRoad, List<Point>> createMapDemo01 = mapBuilder_corr.createMapDemo01("D2");

    int sn_td = 0;
    Point point_TrafficDetector;

    point_TrafficDetector = new Point(400, 200);
    TrafficDetector trafficDetector = createTrafficDetector(name_TD_prepend + ++sn_td, Instant.ofEpochSecond(0), point_TrafficDetector, 20 * factor_radiusDetection);
    point_TrafficDetector = new Point(500, 550);
    TrafficDetector trafficDetector_02 = createTrafficDetector(name_TD_prepend + ++sn_td, Instant.ofEpochSecond(0), point_TrafficDetector, 20 * factor_radiusDetection);

    MapFile mapFile_L = mapBuilder_corr.getMapFile();
    Platform.runLater(() -> {
      mapFile_L.getPaneForTrafficDetector().getChildren().add(trafficDetector.getPaneWrap());
      mapFile_L.getPaneForTrafficDetector().getChildren().add(trafficDetector_02.getPaneWrap());
    });

    //___________________________
    return mapFile_L;
  }

  //_____
  //_____

  //_____________

  private final Map<Vehicle, FutureMovement<Boolean>> mppVehicleFutureMovement = new HashMap<>();

  @Debug
  @Deprecated
  public Map<Vehicle, FutureMovement<Boolean>> getMppVehicleFutureMovement() { return mppVehicleFutureMovement; }

  //______________________________________
  //______________________________________________________

  //________________________________________________________________________________________________________________________________________________________________________________________________________________________________
  //_____________________________________________________________
  @Messy
  @Transactional
  public Vehicle gotoTarget(long idSql, double x, double y) throws PointIsNotNearAnyRoadwayException, FoundNoItemWithIdException {
    MapFile mapFile = mapBuilder_corr.getMapFile();
    Vehicle vehicle = mapFile.getMppVehicleInMap().get(idSql);
    if (vehicle == null) {
      //_________________________________________________
      //______________________________________________________________________
      throw new FoundNoItemWithIdException("Vehicle Not in Map");
    }

    FutureMovement<Boolean> futureMovement_prev = mppVehicleFutureMovement.get(vehicle);
    if (futureMovement_prev != null) {
      futureMovement_prev.getFuture().cancel(true);
      try {
        futureMovement_prev.waitUntilEnded();
      } catch (InterruptedException e) {
        throw new Error(e);
      }
    }

    //______________________________________________________________
    Point point_Self = vehicle.getPosActual();
    Point point_Target = new Point(x, y);

    Triple<LinkedList<RoadwayDirLinkerComponent>, Pair<RoadwayNormalSegment, Point>, Pair<RoadwayNormalSegment, Point>> result_findPath;
    result_findPath = pathFinder.findPath(point_Self, point_Target);
    //____________________________________________________________________
    //_____________________________________________________________________
    LinkedList<RoadwayDirLinkerComponent> pathArr = result_findPath.getLeft();
    List<Point> pathArr_point = pathArr.stream().map(t -> t.getRoadwayPoint().getPointLocation()).toList();

    Circle circle = new Circle(x, y, 3, Color.rgb(0, 200, 10, 0.5));
    circle.setViewOrder(-1);

    Path path = JavafxUtil.convert_arrPath2pathJfx(pathArr_point);
    path.setStrokeWidth(4);
    path.setStroke(JavafxUtil.color_Teal);
    Platform.runLater(() -> {
      windowSessionJavafx.panel_SemanticRoot.getChildren().add(path);
      windowSessionJavafx.panel_SemanticRoot.getChildren().add(circle);
    });
    //__________________________________________________
    //_________________________________________________
    Consumer<Vehicle> callback = t -> {
      //_______________________________________________
      //______________________________________________________________________________________
      Platform.runLater(() -> {
        if (!windowSessionJavafx.panel_SemanticRoot.getChildren().remove(path)) { throw new Error(); }
        if (!windowSessionJavafx.panel_SemanticRoot.getChildren().remove(circle)) { throw new Error(); }
      });
    };

    FutureMovement<Boolean> futureMovement = moveController.gotoTarget(pathArr_point, vehicle, callback);
    mppVehicleFutureMovement.put(vehicle, futureMovement);

    return vehicle;
  }

  //___________________________________
  //___________________________________
  //___________________________________
  //___________________________________
  //___________________________________
  //_________

  //__________
  //__________

  @Debug
  public static boolean mode_Use_SeqNum_as_IdSql_ifItsNull_for_NoDatabase_TestMode = false;

  //_____________________________________________________________________

  @Main
  public Vehicle placeVehicleInMap(Vehicle vehicle) throws AlreadyExistedException {
    //______________________________________________________

    //______________________________
    @BugPotential byte dmy027; //______________________________________
    if (vehicle.getIdSql() == null) {
      if (mode_Use_SeqNum_as_IdSql_ifItsNull_for_NoDatabase_TestMode) {
        vehicle.setIdSql(vehicle.getSeqNum());
      }
      else {
        throw new Error();
      }
    }
    MapFile mapFile = mapBuilder_corr.getMapFile();
    Vehicle vehicle_prev = mapFile.getMppVehicleInMap().put(vehicle.getIdSql(), vehicle);
    if (vehicle_prev != null) {
      throw new AlreadyExistedException(vehicle_prev);
      //___________________________
    }
    //______________________________________
    //____________________________________________________________________________
    //______________________________________________________

    @Messy byte messy; //________________________________________________________________________
    //_________________________________________________________
    //______________________________________
    //______________________
    //___________________________________________________________________________________________________________
    //_________________________________________________________________________________________________________________
    //_______________________________________________________________________________________________
    @PerformancePotential
    @Todo
    @Warn
    //_________________________________________________________________________________________________________________________________________
    //____________________________________________________________________________________
    io.reactivex.rxjava3.functions.Consumer<Point> consumer_VehiclePosChangedEvent = new io.reactivex.rxjava3.functions.Consumer<Point>()
      {

        //_______________________________________________________________________________________
        private final ExecutorService executor = Executors.newSingleThreadExecutor(new ThreadFactoryBuilder().setNameFormat("thd-ReactiToVehiclePosChange-%d").build());

        @Override
        public void accept(@io.reactivex.rxjava3.annotations.NonNull final Point point_VehiclePos) throws Throwable {

          final Instant detectionCreationTime = Instant.now();

          //________________________
          executor.execute(() -> {
            for (TrafficDetector trafficDetector_curr : mapBuilder_corr.getMapFile().getGpTrafficDetector()) {
              //______________________________________________________________
              double distance = point_VehiclePos.distance(trafficDetector_curr.getPointLocation());

              HashMap<Vehicle, Pair<TrafficDetector.VehicleLockInStatus, ArrayList<PointAndTime>>> mpp_VehicleLockedInVsHistoryPoint = trafficDetector_curr.getMppVehicleLockedInVsHistoryPoint();
              //______________________________________________________________
              @Todo
              @Messy
              //________________________________________________________________________________________________
              Pair<TrafficDetector.VehicleLockInStatus, ArrayList<PointAndTime>> result = mpp_VehicleLockedInVsHistoryPoint.get(vehicle);

              if (distance <= trafficDetector_curr.getRadiusDetection()) {
                log.trace(mk_TD, "Detected" + " :: " + trafficDetector_curr + " :: " + vehicle + " :: " + distance);

                //________________________________________________________________
                Platform.runLater(() -> {
                  Circle circle = trafficDetector_curr.getCircle();
                  @Todo Paint paint_ori = JavafxUtil.color_Yellow; //_________________________________________________________________
                  Paint paint_blink = JavafxUtil.color_Cyan;
                  //_______________________________________________
                  Timeline flasher = new Timeline(
                                                  new KeyFrame(javafx.util.Duration.seconds(0), e -> {
                                                    //_____________________________________________________________________________________________________________
                                                    circle.setFill(paint_blink);
                                                    //____________________________________________________________________________________
                                                  }),

                                                  new KeyFrame(javafx.util.Duration.seconds(0.3), e -> {
                                                    //______________________________________________________________________________________________________________
                                                    circle.setFill(paint_ori);
                                                    //__________________________________________________________________________________
                                                  }));
                  //____________________________________________________________
                  //_________________________________________
                  //______________________________________________
                  flasher.play();
                });

                if (result == null) {
                  //______________________________________
                  ArrayList<PointAndTime> arr_HistoryPoint = new ArrayList<>();
                  mpp_VehicleLockedInVsHistoryPoint.put(vehicle, new ImmutablePair<TrafficDetector.VehicleLockInStatus, ArrayList<PointAndTime>>(TrafficDetector.VehicleLockInStatus.VehicleEntered, arr_HistoryPoint));
                  arr_HistoryPoint.add(new PointAndTime(point_VehiclePos, detectionCreationTime));
                }
                else {
                  TrafficDetector.VehicleLockInStatus trafficDetectorVehicleLockInStatus = result.getLeft();
                  if (trafficDetectorVehicleLockInStatus == TrafficDetector.VehicleLockInStatus.VehicleEntered) {
                    final ArrayList<PointAndTime> arr_HistoryPoint = result.getRight();
                    @Todo byte todo; //_______________________________________________________________________________________________________
                    if (trafficDetectorVehicleLockInStatus != TrafficDetector.VehicleLockInStatus.VehicleEntered) { throw new Error("Not_Reachable"); }
                    if (arr_HistoryPoint == null || arr_HistoryPoint.isEmpty()) { throw new Error("Not_Reachable"); }
                    if (arr_HistoryPoint.size() < numOfSamplesNeededToMeasureTheVehicleSpeed) {
                      arr_HistoryPoint.add(new PointAndTime(point_VehiclePos, detectionCreationTime));
                    }
                    else {
                      //______________________________________________________________________________________________________________________________________
                      @Main byte main; //
                      //_________________________________________________________
                      //________________________________________________________________
                      double speedMeasuredByTrafficDetector = calculateSpeed(arr_HistoryPoint);
                      VehicleInfoTrafficDetectorDto vehicleInfoTrafficDetectorDto = new VehicleInfoTrafficDetectorDto(
                                                                                                                      detectionCreationTime.truncatedTo(ChronoUnit.MICROS),
                                                                                                                      trafficDetector_curr.getIdBsi(),
                                                                                                                      vehicle.getIdBsi(),  //____________________________________________________________________________________________________________________
                                                                                                                      vehicle.getVehicleCreationTime(),
                                                                                                                      vehicle.getVehicleNum(),
                                                                                                                      //
                                                                                                                      point_VehiclePos,
                                                                                                                      speedMeasuredByTrafficDetector,
                                                                                                                      //
                                                                                                                      vehicle.getLocationDestination(),
                                                                                                                      vehicle.getLocationOriginate(),
                                                                                                                      //
                                                                                                                      vehicle.getVehicleType(),
                                                                                                                      vehicle.getBrandVehicle(),
                                                                                                                      vehicle.getAmountPplActual(),
                                                                                                                      vehicle.getAmountLimitMaxPpl(),
                                                                                                                      vehicle.getAmountWeightActual(),
                                                                                                                      vehicle.getAmountLimitMaxWeight(),
                                                                                                                      vehicle.getDateIntegrityVerifiedLastTime());
                      List<VehicleInfoTrafficDetectorDto> arr_VehicleInfoTrafficDetectorDto_history = trafficDetector_curr.getArrVehicleInfoTrafficDetectorDtoHistory();
                      arr_VehicleInfoTrafficDetectorDto_history.add(vehicleInfoTrafficDetectorDto);
                      //_______________________________________________
                      //_________________________________________________________
                      //_____________________________________
                      //__________________________________________________________
                      //_____________________________________________________________
                      //_______________________
                      //_____________________________________________
                      if (emTx != emTx_pseudoNoSpring) {
                        emTx.persist(vehicleInfoTrafficDetectorDto);

                      }
                      if (trafficInfoPublisher != trafficInfoPublisher_pseudoNoSpring) {
                        log.debug(">> publish vehicleInfoTrafficDetectorDto :: " + vehicleInfoTrafficDetectorDto);
                        trafficInfoPublisher.pub_VehicleInfoTrafficDetectorDto(vehicleInfoTrafficDetectorDto, mapBuilder_corr.getMapFile().getGpTrafficDetector()); //
                      }
                      //_____________________________________
                      mpp_VehicleLockedInVsHistoryPoint.put(vehicle, new ImmutablePair<TrafficDetector.VehicleLockInStatus, ArrayList<PointAndTime>>(
                                                                                                                                                     TrafficDetector.VehicleLockInStatus.VehicleAnalysisDone, arr_HistoryPoint));
                    }
                  }
                  else if (trafficDetectorVehicleLockInStatus == TrafficDetector.VehicleLockInStatus.VehicleAnalysisDone) {
                    //_____________________________________________________
                    //_____________________________________________________________________________________________________________________________________
                  }
                  else {
                    throw new Error();
                  }
                }
              }
              else {
                if (result == null) { //___________________________________________________
                  //_____________
                }
                else {
                  TrafficDetector.VehicleLockInStatus trafficDetectorVehicleLockInStatus = result.getLeft();
                  if (trafficDetectorVehicleLockInStatus == TrafficDetector.VehicleLockInStatus.VehicleAnalysisDone) {
                    //___________________________________________________
                    //___________________________________________________________________________
                    mpp_VehicleLockedInVsHistoryPoint.remove(vehicle);
                    //________________________________________________________________________________________________________________________
                  }
                  else if (trafficDetectorVehicleLockInStatus == TrafficDetector.VehicleLockInStatus.VehicleEntered) {
                    @Todo byte dmy; //_
                    throw new Error("TrafficDetectorFailedToAnalyzeFastVehicleException");
                  }
                  else {
                    throw new Error();
                  }
                }

              }

            }
          });

        }
      };

    vehicle.getPublisherPosChanged().subscribe(consumer_VehiclePosChangedEvent);

    //____
    Platform.runLater(() -> {
      mapBuilder_corr.listen_SelectVehicle(vehicle);
    });

    return vehicle;
  }

  //_________

  /**
_________________________________________________
___________________________________________________________________
__*/
  //___
  private static double calculateSpeed(@NonNull List<PointAndTime> arr_HistoryPoint) {
    //__________________________________________________
    int length = arr_HistoryPoint.size();
    if (length < 2) { throw new Error(); }

    //____________________
    //________________________
    //__________________________________________
    //________________________________________________________________
    //_____________________________________________________________
    //__________________
    //_______________________________________
    //____________
    //________________________________________________________
    //________________________________________________________
    //_____________________________________________________
    //_____________________________________________________
    //
    //_____________________________________________________________
    //_____________________________________________
    //___________________________________________________________________________________
    //_______________________________________________
    //
    //________________________________________________
    //__________________________________________
    //_____________________________
    //_______
    //____________________________________________
    //_____
    //
    //_____________________________
    //________________________________________________________________________________
    //___________________________________________
    //
    //_____________________________

    //_____________________________________________________________
    //_______________________________________________________________________________________
    //____________________________________________________
    //____________________________________________________
    //____________________________________________________
    //____________________________________________________
    //
    //_________________________________________________________
    //________________________________________________________________
    //___________________________________________________________________________________________________________________________________________________________________________
    //___________________________________________
    //
    //____________________________________________________________________________________
    //____
    //_______________________________________________________________________________________________________
    //______________________________________________________________________________________

    double sum_length = MathUtil.cal_Length(arr_HistoryPoint.stream().map(e -> e.getPoint()).toList());
    Instant first = arr_HistoryPoint.get(0).getTime();
    Instant last = arr_HistoryPoint.get(length - 1).getTime();
    double sum_time = ChronoUnit.NANOS.between(first, last) * 1E-6;

    return sum_length / sum_time;
  }

}
