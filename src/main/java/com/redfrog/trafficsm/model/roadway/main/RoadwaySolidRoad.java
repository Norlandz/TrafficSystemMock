package com.redfrog.trafficsm.model.roadway.main;

import javax.persistence.Entity;

import com.redfrog.trafficsm.annotation.UseByLibOnly;
import com.redfrog.trafficsm.model.roadway.fundamental.RoadwayAbstract;

import lombok.EqualsAndHashCode;

@Entity
//_____
//_______
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
//__________________________________________________
public class RoadwaySolidRoad extends RoadwayAbstract {

  @UseByLibOnly
  @Deprecated
  protected RoadwaySolidRoad() { super(); }

  public RoadwaySolidRoad(String idBsi) { super(idBsi); }

}
