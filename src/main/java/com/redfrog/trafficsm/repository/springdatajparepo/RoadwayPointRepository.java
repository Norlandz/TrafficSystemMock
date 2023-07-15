package com.redfrog.trafficsm.repository.springdatajparepo;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.data.jpa.repository.JpaRepository;

import com.redfrog.trafficsm.model.roadway.fundamental.RoadwayPoint;

public interface RoadwayPointRepository extends JpaRepository<RoadwayPoint, Long>, RoadwayPointRepositoryCustom {}
