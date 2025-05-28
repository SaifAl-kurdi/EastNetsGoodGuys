package frontEnd.beans;

import frontEnd.dto.authenticationDTOs.Credentials;
import frontEnd.dto.authenticationDTOs.User;
import frontEnd.restClient.AuthenticationClient;
import frontEnd.zookeeper.ZooKeeperService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.ws.rs.core.Response;
import lombok.Getter;
import lombok.Setter;
import org.eclipse.microprofile.rest.client.RestClientBuilder;
import java.io.Serializable;
import java.net.URI;

@ApplicationScoped
@Named
@Getter @Setter
public class AuthenticationBean implements Serializable {

    @Inject
    private ZooKeeperService zooKeeperService;

    private String token;
    private User user = new User();
    private String[] errorMessage = {"Error", "Invalid username or password. Please try again"};

    public Response login() {
        try {
            Credentials credentials = new Credentials(user);

            String authServiceUrl = zooKeeperService.getServiceAddress("auth-service");
            if (authServiceUrl == null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Service Not Found","Authentication service is not registered in ZooKeeper"));
                return null;
            }

            URI baseUri = new URI(authServiceUrl);
            AuthenticationClient authenticationClient = RestClientBuilder.newBuilder().baseUri(baseUri).build(AuthenticationClient.class);

            Response response = authenticationClient.login(credentials);
            if (response.getStatus() == Response.Status.OK.getStatusCode()) {
                String responseBody = response.readEntity(String.class);
                if ("Invalid".equals(responseBody)) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage[0], errorMessage[1]));
                } else {
                    token = responseBody;
                    FacesContext facesContext = FacesContext.getCurrentInstance();
                    String contextPath = facesContext.getExternalContext().getRequestContextPath();
                    facesContext.getExternalContext().redirect(contextPath + "/templates/index.xhtml");
                }
                return response;
            } else {
                FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage[0], errorMessage[1]));
                return response;
            }
        } catch (Exception e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage[0], errorMessage[1]));
            return null;
        }
    }

}
