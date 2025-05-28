package dtoTest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import search.dtos.AcceptedMatchDTO;
import search.model.AcceptedMatches;

class AcceptedMatchDTOTest {

    @Test
    void testAcceptedMatchDTOMapping() {
        AcceptedMatches match = new AcceptedMatches();
        match.setId(101);
        match.setCheckSum("checksumValue");
        match.setExternalID("externalIDValue");
        match.setListName("listNameValue");
        match.setAcceptedMatch("acceptedMatchValue");
        match.setEntityMainName("entityMainValue");

        AcceptedMatchDTO dto = new AcceptedMatchDTO(match);

        Assertions.assertEquals("checksumValue", dto.getCheckSum());
        Assertions.assertEquals("externalIDValue", dto.getExternalID());
        Assertions.assertEquals("listNameValue", dto.getListName());
        Assertions.assertEquals("acceptedMatchValue", dto.getAcceptedMatch());
        Assertions.assertEquals("entityMainValue", dto.getEntityMainName());
    }
}
