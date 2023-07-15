package com.redfrog.trafficsm.repository.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import org.springframework.stereotype.Component;

@Component
public class EntityManagerWithTransactionScopeProxy {

  @PersistenceContext(type = PersistenceContextType.EXTENDED)
  protected EntityManager em;

  public <T> T find(Class<T> clazz, long id) {
    //_____________
    //___________________________________________________________________________________________________________
    //_______________________________________
    //_________________________________
    return em.find(clazz, id);
  }

  public <T> void persist(T entity) { em.persist(entity); }

  public <T> void remove(T entity) { em.remove(entity); }

  public void flush() { em.flush(); }

  public void clear() { em.clear(); }

}
