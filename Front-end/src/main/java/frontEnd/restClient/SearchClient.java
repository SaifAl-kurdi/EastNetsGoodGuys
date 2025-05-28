package frontEnd.restClient;

import frontEnd.dto.GoodGuysDTO;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import java.io.Serializable;
import java.util.List;

@Path("goodguys")
public interface SearchClient extends Serializable {

    @GET
    @Transactional
    @Produces(MediaType.APPLICATION_JSON)
    @Path("search")
    List<GoodGuysDTO> getGoodGuys(
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
