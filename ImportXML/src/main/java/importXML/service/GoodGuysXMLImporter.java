package importXML.service;

import importXML.dtos.MatchDTO;
import importXML.dtos.XMLWrapperDTO;
import importXML.dtos.GoodGuyDTO;
import importXML.model.GoodGuysModel;
import importXML.model.AcceptedMatchesModel;
import importXML.repositry.XMLRepositoryService;
import importXML.template.AbstractXMLImporter;
import importXML.zookeeper.ZookeeperClientService;
import lombok.Getter;
import lombok.Setter;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class GoodGuysXMLImporter extends AbstractXMLImporter {

    @Inject
    @Getter @Setter
    public XMLRepositoryService xmlRepository;

    @Inject
    ZookeeperClientService zookeeperClientService;

    @Override
    protected Object parseXML(byte[] xmlFile) throws Exception {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(xmlFile);
        JAXBContext context = JAXBContext.newInstance(XMLWrapperDTO.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        return unmarshaller.unmarshal(inputStream);
    }

    @Override
    protected void validateData(Object parsedData) throws Exception {
        if (!(parsedData instanceof XMLWrapperDTO)) {
            throw new IllegalArgumentException("Invalid XML data format.");
        }
        XMLWrapperDTO wrapperDTO = (XMLWrapperDTO) parsedData;
        if (wrapperDTO.getGoodGuysDTOOO() == null || wrapperDTO.getGoodGuysDTOOO().getGoodGuyDTOList().isEmpty()) {
            throw new IllegalArgumentException("No valid GoodGuys data found in XML.");
        }
    }

    @Override
    protected List<GoodGuysModel> mapToEntities(Object parsedData) {
        XMLWrapperDTO wrapperDTO = (XMLWrapperDTO) parsedData;
        List<GoodGuysModel> goodGuysModels = new ArrayList<>();
        for (GoodGuyDTO dto : wrapperDTO.getGoodGuysDTOOO().getGoodGuyDTOList()) {
            GoodGuysModel model = new GoodGuysModel();
            model.setAcceptedString(dto.getAcceptedStringCustom());
            model.setCondition(dto.getConditionCustom());
            model.setComments(dto.getCommentsCustom());
            model.setCreatedBy(dto.getCreatedByCustom());
            model.setCreationDate(dto.getCreationDateCustom());
            model.setRank(dto.getRankCustom());
            model.setReportViolations(dto.getReportViolationsCustom());
            model.setShared(dto.getSharedCustom());

            List<AcceptedMatchesModel> matches = new ArrayList<>();
            if (dto.getAcceptedMatchesCustom() != null && !dto.getAcceptedMatchesCustom().isEmpty()) {
                for (MatchDTO matchDTO : dto.getAcceptedMatchesCustom().get(0).getMatchesCustom()) {
                    AcceptedMatchesModel match = new AcceptedMatchesModel();
                    match.setAcceptedMatch(matchDTO.getAcceptedMatchCustom());
                    match.setCheckSum(matchDTO.getCheckSumCustom());
                    match.setEntityMainName(matchDTO.getEntityMainNameCustom());
                    match.setExternalID(matchDTO.getExternalIDCustom());
                    match.setListName(matchDTO.getListNameCustom());
                    match.setGoodGuysModel(model);
                    matches.add(match);
                }
            }
            model.setAcceptedMatchesModels(matches);
            goodGuysModels.add(model);
        }
        return goodGuysModels;
    }

    @Override
    protected void saveToDatabase(List<?> entities, String fileName, String token) {
        xmlRepository.saveAllGoodGuys((List<GoodGuysModel>) entities);
        int count = ((List<GoodGuysModel>) entities).size();
        triggerNotificationService(token, fileName, count);
    }


    private void triggerNotificationService(String authHeader, String fileName, int goodGuysCount) {
        try {
            String notificationServiceUrl = zookeeperClientService.getServiceAddress("notification-service");
            String token = authHeader.substring("Bearer ".length());

            if (notificationServiceUrl == null) {
                Response.status(500).build();
                return;
            }

            if (token == null || token.isEmpty()) {
                Response.status(401).build();
                return;
            }

            Client client = ClientBuilder.newClient();
            WebTarget target = client.target(notificationServiceUrl).path("GetNotification").queryParam("fileName", fileName).queryParam("goodGuysCount", goodGuysCount);

            Response response = target.request().header("Authorization", "Bearer " + token).get();

            if (response.getStatus() == Response.Status.OK.getStatusCode()) {
                System.out.println("Notification service triggered successfully.");
            } else {
                System.err.println("Failed to trigger notification service. Response: " + response.getStatus());
            }
        } catch (Exception e) {
            System.err.println("Error calling notification service: " + e.getMessage());
        }
    }

}