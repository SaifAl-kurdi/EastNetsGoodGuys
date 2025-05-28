/*
package servicetest;

import importXML.model.GoodGuysModel;
import importXML.repositry.XMLRepositoryService;
import importXML.service.GoodGuysXMLImporter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GoodGuysXMLImporterTest {

    private GoodGuysXMLImporter importer;
    private XMLRepositoryService repositoryMock;

    @BeforeEach
    void setUp() {
        repositoryMock = Mockito.mock(XMLRepositoryService.class);
        importer = new GoodGuysXMLImporter();
        importer.xmlRepository = repositoryMock;
    }

    @Test
    void testImportXML_validData() throws Exception {
        String xml = "<XML>" +
                  "<GOODGUYS>" +
                    "<GOODGUY>" +
                      "<ID>123</ID>" +
                      "<ACCEPTED_STRING>test</ACCEPTED_STRING>" +
                      "<COMMENTS>some comment</COMMENTS>" +
                      "<CONDITION>some condition</CONDITION>" +
                      "<CREATED_BY>tester</CREATED_BY>" +
                      "<CREATION_DATE>2025/01/01 12:00:00</CREATION_DATE>" +
                      "<RANK>10</RANK>" +
                      "<REPORT_VIOLATIONS>YES</REPORT_VIOLATIONS>" +
                      "<SHARED>NO</SHARED>" +
                      "<ACCEPTED_MATCHES>" +
                        "<MATCH>" +
                          "<ACCEPTED_MATCH>matchValue</ACCEPTED_MATCH>" +
                          "<CHECKSUM>checksumVal</CHECKSUM>" +
                          "<ENTITY_MAIN_NAME>entityName</ENTITY_MAIN_NAME>" +
                          "<EXTERNAL_ID>extId</EXTERNAL_ID>" +
                          "<LIST_NAME>listName</LIST_NAME>" +
                        "</MATCH>" +
                      "</ACCEPTED_MATCHES>" +
                    "</GOODGUY>" +
                  "</GOODGUYS>" +
                "</XML>";

        byte[] fileData = xml.getBytes();
        importer.importXML(fileData);
        ArgumentCaptor<List<GoodGuysModel>> captor = ArgumentCaptor.forClass(List.class);
        verify(repositoryMock, times(1)).saveAllGoodGuys(captor.capture());

        List<GoodGuysModel> savedList = captor.getValue();
        assertEquals(1, savedList.size());

        GoodGuysModel model = savedList.get(0);
        assertEquals("test", model.getAcceptedString());
        assertEquals("some comment", model.getComments());
        assertTrue(model.isReportViolations());
        assertFalse(model.isShared()); // Because it was "NO"
        assertEquals(10, model.getRank());
        assertNotNull(model.getAcceptedMatchesModels());
        assertEquals(1, model.getAcceptedMatchesModels().size());
        assertEquals("checksumVal", model.getAcceptedMatchesModels().get(0).getCheckSum());
    }

    @Test
    void testImportXML_invalidData() {
        String xml = "<XML><GOODGUYS><BAD_NODE></BAD_NODE></GOODGUYS></XML>";
        byte[] fileData = xml.getBytes();
        assertDoesNotThrow(() -> importer.importXML(fileData));
        verify(repositoryMock, never()).saveAllGoodGuys(any());
    }
}

*/
