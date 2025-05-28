package exportPDF.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.util.List;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class PDFGoodGuys {
    private int id;
    private String acceptedString;
    private String condition;
    private String comments;
    private String creationDate;
    private String createdBy;
    private String reportViolations;
    private String shared;
    private int rank;
    private List<PDFAcceptedMatches> acceptedMatches;
}
