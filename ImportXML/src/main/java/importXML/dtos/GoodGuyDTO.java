package importXML.dtos;

import importXML.adapter.BooleanAdapter;
import importXML.adapter.LocalDateTimeAdapter;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDateTime;
import java.util.List;

@XmlRootElement(name = "GOODGUY")
public class GoodGuyDTO {

    @XmlElement(name = "ID")
    private int id;

    @XmlElement(name = "ACCEPTED_STRING")
    private String acceptedString;

    @XmlElement(name = "COMMENTS")
    private String comments;

    @XmlElement(name = "CONDITION")
    private String condition;

    @XmlElement(name = "CREATED_BY")
    private String createdBy;

    @XmlElement(name = "CREATION_DATE")
    @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
    private LocalDateTime creationDate;

    @XmlElement(name = "RANK")
    private int rank;

    @XmlElement(name = "REPORT_VIOLATIONS")
    @XmlJavaTypeAdapter(BooleanAdapter.class)
    private Boolean reportViolations;

    @XmlElement(name = "SHARED")
    @XmlJavaTypeAdapter(BooleanAdapter.class)
    private Boolean shared;

    @XmlElement(name = "ACCEPTED_MATCHES")
    private List<AcceptedMatchesDTO> acceptedMatches;


    public String getAcceptedStringCustom() {
        return acceptedString;
    }
    public void setAcceptedStringCustom(String acceptedString) {
        this.acceptedString = acceptedString;
    }

    public String getCommentsCustom() {
        return comments;
    }
    public void setCommentsCustom(String comments) {
        this.comments = comments;
    }

    public String getConditionCustom() {
        return condition;
    }
    public void setConditionCustom(String condition) {
        this.condition = condition;
    }

    public String getCreatedByCustom() {
        return createdBy;
    }
    public void setCreatedByCustom(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getCreationDateCustom() {
        return creationDate;
    }
    public void setCreationDateCustom(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public int getRankCustom() {
        return rank;
    }
    public void setRankCustom(int rank) {
        this.rank = rank;
    }

    public Boolean getReportViolationsCustom() {
        return reportViolations;
    }
    public void setReportViolationsCustom(Boolean reportViolations) {
        this.reportViolations = reportViolations;
    }

    public Boolean getSharedCustom() {
        return shared;
    }
    public void setSharedCustom(Boolean shared) {
        this.shared = shared;
    }

    public List<AcceptedMatchesDTO> getAcceptedMatchesCustom() {
        return acceptedMatches;
    }
    public void setAcceptedMatchesCustom(List<AcceptedMatchesDTO> acceptedMatches) {
        this.acceptedMatches = acceptedMatches;
    }
}