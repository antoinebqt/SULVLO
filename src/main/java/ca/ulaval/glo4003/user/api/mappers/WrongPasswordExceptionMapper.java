package ca.ulaval.glo4003.user.api.mappers;

import ca.ulaval.glo4003.main.api.responses.ErrorResponse;
import ca.ulaval.glo4003.user.domain.exceptions.WrongPasswordException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class WrongPasswordExceptionMapper implements ExceptionMapper<WrongPasswordException> {

    private static final String DESCRIPTION = "email or password is invalid.";

    @Override
    public Response toResponse(WrongPasswordException e) {
        ErrorResponse errorResponse = new ErrorResponse(DESCRIPTION);
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(errorResponse)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
