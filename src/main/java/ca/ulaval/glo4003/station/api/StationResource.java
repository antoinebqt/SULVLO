package ca.ulaval.glo4003.station.api;

import ca.ulaval.glo4003.main.api.filters.annotations.RequireAuthentication;
import ca.ulaval.glo4003.main.api.filters.annotations.RequirePermissions;
import ca.ulaval.glo4003.main.application.message.ErrorMessages;
import ca.ulaval.glo4003.station.application.StationService;
import ca.ulaval.glo4003.station.application.dtos.BicycleTransferDto;
import ca.ulaval.glo4003.station.application.dtos.StationDto;
import ca.ulaval.glo4003.station.application.dtos.StationMaintenanceDto;
import ca.ulaval.glo4003.user.domain.UserRole;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;

import java.util.List;

@Path("/stations")
public class StationResource {
    private final StationService stationService;

    public StationResource(StationService stationService) {
        this.stationService = stationService;
    }

    @GET
    @Path("{stationId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getStation(@PathParam("stationId") String stationId) {
        StationDto stationDto = stationService.getStation(stationId);
        return Response.ok(stationDto).type(MediaType.APPLICATION_JSON).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllStations() {
        List<StationDto> stationDtoList = stationService.getAllStations();
        return Response.ok(stationDtoList).type(MediaType.APPLICATION_JSON).build();
    }

    @POST
    @Path("{stationId}/requestMaintenance")
    @RequireAuthentication
    @Consumes(MediaType.APPLICATION_JSON)
    public Response requestMaintenance(@PathParam("stationId") String stationId) {
        stationService.requestMaintenance(stationId);
        return Response.noContent().build();
    }


    @PATCH()
    @Path("{stationId}")
    @RequirePermissions(roles = {UserRole.TECHNICIAN, UserRole.ADMIN})
    @Produces(MediaType.APPLICATION_JSON)
    public Response setInMaintenance(@PathParam("stationId") String stationId,
                                     @Valid @NotNull(message = ErrorMessages.MISSING_REQUEST)
                                     StationMaintenanceDto stationMaintenanceDto) {
        stationService.setInMaintenance(stationId, stationMaintenanceDto.inMaintenance());
        return Response.noContent().build();
    }

    @POST
    @Path("{stationId}/removeBicycles")
    @RequirePermissions(roles = {UserRole.TECHNICIAN, UserRole.ADMIN})
    @Consumes(MediaType.APPLICATION_JSON)
    public Response removeBicycles(@PathParam("stationId") String stationId,
                                   @Valid @NotNull(message = ErrorMessages.MISSING_REQUEST)
                                   BicycleTransferDto bicycleTransferDto,
                                   @Context SecurityContext securityContext) {
        String userId = securityContext.getUserPrincipal().getName();

        stationService.removeBicycles(userId, stationId, bicycleTransferDto);

        return Response.noContent().build();
    }

    @POST
    @Path("{stationId}/addBicycles")
    @RequirePermissions(roles = {UserRole.TECHNICIAN, UserRole.ADMIN})
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addBicycles(@PathParam("stationId") String stationId,
                                @Valid @NotNull(message = ErrorMessages.MISSING_REQUEST)
                                BicycleTransferDto bicycleTransferDto,
                                @Context SecurityContext securityContext) {
        String userId = securityContext.getUserPrincipal().getName();

        stationService.addBicycles(userId, stationId, bicycleTransferDto);

        return Response.noContent().build();
    }
}
