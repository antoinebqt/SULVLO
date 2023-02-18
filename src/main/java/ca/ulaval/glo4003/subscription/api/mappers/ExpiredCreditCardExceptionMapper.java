package ca.ulaval.glo4003.subscription.api.mappers;

import ca.ulaval.glo4003.main.api.responses.ErrorResponse;
import ca.ulaval.glo4003.subscription.domain.exceptions.ExpiredCreditCardException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class ExpiredCreditCardExceptionMapper implements ExceptionMapper<ExpiredCreditCardException> {

    private static final String DESCRIPTION = "The credit card is expired.";

    @Override
    public Response toResponse(ExpiredCreditCardException e) {
        ErrorResponse response = new ErrorResponse(DESCRIPTION);
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(response)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }

}
