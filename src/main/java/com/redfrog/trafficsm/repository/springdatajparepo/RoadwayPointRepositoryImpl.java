package com.redfrog.trafficsm.repository.springdatajparepo;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class RoadwayPointRepositoryImpl implements RoadwayPointRepositoryCustom {
  @PersistenceContext
  private EntityManager entityManager;

  public void clear() { entityManager.clear(); }
}
