package ca.ulaval.glo4003.trip.application.dtos;

public record SummaryDto(
        String totalTravelTime,
        String averageTravelTime,
        int numberOfTrips,
        String favouriteStation
) {
}
