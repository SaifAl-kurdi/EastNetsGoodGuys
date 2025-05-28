//package controllertest;
//
//import eastnets.validate.token.TokenValidator;
//import importXML.controller.XMLImportController;
//import importXML.service.GoodGuysXMLImporter;
//import org.eclipse.microprofile.context.ManagedExecutor;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//import javax.ws.rs.core.Response;
//import java.util.Base64;
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//public class XMLImportControllerTest {
//    private XMLImportController controller;
//    private GoodGuysXMLImporter importerMock;
//    private TokenValidator tokenValidatorMock;
//    private ManagedExecutor executorMock;
//
//    @BeforeEach
//    void setUp() {
//        controller = new XMLImportController();
//        importerMock = Mockito.mock(GoodGuysXMLImporter.class);
//        tokenValidatorMock = Mockito.mock(TokenValidator.class);
//        executorMock = Mockito.mock(ManagedExecutor.class);
//        controller.goodGuysXMLImporter = importerMock;
//        controller.tokenValidator = tokenValidatorMock;
//        controller.managedExecutor = executorMock;
//    }
//
//    @Test
//    void testImportXML_invalidToken() {
//        when(tokenValidatorMock.isValidToken("bad-token")).thenReturn(false);
//        Response response = controller.importXML("bad-token", "someBase64Content");
//        assertEquals(Response.Status.UNAUTHORIZED.getStatusCode(), response.getStatus());
//        verify(importerMock, never()).importXML(any());
//    }
//
//    @Test
//    void testImportXML_nullOrEmptyContent() {
//        when(tokenValidatorMock.isValidToken("good-token")).thenReturn(true);
//        Response response1 = controller.importXML("good-token", null);
//        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response1.getStatus());
//        Response response2 = controller.importXML("good-token", "");
//        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response2.getStatus());
//        verify(importerMock, never()).importXML(any());
//    }
//
//    @Test
//    void testImportXML_valid() {
//        when(tokenValidatorMock.isValidToken("good-token")).thenReturn(true);
//        String encoded = Base64.getEncoder().encodeToString("<XML>some content</XML>".getBytes());
//        doAnswer(invocation -> {
//            Runnable runnable = invocation.getArgument(0);
//            runnable.run();
//            return null;
//        }).when(executorMock).runAsync(any(Runnable.class));
//
//        Response response = controller.importXML("good-token", encoded);
//        assertEquals(Response.Status.ACCEPTED.getStatusCode(), response.getStatus());
//        verify(importerMock, times(1)).importXML(any());
//    }
//}
