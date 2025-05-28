package eastnets.validate.token;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

@Provider
@Priority(Priorities.AUTHENTICATION)
public class TokenFilter implements ContainerRequestFilter {

    @Inject
    TokenValidator tokenValidator;

    public void filter(ContainerRequestContext requestContext) {
        String authorizationHeader = requestContext.getHeaderString("Authorization");
        if (authorizationHeader == null) {
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).entity("Missing Authorization header").build());
            return;
        }

        if (!tokenValidator.isValidToken(authorizationHeader)) {
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).entity("Invalid token").build());
        }
    }

}
