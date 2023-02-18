package ca.ulaval.glo4003.subscription.api.mappers;

import ca.ulaval.glo4003.main.api.responses.ErrorResponse;
import ca.ulaval.glo4003.subscription.domain.exceptions.InvalidSemesterException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class InvalidSemesterExceptionMapper implements ExceptionMapper<InvalidSemesterException> {

    private static final String DESCRIPTION =
            "The specified semester is invalid. The format: the season (E, H or A) followed by the year. ex: H2022";

    @Override
    public Response toResponse(InvalidSemesterException e) {
        ErrorResponse response = new ErrorResponse(DESCRIPTION);
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(response)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }

}
