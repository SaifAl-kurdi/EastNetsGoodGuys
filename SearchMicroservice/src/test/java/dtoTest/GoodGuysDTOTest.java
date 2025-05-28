package dtoTest;

import org.junit.jupiter.api.Test;
import search.dtos.GoodGuysDTO;
import search.model.AcceptedMatches;
import search.model.GoodGuys;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class GoodGuysDTOTest {

    @Test
    void testGoodGuysDTOMapping() {
        GoodGuys goodGuy = new GoodGuys();
        goodGuy.setId(1);
        goodGuy.setAcceptedString("acceptedString");
        goodGuy.setCondition("condition");
        goodGuy.setComments("comments");
        goodGuy.setCreationDate("2025-01-01");
        goodGuy.setCreatedBy("tester");
        goodGuy.setReportViolations("none");
        goodGuy.setShared("yes");
        goodGuy.setRank(5);

        AcceptedMatches match1 = new AcceptedMatches();
        match1.setId(101);
        match1.setCheckSum("abc123");
        match1.setExternalID("ext101");
        match1.setListName("listName1");
        match1.setAcceptedMatch("acceptedMatch1");
        match1.setEntityMainName("entityMain1");
        match1.setGoodGuy(goodGuy);

        AcceptedMatches match2 = new AcceptedMatches();
        match2.setId(102);
        match2.setCheckSum("xyz789");
        match2.setExternalID("ext102");
        match2.setListName("listName2");
        match2.setAcceptedMatch("acceptedMatch2");
        match2.setEntityMainName("entityMain2");
        match2.setGoodGuy(goodGuy);

        List<AcceptedMatches> acceptedMatches = new ArrayList<>();
        acceptedMatches.add(match1);
        acceptedMatches.add(match2);

        goodGuy.setAcceptedMatches(acceptedMatches);


        GoodGuysDTO dto = new GoodGuysDTO(goodGuy);
        assertEquals(1, dto.getId());
        assertEquals("acceptedString", dto.getAcceptedString());
        assertEquals("condition", dto.getCondition());
        assertEquals("comments", dto.getComments());
        assertEquals("2025-01-01", dto.getCreationDate());
        assertEquals("tester", dto.getCreatedBy());
        assertEquals("none", dto.getReportViolations());
        assertEquals("yes", dto.getShared());
        assertEquals(5, dto.getRank());
        assertNotNull(dto.getAcceptedMatches());
        assertEquals(2, dto.getAcceptedMatches().size());
        assertEquals("abc123", dto.getAcceptedMatches().get(0).getCheckSum());
        assertEquals("ext101", dto.getAcceptedMatches().get(0).getExternalID());
    }
}

