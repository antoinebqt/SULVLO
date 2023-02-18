package ca.ulaval.glo4003.code.api.mappers;

import ca.ulaval.glo4003.code.application.exceptions.UnauthorizedTripException;
import ca.ulaval.glo4003.main.api.responses.ErrorResponse;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class UnauthorizedTripExceptionMapper implements ExceptionMapper<UnauthorizedTripException> {

    private static final String DESCRIPTION = "This traveller is not eligible to start a trip.";

    @Override
    public Response toResponse(UnauthorizedTripException e) {
        ErrorResponse response = new ErrorResponse(DESCRIPTION);
        return Response.status(Response.Status.FORBIDDEN)
                .entity(response)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
