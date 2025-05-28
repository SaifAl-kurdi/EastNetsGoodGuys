package search.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import search.model.AcceptedMatches;
import search.model.GoodGuys;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class GoodGuysRepositoryImplementation implements PanacheRepository<GoodGuys>, GoodGuyRepository {

    private final EntityManager entityManager;

    @Inject
    public GoodGuysRepositoryImplementation(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<GoodGuys> findGoodGuys(
            Integer id, String acceptedString, String condition, String comments,
            String creationDate, String createdBy, String reportViolations,
            String shared, Integer rank, String externalID, String acceptedMatch,
            String entityMainName) {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<GoodGuys> cq = criteriaBuilder.createQuery(GoodGuys.class);
        Root<GoodGuys> root = cq.from(GoodGuys.class);

        Join<GoodGuys, AcceptedMatches> joinMatches = null;
        if (needsJoin(externalID, acceptedMatch, entityMainName)) {
            joinMatches = root.join("acceptedMatches", JoinType.INNER);
        }

        List<Predicate> predicates = new ArrayList<>();
        addPredicateIfNotNull(predicates, criteriaBuilder, root, "id", id);
        addPredicateIfNotNull(predicates, criteriaBuilder, root, "acceptedString", acceptedString);
        addPredicateIfNotNull(predicates, criteriaBuilder, root, "condition", condition);
        addPredicateIfNotNull(predicates, criteriaBuilder, root, "comments", comments);
        addPredicateIfNotNull(predicates, criteriaBuilder, root, "creationDate", creationDate);
        addPredicateIfNotNull(predicates, criteriaBuilder, root, "createdBy", createdBy);
        addPredicateIfNotNull(predicates, criteriaBuilder, root, "reportViolations", reportViolations);
        addPredicateIfNotNull(predicates, criteriaBuilder, root, "shared", shared);
        addPredicateIfNotNull(predicates, criteriaBuilder, root, "rank", rank);

        if (joinMatches != null) {
            addPredicateIfNotNull(predicates, criteriaBuilder, joinMatches, "externalID", externalID);
            addPredicateIfNotNull(predicates, criteriaBuilder, joinMatches, "acceptedMatch", acceptedMatch);
            addPredicateIfNotNull(predicates, criteriaBuilder, joinMatches, "entityMainName", entityMainName);
        }

        cq.select(root).distinct(true).where(predicates.toArray(new Predicate[0]));
        return entityManager.createQuery(cq).getResultList();
    }

    private boolean needsJoin(String externalID, String acceptedMatch, String entityMainName) {
        return (externalID != null && !externalID.isEmpty()) ||
                (acceptedMatch != null && !acceptedMatch.isEmpty()) ||
                (entityMainName != null && !entityMainName.isEmpty());
    }

    private void addPredicateIfNotNull(List<Predicate> predicates, CriteriaBuilder cb, Path<?> path, String field, Object value) {
        if (value instanceof String) {
            String str = (String) value;
            if (str.isEmpty()) {
                predicates.add(cb.equal(path.get(field), str));
            }
        } else if (value != null) {
            predicates.add(cb.equal(path.get(field), value));
        }
    }
}
