package importXML.controller;

import importXML.service.GoodGuysXMLImporter;
import eastnets.validate.token.TokenValidator;
import org.eclipse.microprofile.context.ManagedExecutor;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Base64;

@Path("/import")
public class XMLImportController {

    @Inject
    public GoodGuysXMLImporter goodGuysXMLImporter;

    @Inject
    public TokenValidator tokenValidator;

    @Inject
    public ManagedExecutor managedExecutor;

    @POST
    @Path("/xml")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response importXML(@HeaderParam("Authorization") String token,
                              @QueryParam("fileName") String fileName,
                              String fileContent) {
        if (!tokenValidator.isValidToken(token)) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("Invalid token").build();
        }

        if (fileContent == null || fileContent.isEmpty() || fileName == null || fileName.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST).entity("File must be provided").build();
        }


        // Base64 just to encode binary data as printable text.
        byte[] file = Base64.getDecoder().decode(fileContent);
        managedExecutor.runAsync(()-> {
            try {
                goodGuysXMLImporter.importXML(file, fileName, token);
                Response.ok("XML imported successfully").build();
            } catch (Exception e) {
                Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error importing XML: " + e.getMessage()).build();
            }
        });
        return Response.accepted().entity("Import started").build();
    }
}

