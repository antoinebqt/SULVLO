package ca.ulaval.glo4003.code.api;

import ca.ulaval.glo4003.code.application.UnlockCodeService;
import ca.ulaval.glo4003.main.api.filters.annotations.RequireAuthentication;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;


@Path("/codes")
public class UnlockCodeResource {
    private final UnlockCodeService unlockCodeService;

    public UnlockCodeResource(UnlockCodeService unlockCodeService) {
        this.unlockCodeService = unlockCodeService;
    }

    @Produces(MediaType.APPLICATION_JSON)
    @RequireAuthentication
    @POST
    public Response createCode(@Context SecurityContext securityContext) {
        String userId = securityContext.getUserPrincipal().getName();
        unlockCodeService.createCode(userId);
        return Response.noContent().build();
    }
}
