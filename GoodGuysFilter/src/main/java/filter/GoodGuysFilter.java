package filter;

import lombok.Getter;
import lombok.Setter;

import javax.ws.rs.QueryParam;

@Getter @Setter
public class GoodGuysFilter {

    @QueryParam("id")
    private Integer id;

    @QueryParam("acceptedString")
    private String acceptedString;

    @QueryParam("condition")
    private String condition;

    @QueryParam("comments")
    private String comments;

    @QueryParam("creationDate")
    private String creationDate;

    @QueryParam("createdBy")
    private String createdBy;

    @QueryParam("reportViolations")
    private String reportViolations;

    @QueryParam("shared")
    private String shared;

    @QueryParam("rank")
    private Integer rank;

    @QueryParam("externalID")
    private String externalID;

    @QueryParam("acceptedMatch")
    private String acceptedMatch;

    @QueryParam("entityMainName")
    private String entityMainName;
}
