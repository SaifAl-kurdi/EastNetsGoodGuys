package search.repository;

/*
import search.model.GoodGuys;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@ApplicationScoped
public class GoodGuyRepository2 implements GoodGuyRepository {

    @Inject
    EntityManager entityManager;

    @Override
    public List<GoodGuys> findGoodGuys(Integer id, String acceptedString, String condition, String comments, String creationDate, String createdBy, String reportViolations, String shared, Integer rank, String externalID, String acceptedMatch, String entityMainName) {
        GoodGuyQueryBuilder builder = new GoodGuyQueryBuilder();
        builder.addCondition("g", "id", id)
                .addCondition("g", "acceptedString", acceptedString)
                .addCondition("g", "condition", condition)
                .addCondition("g", "comments", comments)
                .addCondition("g", "creationDate", creationDate)
                .addCondition("g", "createdBy", createdBy)
                .addCondition("g", "reportViolations", reportViolations)
                .addCondition("g", "shared", shared)
                .addCondition("g", "rank", rank)
                .addCondition("a", "externalID", externalID)
                .addCondition("a", "acceptedMatch", acceptedMatch)
                .addCondition("a", "entityMainName", entityMainName);

        Query query = builder.build(entityManager);
        return query.getResultList();
    }
}*/
