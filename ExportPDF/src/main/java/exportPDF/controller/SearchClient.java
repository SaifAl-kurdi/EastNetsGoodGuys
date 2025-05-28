package exportPDF.controller;

import exportPDF.dtos.PDFGoodGuysDTO;
import filter.GoodGuysFilter;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.Serializable;
import java.util.List;

@Path("/goodguys")
public interface SearchClient extends Serializable {

    @GET
    @Path("/search")
    @Produces(MediaType.APPLICATION_JSON)
    List<PDFGoodGuysDTO> getGoodGuys(
            @HeaderParam("Authorization") String token,
            @BeanParam GoodGuysFilter filter
    );
}



