package ca.ulaval.glo4003.station.api.mappers;

import ca.ulaval.glo4003.main.api.responses.ErrorResponse;
import ca.ulaval.glo4003.station.domain.exceptions.NotEnoughBicyclesInTransferException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class NotEnoughBicyclesInTransferExceptionMapper
        implements ExceptionMapper<NotEnoughBicyclesInTransferException> {

    private static final String DESCRIPTION = "There is not enough bicycles in transfer";

    @Override
    public Response toResponse(NotEnoughBicyclesInTransferException e) {
        ErrorResponse errorResponse = new ErrorResponse(DESCRIPTION);
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(errorResponse)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
