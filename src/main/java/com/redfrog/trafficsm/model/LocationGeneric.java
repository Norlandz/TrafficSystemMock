package com.redfrog.trafficsm.model;

import javax.persistence.Embeddable;
import javax.persistence.Entity;

import com.redfrog.trafficsm.root.EntityGeneral;
import com.redfrog.trafficsm.shape.Point;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
//____________________________________
//___________
public class LocationGeneric extends EntityGeneral {

  private String nameLocation;
  
  private Point pointLocation;

}
