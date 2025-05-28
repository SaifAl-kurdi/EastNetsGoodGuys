package frontEnd.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class GoodGuysDTO {
    private Integer id;
    private String acceptedString;
    private String condition;
    private String comments;
    private String creationDate;
    private String createdBy;
    private String reportViolations;
    private String shared;
    private Integer rank;
    private String checkSum;
    private String externalID;
    private String listName;
    private String acceptedMatch;
    private String entityMainName;
    private List<AcceptedMatchsDTO> acceptedMatches;
}
