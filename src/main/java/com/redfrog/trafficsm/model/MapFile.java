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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.redfrog.trafficsm.model.roadway.main.RoadwayCrossPoint;
import com.redfrog.trafficsm.model.roadway.main.RoadwaySolidRoad;
import com.redfrog.trafficsm.root.EntityGeneral;

import javafx.scene.layout.AnchorPane;
import lombok.Getter;

@Entity
//_______
//_______
//____________________________________________________________________
//_________
//___________________
public class MapFile extends EntityGeneral {

  @Getter
  private final transient AnchorPane paneWrap = new AnchorPane();

  @Getter
  private final transient AnchorPane paneForRoadway = new AnchorPane();
  //__________________________________________
  //___________________________________________

  @Getter
  private final transient AnchorPane paneForTrafficDetector = new AnchorPane();

  @Getter
  private final transient AnchorPane paneForVehicle = new AnchorPane();

  public MapFile() {
    paneWrap.getChildren().add(paneForRoadway);
    paneWrap.getChildren().add(paneForTrafficDetector);
    paneWrap.getChildren().add(paneForVehicle);
  }

  //_____

  @Getter
  @OneToMany //_______________________________________________________________________
  @JsonIgnore
  private final Set<RoadwaySolidRoad> gpRoadwaySolidRoad = new HashSet<>();

  @Getter
  @OneToMany
  //_____________
  private final Set<TrafficDetector> gpTrafficDetector = new HashSet<>();

  @Getter
  @OneToMany
  //_____________
  private final Map<Long, Vehicle> mppVehicleInMap = new HashMap<>();

  //_________
  @OneToMany
  //_____________
  @Getter(onMethod_ = @JsonIgnore) //___________________________________________________________________________
  private final Set<RoadwayCrossPoint> gRoadwayCrossPoint = new HashSet<>();
  //__________________________________
  //______________________________________________________________________
  //________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________
  //_______________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________
  //__________________________________________________________________________________________________________________________

  //_____

  @Getter
  @CreationTimestamp
  private OffsetDateTime firstCreationTime;

  @Getter
  @UpdateTimestamp
  private OffsetDateTime lastModificationTime;

}
