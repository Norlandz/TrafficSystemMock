package com.redfrog.trafficsm.model.roadway.main;

import java.time.Instant;
import java.util.concurrent.atomic.AtomicLong;

import javax.persistence.Entity;

import com.redfrog.trafficsm.annotation.UseByLibOnly;
import com.redfrog.trafficsm.model.roadway.fundamental.RoadwayPoint;
import com.redfrog.trafficsm.shape.Point;

import lombok.EqualsAndHashCode;

//_______
//_____
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
//__________________________________________________
public class RoadwayGenericLocationPointOnRoadway extends RoadwayPoint {

  //_______________
  //_____________
  //_______________________________________________

  private transient static final AtomicLong seqNumAtom = new AtomicLong(0L);

  //____
  public RoadwayGenericLocationPointOnRoadway(Point pointLocation) { super(RoadwayGenericLocationPointOnRoadway.class.getSimpleName() + Instant.now() + seqNumAtom.incrementAndGet(), pointLocation); }

}
