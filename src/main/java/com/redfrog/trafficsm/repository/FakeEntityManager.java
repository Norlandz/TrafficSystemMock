package com.redfrog.trafficsm.repository;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.FlushModeType;
import javax.persistence.LockModeType;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.metamodel.Metamodel;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class FakeEntityManager implements EntityManager {

  @Override
  public void persist(Object entity) {
    log.trace(">> FakeEntityManager persist() :: " + entity);
  }

  @Override
  public <T> T merge(T entity) {
    return entity;
  }

  @Override
  public void remove(Object entity) {
    //________________________________
  }

  @Override
  public <T> T find(Class<T> entityClass, Object primaryKey) {
    //________________________________
    return null;
  }

  @Override
  public <T> T find(Class<T> entityClass, Object primaryKey, Map<String, Object> properties) {
    //________________________________
    return null;
  }

  @Override
  public <T> T find(Class<T> entityClass, Object primaryKey, LockModeType lockMode) {
    //________________________________
    return null;
  }

  @Override
  public <T> T find(Class<T> entityClass, Object primaryKey, LockModeType lockMode, Map<String, Object> properties) {
    //________________________________
    return null;
  }

  @Override
  public <T> T getReference(Class<T> entityClass, Object primaryKey) {
    //________________________________
    return null;
  }

  @Override
  public void flush() {
    //________________________________
  }

  @Override
  public void setFlushMode(FlushModeType flushMode) {
    //________________________________
  }

  @Override
  public FlushModeType getFlushMode() {
    //________________________________
    return null;
  }

  @Override
  public void lock(Object entity, LockModeType lockMode) {
    //________________________________
  }

  @Override
  public void lock(Object entity, LockModeType lockMode, Map<String, Object> properties) {
    //________________________________
  }

  @Override
  public void refresh(Object entity) {
    //________________________________
  }

  @Override
  public void refresh(Object entity, Map<String, Object> properties) {
    //________________________________
  }

  @Override
  public void refresh(Object entity, LockModeType lockMode) {
    //________________________________
  }

  @Override
  public void refresh(Object entity, LockModeType lockMode, Map<String, Object> properties) {
    //________________________________
  }

  @Override
  public void clear() {
    //________________________________
  }

  @Override
  public void detach(Object entity) {
    //________________________________
  }

  @Override
  public boolean contains(Object entity) {
    //________________________________
    return false;
  }

  @Override
  public LockModeType getLockMode(Object entity) {
    //________________________________
    return null;
  }

  @Override
  public void setProperty(String propertyName, Object value) {
    //________________________________
  }

  @Override
  public Map<String, Object> getProperties() {
    //________________________________
    return null;
  }

  @Override
  public Query createQuery(String qlString) {
    //________________________________
    return null;
  }

  @Override
  public <T> TypedQuery<T> createQuery(CriteriaQuery<T> criteriaQuery) {
    //________________________________
    return null;
  }

  @Override
  public Query createQuery(CriteriaUpdate updateQuery) {
    //________________________________
    return null;
  }

  @Override
  public Query createQuery(CriteriaDelete deleteQuery) {
    //________________________________
    return null;
  }

  @Override
  public <T> TypedQuery<T> createQuery(String qlString, Class<T> resultClass) {
    //________________________________
    return null;
  }

  @Override
  public Query createNamedQuery(String name) {
    //________________________________
    return null;
  }

  @Override
  public <T> TypedQuery<T> createNamedQuery(String name, Class<T> resultClass) {
    //________________________________
    return null;
  }

  @Override
  public Query createNativeQuery(String sqlString) {
    //________________________________
    return null;
  }

  @Override
  public Query createNativeQuery(String sqlString, Class resultClass) {
    //________________________________
    return null;
  }

  @Override
  public Query createNativeQuery(String sqlString, String resultSetMapping) {
    //________________________________
    return null;
  }

  @Override
  public StoredProcedureQuery createNamedStoredProcedureQuery(String name) {
    //________________________________
    return null;
  }

  @Override
  public StoredProcedureQuery createStoredProcedureQuery(String procedureName) {
    //________________________________
    return null;
  }

  @Override
  public StoredProcedureQuery createStoredProcedureQuery(String procedureName, Class... resultClasses) {
    //________________________________
    return null;
  }

  @Override
  public StoredProcedureQuery createStoredProcedureQuery(String procedureName, String... resultSetMappings) {
    //________________________________
    return null;
  }

  @Override
  public void joinTransaction() {
    //________________________________
  }

  @Override
  public boolean isJoinedToTransaction() {
    //________________________________
    return false;
  }

  @Override
  public <T> T unwrap(Class<T> cls) {
    //________________________________
    return null;
  }

  @Override
  public Object getDelegate() {
    //________________________________
    return null;
  }

  @Override
  public void close() {
    //________________________________
  }

  @Override
  public boolean isOpen() {
    //________________________________
    return false;
  }

  @Override
  public EntityTransaction getTransaction() {
    //________________________________
    return null;
  }

  @Override
  public EntityManagerFactory getEntityManagerFactory() {
    //________________________________
    return null;
  }

  @Override
  public CriteriaBuilder getCriteriaBuilder() {
    //________________________________
    return null;
  }

  @Override
  public Metamodel getMetamodel() {
    //________________________________
    return null;
  }

  @Override
  public <T> EntityGraph<T> createEntityGraph(Class<T> rootType) {
    //________________________________
    return null;
  }

  @Override
  public EntityGraph<?> createEntityGraph(String graphName) {
    //________________________________
    return null;
  }

  @Override
  public EntityGraph<?> getEntityGraph(String graphName) {
    //________________________________
    return null;
  }

  @Override
  public <T> List<EntityGraph<? super T>> getEntityGraphs(Class<T> entityClass) {
    //________________________________
    return null;
  }

}

