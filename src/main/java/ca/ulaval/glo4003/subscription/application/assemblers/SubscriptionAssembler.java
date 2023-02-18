package ca.ulaval.glo4003.subscription.application.assemblers;

import ca.ulaval.glo4003.subscription.application.dtos.SemesterDto;
import ca.ulaval.glo4003.subscription.application.dtos.SubscriptionDto;
import ca.ulaval.glo4003.subscription.domain.Subscription;
import ca.ulaval.glo4003.subscription.domain.semester.Semester;

import java.util.ArrayList;
import java.util.List;

public class SubscriptionAssembler {
    public List<SubscriptionDto> toDtos(List<Subscription> subscriptions) {
        List<SubscriptionDto> subscriptionDtos = new ArrayList<>();

        for (Subscription subscription : subscriptions) {
            SemesterDto semesterDto = createSemesterDto(subscription.getSemester());
            String travelTimeLimit = subscription.getTravelTimePlan().toString();
            double balance = subscription.getBalance().toDouble();

            subscriptionDtos.add(new SubscriptionDto(semesterDto, travelTimeLimit, balance));
        }

        return subscriptionDtos;
    }

    private SemesterDto createSemesterDto(Semester semester) {
        if (semester == null) {
            return null;
        }
        String season = semester.getSeason().toString();
        int year = semester.getYear();
        String startDate = semester.getStartDate().toString();
        String endDate = semester.getEndDate().toString();

        return new SemesterDto(season, year, startDate, endDate);
    }
}
