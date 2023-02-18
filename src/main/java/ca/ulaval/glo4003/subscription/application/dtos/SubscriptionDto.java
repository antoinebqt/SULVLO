package ca.ulaval.glo4003.subscription.application.dtos;

public record SubscriptionDto(SemesterDto semester,
                              String travelTimeLimit, double balance) {
}
