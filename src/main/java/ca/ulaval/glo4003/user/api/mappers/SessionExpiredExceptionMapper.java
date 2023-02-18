package ca.ulaval.glo4003.user.api.mappers;

import ca.ulaval.glo4003.main.api.responses.ErrorResponse;
import ca.ulaval.glo4003.user.domain.exceptions.SessionExpiredException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class SessionExpiredExceptionMapper implements ExceptionMapper<SessionExpiredException> {

    private static final String DESCRIPTION = "Session is expired.";

    @Override
    public Response toResponse(SessionExpiredException e) {
        ErrorResponse errorResponse = new ErrorResponse(DESCRIPTION);
        return Response.status(Response.Status.UNAUTHORIZED)
                .entity(errorResponse)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }

}
