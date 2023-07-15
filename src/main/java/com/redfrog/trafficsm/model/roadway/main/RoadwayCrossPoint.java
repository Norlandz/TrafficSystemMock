package com.redfrog.trafficsm.model.roadway.main;

import javax.persistence.Entity;

import com.redfrog.trafficsm.annotation.UseByLibOnly;
import com.redfrog.trafficsm.model.roadway.fundamental.RoadwayPoint;
import com.redfrog.trafficsm.shape.Point;

import lombok.Data;
import lombok.EqualsAndHashCode;

//_________________________________________________________________________

//________________________________________________________
//_____________________________________

//__________________________
//___________

//______________
//______________________________________

//_______________________________________________________________________________________________________

//______________________________________________________________________________________________________________________________________________________________________

//______________________________________________________________________
//__
//_______________________________________________
//_______________________________________________________________________________
//__________________________________
//______________________________________________________________________
//___________

//_________________________________________________________________________________________________________
//_____________________________________


@Entity
//_____
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
//__________________________________________________
public class RoadwayCrossPoint extends RoadwayPoint {

  @UseByLibOnly
  @Deprecated
  protected RoadwayCrossPoint() { super(); }
  
  public RoadwayCrossPoint(String idBsi, Point pointLocation) { super(idBsi, pointLocation); }

}
