package com.redfrog.trafficsm.model;

import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.redfrog.trafficsm.model.roadway.main.RoadwaySolidRoad;
import com.redfrog.trafficsm.root.EntityGeneral;

import lombok.Getter;

@Entity
//_______
//_______
//____________________________________________________________________
//_________
//___________________
public class VehicleInventory extends EntityGeneral {

  @Getter
  @OneToMany
  private final Map<Long, Vehicle> mppVehicle = new HashMap<>();

  //_____

  @Getter
  @CreationTimestamp
  private OffsetDateTime saveTime;

  @Getter
  @UpdateTimestamp
  private OffsetDateTime lastModificationTime;

}
