package ca.ulaval.glo4003.code.application;

import ca.ulaval.glo4003.code.domain.UnlockCode;
import ca.ulaval.glo4003.code.domain.UnlockCodeGenerator;
import ca.ulaval.glo4003.communication.domain.EmailService;
import ca.ulaval.glo4003.communication.domain.emails.CodeEmail;
import ca.ulaval.glo4003.trip.domain.traveler.Traveler;
import ca.ulaval.glo4003.trip.domain.traveler.TravelerRepository;
import ca.ulaval.glo4003.user.domain.UserId;

public class UnlockCodeService {

    private final TravelerRepository travelerRepository;
    private final EmailService emailService;
    private final UnlockCodeGenerator unlockCodeGenerator;

    public UnlockCodeService(TravelerRepository travelerRepository,
                             EmailService emailService,
                             UnlockCodeGenerator unlockCodeGenerator
    ) {
        this.travelerRepository = travelerRepository;
        this.emailService = emailService;
        this.unlockCodeGenerator = unlockCodeGenerator;
    }

    public void createCode(String userIdString) {
        UserId userId = new UserId(userIdString);
        Traveler traveler = travelerRepository.findById(userId);

        UnlockCode unlockCode = unlockCodeGenerator.generate();
        traveler.setUnlockCode(unlockCode);

        emailService.sendEmail(new CodeEmail(traveler.getEmailAddress(), unlockCode));

        travelerRepository.persist(traveler);
    }
}
