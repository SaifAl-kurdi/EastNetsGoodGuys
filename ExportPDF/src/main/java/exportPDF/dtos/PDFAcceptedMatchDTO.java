package exportPDF.dtos;

import exportPDF.model.PDFAcceptedMatches;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class PDFAcceptedMatchDTO {
    private String checkSum;
    private String externalID;
    private String listName;
    private String acceptedMatch;
    private String entityMainName;

    public PDFAcceptedMatchDTO(PDFAcceptedMatches matches) {
        this.checkSum = matches.getCheckSum();
        this.externalID = matches.getExternalID();
        this.listName = matches.getListName();
        this.acceptedMatch = matches.getAcceptedMatch();
        this.entityMainName = matches.getEntityMainName();
    }
}
