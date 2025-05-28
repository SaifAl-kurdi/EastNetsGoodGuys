package exportPDF.dtos;

import exportPDF.model.PDFAcceptedMatches;
import exportPDF.model.PDFGoodGuys;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@NoArgsConstructor
@Getter @Setter
public class PDFGoodGuysDTO {
    private int id;
    private String acceptedString;
    private String condition;
    private String comments;
    private String creationDate;
    private String createdBy;
    private String reportViolations;
    private String shared;
    private int rank;
    private String checkSum;
    private String externalID;
    private String listName;
    private String acceptedMatch;
    private String entityMainName;
    private List<PDFAcceptedMatchDTO> acceptedMatches;

    public PDFGoodGuysDTO(PDFGoodGuys model) {
        this.id = model.getId();
        this.acceptedString = model.getAcceptedString();
        this.condition = model.getCondition();
        this.comments = model.getComments();
        this.creationDate = model.getCreationDate();
        this.createdBy = model.getCreatedBy();
        this.reportViolations = model.getReportViolations();
        this.shared = model.getShared();
        this.rank = model.getRank();

        if (model.getAcceptedMatches() != null && !model.getAcceptedMatches().isEmpty()) {
            PDFAcceptedMatches match = model.getAcceptedMatches().get(0);
            this.checkSum = match.getCheckSum();
            this.externalID = match.getExternalID();
            this.listName = match.getListName();
            this.acceptedMatch = match.getAcceptedMatch();
            this.entityMainName = match.getEntityMainName();
        }
    }
}
