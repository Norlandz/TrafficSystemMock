package com.redfrog.trafficsm.repository.springdatajparepo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.redfrog.trafficsm.model.roadway.fundamental.RoadwaySegment;

public interface RoadwaySegmentRepository extends JpaRepository<RoadwaySegment, Long> {}
