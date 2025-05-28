package controller;

import eastnets.validate.token.TokenValidator;
import service.NotificationService;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/")
@ApplicationScoped
public class NotificationController {

    @Inject
    TokenValidator tokenValidator;

    @Inject
    NotificationService notificationService;

    @Transactional
    @Path("GetNotification")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String sendNotification(@HeaderParam("Authorization") String token,
                                   @QueryParam("fileName") String fileName,
                                   @QueryParam("goodGuysCount") int goodGuysCount) {
        if (!tokenValidator.isValidToken(token)){
            Response.status(Response.Status.UNAUTHORIZED).entity("Invalid token").build();
        }
        String message = notificationService.notificationServices(fileName, goodGuysCount, token);
        System.out.println(message);
        return message;
    }
}
