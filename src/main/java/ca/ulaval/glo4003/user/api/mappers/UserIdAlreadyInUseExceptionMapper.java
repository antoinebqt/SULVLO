package ca.ulaval.glo4003.user.api.mappers;

import ca.ulaval.glo4003.main.api.responses.ErrorResponse;
import ca.ulaval.glo4003.user.domain.exceptions.UserIdAlreadyInUseException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class UserIdAlreadyInUseExceptionMapper implements ExceptionMapper<UserIdAlreadyInUseException> {

    @Override
    public Response toResponse(UserIdAlreadyInUseException exception) {
        String description = this.getDescription();
        ErrorResponse response = new ErrorResponse(description);
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(response)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }

    private String getDescription() {
        return "This user already exists";
    }
}
