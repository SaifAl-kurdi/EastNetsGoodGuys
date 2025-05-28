package search.controller;

import filter.GoodGuysFilter;
import lombok.Getter;
import lombok.Setter;
import org.eclipse.microprofile.context.ManagedExecutor;
import search.dtos.GoodGuysDTO;
import search.service.GoodGuysService;
import eastnets.validate.token.TokenValidator;
import javax.inject.Inject;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.concurrent.CompletionStage;

@Path("goodguys")
@Getter @Setter
public class SearchController {

    @Inject
    public GoodGuysService goodGuyService;

    @Inject
    public TokenValidator tokenValidator;

    @Inject
    public ManagedExecutor managedExecutor;

    @Transactional
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("search")
    public CompletionStage<List<GoodGuysDTO>> getGoodGuys(
            @HeaderParam("Authorization") String token,
            @BeanParam GoodGuysFilter filter) {
        if (!tokenValidator.isValidToken(token)) {
            Response.status(Response.Status.UNAUTHORIZED).entity("Invalid token").build();
            throw new WebApplicationException("Unauthorized", Response.Status.UNAUTHORIZED);
        }
        return managedExecutor.supplyAsync(() -> goodGuyService.getFilteredGoodGuys(
                filter.getId(),
                filter.getAcceptedString(),
                filter.getCondition(),
                filter.getComments(),
                filter.getCreationDate(),
                filter.getCreatedBy(),
                filter.getReportViolations(),
                filter.getShared(),
                filter.getRank(),
                filter.getExternalID(),
                filter.getAcceptedMatch(),
                filter.getEntityMainName()));
    }
}