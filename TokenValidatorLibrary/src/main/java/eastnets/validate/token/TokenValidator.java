package eastnets.validate.token;

import io.smallrye.jwt.auth.principal.JWTParser;
import org.eclipse.microprofile.jwt.JsonWebToken;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class TokenValidator {

    @Inject
    JWTParser jwtParser;

    public boolean isValidToken(String authHeader) {
        try {
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return false;
            }

            String token = authHeader.substring("Bearer ".length());
            JsonWebToken parsedToken = jwtParser.parse(token);

            if (!"Authorization".equals(parsedToken.getIssuer())) {
                return false;
            }

            long expirationTime = parsedToken.getExpirationTime();
            long currentTime = System.currentTimeMillis() / 1000;
            return (expirationTime > currentTime);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}