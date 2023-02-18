package ca.ulaval.glo4003.trip.api.mappers;

import ca.ulaval.glo4003.main.api.responses.ErrorResponse;
import ca.ulaval.glo4003.trip.domain.exceptions.TripAlreadyEndedException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class TripAlreadyEndedExceptionMapper implements ExceptionMapper<TripAlreadyEndedException> {

    private static final String DESCRIPTION = "This trip is already ended.";

    @Override
    public Response toResponse(TripAlreadyEndedException e) {
        ErrorResponse response = new ErrorResponse(DESCRIPTION);
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(response)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }

}
