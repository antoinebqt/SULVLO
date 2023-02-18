package ca.ulaval.glo4003.main.api.mappers;

import ca.ulaval.glo4003.main.api.responses.ErrorResponse;
import ca.ulaval.glo4003.main.application.exceptions.PermissionsException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class PermissionsExceptionMapper implements ExceptionMapper<PermissionsException> {

    private static final String DESCRIPTION = "Higher privileges are required to perform this action.";

    @Override
    public Response toResponse(PermissionsException e) {
        ErrorResponse response = new ErrorResponse(DESCRIPTION);
        return Response.status(Response.Status.FORBIDDEN)
                .entity(response)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
