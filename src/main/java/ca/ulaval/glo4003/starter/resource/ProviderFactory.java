package ca.ulaval.glo4003.starter.resource;

import ca.ulaval.glo4003.subscription.domain.semester.SemesterProvider;
import ca.ulaval.glo4003.subscription.infrastructure.providers.DevSemesterProvider;
import ca.ulaval.glo4003.subscription.infrastructure.providers.JsonSemesterProvider;

public class ProviderFactory {

    public SemesterProvider createSemesterProvider(boolean isDev) {
        if (isDev) {
            return new DevSemesterProvider();
        }
        return new JsonSemesterProvider();
    }
}
