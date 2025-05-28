package eastNets.repository;

import eastNets.dtos.UserDTO;
import eastNets.model.User;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UserRepositoryImplementation implements PanacheRepository<User>, UserRepository {

    @Override
    public UserDTO findUser(String username, String password) {
        try {
            return new UserDTO(find("username = ?1 and password = ?2", username, password).singleResult());
        } catch (Exception e) {
            return null;
        }
    }
}
