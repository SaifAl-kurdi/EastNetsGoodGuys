package test;

import eastNets.dtos.UserDTO;
import eastNets.generateJWT.GenerateToken;
import eastNets.repository.UserRepository;
import eastNets.service.AuthenticationServiceImplementation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AuthenticationServiceImplementationTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private GenerateToken generateToken;

    @InjectMocks
    private AuthenticationServiceImplementation authenticationService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAuthenticateUserWithValidCredentials() {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("validUser");
        userDTO.setPassword("validPassword");

        when(userRepository.findUser("validUser", "validPassword")).thenReturn(userDTO);
        when(generateToken.generateJWT()).thenReturn("validToken");

        String token = authenticationService.authenticateUser("validUser", "validPassword");

        assertNotNull(token);
        assertEquals("validToken", token);
    }

    @Test
    public void testAuthenticateUserWithInvalidCredentials() {
        when(userRepository.findUser("invalidUser", "invalidPassword")).thenReturn(null);

        String token = authenticationService.authenticateUser("invalidUser", "invalidPassword");

        assertNull(token);
    }
}

