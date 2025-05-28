package exportPDF.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class PDFAcceptedMatches {
    private int id;
    private String checkSum;
    private String externalID;
    private String listName;
    private String acceptedMatch;
    private String entityMainName;
    private PDFGoodGuys goodGuy;
}
