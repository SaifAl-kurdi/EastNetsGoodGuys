package search.dtos;

import lombok.Getter;
import lombok.Setter;
import search.model.GoodGuys;
import java.util.List;
import java.util.stream.Collectors;

@Getter @Setter
public class GoodGuysDTO {

    private int id;
    private String acceptedString;
    private String condition;
    private String comments;
    private String creationDate;
    private String createdBy;
    private String reportViolations;
    private String shared;
    private int rank;
    private List<AcceptedMatchDTO> acceptedMatches;

    public GoodGuysDTO(GoodGuys model) {
        this.id = model.getId();
        this.acceptedString = model.getAcceptedString();
        this.condition = model.getCondition();
        this.comments = model.getComments();
        this.creationDate = model.getCreationDate();
        this.createdBy = model.getCreatedBy();
        this.reportViolations = model.getReportViolations();
        this.shared = model.getShared();
        this.rank = model.getRank();
        if (model.getAcceptedMatches() != null) {
            this.acceptedMatches = model.getAcceptedMatches().stream().map(AcceptedMatchDTO::new).collect(Collectors.toList());
        }
    }

    public GoodGuysDTO() {  }
}