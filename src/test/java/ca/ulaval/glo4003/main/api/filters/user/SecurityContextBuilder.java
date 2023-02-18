package ca.ulaval.glo4003.main.api.filters.user;

import jakarta.ws.rs.core.SecurityContext;

public class SecurityContextBuilder {

    private String userId;

    public SecurityContextBuilder() {
        this.userId = "bob";
    }

    public SecurityContextBuilder withUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public SecurityContext build() {
        return new UserRequestSecurityContext(new UserPrincipal(userId));
    }
}
