package frontEnd.restClient;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

@Path("pdf")
public interface ExportPDFClient {

    @GET
    @Path("/generate")
    @Produces("application/pdf")
    Response getPDFReport(
            @HeaderParam("Authorization") String token,
            @QueryParam("id") Integer id,
            @QueryParam("acceptedString") String acceptedString,
            @QueryParam("condition") String condition,
            @QueryParam("comments") String comments,
            @QueryParam("creationDate") String creationDate,
            @QueryParam("createdBy") String createdBy,
            @QueryParam("reportViolations") String reportViolations,
            @QueryParam("shared") String shared,
            @QueryParam("rank") Integer rank,
            @QueryParam("externalID") String externalID,
            @QueryParam("acceptedMatch") String acceptedMatch,
            @QueryParam("entityMainName") String entityMainName
    );
}
