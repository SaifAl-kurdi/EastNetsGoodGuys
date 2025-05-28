package dtotest;

import importXML.dtos.GoodGuyDTO;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

class GoodGuyDTOTest {

    @Test
    void testSettersAndGetters() {
        GoodGuyDTO dto = new GoodGuyDTO();
        dto.setAcceptedStringCustom("acceptedString");
        dto.setCommentsCustom("some comments");
        dto.setConditionCustom("condition");
        dto.setCreatedByCustom("tester");
        dto.setCreationDateCustom(LocalDateTime.of(2025, 1, 1, 12, 0));
        dto.setRankCustom(5);
        dto.setReportViolationsCustom(true);
        dto.setSharedCustom(true);

        assertEquals("acceptedString", dto.getAcceptedStringCustom());
        assertEquals("some comments", dto.getCommentsCustom());
        assertEquals("condition", dto.getConditionCustom());
        assertEquals("tester", dto.getCreatedByCustom());
        assertEquals(LocalDateTime.of(2025, 1, 1, 12, 0), dto.getCreationDateCustom());
        assertEquals(5, dto.getRankCustom());
        assertEquals(true, dto.getReportViolationsCustom());
        assertEquals(true, dto.getSharedCustom());


    }
}
