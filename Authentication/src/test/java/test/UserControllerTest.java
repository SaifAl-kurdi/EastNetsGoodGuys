package test;

import eastNets.controller.UserController;
import eastNets.dtos.UserDTO;
import eastNets.service.AuthenticationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import javax.ws.rs.core.Response;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserControllerTest {

    @Mock
    private AuthenticationService authenticationService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testLoginWithValidCredentials() {
        // Arrange
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("validUser");
        userDTO.setPassword("validPassword");

        when(authenticationService.authenticateUser("validUser", "validPassword")).thenReturn("validToken");

        // Act
        Response response = userController.login(userDTO);

        // Assert
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertNotNull(response.getCookies().get("authToken"));
        assertEquals("validToken", response.getEntity());
    }

    @Test
    public void testLoginWithInvalidCredentials() {
        // Arrange
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("invalidUser");
        userDTO.setPassword("invalidPassword");

        when(authenticationService.authenticateUser("invalidUser", "invalidPassword")).thenReturn(null);

        // Act
        Response response = userController.login(userDTO);

        // Assert
        assertEquals(Response.Status.UNAUTHORIZED.getStatusCode(), response.getStatus());
        assertEquals("Invalid", response.getEntity());
    }
}
