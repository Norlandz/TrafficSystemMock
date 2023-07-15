package com.redfrog.trafficsm.model.roadway.main;

import javax.persistence.Entity;

import com.redfrog.trafficsm.annotation.UseByLibOnly;
import com.redfrog.trafficsm.model.roadway.fundamental.RoadwayPoint;
import com.redfrog.trafficsm.model.roadway.fundamental.RoadwaySegment;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
//________________________
//_________
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
//__________________
public class RoadwayGenericLocationSegment extends RoadwaySegment {

  @UseByLibOnly
  @Deprecated
  protected RoadwayGenericLocationSegment() { super(); }

  public RoadwayGenericLocationSegment(RoadwayPoint roadwayPointA, RoadwayPoint roadwayPointB) { super(roadwayPointA, roadwayPointB); }

}
