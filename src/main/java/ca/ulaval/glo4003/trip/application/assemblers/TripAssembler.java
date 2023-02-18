package ca.ulaval.glo4003.trip.application.assemblers;

import ca.ulaval.glo4003.trip.application.dtos.SummaryDto;
import ca.ulaval.glo4003.trip.application.dtos.TripDto;
import ca.ulaval.glo4003.trip.domain.Trip;
import ca.ulaval.glo4003.trip.domain.summary.Summary;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class TripAssembler {


    public List<TripDto> toTripDtos(List<Trip> trips) {

        List<TripDto> tripDtos = new ArrayList<>();

        for (Trip trip : trips) {
            tripDtos.add(new TripDto(
                    convertDurationToString(trip.calculateDuration()),
                    trip.getDepartureStation().getValue(),
                    convertInstantToString(trip.getDepartureDate()),
                    trip.getArrivalStation().getValue(),
                    convertInstantToString(trip.getArrivalDate()),
                    trip.getExtraCharge().toDouble())
            );
        }

        return tripDtos;
    }

    public SummaryDto toSummaryDto(Summary summary) {
        if (summary == null) return null;

        return new SummaryDto(
                convertDurationToString(summary.getTotalTravelTime()),
                convertDurationToString(summary.getAverageTravelTime()),
                summary.getNumberOfTrips(),
                summary.getFavouriteStation().getValue()
        );
    }

    private String convertDurationToString(Duration duration) {
        long durationInSeconds = duration.getSeconds();
        return String.format("%d:%02d:%02d",
                durationInSeconds / 3600, (durationInSeconds % 3600) / 60, (durationInSeconds % 60));
    }

    private String convertInstantToString(LocalDateTime date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
                .withLocale(Locale.CANADA).withZone(ZoneId.systemDefault());

        return formatter.format(date);
    }
}
