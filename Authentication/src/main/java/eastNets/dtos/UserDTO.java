package eastNets.dtos;

import eastNets.model.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class UserDTO {
    private String username;
    private String password;

    public UserDTO(User user) {
        this.username = user.getUsername();
        this.password = user.getPassword();
    }
}
