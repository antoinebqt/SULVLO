package ca.ulaval.glo4003.main.api.filters;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.core.MultivaluedHashMap;
import org.mockito.Mockito;

public class ContainerRequestContextBuilder {

    private String authorizationHeader;
    private String role;

    public ContainerRequestContextBuilder() {
        this.authorizationHeader = "Bearer: abc";
        this.role = "DEFAULT";
    }

    public ContainerRequestContextBuilder withAuthorizationHeader(String authorizationHeader) {
        this.authorizationHeader = authorizationHeader;
        return this;
    }

    public ContainerRequestContextBuilder withToken(String token) {
        this.authorizationHeader = "Bearer:" + token;
        return this;
    }

    public ContainerRequestContextBuilder withRole(String role) {
        this.role = role;
        return this;
    }

    public ContainerRequestContext build() {
        MultivaluedHashMap<String, String> headers = new MultivaluedHashMap<>();
        headers.add("Authorization", authorizationHeader);

        ContainerRequestContext context = Mockito.mock(ContainerRequestContext.class);

        Mockito.when(context.getHeaders()).thenReturn(headers);
        Mockito.when(context.getProperty("role")).thenReturn(role);

        return context;
    }
}
