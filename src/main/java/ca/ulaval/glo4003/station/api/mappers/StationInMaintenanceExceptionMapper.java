package ca.ulaval.glo4003.station.api.mappers;

import ca.ulaval.glo4003.main.api.responses.ErrorResponse;
import ca.ulaval.glo4003.station.domain.exceptions.StationInMaintenanceException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class StationInMaintenanceExceptionMapper implements ExceptionMapper<StationInMaintenanceException> {

    private static final String DESCRIPTION = "The station is in maintenance and therefore not available at the moment";

    @Override
    public Response toResponse(StationInMaintenanceException e) {
        ErrorResponse errorResponse = new ErrorResponse(DESCRIPTION);
        return Response.status(Response.Status.SERVICE_UNAVAILABLE)
                .entity(errorResponse)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
