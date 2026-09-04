package com.c2.lc.lib.services;

import com.c2.lc.lib.bo.SearchBO;
import com.c2.lc.lib.services.interfaces.BaseDBService;
import com.google.gson.JsonObject;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class BaseDBServiceImpl extends BaseServicesImpl implements BaseDBService {


   @PersistenceContext private EntityManager entityManager;

    @Value("${fetch.max.size.limit}") protected int maxSize;

    protected void setEntityManager(EntityManager manager) {
        this.entityManager = manager;
    }

    protected Query getQuery(String sql) {
        return entityManager.createNativeQuery(sql);
    }

    protected Query getQuery(String sql, Class<?> cls) {
        return entityManager.createNativeQuery(sql, cls);
    }

    protected Object getSingleResult(Query query) {
        return getFirst(query).orElse(0);
    }

    protected BigDecimal getSingleResultBD(Query query) {
        return (BigDecimal) getFirst(query).orElse(null);
    }

    protected BigDecimal getSingleResultBDNotNUll(Query query) {
        BigDecimal value = new BigDecimal(0.00);
        Object obj = getFirst(query).orElse(null);
        if(obj == null) {
           obj = value;

        }
        return (BigDecimal) obj;
    }

    //access trail
    //access trail2
    //access trial 4
    /*
    protected String getSingleResultNull(Query query) {
        String ret = null;
        List<Object[]> list = getResultList(query);
        if (list != null && list.size() > 0) {
            Object[] objects = list.get(0);
            if (objects != null && objects.length > 0 && objects[0] != null) {
                ret = objects[0].toString();
            }
        }
        return ret;
    }
*/
    protected String getSingleResultNull(Query query) {
        String ret = null;
        Object obj = getFirst(query).orElse(null);
        if (obj != null) {
            ret = obj.toString();
        }
        return ret;
    }

    @NotNull
    private Optional getFirst(Query query) {
        return query.getResultList().stream().filter(Objects::nonNull).findFirst();
    }

    protected List<Object[]> getResultList(Query query) {
        return getResultList(query, 0, maxSize);
    }

    protected List<Object[]> getResultList(Query query, int page, int size) {
        query.setFirstResult(page * size);
        query.setMaxResults(size);
        return query.getResultList();
    }

    protected org.springframework.data.mongodb.core.query.Query getMongoSearchParameter(String field, SearchBO searchBO) {
        Criteria criteria = Criteria.where(field).regex(helper.getMongoSearchParameter(searchBO.getSearchTerm()), "i");
        return getMongoQuery(field, searchBO, criteria);
    }

    protected org.springframework.data.mongodb.core.query.Query getMongoEqualsQuery(String field, SearchBO searchBO) {
        Criteria criteria = Criteria.where(field).regex(searchBO.getSearchTerm(), "i");
        return getMongoQuery(field, searchBO, criteria);
    }

    protected org.springframework.data.mongodb.core.query.Query getMongoQuery(String field, SearchBO searchBO, Criteria criteria) {
        var query = org.springframework.data.mongodb.core.query.Query.query(criteria).with(Sort.by(Sort.Direction.ASC, field));
        query.limit(searchBO.getLimit());
        query.skip((long) searchBO.getPage() * searchBO.getLimit());
        return query;
    }

    protected org.springframework.data.mongodb.core.query.Query getMongoInQuery(String field, int page, int limit, Criteria criteria) {
        var query = org.springframework.data.mongodb.core.query.Query.query(criteria).with(Sort.by(Sort.Direction.ASC, field));
        query.limit(limit);
        query.skip((long) page * limit);
        return query;
    }

    protected org.springframework.data.mongodb.core.query.Query getMongoCount(String field, SearchBO searchBO) {
        Criteria criteria = Criteria.where(field).regex(searchBO.getSearchTerm(), "i");
        return org.springframework.data.mongodb.core.query.Query.query(criteria);
    }

    protected void setQueryParameters(Query query, @javax.validation.constraints.NotNull JsonObject parameters) {
        for (String key : parameters.keySet()) {
            query.setParameter(key, parameters.get(key).getAsString());
        }
    }

    protected String getInsertQuery(String tableName, JsonObject data) {
        return "INSERT INTO " + tableName + " ( " + getFields(data, "", ", ") + " ) VALUES ( " + getFields(data, ":", ", ") + " );";
    }

    protected String getDeleteQuery(String tableName, JsonObject data) {
        return " DELETE FROM " + tableName + " WHERE " + getFields(data, ":", " AND ") + ";";
    }

    private String getFields(JsonObject data, String prefix, String suffix) {
        StringBuilder sqlString = new StringBuilder();
        for (String key : data.keySet()) {
            sqlString.append(prefix).append(key).append(suffix);
        }
        return sqlString.substring(0, sqlString.length() - suffix.length());
    }

}
