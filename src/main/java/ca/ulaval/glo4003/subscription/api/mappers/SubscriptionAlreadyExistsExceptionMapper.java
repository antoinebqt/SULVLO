package ca.ulaval.glo4003.subscription.api.mappers;

import ca.ulaval.glo4003.main.api.responses.ErrorResponse;
import ca.ulaval.glo4003.subscription.application.exceptions.SubscriptionAlreadyExistsException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class SubscriptionAlreadyExistsExceptionMapper implements ExceptionMapper<SubscriptionAlreadyExistsException> {

    private static final String DESCRIPTION = "User is already subscribed for this semester.";

    @Override
    public Response toResponse(SubscriptionAlreadyExistsException e) {
        ErrorResponse response = new ErrorResponse(DESCRIPTION);
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(response)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }

}
