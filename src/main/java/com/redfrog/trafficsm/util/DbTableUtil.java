package com.redfrog.trafficsm.util;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.query.internal.NativeQueryImpl;
import org.hibernate.transform.AliasedTupleSubsetResultTransformer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.CaseFormat;
import com.redfrog.trafficsm.annotation.Hack;

@Service
public class DbTableUtil {

  @PersistenceContext
  private EntityManager em;

  public static String ca2sn(Class<?> clazz) { return ca2sn(clazz.getSimpleName()); }

  //_____________
  public static String ca2sn(String str) { return CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, (str)); }

  @Transactional
  public String queryDbTableAsStr_selectAll(Class<?> clazz) { return queryDbTableAsStr_selectAll(ca2sn(clazz.getSimpleName())); }

  @Transactional
  public String queryDbTableAsStr_selectAll(String tableName) {
    String queryStr = "select * FROM " + tableName + " ee";
    return queryDbTableAsStr(queryStr, true);
  }

  @Transactional
  public List queryDbTable_resultList(Class<?> clazz) {
    String tableName = ca2sn(clazz.getSimpleName());
    String queryStr = "select * FROM " + tableName + " ee";
    return em.createNativeQuery(queryStr).getResultList();
  }

  //_______________
  @Hack
  //__________________________________________________________________________________________
  public static class AliasToEntityMapResultTransformer_LinkedHashMap extends AliasedTupleSubsetResultTransformer {
    public static final AliasToEntityMapResultTransformer_LinkedHashMap INSTANCE = new AliasToEntityMapResultTransformer_LinkedHashMap();      private AliasToEntityMapResultTransformer_LinkedHashMap() {}      @Override     public Object transformTuple(Object[] tuple, String[] aliases) {       Map result = new LinkedHashMap<>(tuple.length);       for (int i = 0; i < tuple.length; i++) {         String alias = aliases[i];         if (alias != null) { result.put(alias, tuple[i]); }       }       return result;     }      @Override     public boolean isTransformedValueATupleElement(String[] aliases, int tupleLength) { return false; }      private Object readResolve() { return INSTANCE; }
  }
  //______________

  @Transactional
  public String queryDbTableAsStr(String queryStr, boolean mode_NativeSql) {
    //____________________________________________________________________________________________________________________________
    //_____________________________________________________________________________________________________________________________
    //_________________________________________________
    //____________________________________________________________________________________________________________________________________________________________________________
    //________________________________________
    //___________________________________________________
    //_________
    //_________

    //_______________________________________________
    //___________________________________________________________________________________________________________________________________________________________________
    //____________________________________
    //_____________________________________________
    //_________________________________________________________
    //_________
    //_______
    //____________
    //_____________________________________________________
    //_______
    //_______
    //__________________________

    String result = queryStr + "\n";

    List resultList;
    if (mode_NativeSql) {
      resultList = em.createNativeQuery(queryStr).getResultList();

      if (!resultList.isEmpty()) {
        @Hack NativeQueryImpl query = (NativeQueryImpl) em.createNativeQuery(queryStr);
        query.setResultTransformer(AliasToEntityMapResultTransformer_LinkedHashMap.INSTANCE);
        List<LinkedHashMap<String, Object>> listMap = query.getResultList();
        resultList.add(0, listMap.get(0).keySet().toArray());
      }
    }
    else {
      resultList = em.createQuery(queryStr).getResultList();
    }

    if (resultList.isEmpty()) {
      return result + "\n";
    }
    else {
      Object row = resultList.get(0);
      if (row instanceof Object[]) {

        //_____________________________________________________________
        //_________________________________
        return result += MathUtil.printTable((List<Object[]>) resultList);
      }
      else {
        return result += resultList.toString() + "\n";
      }
    }

  }
  //___________________________________________________

}
