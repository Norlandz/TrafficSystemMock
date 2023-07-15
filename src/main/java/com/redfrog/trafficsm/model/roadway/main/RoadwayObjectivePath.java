package com.redfrog.trafficsm.model.roadway.main;

import java.util.List;

import javax.persistence.Entity;

import com.redfrog.trafficsm.annotation.UseByLibOnly;
import com.redfrog.trafficsm.model.roadway.fundamental.RoadwayAbstract;

import lombok.EqualsAndHashCode;
import lombok.NonNull;

@Entity
//_____
//_______
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
//__________________
public class RoadwayObjectivePath extends RoadwayAbstract {

  @UseByLibOnly
  @Deprecated
  public RoadwayObjectivePath() { super(); }

  public RoadwayObjectivePath(String idBsi) { super(idBsi); }

}
