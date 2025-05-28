package search.repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.HashMap;
import java.util.Map;

public class GoodGuyQueryBuilder {
    private StringBuilder query;
    private Map<String, Object> parameters;
    public GoodGuyQueryBuilder() {
        query = new StringBuilder("SELECT DISTINCT g FROM GoodGuys g INNER JOIN g.acceptedMatches a WHERE 1=1");
        parameters = new HashMap<>();
    }

    public GoodGuyQueryBuilder addCondition(String alias, String field, Object value) {
        if (value != null) {
            query.append(" AND ").append(alias).append(".").append(field).append(" = :").append(field);
            parameters.put(field, value);
        }
        return this;
    }

    public Query build(EntityManager entityManager) {
        Query q = entityManager.createQuery(query.toString());
        parameters.forEach(q::setParameter);
        return q;
    }
}



