package frontEnd.beans;

import frontEnd.dto.GoodGuysDTO;
import frontEnd.restClient.SearchClient;
import frontEnd.zookeeper.ZooKeeperService;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.Setter;
import org.eclipse.microprofile.rest.client.RestClientBuilder;
import java.io.Serializable;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Named
@SessionScoped
@Getter
@Setter
public class SearchBean implements Serializable {

    @Inject
    private transient ZooKeeperService zooKeeperService;

    @Inject
    private AuthenticationBean authenticationBean;

    private transient GoodGuysDTO goodGuysDTO = new GoodGuysDTO();

    private List<Integer> rankOptions = IntStream.rangeClosed(50, 100).boxed().collect(Collectors.toList());

    private String errorMessage = "Error";
    private List<GoodGuysDTO> goodGuysList = new ArrayList<>();
    private GoodGuysDTO selectedGoodGuy;

    public void getGoodGuys() {
        if (isAllCriteriaEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN, "Please fill at least one search field.", ""));
            return;
        }
        try {
            String token = "Bearer " + authenticationBean.getToken();

            String searchServiceUrl = zooKeeperService.getServiceAddress("search-service");
            if (searchServiceUrl == null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, "Search service unavailable"));
                return;
            }

            URI baseUri = new URI(searchServiceUrl);
            SearchClient searchClient = RestClientBuilder.newBuilder().baseUri(baseUri).build(SearchClient.class);

            goodGuysList = searchClient.getGoodGuys(
                    token,
                    goodGuysDTO.getId(),
                    isNullOrEmpty(goodGuysDTO.getAcceptedString()) ? null : goodGuysDTO.getAcceptedString(),
                    isNullOrEmpty(goodGuysDTO.getCondition()) ? null : goodGuysDTO.getCondition(),
                    isNullOrEmpty(goodGuysDTO.getComments()) ? null : goodGuysDTO.getComments(),
                    isNullOrEmpty(goodGuysDTO.getCreationDate()) ? null : goodGuysDTO.getCreationDate(),
                    isNullOrEmpty(goodGuysDTO.getCreatedBy()) ? null : goodGuysDTO.getCreatedBy(),
                    isNullOrEmpty(goodGuysDTO.getReportViolations()) ? null : goodGuysDTO.getReportViolations(),
                    isNullOrEmpty(goodGuysDTO.getShared()) ? null : goodGuysDTO.getShared(),
                    goodGuysDTO.getRank(),
                    isNullOrEmpty(goodGuysDTO.getExternalID()) ? null : goodGuysDTO.getExternalID(),
                    isNullOrEmpty(goodGuysDTO.getAcceptedMatch()) ? null : goodGuysDTO.getAcceptedMatch(),
                    isNullOrEmpty(goodGuysDTO.getEntityMainName()) ? null : goodGuysDTO.getEntityMainName()
            );

            if (goodGuysList == null || goodGuysList.isEmpty()) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_WARN, "No Results", "No records found for the given criteria."));
            } else {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "The results are displayed in the data table."));
            }
        } catch (Exception e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, "There was an error processing your search."));
        }
    }

    private boolean isNullOrEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }

    private boolean isAllCriteriaEmpty() {
        return  goodGuysDTO.getId() == null &&
                isNullOrEmpty(goodGuysDTO.getAcceptedString()) &&
                isNullOrEmpty(goodGuysDTO.getCondition()) &&
                isNullOrEmpty(goodGuysDTO.getComments()) &&
                isNullOrEmpty(goodGuysDTO.getCreationDate()) &&
                isNullOrEmpty(goodGuysDTO.getCreatedBy()) &&
                isNullOrEmpty(goodGuysDTO.getReportViolations()) &&
                isNullOrEmpty(goodGuysDTO.getShared()) &&
                goodGuysDTO.getRank() == null &&
                isNullOrEmpty(goodGuysDTO.getExternalID()) &&
                isNullOrEmpty(goodGuysDTO.getAcceptedMatch()) &&
                isNullOrEmpty(goodGuysDTO.getEntityMainName());
    }
}
