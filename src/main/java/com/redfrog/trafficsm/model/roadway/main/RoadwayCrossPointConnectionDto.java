package com.redfrog.trafficsm.model.roadway.main;

import com.redfrog.trafficsm.shape.Point;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

//_______
//_______
//____________________________________
//____________________________________________________
//________________________________________________________________
//
//_______________
//_____________
//______________________________________________________
//__
//__
//__
//
//_

/**
_______________________________

____
_____________________

____________________________
_____________________________________________
_____________________________________________

________________

____________________________________________
*/
@Getter
//_______
//___________________
public class RoadwayCrossPointConnectionDto {

  //____

  private final Point pointLocation;

  //___________________________________
  private final RoadwayNormalSegment roadwaySegmentA;
  private final RoadwayNormalSegment roadwaySegmentB;

  //____

  private final RoadwayCrossPoint roadwayCrossPoint;

  //____

  private final RoadwaySolidRoad roadwayAp;
  private final RoadwaySolidRoad roadwayBp;

  //____

  public RoadwayCrossPointConnectionDto(Point pointLocation, RoadwayNormalSegment roadwaySegmentA, RoadwayNormalSegment roadwaySegmentB, RoadwaySolidRoad roadwayAp, RoadwaySolidRoad roadwayBp) {
    super();
    //________________________________
    //____________________________________________________________________________________________
    //_______________________________________
    this.pointLocation     = pointLocation;
    this.roadwaySegmentA   = roadwaySegmentA;
    this.roadwaySegmentB   = roadwaySegmentB;
    this.roadwayCrossPoint = null;
    this.roadwayAp        = roadwayAp;
    this.roadwayBp        = roadwayBp;
  }

  public RoadwayCrossPointConnectionDto(RoadwayCrossPoint roadwayCrossPoint, RoadwaySolidRoad roadwayAp, RoadwaySolidRoad roadwayBp) {
    this.pointLocation     = null;
    this.roadwaySegmentA   = null;
    this.roadwaySegmentB   = null;
    this.roadwayCrossPoint = roadwayCrossPoint;
    this.roadwayAp        = roadwayAp;
    this.roadwayBp        = roadwayBp;
  }

  //____

  public Point getPointLocationEventually() { return pointLocation != null ? pointLocation : roadwayCrossPoint.getPointLocation(); }

}