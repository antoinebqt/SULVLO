package ca.ulaval.glo4003.station.api.mappers;

import ca.ulaval.glo4003.main.api.responses.ErrorResponse;
import ca.ulaval.glo4003.station.domain.exceptions.BicycleNotFoundException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class BicycleNotFoundExceptionMapper implements ExceptionMapper<BicycleNotFoundException> {

    private static final String DESCRIPTION = "The bicycle was not found in the station.";

    @Override
    public Response toResponse(BicycleNotFoundException e) {
        ErrorResponse response = new ErrorResponse(DESCRIPTION);
        return Response.status(Response.Status.NOT_FOUND)
                .entity(response)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }

}
