package com.redfrog.trafficsm.repository.springdatajparepo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.redfrog.trafficsm.model.roadway.fundamental.RoadwayAbstract;

public interface RoadwayAbstractRepository extends JpaRepository<RoadwayAbstract, Long> {}
