package com.redfrog.trafficsm.repository.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

//_________________________________________________________________________________________________________

@Repository /*_*/
@Transactional /*_*/
public abstract class AbstractGenericDao<T> implements GenericDao<T> {

  @PersistenceContext(type = PersistenceContextType.EXTENDED) /*_*/
  protected EntityManager em; /*_*/

  private Class<T> clazz; /*_*/

  public void setClazz(Class<T> clazz) { /*_*/
    this.clazz = clazz; /*_*/
  }

  @Override /*_*/
  public T find(long id) { /*_*/
    //_____________
    //_____________________________________________________________________________________________________________________
    //_______________________________________
    //______________________________________
    return em.find(clazz, id);
  }

  //___________________________________________________________
  @Override /*_*/
  public void persist(T entity) { /*_*/
    em.persist(entity); /*_*/
  }

  @Override /*_*/
  public void remove(T entity) { /*_*/
    em.remove(entity); /*_*/
  }

  @Override /*_*/
  public List<T> getAll() { /*_*/
    return em
             .createQuery("from " + clazz.getName(), clazz) //__________________________________________________
             .getResultList(); /*_*/
  }

  @Override
  public void update(long id, String propertyName, Object propertyValue) { /*_*/
    em
      .createQuery("UPDATE " + clazz.getName() + " e SET e." + propertyName + " = :propertyValue WHERE e.id = :id")//_____________
      .setParameter("propertyValue", propertyValue)//____________________________________________________
      .setParameter("id", id) //
      .executeUpdate(); /*_*/
  }

  @Override
  public List<T> findByProperty(String propertyName, Object propertyValue) { /*_*/
    /*_*/
    return em
             .createQuery("SELECT e FROM " + clazz.getName() + " e WHERE e." + propertyName + " = :propertyValue", clazz)//___________________
             .setParameter("propertyValue", propertyValue)//____________________________________________________
             .getResultList(); /*_*/
  }

  @Override
  public void flush() { em.flush(); }
  
  @Override
  public void clear() { em.clear(); }
  
}