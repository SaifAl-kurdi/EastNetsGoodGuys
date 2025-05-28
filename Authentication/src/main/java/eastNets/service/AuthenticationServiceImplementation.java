package eastNets.service;

import eastNets.dtos.UserDTO;
import eastNets.generateJWT.GenerateToken;
import eastNets.repository.UserRepository;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class AuthenticationServiceImplementation implements AuthenticationService {

    @Inject
    UserRepository userRepository;

    @Inject
    GenerateToken generateToken;

    @Override
    public String authenticateUser(String username, String password) {
        try {
            UserDTO user = userRepository.findUser(username, password);
            if (user != null) {
                String token = generateToken.generateJWT();
                return token;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


}
