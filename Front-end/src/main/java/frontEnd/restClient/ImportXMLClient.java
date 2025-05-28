package frontEnd.restClient;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import jakarta.transaction.Transactional;

@Path("/import")
public interface ImportXMLClient {

    @Transactional
    @Path("/xml")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Response importXML(@HeaderParam("Authorization") String token,
                       @QueryParam("fileName") String fileName,
                       String encodedFile);
}
