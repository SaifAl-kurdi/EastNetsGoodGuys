package eastNets.controller;

import eastNets.dtos.UserDTO;
import eastNets.service.AuthenticationService;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;

@Path("login")
public class UserController {

    @Inject
    AuthenticationService authenticationService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(UserDTO userDTO) {
        try {
            String token = authenticationService.authenticateUser(userDTO.getUsername(), userDTO.getPassword());
            if (token == null) {
                return Response.status(Response.Status.UNAUTHORIZED).entity("Invalid").build();
            }
            NewCookie authCookie = new NewCookie("authToken", token, "/", null, "JWT Auth Token", 1800, true, true);
            return Response.ok(token).cookie(authCookie).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error processing login").build();
        }
    }
}