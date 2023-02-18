package ca.ulaval.glo4003.subscription.api;

import ca.ulaval.glo4003.main.api.filters.annotations.RequirePermissions;
import ca.ulaval.glo4003.main.application.message.ErrorMessages;
import ca.ulaval.glo4003.subscription.application.SubscriptionService;
import ca.ulaval.glo4003.subscription.application.dtos.SubscriptionCreationDto;
import ca.ulaval.glo4003.subscription.application.dtos.SubscriptionDto;
import ca.ulaval.glo4003.user.domain.UserRole;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;

import java.util.List;

@Path("/subscriptions")
public class SubscriptionResource {
    private final SubscriptionService subscriptionService;

    public SubscriptionResource(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    @POST
    @RequirePermissions(roles = {UserRole.DEFAULT})
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createSubscription(
            @Valid @NotNull(message = ErrorMessages.MISSING_REQUEST) SubscriptionCreationDto subscriptionCreationDto,
            @Context SecurityContext securityContext) {
        String userId = securityContext.getUserPrincipal().getName();

        subscriptionService.createSemesterSubscription(userId, subscriptionCreationDto);

        return Response.noContent().build();
    }

    @GET
    @RequirePermissions(roles = {UserRole.DEFAULT, UserRole.TECHNICIAN})
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSubscriptions(@Context SecurityContext securityContext) {
        String userId = securityContext.getUserPrincipal().getName();

        List<SubscriptionDto> subscriptionDtos = subscriptionService.getSubscriptions(userId);

        return Response.ok(subscriptionDtos).build();
    }

}
