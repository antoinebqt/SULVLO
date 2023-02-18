package ca.ulaval.glo4003.trip.api.mappers;

import ca.ulaval.glo4003.main.api.responses.ErrorResponse;
import ca.ulaval.glo4003.trip.domain.exceptions.TripNotFoundException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class TripNotFoundExceptionMapper implements ExceptionMapper<TripNotFoundException> {

    private static final String DESCRIPTION = "There is no ongoing trip.";

    @Override
    public Response toResponse(TripNotFoundException e) {
        ErrorResponse response = new ErrorResponse(DESCRIPTION);
        return Response.status(Response.Status.NOT_FOUND)
                .entity(response)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
