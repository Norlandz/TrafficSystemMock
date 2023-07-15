package com.redfrog.trafficsm.test.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

//_______
//_____
//____________________________________
//__________________________________________________
//____________________________________________________________________________
//_________________________________________


//___
//__________________________________________________________________________________
//___
//______________________________________________________________________________________________


@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
//___________________
public class Ball {

  @Id
  @GeneratedValue
  @EqualsAndHashCode.Exclude
  private Long idSql;

  private String nameSig;

  private Double price;

  public Ball(String nameSig, Double price) {
    this.nameSig = nameSig;
    this.price = price;
  }
}
