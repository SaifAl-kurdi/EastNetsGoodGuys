package frontEnd.beans;

import frontEnd.dto.GoodGuysDTO;
import frontEnd.restClient.ExportPDFClient;
import frontEnd.zookeeper.ZooKeeperService;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.core.Response;
import lombok.Getter;
import lombok.Setter;
import org.eclipse.microprofile.rest.client.RestClientBuilder;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.URI;
import java.util.logging.Logger;

@Named
@SessionScoped
@Getter @Setter
public class ExportPDFBean implements Serializable {

    @Inject
    ZooKeeperService zooKeeperService;

    @Inject
    SearchBean searchBean;

    @Inject
    AuthenticationBean authenticationBean;

    transient Logger logger;

    String errorMessage = "Error";

    public void generatePDF() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        String exportServiceUrl = zooKeeperService.getServiceAddress("export-service");
        if (exportServiceUrl == null) {
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Export PDF service unavailable"));
            return;
        }

        if (searchBean.getGoodGuysList() == null || searchBean.getGoodGuysList().isEmpty()) {
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Warn", "Please perform a search before exporting PDF."));
            return;
        }

        try {
            String token = authenticationBean.getToken();
            if (token == null || token.trim().isEmpty()) {
                facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "User is not authenticated."));
                return;
            }
            token = "Bearer " + token;
            GoodGuysDTO goodGuysDTO = searchBean.getGoodGuysDTO();

            URI baseUri = new URI(exportServiceUrl);
            ExportPDFClient exportPDFClient = RestClientBuilder.newBuilder().baseUri(baseUri).build(ExportPDFClient.class);

            Response response = exportPDFClient.getPDFReport(
                    token,
                    goodGuysDTO.getId(),
                    emptyToNull(goodGuysDTO.getAcceptedString()),
                    emptyToNull(goodGuysDTO.getCondition()),
                    emptyToNull(goodGuysDTO.getComments()),
                    emptyToNull(goodGuysDTO.getCreationDate()),
                    emptyToNull(goodGuysDTO.getCreatedBy()),
                    emptyToNull(goodGuysDTO.getReportViolations()),
                    emptyToNull(goodGuysDTO.getShared()),
                    goodGuysDTO.getRank(),
                    emptyToNull(goodGuysDTO.getExternalID()),
                    emptyToNull(goodGuysDTO.getAcceptedMatch()),
                    emptyToNull(goodGuysDTO.getEntityMainName())
            );

            if (response.getStatus() == Response.Status.OK.getStatusCode()) {
                InputStream pdfStream = response.readEntity(InputStream.class);
                HttpServletResponse httpServletResponse = (HttpServletResponse)
                        facesContext.getExternalContext().getResponse();

                httpServletResponse.reset();
                httpServletResponse.setContentType("application/pdf");
                httpServletResponse.setHeader("Content-Disposition", "attachment; filename=\"report.pdf\"");

                byte[] buffer = new byte[1024];
                int bytesRead;

                while ((bytesRead = pdfStream.read(buffer)) != -1) {
                    httpServletResponse.getOutputStream().write(buffer, 0, bytesRead);
                }

                httpServletResponse.getOutputStream().flush();
                facesContext.responseComplete();
            } else {
                facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error","PDF export failed with status " + response.getStatus()));
            }
        } catch (IOException e) {
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "PDF export failed (I/O Error)."));
            if (logger != null) {
                logger.info(e.getMessage());
            }
        } catch (Exception e) {
            facesContext.addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_ERROR, "Error", e.getMessage()));
            e.printStackTrace();
        }
    }

    private String emptyToNull(String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }
        return value;
    }
}
