package ca.ulaval.glo4003.station.application;

import ca.ulaval.glo4003.communication.domain.EmailService;
import ca.ulaval.glo4003.communication.domain.emails.MaintenanceRequestEmail;
import ca.ulaval.glo4003.station.application.assemblers.StationAssembler;
import ca.ulaval.glo4003.station.application.dtos.BicycleTransferDto;
import ca.ulaval.glo4003.station.application.dtos.StationDto;
import ca.ulaval.glo4003.station.domain.Station;
import ca.ulaval.glo4003.station.domain.StationId;
import ca.ulaval.glo4003.station.domain.StationRepository;
import ca.ulaval.glo4003.station.domain.chargingPoint.ChargingPointId;
import ca.ulaval.glo4003.station.domain.technician.Technician;
import ca.ulaval.glo4003.station.domain.technician.TechnicianRepository;
import ca.ulaval.glo4003.user.domain.EmailAddress;
import ca.ulaval.glo4003.user.domain.UserId;

import java.util.List;

public class StationService {
    private final StationRepository stationRepository;
    private final TechnicianRepository technicianRepository;
    private final EmailService emailService;
    private final StationAssembler stationAssembler;

    public StationService(StationRepository stationRepository, TechnicianRepository technicianRepository,
                          EmailService emailService,
                          StationAssembler stationAssembler) {
        this.stationRepository = stationRepository;
        this.technicianRepository = technicianRepository;
        this.emailService = emailService;
        this.stationAssembler = stationAssembler;
    }

    public void requestMaintenance(String stationId) {
        Station station = stationRepository.findById(new StationId(stationId));
        List<EmailAddress> technicianEmailAddresses = technicianRepository.findAllEmailAdresses();

        emailService.sendEmail(new MaintenanceRequestEmail(technicianEmailAddresses, station.getName()));
    }

    public void setInMaintenance(String stationId, boolean inMaintenance) {
        Station station = stationRepository.findById(new StationId(stationId));
        station.setInMaintenance(inMaintenance);
        stationRepository.persist(station);
    }

    public void removeBicycles(String userIdString, String stationId, BicycleTransferDto bicycleTransferDto) {
        UserId userId = new UserId(userIdString);

        Station station = stationRepository.findById(new StationId(stationId));
        List<ChargingPointId> chargingPointIds =
                bicycleTransferDto.chargingPointIds().stream().map(ChargingPointId::new).toList();
        Technician technician = technicianRepository.findById(userId);
        technician.removeBicyclesFromStation(chargingPointIds, station);

        technicianRepository.persist(technician);
        stationRepository.persist(station);
    }

    public void addBicycles(String userIdString, String stationId, BicycleTransferDto bicycleTransferDto) {
        UserId userId = new UserId(userIdString);

        Technician technician = technicianRepository.findById(userId);
        List<ChargingPointId> chargingPointIds =
                bicycleTransferDto.chargingPointIds().stream().map(ChargingPointId::new).toList();
        Station station =
                stationRepository.findById(new StationId(stationId));
        technician.returnBicyclesToStation(chargingPointIds, station);

        technicianRepository.persist(technician);
        stationRepository.persist(station);
    }

    public StationDto getStation(String stationId) {
        Station station = stationRepository.findById(new StationId(stationId));
        return stationAssembler.toDto(station);
    }

    public List<StationDto> getAllStations() {
        List<Station> stations = stationRepository.findAll();
        return stationAssembler.toDtos(stations);
    }

}
