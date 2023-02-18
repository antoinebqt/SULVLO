package ca.ulaval.glo4003.trip.api.dtos;

import ca.ulaval.glo4003.trip.application.dtos.SummaryDto;
import ca.ulaval.glo4003.trip.application.dtos.TripDto;

import java.util.List;

public record HistorySummaryResponse(SummaryDto summary, List<TripDto> trips) {
}
