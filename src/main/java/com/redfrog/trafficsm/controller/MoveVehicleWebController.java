package com.redfrog.trafficsm.controller;

import java.time.Instant;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.redfrog.trafficsm.annotation.Config;
import com.redfrog.trafficsm.annotation.Debug;
import com.redfrog.trafficsm.exception.AlreadyExistedException;
import com.redfrog.trafficsm.exception.DuplicatedIdException;
import com.redfrog.trafficsm.exception.FoundNoItemWithIdException;
import com.redfrog.trafficsm.model.MapFile;
import com.redfrog.trafficsm.model.TrafficDetector;
import com.redfrog.trafficsm.model.Vehicle;
import com.redfrog.trafficsm.model.Vehicle.VehicleBrand;
import com.redfrog.trafficsm.model.Vehicle.VehicleType;
import com.redfrog.trafficsm.model.roadway.main.RoadwaySolidRoad;
import com.redfrog.trafficsm.repository.dao.GenericDao;
import com.redfrog.trafficsm.service.MapBuilder;
import com.redfrog.trafficsm.service.MoveController;
import com.redfrog.trafficsm.service.PathFinder;
import com.redfrog.trafficsm.service.TrafficAnalyzerBuilder;
import com.redfrog.trafficsm.service.WindowSessionJavafx;
import com.redfrog.trafficsm.service.exception.PointIsNotNearAnyRoadwayException;
import com.redfrog.trafficsm.shape.Point;

import lombok.extern.log4j.Log4j2;

//________________

//_____
@Controller
@RequestMapping("/v0.1/user")
@CrossOrigin(origins = MoveVehicleWebController.url_JsVite_CrossOrigin) //_______________________________________________
@Log4j2
public class MoveVehicleWebController {

  @Config
  public static final String url_JsVite_CrossOrigin = "http://localhost:5173";

  @GetMapping("/testGetMap")
  public ResponseEntity<String> testGetMap() { return ResponseEntity.ok().body("return testGetMap" + " :: " + Instant.now()); }

  //_____

  //_____________________________________________________________________________________________________

  private final MapBuilder mapBuilder;
  private final PathFinder pathFinder;
  private final MoveController moveController;
  private final TrafficAnalyzerBuilder trafficAnalyzerBuilder;
  private final WindowSessionJavafx windowSessionJavafx;
  //________________________________________
  private final GenericDao<Vehicle> vehicleDao;

  @Autowired
  public MoveVehicleWebController(MapBuilder mapBuilder, PathFinder pathFinder, MoveController moveController, TrafficAnalyzerBuilder trafficAnalyzerBuilder, GenericDao<Vehicle> vehicleDao, WindowSessionJavafx windowSessionJavafx) {
    super();
    this.mapBuilder             = mapBuilder;
    this.pathFinder             = pathFinder;
    this.moveController         = moveController;
    this.trafficAnalyzerBuilder = trafficAnalyzerBuilder;
    this.vehicleDao             = vehicleDao;
    this.windowSessionJavafx    = windowSessionJavafx;
    //_________________________________________________________________________
    //__________________________________________________________________________________________________________________________________________________________
    //_____________________
    //____________________________________________________________________________________________________________________
    //_____________________________________________________________________________________
    //________________________________________________________________________________________________________
    //____________________________________________________________________________________________________
    //________________________________________________________________________________________________________________________
    //_______________________________________________________
    //____________________________________________________
    //___________________________________________________________________________________________________________________

  }

  //_____________

  //__________________________________________________________

  //_______________

  //_____________________

  @ConditionalOnExpression("false") //_______________________
  @PostMapping("/createVehicle")
  public ResponseEntity<Vehicle> createVehicle(
                                               @RequestParam(name = "speed", required = false, defaultValue = "0.5") double speed,
                                               @RequestParam(name = "vehicleType", required = false) VehicleType vehicleType,
                                               @RequestParam(name = "brand", required = false) VehicleBrand brand

  ) {
    Vehicle vehicle = trafficAnalyzerBuilder.createVehicle(speed, vehicleType, brand);
    return ResponseEntity.status(HttpStatus.OK).body(vehicle);
  }

  @PostMapping("/placeVehicleInMap")
  public ResponseEntity<Vehicle> placeVehicleInMap(
                                                   @RequestParam(name = "idSqlOfVehicle", required = true) long idSql
  //_______________________________________________________________________________________________________
  //______________________________________________________________________________________________________

  ) {
    try {
      Vehicle vehicle = trafficAnalyzerBuilder.placeVehicleInMap(idSql);
      return ResponseEntity.status(HttpStatus.OK).body(vehicle);
    } catch (FoundNoItemWithIdException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    } catch (AlreadyExistedException e) {
      log.info(">> placeVehicleInMap({}) -- Vehicle is already in the MapFile", idSql);
      Vehicle vehicle = (Vehicle) e.getObjAlreadyExisted();
      return ResponseEntity.status(HttpStatus.OK).body(vehicle);
    }
  }

  //________________________

  @PostMapping("/setPosSelfOfVehicleInMap")
  public ResponseEntity<Boolean> setPosSelfOfVehicleInMap(
                                                          @RequestParam(name = "idSqlOfVehicle", required = true) long idSql,
                                                          @RequestParam(name = "x", required = true) double x,
                                                          @RequestParam(name = "y", required = true) double y

  ) {
    try {
      trafficAnalyzerBuilder.setPosSelfOfVehicleInMap(idSql, x, y);
      return ResponseEntity.status(HttpStatus.OK).body(true);
    } catch (FoundNoItemWithIdException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(false);
    }
  }

  @PostMapping("/gotoTarget")
  public ResponseEntity<Boolean> gotoTarget(
                                            @RequestParam(name = "idSqlOfVehicle", required = true) long idSql,
                                            @RequestParam(name = "x", required = true) double x,
                                            @RequestParam(name = "y", required = true) double y

  ) {
    try {
      trafficAnalyzerBuilder.gotoTarget(idSql, x, y);
      return ResponseEntity.status(HttpStatus.OK).body(true);
    } catch (PointIsNotNearAnyRoadwayException e) {
      System.err.println(e);
      return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(false);
    } catch (FoundNoItemWithIdException e) {
      System.err.println(e);
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(false);
    }
  }

  //_____________

  //________________________________________________
  //______________________________________________
  //_________________________________________________________________________________
  //__________________________________________________________________________

  //____________________________________________________________________________________________________________
  @PostMapping("/createMapDemo01")
  public ResponseEntity<LinkedHashMap<RoadwaySolidRoad, List<Point>>> createMapDemo01() {
    LinkedHashMap<RoadwaySolidRoad, List<Point>> mpp__roadway_vs_pathArr_point = mapBuilder.createMapDemo01();
    return ResponseEntity.status(HttpStatus.OK).body(mpp__roadway_vs_pathArr_point);
    //_______________________________________
    //__________________________________________________________
    //_____
  }

  @PostMapping("/createMapDemo02")
  public ResponseEntity<MapFile> createMapDemo02() {
    MapFile mapFile_L = trafficAnalyzerBuilder.createMapDemo02();
    return ResponseEntity.status(HttpStatus.OK).body(mapFile_L);
  }

  //____
  @ExceptionHandler(DuplicatedIdException.class)
  public ResponseEntity<DuplicatedIdException> handleDuplicatedIdException(DuplicatedIdException ex) {
    System.out.println(ex);
    return new ResponseEntity<DuplicatedIdException>(ex, HttpStatus.CONFLICT); //
  }

  @GetMapping("/getMapImg")
  public ResponseEntity<String> getMapImg() {
    String mapImg = mapBuilder.getMapImg();
    return ResponseEntity.status(HttpStatus.OK).body(mapImg);
  }

  //_____________

  @PostMapping("/createVehicleDemo01")
  public ResponseEntity createVehicleDemo01() {
    trafficAnalyzerBuilder.createVehicleDemo01();
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }

  @GetMapping("/getGpVehicleInVehicleInventory")
  public ResponseEntity<Collection<Vehicle>> getGpVehicleInVehicleInventory() {
    return ResponseEntity.status(HttpStatus.OK).body(mapBuilder.getGpVehicleInVehicleInventory());

  }

  @GetMapping("/getGpTrafficDetector")
  public ResponseEntity<Set<TrafficDetector>> getGpTrafficDetector() {
    MapFile mapFile_L = mapBuilder.getMapFile();
    if (mapFile_L == null) {
      return ResponseEntity.status(HttpStatus.OK).body(new HashSet()); //_____
    }
    else {
      return ResponseEntity.status(HttpStatus.OK).body(mapFile_L.getGpTrafficDetector());
    }

  }

  //_____________

  //_________________________________________________________________________
  //__________________________________________________________
  //____________________________________
  //___________________________________________________________________
  //______________________________________________________________________________________
  //___
  //
  //______________________________________
  //______________________________________________________________________
  //________________________________________________________________________________________
  //___

  //_____________

  @PostMapping("/newMapFile")
  public ResponseEntity newMapFile() {
    mapBuilder.newMapFile();
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }

  @PostMapping("/loadMapFile")
  public ResponseEntity<MapFile> loadMapFile(@RequestParam(name = "idSql", required = true) long idSql) {
    try {
      MapFile loadMapFile = mapBuilder.loadMapFile(idSql);
      System.out.println(">> loadMapFile.getGpTrafficDetector()");
      System.out.println(loadMapFile.getGpTrafficDetector());
      return ResponseEntity.status(HttpStatus.OK).body(loadMapFile);
      //______________________________
    } catch (FoundNoItemWithIdException e) {
      //_____________________________________________________________________
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    } catch (AlreadyExistedException e) {
      log.info(">> loadMapFile({}) -- current MapFile is already the tobe-loaded MapFile", idSql);
      MapFile loadMapFile = (MapFile) e.getObjAlreadyExisted();
      return ResponseEntity.status(HttpStatus.OK).body(loadMapFile);
    }
  }

  @PostMapping("/saveMapFile")
  public ResponseEntity<MapFile> saveMapFile() {
    MapFile mapFile = mapBuilder.saveMapFile();
    System.out.println(mapFile);
    return ResponseEntity.status(HttpStatus.OK).body(mapFile);
    //___________________________________________________________
  }

  @PostMapping("/removeMapFile")
  public ResponseEntity<Boolean> removeMapFile(@RequestParam(name = "idSql", required = true) long idSql) {
    try {
      mapBuilder.removeMapFile(idSql);
      return ResponseEntity.status(HttpStatus.OK).body(true);
    } catch (FoundNoItemWithIdException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
  }

  @Debug
  @GetMapping("/querySomeTable_MapFile_helper")
  public ResponseEntity<String> querySomeTable_MapFile_helper() {
    String someTableStr = mapBuilder.querySomeTable_MapFile_helper();
    log.debug(someTableStr);
    return ResponseEntity.status(HttpStatus.OK).body(someTableStr);
  }

}
