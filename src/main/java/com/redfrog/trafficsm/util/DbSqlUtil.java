package com.redfrog.trafficsm.util;

import java.util.List;

import javax.persistence.EntityManager;

import lombok.NonNull;

public class DbSqlUtil {

  //________________________________________________________________________
  public static int deleteByArrId(Class<?> clazz, @NonNull List<Long> arr_idSql, @NonNull EntityManager em, @NonNull String vN_idSql) {
    //_______________________________________________________
    if (arr_idSql.isEmpty()) { throw new Error(); }
    for (Long idSql_curr : arr_idSql) {
      if (idSql_curr == null) { throw new Error(); }
    }
    if (vN_idSql.matches("\\W")) { throw new Error(); }

    String query_delete = "delete from " + clazz.getSimpleName() + " EE "
                          + "\n" + "where EE." + vN_idSql + " in (:jp_arr_idSql)";
    int amount_RowAffected = em.createQuery(query_delete)
                               .setParameter("jp_arr_idSql", arr_idSql)
                               .executeUpdate();

    if (amount_RowAffected == 0) { throw new Error("amount_RowAffected == 0" + " :: " + arr_idSql); }

    return amount_RowAffected;

  }

}
