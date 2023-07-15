package com.redfrog.trafficsm.model.roadway.fundamental;

import javax.persistence.Column;
import javax.persistence.ConstraintMode;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.redfrog.trafficsm.annotation.Messy;
import com.redfrog.trafficsm.annotation.UseByLibOnly;
import com.redfrog.trafficsm.model.roadway.fundamental.pseudo.RoadwaySegmentPseudoBegin;
import com.redfrog.trafficsm.root.EntityGeneral;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;

@Entity
//_____
@Getter
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
//__________________________________________________
public class RoadwayDirLinkerComponent extends EntityGeneral {

  //__________
  //___________________________
  @ManyToOne
  //___________________________________________________________________________________________________
  @JoinColumn(nullable = false, updatable = false)
  @EqualsAndHashCode.Include
  private final RoadwaySegment roadwaySegment;

  @Messy
  //__________
  //___________________________
  @ManyToOne
  @JoinColumn(nullable = false, updatable = false) //______________________________________
  //________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________
  //______________________________________________________________________________________
  //_________________________________________________
  //_____________________________________________________________________________________________________
  //_________________________________________________________________________
  //_____________________________________________________________________________________________________________________________________________________________
  //_______________________________________________________________________________________________________________________
  //__________________________________________________________
  //______________________________________________________________________________________
  //__________________________________________________________________________________________________________
  //___________________________________________________________________________________________________________________________________________________________________________________________
  //_________________________________________________________________________________________________________
  //__________________________________________________________________________________________________________________
  @EqualsAndHashCode.Include
  private final RoadwayPoint roadwayPoint;

  @UseByLibOnly
  @Deprecated
  protected RoadwayDirLinkerComponent() {
    super();
    this.roadwaySegment = null;
    this.roadwayPoint   = null;
  }

  /**
_______________________________________________
________________________________________________________________________________________
____
__________________________________________________________
__*/
  //______________________________________________________________________________________
  //_________________________________________________________________
  //_________________________________________________
  public RoadwayDirLinkerComponent(@NonNull RoadwaySegment roadwaySegment, @NonNull RoadwayPoint roadwayPoint) {
    super();
    this.roadwaySegment = roadwaySegment;
    this.roadwayPoint   = roadwayPoint;

    if (!(roadwaySegment instanceof RoadwaySegmentPseudoBegin)) {
      if (!(roadwaySegment.getRoadwayPointSp() == roadwayPoint || roadwaySegment.getRoadwayPointNp() == roadwayPoint)) {
        throw new Error("The point must be on one side of the Segment"); //
      }
    }
  }

  //_____________

  //___________
  //_____________________________________________________________________________________________________________________________________________________________________

}
