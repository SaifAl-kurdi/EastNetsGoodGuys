package search.service;

import search.dtos.GoodGuysDTO;
import java.util.List;

public interface GoodGuysService {
    public List<GoodGuysDTO> getFilteredGoodGuys(
            Integer id, String acceptedString, String condition, String comments,
            String creationDate, String createdBy, String reportViolations,
            String shared, Integer rank, String externalID, String acceptedMatch, String entityMainName
    );
}
