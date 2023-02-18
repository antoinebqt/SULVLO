package ca.ulaval.glo4003.subscription.api.mappers;

import ca.ulaval.glo4003.main.api.responses.ErrorResponse;
import ca.ulaval.glo4003.subscription.domain.exceptions.InvalidTravelTimePlanException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class InvalidTravelTimePlanExceptionMapper implements ExceptionMapper<InvalidTravelTimePlanException> {

    private static final String DESCRIPTION = "The travelTimePlan must be 10 or 30.";

    @Override
    public Response toResponse(InvalidTravelTimePlanException e) {
        ErrorResponse response = new ErrorResponse(DESCRIPTION);
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(response)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }

}
