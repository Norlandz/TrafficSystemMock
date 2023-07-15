package com.redfrog.trafficsm.model.roadway.main;

import java.util.List;
import java.util.Set;

import javax.persistence.Entity;

import com.redfrog.trafficsm.annotation.UseByLibOnly;
import com.redfrog.trafficsm.model.roadway.fundamental.RoadwayPoint;
import com.redfrog.trafficsm.root.EntityGeneral;
import com.redfrog.trafficsm.shape.Point;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Entity
//_____
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
//__________________________________________________
public class RoadwayNormalPoint extends RoadwayPoint {

  @UseByLibOnly
  @Deprecated
  protected RoadwayNormalPoint() { super(); }

  public RoadwayNormalPoint(String idBsi, Point pointLocation) { super(idBsi, pointLocation); }

}
