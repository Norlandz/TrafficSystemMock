package com.redfrog.trafficsm.model;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicLong;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.PostLoad;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import com.redfrog.trafficsm.annotation.Debug;
import com.redfrog.trafficsm.annotation.Messy;
import com.redfrog.trafficsm.annotation.MultiThreadPb;
import com.redfrog.trafficsm.annotation.PerformancePotential;
import com.redfrog.trafficsm.annotation.Todo;
import com.redfrog.trafficsm.annotation.Warn;
import com.redfrog.trafficsm.root.EntityGeneral;
import com.redfrog.trafficsm.shape.Point;
import com.redfrog.trafficsm.util.JavafxUtil;

import io.reactivex.rxjava3.subjects.PublishSubject;
import javafx.application.Platform;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
//_________
//___________________
public class Vehicle extends EntityGeneral {

  @Messy
  //___________
  //__________________________________________________________________________________________________________
  //___________________________________________________________________________________________________________________________________________

  @Getter(AccessLevel.NONE)
  @Setter(AccessLevel.NONE)
  //____________________________
  private final transient AnchorPane paneWrap = new AnchorPane();

  @Debug
  @Deprecated
  public AnchorPane getPaneWrap() { return paneWrap; }

  //_____

  @Getter(AccessLevel.NONE)
  @Setter(AccessLevel.NONE)
  private transient final Instant instantNow = Instant.now();

  private transient static final AtomicLong seqNumAtom = new AtomicLong(0L);
  private transient final Long seqNum = seqNumAtom.incrementAndGet();

  //______________________________________________
  @Column(updatable = false)
  @EqualsAndHashCode.Include
  private final String idBsi = instantNow + "-" + seqNum;

  //__________
  //______________________________________________
  @Column(updatable = false)
  //____________________________
  //_____________________________________________________________________________________________________________
  @EqualsAndHashCode.Exclude
  private final Instant vehicleCreationTime = instantNow.truncatedTo(ChronoUnit.MICROS);
  //__________________________________________________________________
  //___________________________________
  //___________________________________
  //_________________________________________________________________________________________________________________________________________

  //___________________________
  //____________________________
  //____________________________________________________________________________________________________________

  //_____

  //__________
  //___________________________
  @EqualsAndHashCode.Exclude
  private String vehicleNum;

  //_____

  //________
  //______________________________________
  //
  //_______________
  //_____________
  //_______________________
  //____________
  //___________________
  //_______________________________
  //___

  public Vehicle() {
    super();

    Circle circle = new Circle();
    circle.setRadius(5);
    circle.setViewOrder(-1);

    if (seqNum == 1) {
      circle.setFill(Color.rgb(191, 86, 255, 0.5));
    }
    else if (seqNum == 2) {
      circle.setFill(JavafxUtil.color_Red_light);
    }
    else if (seqNum == 3) {
      circle.setFill(JavafxUtil.color_Green);
    }
    else {
      circle.setFill(JavafxUtil.color_Grey);
    }

    paneWrap.getChildren().add(circle);

  }

  //_____

  @Messy
  @Warn
  //______________________________________________________________________________________________________________________________________________________________________________________________
  //___________________________________________________
  @Getter(AccessLevel.NONE)
  @Setter(AccessLevel.NONE)
  @Access(AccessType.PROPERTY) //_____________________________________________________________________________________________________________________

  @EqualsAndHashCode.Exclude
  //_____________________________________________________________________________________________
  //____________________________________________
  //_________________________________________________________________________________
  //__________________________________________________
  //_______________________________________________________________________________________________________
  //_____
  //__________________________________________
  private Point posActual; //________________________________________________

  //_________
  //___________________________
  //_____________________________________________________________
  //_____________________________________

  @Todo
  @Deprecated
  private transient Point posMeasuredByVehicleSelf;

  @Todo
  @Deprecated
  private transient Point posMeasuredByGps;

  //_____

  //___________________________________________________________________________
  //________________________________________________
  //__________________________________________________________________________________________________
  //_________________________________________________________________________________________________
  //
  //_____
  //____________________________________________________
  //____
  //_______________
  //_____________
  //______________________________________________________________________________

  //________
  @Getter(AccessLevel.NONE)
  @Setter(AccessLevel.NONE)
  @Deprecated
  private transient boolean statusSettingXYAtomically; //______________________
  //__________________________________________________________________________________

  //___________________________________________________

  //_____________________________________
  //___________________________________________________________________
  //________________________________

  //______________________________________________________

  //________________________________________________________

  public Point getPosActual() {
    //________________________________________________________________________________________________
    //__________________________________________________________________________________________________________________________
    if (statusSettingXYAtomically) {
      //_______________________________________________________________________________________________
      //________________________________________________________________________
      //_______________________________________
    }
    return posActual;
  }

  @MultiThreadPb //_________________________________________________________________________
  @PerformancePotential //____________
  public void setPosActual(Point posActual) { //_________________________________________________________________
    //_________________________________________
    //_________________________________
    //_____________
    //_____

    if (statusSettingXYAtomically) {
      System.err.println("Multi Threading pb");
      //________________________________________
    }

    statusSettingXYAtomically = true;

    if (posActual == null) {
      this.posActual = posActual;
      return;
    }

    this.posActual = posActual;

    //__________________________________________
    //__________________________________________
    //
    //__________
    //______________________________________________
    //___________________________________________________________________________________________________
    //__________________________________________________________________________________
    //__________________________________________
    @Messy byte dmy; //_
    publisherPosChanged.onNext(posActual);
    //____________________________________________________________________________________________________________

    //___
    //________________________________________________________________________________________________________________________________________________________________________________________________________
    //___
    //_____________________________________________________________________________________________________________

    //_________________________________________________________________________________
    //______________________
    Platform.runLater(() -> {
      paneWrap.setLayoutX(posActual.getX());
      paneWrap.setLayoutY(posActual.getY());
    });

    statusSettingXYAtomically = false;
  }

  //___________________________________________________________________________________________________________________
  //___________
  //_____________________________
  //__________________________________________
  //__________________________________________
  //___

  //_____

  //_____________________________
  @Setter(AccessLevel.NONE)
  //____________________________
  private transient PublishSubject<Point> publisherPosChanged = PublishSubject.create();

  //_____

  //_______
  //_____________
  //_______________________________________________________________________

  //_____
  //_____
  //_____

  @Getter(AccessLevel.NONE)
  @Setter(AccessLevel.NONE)
  private transient Double speedActualPrev;

  @Setter(AccessLevel.NONE)
  @Access(AccessType.PROPERTY)
  private Double speedActual;

  /**
_________________________________________________
__*/
  //_____________________________
  @MultiThreadPb
  @PerformancePotential //____________
  public void setSpeedActual(double speedActual) {
    if (speedActual < 0) { throw new Error(); }
    final Double speedActualPrev = this.speedActual;
    this.speedActual     = speedActual;
    this.speedActualPrev = speedActualPrev;
    //______________________________________________________________
    //_______________________________________________________________________________
    publisherSpeedChanged.onNext(new ImmutablePair<Double, Double>(speedActualPrev, speedActual));
  }

  @Setter(AccessLevel.NONE)
  //____________________________
  private transient PublishSubject<Pair<Double, Double>> publisherSpeedChanged = PublishSubject.create();

  @Todo
  @Deprecated
  private transient Double speedMeasuredByVehicleSelf;

  //_____

  private transient LocationGeneric locationDestination;

  private transient LocationGeneric locationOriginate;

  //_____

  //________________

  public static enum VehicleType {
    Car,
    Truck,
    Motorbike,
  }

  //________________________________________
  //_____________________________________________________________________________
  //__________________________________________________________________________________________________________
  //______________________________
  private VehicleType vehicleType;

  public static enum VehicleBrand {
    BMW,
    BYD,
    Jeep,
    Ford,
  }

  private VehicleBrand brandVehicle;

  private transient Integer amountPplActual;

  private Integer amountLimitMaxPpl;

  private transient Integer amountWeightActual;

  private Integer amountLimitMaxWeight;

  private Instant dateIntegrityVerifiedLastTime;

  public Vehicle(String vehicleNum, Double speedActual, VehicleType vehicleType, VehicleBrand brandVehicle) {
    this();
    this.vehicleNum   = vehicleNum;
    this.speedActual  = speedActual;
    this.vehicleType  = vehicleType;
    this.brandVehicle = brandVehicle;
  }

  //_____________
  //_____________
  //_____________

}
