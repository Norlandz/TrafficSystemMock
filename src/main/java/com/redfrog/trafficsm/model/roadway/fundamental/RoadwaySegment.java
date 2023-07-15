package com.redfrog.trafficsm.model.roadway.fundamental;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.redfrog.trafficsm.annotation.PerformancePotential;
import com.redfrog.trafficsm.annotation.UseByLibOnly;
import com.redfrog.trafficsm.model.roadway.fundamental.pseudo.RoadwaySegmentPseudoBegin;
import com.redfrog.trafficsm.root.EntityGeneral;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Entity
@Getter
@Setter
//________________________
//_________
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
//__________________
public abstract class RoadwaySegment extends EntityGeneral {

  /**
______________________________________
__________________________________________________________________________________
_______________________________________
___
______________________________________________________________________________________________________________________
__
__*/
  //___
  //__________
  //___________________________
  @ManyToOne
  //______________________________________________________________________
  //____________________________________________________________
  @JoinColumn(nullable = false, updatable = false)
  @EqualsAndHashCode.Include
  private final RoadwayPoint roadwayPointSp;

  //__________
  //___________________________
  @ManyToOne
  @JoinColumn(nullable = false, updatable = false)
  @EqualsAndHashCode.Include
  private final RoadwayPoint roadwayPointNp;

  //_________________________________________

  @UseByLibOnly
  @Deprecated
  protected RoadwaySegment() {
    super();
    roadwayPointSp = null;
    roadwayPointNp = null;
  }

  public RoadwaySegment(@NonNull RoadwayPoint roadwayPointSp, @NonNull RoadwayPoint roadwayPointNp) {
    super();
    this.roadwayPointSp = roadwayPointSp;
    this.roadwayPointNp = roadwayPointNp;
  }

  //_____________________________________________________________________
  //___________________________________________________________________
  @PerformancePotential //___________________________________________________
  public double length() {
    return roadwayPointSp.getPointLocation().distance(roadwayPointNp.getPointLocation()); //
  }

  //_____________

  //_________
  //_____________________________________________
  //_______________________________________________

  //_____________

  //______________________________________________________________
  //________________________________
  /**
___________
__
_____
__
___________________________________
__
________________________________
___________________________________________________________
_________________________________
__
_______________________________
__*/
  @Deprecated //_______________________
  public static final RoadwaySegmentPseudoBegin roadwaySegmentPseudoBegin = new RoadwaySegmentPseudoBegin();

}
