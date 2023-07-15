package com.redfrog.trafficsm.root;

import java.io.Serializable;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.atomic.AtomicLong;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import org.hibernate.annotations.DiscriminatorOptions;
import org.springframework.data.annotation.Version;

import com.redfrog.trafficsm.annotation.Debug;
import com.redfrog.trafficsm.util.StringUtil;
import com.redfrog.trafficsm.util.SystemMetric;
import com.redfrog.trafficsm.util.TimeUtil;

import lombok.EqualsAndHashCode;

//_________________________________________________________________________________________________________
//______________________________________

//_____________________________________________________________________________________________________________
//___________________________________________________________________________________________

//___________________________________________________________________________________________________________________________________________________

//__________________
@Entity
//____________________________________________________________________________
@Inheritance(strategy = InheritanceType.JOINED)
//______________________________________________________________________________________________________________
//_________________________________________________________________________________________________________________________
//_______________________________________________
//____________________________________________________________________________
//_______________________________________________________________________________________________
//___________________________________
//__________________
public abstract class EntityGeneral implements Serializable {

  //_____
  //_________________________________________________
  //___________________________________________________________________________________________________________________________________________________________________________________________________________________
  //____________________________________________________________________________
  //______________________________________
  //_____________________________________________
  //________________________________________________
  //____________________________________________________
  //_______________________________________________
  //____________________________
  //_____________________
  //_________________________________________
  //___________________________________________________________________________________
  //_________________________
  //__
  //______________________________________________________
  //__

  @Id
  @GeneratedValue(generator = "ID_GENERATOR")
  //_________________________________________________
  @EqualsAndHashCode.Exclude
  private Long idSql;
  //________________________________
  //___________________________________________________________
  //________________________________________
  //___________________________________________________________________________________________________
  //__________
  //_
  //_____________________________________
  //______________________________

  //__________
  @Column(nullable = false, updatable = false)
  private final String idJava;

  //______________________________________________________________________________________
  //____________________________________________________________________________________________________________________________________________________________
  @Column(nullable = false, updatable = false)
  //__________________________________________________________
  private final Instant entityCodeInsCreationTime = Instant.now().truncatedTo(ChronoUnit.MICROS); //_____________________________________________________________________________________________________________________

  //______________________________________________________________________________________________________________________________________________________________
  private transient static final AtomicLong seqNumAtom = new AtomicLong(0L);
  private transient final Long seqNum;

  private final String versionNum; //____

  @Version
  private Long optimisticVersionLock;

  private final Class<? extends EntityGeneral> clazz = this.getClass();

  //__________________________________________________________________________________________________________________________
  //___________________________________________________________________________________________________
  //_______________________________________________________________________________________________________________________________________
  protected EntityGeneral() {
    //________________________________________________________________________________________________________________________
    seqNum     = seqNumAtom.incrementAndGet();
    idJava     = String.format("%s--%s--%d", SystemMetric.appBootTime, TimeUtil.time2strnano(entityCodeInsCreationTime), seqNum);
    versionNum = "v0.0.1";
  }

  //____________________________________________
  public Long getIdSql() { return idSql; }

  public String getVersionNum() { return versionNum; }

  public String getIdJava() { return idJava; }

  public Instant getEntityCodeInsCreationTime() { return entityCodeInsCreationTime; }

  @Debug
  public static Long getStaticSeqNum() { return seqNumAtom.get(); }

  @Debug
  public Long getSeqNum() { return seqNum; }

  @Debug
  @Deprecated
  public void setIdSql(Long idSql) { this.idSql = idSql; }

  @Override
  public String toString() { return "‘" + getClass().getSimpleName() + "@" + Integer.toHexString(hashCode()) + " :: idSql=" + idSql + " :: " + StringUtil.omitString(idJava, 20, 2) + "’"; }

}
