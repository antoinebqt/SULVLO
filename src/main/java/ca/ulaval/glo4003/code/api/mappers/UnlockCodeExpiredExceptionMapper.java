package ca.ulaval.glo4003.code.api.mappers;

import ca.ulaval.glo4003.code.domain.exceptions.UnlockCodeExpiredException;
import ca.ulaval.glo4003.main.api.responses.ErrorResponse;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class UnlockCodeExpiredExceptionMapper implements ExceptionMapper<UnlockCodeExpiredException> {

    private static final String DESCRIPTION = "The unlock code is expired.";

    @Override
    public Response toResponse(UnlockCodeExpiredException e) {
        ErrorResponse response = new ErrorResponse(DESCRIPTION);
        return Response.status(Response.Status.FORBIDDEN)
                .entity(response)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
