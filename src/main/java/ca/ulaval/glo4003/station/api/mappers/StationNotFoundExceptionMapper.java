package ca.ulaval.glo4003.station.api.mappers;

import ca.ulaval.glo4003.main.api.responses.ErrorResponse;
import ca.ulaval.glo4003.station.domain.exceptions.StationNotFoundException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class StationNotFoundExceptionMapper implements ExceptionMapper<StationNotFoundException> {

    private static final String DESCRIPTION = "The station was not found.";

    @Override
    public Response toResponse(StationNotFoundException e) {
        ErrorResponse response = new ErrorResponse(DESCRIPTION);
        return Response.status(Response.Status.NOT_FOUND)
                .entity(response)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
