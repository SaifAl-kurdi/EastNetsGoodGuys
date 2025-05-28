package eastNets.generateJWT;

import io.smallrye.jwt.build.Jwt;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class GenerateTokenImplementation implements GenerateToken {

    @Override
    public String generateJWT() {
        return Jwt.issuer("Authorization").subject("Microservices").expiresAt(System.currentTimeMillis() / 1000 + 1800).sign();
    }
}
