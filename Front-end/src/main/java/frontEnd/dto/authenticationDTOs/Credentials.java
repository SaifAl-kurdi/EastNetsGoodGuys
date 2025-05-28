package frontEnd.dto.authenticationDTOs;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class Credentials {
    private String username;
    private String password;

    public Credentials(User user) {
        this.username = user.getUsername();
        this.password = user.getPassword();
    }
}
