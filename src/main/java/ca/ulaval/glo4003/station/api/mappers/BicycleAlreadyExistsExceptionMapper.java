package ca.ulaval.glo4003.station.api.mappers;

import ca.ulaval.glo4003.main.api.responses.ErrorResponse;
import ca.ulaval.glo4003.station.domain.exceptions.BicycleAlreadyExistsException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class BicycleAlreadyExistsExceptionMapper implements ExceptionMapper<BicycleAlreadyExistsException> {

    private static final String DESCRIPTION = "The bicycle already exists in the station.";

    @Override
    public Response toResponse(BicycleAlreadyExistsException e) {
        ErrorResponse response = new ErrorResponse(DESCRIPTION);
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(response)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }

}
