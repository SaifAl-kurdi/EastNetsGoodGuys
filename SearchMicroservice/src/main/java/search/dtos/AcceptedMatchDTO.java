package search.dtos;

import lombok.Getter;
import lombok.Setter;
import search.model.AcceptedMatches;

@Getter @Setter
public class AcceptedMatchDTO {

    private String checkSum;
    private String externalID;
    private String listName;
    private String acceptedMatch;
    private String entityMainName;

    public AcceptedMatchDTO(AcceptedMatches match) {
        this.checkSum = match.getCheckSum();
        this.externalID = match.getExternalID();
        this.listName = match.getListName();
        this.acceptedMatch = match.getAcceptedMatch();
        this.entityMainName = match.getEntityMainName();
    }
}
