package ca.ulaval.glo4003.trip.api.mappers;

import ca.ulaval.glo4003.main.api.responses.ErrorResponse;
import ca.ulaval.glo4003.trip.domain.exceptions.BicycleNotChargedEnoughException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class BicycleNotChargedEnoughExceptionMapper implements ExceptionMapper<BicycleNotChargedEnoughException> {

    private static final String DESCRIPTION = "The bicycle does not have enough charge.";

    @Override
    public Response toResponse(BicycleNotChargedEnoughException e) {
        ErrorResponse response = new ErrorResponse(DESCRIPTION);
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(response)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
