package com.redfrog.trafficsm.repository.dao;

import org.springframework.stereotype.Repository;

import com.redfrog.trafficsm.model.roadway.fundamental.RoadwayPoint;

//____________________________________________________
public class RoadwayPointDaoImpl extends AbstractGenericDao<RoadwayPoint> {
  
  public RoadwayPointDaoImpl() { /*_*/
    setClazz(RoadwayPoint.class); /*_*/
  }

}
