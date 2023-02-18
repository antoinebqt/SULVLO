package ca.ulaval.glo4003.trip.api.assemblers;

import ca.ulaval.glo4003.trip.api.dtos.HistorySummaryResponse;
import ca.ulaval.glo4003.trip.application.dtos.SummaryDto;
import ca.ulaval.glo4003.trip.application.dtos.TripDto;

import java.util.List;

public class TripDtoAssembler {
    public HistorySummaryResponse toResponse(SummaryDto summaryDto, List<TripDto> tripsDto) {
        return new HistorySummaryResponse(summaryDto, tripsDto);
    }
}
