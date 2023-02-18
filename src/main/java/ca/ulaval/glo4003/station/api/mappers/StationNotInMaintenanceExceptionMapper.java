package ca.ulaval.glo4003.station.api.mappers;

import ca.ulaval.glo4003.main.api.responses.ErrorResponse;
import ca.ulaval.glo4003.station.domain.exceptions.StationNotInMaintenanceException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class StationNotInMaintenanceExceptionMapper implements ExceptionMapper<StationNotInMaintenanceException> {

    private static final String DESCRIPTION = "The station must be in maintenance to perform the action";

    @Override
    public Response toResponse(StationNotInMaintenanceException e) {
        ErrorResponse errorResponse = new ErrorResponse(DESCRIPTION);
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(errorResponse)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
