package exportPDF.controller;

import exportPDF.dtos.PDFGoodGuysDTO;
import exportPDF.service.JasperReportGeneratorService;
import exportPDF.singleton.PDFTemplateManagerServiceImpl;
import eastnets.validate.token.TokenValidator;
import exportPDF.zookeeper.ZookeeperClientService;
import filter.GoodGuysFilter;
import net.sf.jasperreports.engine.JasperReport;
import org.eclipse.microprofile.rest.client.RestClientBuilder;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URI;
import java.util.*;

@ApplicationScoped
@Path("pdf")
public class PDFController {

    @Inject
    JasperReportGeneratorService JasperReportGeneratorService;

    @Inject
    TokenValidator tokenValidator;

    @Inject
    ZookeeperClientService zookeeperClientService;

    @GET
    @Produces("application/pdf")
    @Path("generate")
    public Response test(@HeaderParam("Authorization") String token,
                         @BeanParam GoodGuysFilter filter) {
        String searchUrl = zookeeperClientService.getServiceAddress("search-service");
        if (searchUrl == null) {
            return Response.status(Response.Status.BAD_GATEWAY).entity("search-service not found in ZooKeeper").build();
        }

        SearchClient dynamicSearchClient = RestClientBuilder.newBuilder().baseUri(URI.create(searchUrl)).build(SearchClient.class);

        List<PDFGoodGuysDTO> goodGuysDTOS = dynamicSearchClient.getGoodGuys(token, filter);

        if (!tokenValidator.isValidToken(token)) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("Invalid token").build();
        }

        try {
            String uuidToken = UUID.randomUUID().toString();
            String fileName = "report" + uuidToken + ".pdf";
            String outputFileName = "C:/Users/SAmmar/Downloads/" + fileName;

            String mainReportPath = "C:/Users/SAmmar/Desktop/Microservices/ExportPDF/src/main/resources/jasperReport/JasperPDF.jrxml";
            String subReportPath  = "C:/Users/SAmmar/Desktop/Microservices/ExportPDF/src/main/resources/jasperReport/acceptedMatchesSubreport.jrxml";

            JasperReport mainReport = PDFTemplateManagerServiceImpl.getInstance().getTemplate(mainReportPath);
            JasperReport subReport  = PDFTemplateManagerServiceImpl.getInstance().getTemplate(subReportPath);

            Map<String, Object> parameters = new HashMap<>();
            parameters.put("SUBREPORT", subReport);
            parameters.put("REPORT_DIR", "C:/Users/SAmmar/Desktop/Microservices/ExportPDF/src/main/resources/jasperReport/");

            JasperReportGeneratorService.generatePdfReport(mainReport, outputFileName, parameters, goodGuysDTOS);
            File pdfFile = new File(outputFileName);
            if (!pdfFile.exists()) {
                throw new FileNotFoundException("PDF file was not generated successfully");
            }
            InputStream fileStream = new FileInputStream(pdfFile);
            return Response.ok(fileStream, MediaType.APPLICATION_OCTET_STREAM).header("Content-Disposition", "attachment; filename=\"" + fileName + "\"").build();

        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error generating PDF: " + e.getMessage()).build();
        }
    }
}


