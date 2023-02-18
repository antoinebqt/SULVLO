package ca.ulaval.glo4003.trip.application;


import ca.ulaval.glo4003.station.domain.Station;
import ca.ulaval.glo4003.station.domain.StationLocation;
import ca.ulaval.glo4003.station.domain.StationRepository;
import ca.ulaval.glo4003.station.domain.chargingPoint.ChargingPointId;
import ca.ulaval.glo4003.trip.application.assemblers.TripAssembler;
import ca.ulaval.glo4003.trip.application.dtos.SummaryDto;
import ca.ulaval.glo4003.trip.application.dtos.TripCreationDto;
import ca.ulaval.glo4003.trip.application.dtos.TripDto;
import ca.ulaval.glo4003.trip.application.dtos.TripEndDto;
import ca.ulaval.glo4003.trip.domain.summary.Summary;
import ca.ulaval.glo4003.trip.domain.summary.SummaryFactory;
import ca.ulaval.glo4003.trip.domain.traveler.Traveler;
import ca.ulaval.glo4003.trip.domain.traveler.TravelerRepository;
import ca.ulaval.glo4003.user.domain.UserId;

import java.util.List;

public class TripService {

    private final StationRepository stationRepository;
    private final TravelerRepository travelerRepository;
    private final SummaryFactory summaryFactory;
    private final TripAssembler tripAssembler;

    public TripService(StationRepository stationRepository, TravelerRepository travelerRepository,
                       TripAssembler tripAssembler, SummaryFactory summaryFactory) {
        this.stationRepository = stationRepository;
        this.travelerRepository = travelerRepository;
        this.tripAssembler = tripAssembler;
        this.summaryFactory = summaryFactory;
    }

    public void createTrip(String userIdString, TripCreationDto tripCreationDto) {
        UserId userId = new UserId(userIdString);
        Traveler traveler = travelerRepository.findById(userId);
        StationLocation stationLocation = StationLocation.fromLocation(tripCreationDto.departureLocation());
        Station station = stationRepository.findByLocation(stationLocation);

        traveler.startTrip(tripCreationDto.code(), station, new ChargingPointId(tripCreationDto.chargingPointId()));

        travelerRepository.persist(traveler);
        stationRepository.persist(station);
    }

    public void endTrip(String userIdString, TripEndDto tripEndDto) {
        UserId userId = new UserId(userIdString);
        Traveler traveler = travelerRepository.findById(userId);
        StationLocation stationLocation = StationLocation.fromLocation(tripEndDto.arrivalLocation());
        Station station = stationRepository.findByLocation(stationLocation);

        traveler.terminateTrip(station, new ChargingPointId(tripEndDto.chargingPointId()));

        travelerRepository.persist(traveler);
        stationRepository.persist(station);
    }

    public List<TripDto> getHistory(String userIdString) {
        UserId userId = new UserId(userIdString);
        Traveler traveler = travelerRepository.findById(userId);

        return tripAssembler.toTripDtos(traveler.getLastMonthTrips());
    }

    public SummaryDto getSummary(String userIdString) {
        UserId userId = new UserId(userIdString);
        Traveler traveler = travelerRepository.findById(userId);

        Summary summary = summaryFactory.createSummary(traveler.getLastMonthTrips());

        return tripAssembler.toSummaryDto(summary);
    }
}
