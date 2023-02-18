package ca.ulaval.glo4003.main.api.mappers;

import ca.ulaval.glo4003.main.api.responses.ErrorResponse;
import ca.ulaval.glo4003.main.domain.exceptions.InvalidDateFormatException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class InvalidDateFormatExceptionMapper implements ExceptionMapper<InvalidDateFormatException> {

    @Override
    public Response toResponse(InvalidDateFormatException e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(errorResponse)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }

}
