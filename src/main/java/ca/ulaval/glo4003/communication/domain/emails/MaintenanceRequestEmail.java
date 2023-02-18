package ca.ulaval.glo4003.communication.domain.emails;

import ca.ulaval.glo4003.communication.domain.Email;
import ca.ulaval.glo4003.user.domain.EmailAddress;

import java.util.List;

public class MaintenanceRequestEmail extends Email {

    private static final String SUBJECT = "Maintenance service requested";
    private static final String MESSAGE = "A maintenance service has been requested for station %s";

    public MaintenanceRequestEmail(EmailAddress addressee, String stationName) {
        super(SUBJECT, String.format(MESSAGE, stationName), addressee);
    }

    public MaintenanceRequestEmail(List<EmailAddress> addressees, String stationName) {
        super(SUBJECT, String.format(MESSAGE, stationName), addressees);
    }
}
