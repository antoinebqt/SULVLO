package ca.ulaval.glo4003.subscription.application;

import ca.ulaval.glo4003.communication.domain.EmailService;
import ca.ulaval.glo4003.communication.domain.emails.InvoiceEmail;
import ca.ulaval.glo4003.main.domain.date.DateFactory;
import ca.ulaval.glo4003.subscription.application.assemblers.SubscriptionAssembler;
import ca.ulaval.glo4003.subscription.application.dtos.CreditCardDto;
import ca.ulaval.glo4003.subscription.application.dtos.SubscriptionCreationDto;
import ca.ulaval.glo4003.subscription.application.dtos.SubscriptionDto;
import ca.ulaval.glo4003.subscription.domain.SubscriptionFactory;
import ca.ulaval.glo4003.subscription.domain.payment.Invoice;
import ca.ulaval.glo4003.subscription.domain.payment.creditCard.CreditCard;
import ca.ulaval.glo4003.subscription.domain.payment.creditCard.CreditCardFactory;
import ca.ulaval.glo4003.subscription.domain.subscriptions.SemesterSubscription;
import ca.ulaval.glo4003.trip.domain.traveler.Traveler;
import ca.ulaval.glo4003.trip.domain.traveler.TravelerRepository;
import ca.ulaval.glo4003.user.domain.UserId;

import java.time.LocalDate;
import java.util.List;

public class SubscriptionService {

    private final SubscriptionFactory subscriptionFactory;
    private final CreditCardFactory creditCardFactory;
    private final EmailService emailService;
    private final SubscriptionAssembler subscriptionAssembler;
    private final TravelerRepository travelerRepository;

    public SubscriptionService(SubscriptionFactory subscriptionFactory,
                               TravelerRepository travelerRepository,
                               CreditCardFactory creditCardFactory,
                               EmailService emailService,
                               SubscriptionAssembler subscriptionAssembler) {
        this.subscriptionFactory = subscriptionFactory;
        this.creditCardFactory = creditCardFactory;
        this.emailService = emailService;
        this.subscriptionAssembler = subscriptionAssembler;
        this.travelerRepository = travelerRepository;
    }

    public void createSemesterSubscription(String userIdString, SubscriptionCreationDto subscriptionCreationDto) {
        UserId userId = new UserId(userIdString);
        Traveler traveler = travelerRepository.findById(userId);

        SemesterSubscription subscription = subscriptionFactory.create(
                subscriptionCreationDto.travelTimePlan(),
                subscriptionCreationDto.semester()
        );

        CreditCardDto creditCardDto = subscriptionCreationDto.creditCard();
        LocalDate expirationDate = DateFactory.createCreditCardExpirationDate(creditCardDto.expiration());
        CreditCard creditCard = creditCardFactory.create(
                creditCardDto.number(), creditCardDto.ownerName(),
                creditCardDto.securityCode(), expirationDate);

        Invoice invoice = traveler.purchaseSubscription(subscription, creditCard);

        travelerRepository.persist(traveler);

        emailService.sendEmail(new InvoiceEmail(traveler.getEmailAddress(), invoice));
    }

    public List<SubscriptionDto> getSubscriptions(String userId) {
        Traveler traveler = travelerRepository.findById(new UserId(userId));

        return subscriptionAssembler.toDtos(traveler.getSubscriptions());
    }
}
