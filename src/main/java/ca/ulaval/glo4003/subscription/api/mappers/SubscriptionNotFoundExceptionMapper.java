package ca.ulaval.glo4003.subscription.api.mappers;

import ca.ulaval.glo4003.main.api.responses.ErrorResponse;
import ca.ulaval.glo4003.subscription.domain.exceptions.SubscriptionNotFoundException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class SubscriptionNotFoundExceptionMapper implements ExceptionMapper<SubscriptionNotFoundException> {

    private static final String DESCRIPTION = "The subscription was not found.";

    @Override
    public Response toResponse(SubscriptionNotFoundException e) {
        ErrorResponse response = new ErrorResponse(DESCRIPTION);
        return Response.status(Response.Status.NOT_FOUND)
                .entity(response)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
