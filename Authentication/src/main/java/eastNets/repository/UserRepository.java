package eastNets.repository;

import eastNets.dtos.UserDTO;

public interface UserRepository {
    UserDTO findUser(String username, String password);
}
