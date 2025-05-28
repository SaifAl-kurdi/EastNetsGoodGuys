package frontEnd.beans;

import frontEnd.restClient.ImportXMLClient;
import frontEnd.zookeeper.ZooKeeperService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.ws.rs.core.Response;
import lombok.Getter;
import lombok.Setter;
import org.eclipse.microprofile.rest.client.RestClientBuilder;
import org.primefaces.model.file.UploadedFile;
import java.io.Serializable;
import java.net.URI;
import java.util.Base64;

@Named("importXMLBean")
@RequestScoped
@Getter
@Setter
public class ImportXMLBean implements Serializable {

    @Inject
    private ZooKeeperService zooKeeperService;

    @Inject
    private AuthenticationBean authenticationBean;

    private UploadedFile file;

    public void importXML() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        try {
            String importServiceUrl = zooKeeperService.getServiceAddress("import-service");
            if (importServiceUrl == null) {
                facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Import XML service unavailable"));
                return;
            }

            if (file == null || file.getFileName() == null || file.getSize() <= 0) {
                facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No file selected for upload."));
                return;
            }

            String token = authenticationBean.getToken();
            if (token == null || token.trim().isEmpty()) {
                facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "User is not authenticated."));
                return;
            }

            token = "Bearer " + token;
            String encodedFile = Base64.getEncoder().encodeToString(file.getContent());
            String fileName = file.getFileName();

            URI baseUri = new URI(importServiceUrl);
            ImportXMLClient importXMLClient = RestClientBuilder.newBuilder().baseUri(baseUri).build(ImportXMLClient.class);

            Response response = importXMLClient.importXML(token, fileName, encodedFile);
            int status = response.getStatus();
            if (status == Response.Status.OK.getStatusCode() || status == Response.Status.ACCEPTED.getStatusCode()) {
                facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "XML import was successful (or started)."));
            } else {
                facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "XML import failed with status: " + status));
            }
        } catch (Exception e) {
            e.printStackTrace();
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "XML import failed: " + e.getMessage()));
        }
    }
}
