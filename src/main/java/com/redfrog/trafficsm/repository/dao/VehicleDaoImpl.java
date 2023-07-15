package com.redfrog.trafficsm.repository.dao;

import com.redfrog.trafficsm.model.Vehicle;

public class VehicleDaoImpl extends AbstractGenericDao<Vehicle> {
  
  public VehicleDaoImpl() { 
    setClazz(Vehicle.class); 
  }

}
