package ca.ulaval.glo4003.trip.api;

import ca.ulaval.glo4003.main.api.filters.annotations.RequireAuthentication;
import ca.ulaval.glo4003.main.application.message.ErrorMessages;
import ca.ulaval.glo4003.trip.api.assemblers.TripDtoAssembler;
import ca.ulaval.glo4003.trip.api.dtos.HistorySummaryResponse;
import ca.ulaval.glo4003.trip.application.TripService;
import ca.ulaval.glo4003.trip.application.dtos.SummaryDto;
import ca.ulaval.glo4003.trip.application.dtos.TripCreationDto;
import ca.ulaval.glo4003.trip.application.dtos.TripDto;
import ca.ulaval.glo4003.trip.application.dtos.TripEndDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;

import java.util.List;

@Path("/")
public class TripResource {
    private final TripService tripService;
    private final TripDtoAssembler tripDtoAssembler;

    public TripResource(TripService tripService, TripDtoAssembler tripDtoAssembler) {
        this.tripService = tripService;
        this.tripDtoAssembler = tripDtoAssembler;
    }

    @POST
    @Path("trips")
    @Produces(MediaType.APPLICATION_JSON)
    @RequireAuthentication
    public Response createTrip(
            @Valid @NotNull(message = ErrorMessages.MISSING_REQUEST) TripCreationDto tripCreationDto,
            @Context SecurityContext securityContext) {
        String userId = securityContext.getUserPrincipal().getName();

        tripService.createTrip(userId, tripCreationDto);

        return Response.status(Response.Status.CREATED).build();
    }

    @POST
    @Path("endTrip")
    @Consumes(MediaType.APPLICATION_JSON)
    @RequireAuthentication
    public Response endTrip(@Valid @NotNull(message = ErrorMessages.MISSING_REQUEST) TripEndDto tripEndDto,
                            @Context SecurityContext securityContext) {
        String userId = securityContext.getUserPrincipal().getName();

        tripService.endTrip(userId, tripEndDto);

        return Response.status(Response.Status.NO_CONTENT).build();
    }

    @GET
    @Path("trips")
    @Produces(MediaType.APPLICATION_JSON)
    @RequireAuthentication
    public Response getHistory(@Context SecurityContext securityContext) {
        String userId = securityContext.getUserPrincipal().getName();
        SummaryDto summaryDto = tripService.getSummary(userId);
        List<TripDto> tripsDto = tripService.getHistory(userId);
        HistorySummaryResponse historySummaryResponse = tripDtoAssembler.toResponse(summaryDto, tripsDto);

        return Response.ok().entity(historySummaryResponse).build();
    }
}
