package ca.ulaval.glo4003.user.api.mappers;

import ca.ulaval.glo4003.main.api.responses.ErrorResponse;
import ca.ulaval.glo4003.user.domain.exceptions.SessionNotFoundException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class SessionNotFoundExceptionMapper implements ExceptionMapper<SessionNotFoundException> {

    private static final String DESCRIPTION = "No session found.";

    @Override
    public Response toResponse(SessionNotFoundException e) {
        ErrorResponse errorResponse = new ErrorResponse(DESCRIPTION);
        return Response.status(Response.Status.NOT_FOUND)
                .entity(errorResponse)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
