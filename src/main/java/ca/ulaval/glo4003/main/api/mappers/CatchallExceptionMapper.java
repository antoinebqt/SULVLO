package ca.ulaval.glo4003.main.api.mappers;

import ca.ulaval.glo4003.main.api.responses.ErrorResponse;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class CatchallExceptionMapper implements ExceptionMapper<Exception> {

    private static final String DESCRIPTION = "Unknown server error.";

    @Override
    public Response toResponse(Exception e) {
        ErrorResponse errorResponse = new ErrorResponse(DESCRIPTION);
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(errorResponse)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
