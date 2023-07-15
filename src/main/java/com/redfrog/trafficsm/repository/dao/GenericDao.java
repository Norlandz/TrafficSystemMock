package com.redfrog.trafficsm.repository.dao;

import java.util.List;

//_________________________________________________________________________________________________

public interface GenericDao<T> {
  T find(long id); /*_*/

  void persist(T entity); /*_*/

  void remove(T entity); /*_*/

  void update(long id, String propertyName, Object propertyValue); /*_*/

  List<T> getAll(); /*_*/

  List<T> findByProperty(String propertyName, Object propertyValue); /*_*/
  
  void clear();

  void flush();
}
