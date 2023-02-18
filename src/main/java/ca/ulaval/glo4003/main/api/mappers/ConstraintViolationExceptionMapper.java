package ca.ulaval.glo4003.main.api.mappers;

import ca.ulaval.glo4003.main.api.responses.ErrorResponse;
import ca.ulaval.glo4003.main.api.responses.ManyErrorsResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class ConstraintViolationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

    private static final String DESCRIPTION = "There are many errors.";

    @Override
    public Response toResponse(ConstraintViolationException e) {
        ErrorResponse response;
        if (e.getConstraintViolations().size() > 1) {
            response = new ManyErrorsResponse(DESCRIPTION, e.getConstraintViolations().stream().map(
                    ConstraintViolation::getMessage).toList());
        } else {
            ConstraintViolation<?> constraintViolation = e.getConstraintViolations().stream().toList().get(0);
            response = new ErrorResponse(constraintViolation.getMessage());
        }
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(response)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
