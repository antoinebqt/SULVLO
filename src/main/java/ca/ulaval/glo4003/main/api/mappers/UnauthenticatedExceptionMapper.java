package ca.ulaval.glo4003.main.api.mappers;

import ca.ulaval.glo4003.main.api.responses.ErrorResponse;
import ca.ulaval.glo4003.main.application.exceptions.UnauthenticatedException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class UnauthenticatedExceptionMapper implements ExceptionMapper<UnauthenticatedException> {

    private static final String DESCRIPTION = "Login is required.";

    @Override
    public Response toResponse(UnauthenticatedException e) {
        ErrorResponse response = new ErrorResponse(DESCRIPTION);
        return Response.status(Response.Status.UNAUTHORIZED)
                .entity(response)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
