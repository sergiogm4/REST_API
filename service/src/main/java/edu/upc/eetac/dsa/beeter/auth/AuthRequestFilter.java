package edu.upc.eetac.dsa.beeter.auth;

import com.fasterxml.jackson.annotation.JsonInclude;
import edu.upc.eetac.dsa.beeter.dao.AuthTokenDAOImpl;
import edu.upc.eetac.dsa.beeter.entity.Role;

import javax.annotation.Priority;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.Priorities;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.security.Principal;
import java.sql.SQLException;
import java.util.List;
@JsonInclude(JsonInclude.Include.NON_NULL)

/**
 * Created by SergioGM on 07.10.15.
 */
@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthRequestFilter implements ContainerRequestFilter {
    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        if(Authorized.getInstance().isAuthorized(requestContext))
            return;
        final boolean secure = requestContext.getUriInfo().getAbsolutePath().getScheme().equals("https");

        String token = requestContext.getHeaderString("X-Auth-Token");
        if (token == null)
            throw new WebApplicationException(Response.Status.UNAUTHORIZED);

        try {
            final UserInfo principal = (new AuthTokenDAOImpl()).getUserByAuthToken(token);
            if(principal==null)
                throw new WebApplicationException("auth token doesn't exists", Response.Status.UNAUTHORIZED);
            requestContext.setSecurityContext(new SecurityContext() {
                @Override
                public Principal getUserPrincipal() {
                    return principal;
                }

                @Override
                public boolean isUserInRole(String s) {
                    List<Role> roles = null;
                    if (principal != null) roles = principal.getRoles();
                    return (roles.size() > 0 && roles.contains(Role.valueOf(s)));
                }

                @Override
                public boolean isSecure() {
                    return secure;
                }

                @Override
                public String getAuthenticationScheme() {
                    return "X-Auth-Token";
                }
            });
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }

    }
}
