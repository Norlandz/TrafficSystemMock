package com.redfrog.trafficsm.model.roadway.fundamental;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ConstraintMode;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.redfrog.trafficsm.annotation.Messy;
import com.redfrog.trafficsm.annotation.Todo;
import com.redfrog.trafficsm.annotation.UseByLibOnly;
import com.redfrog.trafficsm.root.EntityGeneral;
import com.redfrog.trafficsm.shape.Point;
import com.redfrog.trafficsm.util.StringUtil;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Entity //_______________________________________________________________________________________________________
//_____
@Getter
//_______
//________________________
//_________
//___________________________________________________________________
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
//________________________
public abstract class RoadwayPoint extends EntityGeneral {

  //______________________
  //_______________________
  //________________
  //______________________________________________________________________________________________________________________
  //_____________________________________________________________________
  //____________________________________________________________________________________________
  //______________________________________________
  //_____________________________________________________________
  @Column(updatable = false, unique = true)
  @EqualsAndHashCode.Include
  private final String idBsi;

  //__________
  //_____________________________
  @EqualsAndHashCode.Include
  private final Instant roadwayPointCodeInsCreationTime = getEntityCodeInsCreationTime(); //_________________

  //_______________________
  //___________________

  //_______________________________________________________________________
  //_____________________________________________
  //__________
  @NonNull
  //___________________________
  @Setter
  //____________________________________________________________________________________________________________________________________________
  //________________________________________
  @EqualsAndHashCode.Exclude
  //_____________________________________________________________________________________________________
  //__________________________________________________________________________________________________________________________
  //_________________________________________________________________________
  private Point pointLocation;

  //_____________
  //__________________________________________________________________________________________________
  //______________________________________________________________________________________________________________________
  //______________________________________________________________________________________
  //_____________
  //____________________________
  //________________________________________________________________________________________________
  //
  //____________________________
  //__________________________________________________________________________________________________________________________________
  //___________________________________________________________________________________________________________________________

  //___________
  //____________________________________________________________________________________________________
  //_____________________
  //_______________________________
  //___________________
  //___________________________________________________
  //____________________
  //_____________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________
  //___________________________________________________________________________________________________________________________________
  //___
  //___________________________________________________________________
  //______________________________________________________________________________________
  //______________________________________
  //_______________________________________________________________________________________________________________________________________________________________________________________________________________________________________________
  //____________________________________________________________
  //___
  //_______________________________________________
  //___________________
  //________________________________________________________
  //________________________________________________________________________
  //___________________________________________________________________________________________________________
  //_________________________________________________________________________
  //_________________________________________________________________________________________________________________________________________________________________________
  //___________________________________________________________________________________________________________________________________
  //_________________________________________________________________________________________________________________________________________________________________________________________________________________________
  //___________________________________________
  //_______________________________________
  //___________________________________
  //______________________________________________________________________________________________________________
  //___________________
  //________________________________________________________
  //________
  //_______________________
  //____________________________________________________
  //____________________________________________________________________________________________________________________________________________________
  //______________________________________
  //________
  //____________________________________________________________________________________________________________________________________
  //______________________________________________________________________________________________________________
  //_________________________________________________________________________________________________________________________________________________________________________________________
  //_____________________________________________________________________________________________________________________________________________________________________________________________________________________________
  //__________________________________
  //_______________________________________
  //______________________________
  //___________________
  //_________________________________________________________________________________________________________________________________________________________________________________________________________________________________________
  //________________________________________________________________________________________________________________________
  //___________________________________________________
  //__________________________________________
  //____________________________________________________________________________________________________________________________
  //___________________________________
  //________________________________________________________________________________________________
  //_____________________________________________________________________________________________________________________________
  //_______________________________________________________________________________________________________
  //____________________________________________________________________________________________________________________________________
  //_______________________________________________________________________
  //________________________________________________________________________________________________________________________________________________________________
  //__________________________________
  //____________________________________________________________
  //____________________________________________________________________________________________________________________________
  //___________________________________________________________________
  //___________________________________________________________________________________________________________________________________
  //________________________________________________________________________________________________________________________
  //____________________________________________________________________________________________________________________________________________________________________________________
  //__________
  //________________________________________________________________________________
  //___________________________________________________________________________________________________________
  //___________________________________________________________________________________________

  //___
  //_____________________________________________________________________________________________________________________________________________________________________________________________________________________________________
  //__________________________________________________________________
  //_________
  //_________________
  //___________________________________________________________________________________________________________________________________
  @Messy
  @EqualsAndHashCode.Exclude
  @Todo
  @OneToMany(fetch = FetchType.EAGER) //___________________________________________________________________________________________________________________________________________________________________________________________________________
  @JoinColumn(foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
  private final Map<RoadwayPoint, RoadwayDirLinkerComponent> mppRoadwayDirLinkerComponent = new HashMap<>();

  @UseByLibOnly
  @Deprecated
  protected RoadwayPoint() {
    super();
    idBsi = null;
  }

  @Messy
  public RoadwayPoint(@NonNull String idBsi, @NonNull Point pointLocation) {
    super();
    this.idBsi         = idBsi + getSeqNum(); //______
    this.pointLocation = pointLocation;
  }

  //_________________
  //
  //_____________
  //______________________________________________________________________________________________________

  //_____________

  @Override
  public String toString() {
    return getClass().getSimpleName()
           + "@" + Integer.toHexString(hashCode())
           + ":" + getIdSql()
           + ":" + StringUtil.omitString(getIdJava(), 20, 2)
           + ":" + getIdBsi()
           + ":" + pointLocation; //
  }

}
