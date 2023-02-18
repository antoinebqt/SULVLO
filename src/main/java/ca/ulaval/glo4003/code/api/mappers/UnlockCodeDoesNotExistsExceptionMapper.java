package ca.ulaval.glo4003.code.api.mappers;

import ca.ulaval.glo4003.code.domain.exceptions.UnlockCodeDoesNotExistsException;
import ca.ulaval.glo4003.main.api.responses.ErrorResponse;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class UnlockCodeDoesNotExistsExceptionMapper implements ExceptionMapper<UnlockCodeDoesNotExistsException> {

    private static final String DESCRIPTION = "This unlock code does not exists.";

    @Override
    public Response toResponse(UnlockCodeDoesNotExistsException e) {
        ErrorResponse response = new ErrorResponse(DESCRIPTION);
        return Response.status(Response.Status.NOT_FOUND)
                .entity(response)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
