package ca.ulaval.glo4003.starter.resource;

import ca.ulaval.glo4003.code.api.UnlockCodeResource;
import ca.ulaval.glo4003.main.api.filters.AuthenticationFilter;
import ca.ulaval.glo4003.main.api.filters.PermissionsFilter;
import ca.ulaval.glo4003.station.api.StationResource;
import ca.ulaval.glo4003.subscription.api.SubscriptionResource;
import ca.ulaval.glo4003.trip.api.TripResource;
import ca.ulaval.glo4003.user.api.UserResource;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;

public class ApplicationRegisterer {

    public ResourceConfig register(
            UserResource userResource,
            SubscriptionResource subscriptionResource,
            UnlockCodeResource unlockCodeResource,
            TripResource tripResource,
            StationResource stationResource,
            AuthenticationFilter authenticationFilter,
            PermissionsFilter permissionsFilter
    ) {
        final AbstractBinder binder = new AbstractBinder() {
            @Override
            protected void configure() {
                bind(userResource).to(UserResource.class);
                bind(subscriptionResource).to((SubscriptionResource.class));
                bind(unlockCodeResource).to(UnlockCodeResource.class);
                bind(tripResource).to(TripResource.class);
                bind(stationResource).to(StationResource.class);
            }
        };

        final ResourceConfig config = new ResourceConfig();
        config.register(binder);
        config.register(authenticationFilter);
        config.register(permissionsFilter);
        config.packages("ca.ulaval.glo4003.session.api");
        config.packages("ca.ulaval.glo4003.user.api");
        config.packages("ca.ulaval.glo4003.subscription.api");
        config.packages("ca.ulaval.glo4003.code.api");
        config.packages("ca.ulaval.glo4003.trip.api");
        config.packages("ca.ulaval.glo4003.main.api");
        config.packages("ca.ulaval.glo4003.station.api");
        config.property(ServerProperties.BV_SEND_ERROR_IN_RESPONSE, true);

        return config;

    }
}
