package ca.ulaval.glo4003.station.api.mappers;

import ca.ulaval.glo4003.main.api.responses.ErrorResponse;
import ca.ulaval.glo4003.station.domain.exceptions.InvalidStationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class InvalidStationExceptionMapper implements ExceptionMapper<InvalidStationException> {

    private static final String DESCRIPTION = "The specified station location is invalid";

    @Override
    public Response toResponse(InvalidStationException e) {
        ErrorResponse errorResponse = new ErrorResponse(DESCRIPTION);
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(errorResponse)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
