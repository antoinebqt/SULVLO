package ca.ulaval.glo4003.station.application;

import ca.ulaval.glo4003.communication.domain.Email;
import ca.ulaval.glo4003.communication.domain.EmailService;
import ca.ulaval.glo4003.communication.domain.emailSender.EmailSender;
import ca.ulaval.glo4003.station.application.assemblers.StationAssembler;
import ca.ulaval.glo4003.station.application.dtos.BicycleTransferDto;
import ca.ulaval.glo4003.station.application.dtos.StationDto;
import ca.ulaval.glo4003.station.domain.Station;
import ca.ulaval.glo4003.station.domain.StationBuilder;
import ca.ulaval.glo4003.station.domain.StationId;
import ca.ulaval.glo4003.station.domain.StationRepository;
import ca.ulaval.glo4003.station.domain.technician.BicycleTransfer;
import ca.ulaval.glo4003.station.domain.technician.Technician;
import ca.ulaval.glo4003.station.domain.technician.TechnicianRepository;
import ca.ulaval.glo4003.trip.domain.bicycle.Bicycle;
import ca.ulaval.glo4003.user.domain.EmailAddress;
import ca.ulaval.glo4003.user.domain.UserId;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StationServiceTest {

    public static final StationId A_STATION_ID = new StationId("1");
    public static final StationId ANOTHER_STATION_ID = new StationId("2");
    private static final int EXPECTED_STATIONS_SIZE = 2;
    private static final UserId USER_ID = new UserId("111 111 111");
    private static final List<Integer> USED_STATION_SLOTS = Arrays.asList(2, 4);
    private static final List<Integer> UNUSED_STATION_SLOTS = Arrays.asList(1, 3);
    private static final BicycleTransferDto BICYCLE_TRANSFER_DTO_WITH_USED_SLOTS =
            new BicycleTransferDto(USED_STATION_SLOTS);
    private static final BicycleTransferDto BICYCLE_TRANSFER_DTO_WITH_UNUSED_SLOTS =
            new BicycleTransferDto(UNUSED_STATION_SLOTS);

    private TechnicianRepository technicianRepository;
    private EmailSender emailSender;
    private StationService stationService;
    private StationRepository stationRepository;
    private Station station;
    private Technician technician;

    @BeforeEach
    public void setUp() {
        setUpRepositories();

        emailSender = Mockito.mock(EmailSender.class);
        EmailService emailService = new EmailService(emailSender);
        StationAssembler stationAssembler = new StationAssembler();

        stationService =
                new StationService(stationRepository, technicianRepository, emailService, stationAssembler);
    }

    @Test
    public void givenAStationInMaintenance_whenRemovingBicycles_thenRemovesBicycleFromStation() {
        station.setInMaintenance(true);

        stationService.removeBicycles(USER_ID.getValue(), A_STATION_ID.value(), BICYCLE_TRANSFER_DTO_WITH_USED_SLOTS);

        Mockito.verify(station).removeBicyclesForTransfer(Mockito.anyList(), Mockito.any(BicycleTransfer.class));
    }

    @Test
    public void givenAStationInMaintenance_whenRemovingBicycles_thenAddsTheBicyclesInTheBicycleTransfer() {
        station.setInMaintenance(true);

        stationService.removeBicycles(USER_ID.getValue(), A_STATION_ID.value(), BICYCLE_TRANSFER_DTO_WITH_USED_SLOTS);

        int expectedBicyclesInTransfer = 4;
        Assertions.assertEquals(expectedBicyclesInTransfer, technician.getBicyclesInTransfer().size());
    }

    @Test
    public void givenAStationInMaintenance_whenRemovingBicycles_thenSavesTheTechnicianInRepository() {
        station.setInMaintenance(true);

        stationService.removeBicycles(USER_ID.getValue(), A_STATION_ID.value(), BICYCLE_TRANSFER_DTO_WITH_USED_SLOTS);

        Mockito.verify(technicianRepository).persist(technician);
    }

    @Test
    public void givenAStationInMaintenance_whenRemovingBicycles_thenUpdatesTheStationInRepository() {
        station.setInMaintenance(true);

        stationService.removeBicycles(USER_ID.getValue(), A_STATION_ID.value(), BICYCLE_TRANSFER_DTO_WITH_USED_SLOTS);

        Mockito.verify(stationRepository).persist(station);
    }

    @Test
    public void whenAddingBicycles_thenAddsBicyclesInTheStation() {
        stationService.addBicycles(USER_ID.getValue(), A_STATION_ID.value(), BICYCLE_TRANSFER_DTO_WITH_UNUSED_SLOTS);

        Mockito.verify(station).returnBicyclesFromTransfer(Mockito.anyList(), Mockito.any(BicycleTransfer.class));
    }

    @Test
    public void whenAddingBicycles_thenRemovesTheBicyclesInTheBicycleTransfer() {
        stationService.addBicycles(USER_ID.getValue(), A_STATION_ID.value(), BICYCLE_TRANSFER_DTO_WITH_UNUSED_SLOTS);

        Assertions.assertTrue(technician.getBicyclesInTransfer().isEmpty());
    }

    @Test
    public void whenAddingBicycles_thenSavesTheTechnicianInRepository() {
        stationService.addBicycles(USER_ID.getValue(), A_STATION_ID.value(), BICYCLE_TRANSFER_DTO_WITH_UNUSED_SLOTS);

        Mockito.verify(technicianRepository).persist(technician);
    }

    @Test
    public void whenAddingBicycles_thenUpdatesTheStationInRepository() {
        stationService.addBicycles(USER_ID.getValue(), A_STATION_ID.value(), BICYCLE_TRANSFER_DTO_WITH_UNUSED_SLOTS);

        Mockito.verify(stationRepository).persist(station);
    }

    @Test
    public void whenRequestingAMaintenance_thenShouldRetrieveTheStation() {
        stationService.requestMaintenance(A_STATION_ID.value());

        Mockito.verify(stationRepository).findById(A_STATION_ID);
    }

    @Test
    public void whenRequestingAMaintenance_thenShouldRetrieveTheEmployeesEmailAddresses() {
        stationService.requestMaintenance(A_STATION_ID.value());

        Mockito.verify(technicianRepository).findAllEmailAdresses();
    }

    @Test
    public void whenRequestingAMaintenance_thenAnEmailShouldBeSentToAllEmployees() {
        stationService.requestMaintenance(A_STATION_ID.value());

        Mockito.verify(emailSender).send(Mockito.any(Email.class));
    }

    @Test
    public void whenSettingAsStationInMaintenance_thenShouldRetrieveTheStation() {
        stationService.setInMaintenance(A_STATION_ID.value(), true);

        Mockito.verify(stationRepository).findById(A_STATION_ID);
    }

    @Test
    public void whenSettingAsStationInMaintenance_thenTheStationShouldBeInMaintenance() {
        stationService.setInMaintenance(A_STATION_ID.value(), true);

        assertTrue(station.isInMaintenance());
    }

    @Test
    public void whenSettingAsStationInMaintenance_thenShouldUpdateTheStation() {
        stationService.setInMaintenance(A_STATION_ID.value(), true);

        Mockito.verify(stationRepository).persist(station);
    }

    @Test
    public void whenGettingTheStation_thenReturnStationDto() {
        StationDto stationDto = stationService.getStation(A_STATION_ID.value());

        assertEquals(stationDto.id(), station.getStationId().value());
        assertEquals(stationDto.name(), station.getName());
        assertEquals(stationDto.capacity(), station.getCapacity());
        assertEquals(stationDto.inMaintenance(), station.isInMaintenance());
        assertEquals(stationDto.chargingPoints().size(), station.getChargingPoints().size());
    }

    @Test
    public void whenGettingStations_thenReturnsAllStations() {
        List<StationDto> stations = stationService.getAllStations();

        assertEquals(EXPECTED_STATIONS_SIZE, stations.size());
    }

    private void setUpRepositories() {
        List<Bicycle> bicycles = new ArrayList<>();
        bicycles.add(new Bicycle());
        bicycles.add(new Bicycle());
        BicycleTransfer bicycleTransfer = new BicycleTransfer(bicycles);
        technician = new Technician(USER_ID, new EmailAddress("dasds"), bicycleTransfer);

        technicianRepository = Mockito.mock(TechnicianRepository.class);
        List<EmailAddress> emailAddresses =
                Arrays.asList(new EmailAddress("an email"), new EmailAddress("another email"));
        Mockito.when(technicianRepository.findAllEmailAdresses()).thenReturn(emailAddresses);
        Mockito.when(technicianRepository.findById(USER_ID)).thenReturn(technician);

        Station anotherStation = new StationBuilder().withId(ANOTHER_STATION_ID).build();
        station = Mockito.spy(new StationBuilder().withId(A_STATION_ID).build());
        stationRepository = Mockito.mock(StationRepository.class);
        Mockito.when(stationRepository.findById(A_STATION_ID)).thenReturn(station);
        Mockito.when(stationRepository.findAll()).thenReturn(List.of(station, anotherStation));
    }

}
