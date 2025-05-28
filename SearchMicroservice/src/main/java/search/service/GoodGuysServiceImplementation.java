package search.service;

import org.springframework.stereotype.Service;
import search.dtos.GoodGuysDTO;
import search.model.GoodGuys;
import search.repository.GoodGuyRepository;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

@Service
@ApplicationScoped
public class GoodGuysServiceImplementation implements GoodGuysService{

    @Inject
    public GoodGuyRepository goodGuyRepository;

    @Override
    public List<GoodGuysDTO> getFilteredGoodGuys(
            Integer id, String acceptedString, String condition, String comments,
            String creationDate, String createdBy, String reportViolations,
            String shared, Integer rank, String externalID, String acceptedMatch, String entityMainName) {

        List<GoodGuys> models = goodGuyRepository.findGoodGuys (
                id, acceptedString, condition, comments, creationDate, createdBy,
                reportViolations, shared, rank, externalID, acceptedMatch, entityMainName
        );
        return models.stream().map(GoodGuysDTO::new).collect(Collectors.toList());
    }
}
