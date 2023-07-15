package com.redfrog.trafficsm.model;

import java.time.Instant;

import javax.persistence.Entity;

import com.google.errorprone.annotations.Immutable;
import com.redfrog.trafficsm.annotation.Messy;
import com.redfrog.trafficsm.annotation.UseByLibOnly;
import com.redfrog.trafficsm.annotation.Warn;
import com.redfrog.trafficsm.model.Vehicle.VehicleBrand;
import com.redfrog.trafficsm.model.Vehicle.VehicleType;
import com.redfrog.trafficsm.root.EntityGeneral;
import com.redfrog.trafficsm.shape.Point;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
//________
@Immutable
//_______
//____________________________________
@ToString
@AllArgsConstructor
//__________________________________________________
public class VehicleInfoTrafficDetectorDto extends EntityGeneral {

  private final Instant detectionCreationTime;

  private final Instant detectionCodeInsCreationTime = getEntityCodeInsCreationTime();

  //_____

  //_________

  private final String idBsiOfDetection;

  private final String idBsiOfVehicle;

  private final Instant vehicleCreationTime;

  private final String vehicleNum;

  //_____
  //_____

  @Messy
  @Warn
  //______
  /**
_______________________________
__________________________________________________________________________
__*/
  private final Point posMeasuredByTrafficDetector;

  //_____

  private final Double speedMeasuredByTrafficDetector;

  //_____
  //_____

  private final LocationGeneric locationDestination;

  private final LocationGeneric locationOriginate;

  //_____

  private final VehicleType vehicleType;

  private final VehicleBrand brandVehicle;

  private final Integer amountPplActual;

  private final Integer amountLimitMaxPpl;

  private final Integer amountWeightActual;

  private final Integer amountLimitMaxWeight;

  private final Instant dateIntegrityVerifiedLastTime;

  //_____

  @UseByLibOnly
  @Deprecated
  protected VehicleInfoTrafficDetectorDto() {
    super();
    this.detectionCreationTime          = null;
    this.idBsiOfDetection               = null;
    this.idBsiOfVehicle                 = null;
    this.vehicleCreationTime            = null;
    this.vehicleNum                     = null;
    this.posMeasuredByTrafficDetector   = null;
    this.speedMeasuredByTrafficDetector = null;
    this.locationDestination            = null;
    this.locationOriginate              = null;
    this.vehicleType                    = null;
    this.brandVehicle                   = null;
    this.amountPplActual                = null;
    this.amountLimitMaxPpl              = null;
    this.amountWeightActual             = null;
    this.amountLimitMaxWeight           = null;
    this.dateIntegrityVerifiedLastTime  = null;
  }

}
