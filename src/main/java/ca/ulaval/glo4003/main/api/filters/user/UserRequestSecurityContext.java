package ca.ulaval.glo4003.main.api.filters.user;

import jakarta.ws.rs.core.SecurityContext;

import java.security.Principal;

public class UserRequestSecurityContext implements SecurityContext {

    private final Principal userPrincipal;

    public UserRequestSecurityContext(Principal userPrincipal) {
        this.userPrincipal = userPrincipal;
    }

    @Override
    public Principal getUserPrincipal() {
        return userPrincipal;
    }

    @Override
    public boolean isUserInRole(String role) {
        return true;
    }

    @Override
    public boolean isSecure() {
        return true;
    }

    @Override
    public String getAuthenticationScheme() {
        return null;
    }
}
