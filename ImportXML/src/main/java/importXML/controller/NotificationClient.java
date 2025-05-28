package importXML.controller;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/")
public interface NotificationClient {

    @GET
    @Path("GetNotification")
    @Produces(MediaType.APPLICATION_JSON)
    Response sendNotification(@HeaderParam("Authorization") String token);
}