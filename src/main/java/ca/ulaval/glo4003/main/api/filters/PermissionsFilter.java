package ca.ulaval.glo4003.main.api.filters;

import ca.ulaval.glo4003.main.api.filters.annotations.RequirePermissions;
import ca.ulaval.glo4003.main.application.PermissionsService;
import ca.ulaval.glo4003.main.application.exceptions.PermissionsException;
import ca.ulaval.glo4003.user.domain.UserRole;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.container.ResourceInfo;
import jakarta.ws.rs.core.Context;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RequirePermissions
public class PermissionsFilter implements ContainerRequestFilter {

    private final PermissionsService permissionsService;
    private final AuthenticationFilter authenticationFilter;
    @Context
    ResourceInfo resourceInfo;

    public PermissionsFilter(AuthenticationFilter authenticationFilter,
                             PermissionsService permissionsService) {
        this.authenticationFilter = authenticationFilter;
        this.permissionsService = permissionsService;
    }

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        this.authenticationFilter.filter(requestContext);

        try {
            String userRole = requestContext.getProperty("role").toString();
            this.permissionsService.validatePermissions(getAcceptedUserRoles(), userRole);
        } catch (Exception e) {
            throw new PermissionsException();
        }
    }

    private List<UserRole> getAcceptedUserRoles() {
        try {
            RequirePermissions permissions = resourceInfo.getResourceMethod()
                    .getAnnotation(RequirePermissions.class);
            return List.of(permissions.roles());
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
}
