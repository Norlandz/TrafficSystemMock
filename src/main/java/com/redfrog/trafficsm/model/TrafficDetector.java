package com.redfrog.trafficsm.model;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.PostLoad;

import org.apache.commons.lang3.tuple.Pair;

import com.redfrog.trafficsm.annotation.UseByLibOnly;
import com.redfrog.trafficsm.root.EntityGeneral;
import com.redfrog.trafficsm.shape.Point;
import com.redfrog.trafficsm.shape.PointAndTime;
import com.redfrog.trafficsm.util.JavafxUtil;

import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

//___________________________________

@Entity
@Getter
@Setter
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
//_________
//___________________
public class TrafficDetector extends EntityGeneral {

  @Deprecated
  private final transient AnchorPane paneWrap = new AnchorPane();

  @Deprecated
  private final transient Circle circle;

  //_________________
  //___________________________
  @EqualsAndHashCode.Include
  private final String idBsi;

  //___________________________
  @EqualsAndHashCode.Include
  private final Instant trafficDetectorCreationTime;

  //________________________________________________________________________________________________________________

  //_____________________________
  @NonNull
  //______________________________
  private Point pointLocation;

  @NonNull
  //___________________________
  private Double radiusDetection;

  @UseByLibOnly
  @Deprecated
  protected TrafficDetector() {
    super();
    idBsi                       = null;
    trafficDetectorCreationTime = null;

    //_______________________________________
    circle                      = new Circle();
  }

  //______________________________________________
  public TrafficDetector(@NonNull String idBsi, @NonNull Instant trafficDetectorCreationTime, @NonNull Point pointLocation, double radiusDetection) {
    super();
    if (radiusDetection < 0) { throw new Error(); }
    this.idBsi                       = idBsi;
    this.trafficDetectorCreationTime = trafficDetectorCreationTime;
    this.pointLocation               = pointLocation;
    this.radiusDetection             = radiusDetection;

    //____________________________________________________________________________________________
    //_____________________________________________
    //________
    //_____________________________________________________
    //_____________________________________________________
    //________
    //_______________________________________________________________________
    //_________________________________
    //______________________________________________
    //_____________________________________________
    //___________________________________________________
    //_____________________________________________
    //_____________________________________________
    //___________________________________
    circle                           = new Circle();
    initJavafx();
  }

  @PostLoad
  private void initJavafx() {
    paneWrap.setLayoutX(pointLocation.getX());
    paneWrap.setLayoutY(pointLocation.getY());
    
    circle.setRadius(radiusDetection);
    circle.setFill(JavafxUtil.color_Yellow);
    circle.setViewOrder(-1);
    paneWrap.getChildren().add(circle);
  }

  //_________

  public static enum VehicleLockInStatus {
    VehicleEntered,
    VehicleAnalysisDone,
    //________________
  }

  //_
  private final transient HashMap<Vehicle, Pair<TrafficDetector.VehicleLockInStatus, ArrayList<PointAndTime>>> mppVehicleLockedInVsHistoryPoint = new HashMap<>();

  //
  //_________________________________________________________________________________
  //_______________________________________________________________________
  @OneToMany(cascade = CascadeType.PERSIST)
  private final List<VehicleInfoTrafficDetectorDto> arrVehicleInfoTrafficDetectorDtoHistory = new ArrayList<>();

  //_________

  //________________________

  //_________

  public int getTrafficVolume() { return arrVehicleInfoTrafficDetectorDtoHistory.size(); }

}
