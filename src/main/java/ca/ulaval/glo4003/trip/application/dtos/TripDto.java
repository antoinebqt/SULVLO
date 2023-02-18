package ca.ulaval.glo4003.trip.application.dtos;

public record TripDto(
        String duration,
        String departureStation,
        String departureDate,
        String arrivalStation,
        String arrivalDate,
        double extraCharge) {
}
