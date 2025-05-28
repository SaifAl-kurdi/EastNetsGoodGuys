package search.repository;

import search.model.GoodGuys;
import java.util.List;

public interface GoodGuyRepository {
    public List<GoodGuys> findGoodGuys(
            Integer id, String acceptedString, String condition, String comments,
            String creationDate, String createdBy, String reportViolations,
            String shared, Integer rank, String externalID, String acceptedMatch, String entityMainName
    );
}
