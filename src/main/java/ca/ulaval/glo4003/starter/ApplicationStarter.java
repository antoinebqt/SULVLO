package ca.ulaval.glo4003.starter;

import ca.ulaval.glo4003.ApplicationMain;
import org.eclipse.jetty.server.Server;
import org.glassfish.jersey.jetty.JettyHttpContainerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;

public class ApplicationStarter {
    public static final String BASE_URI = "http://localhost:8080/";
    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationMain.class);

    public void start(ResourceConfig config) {
        try {
            LOGGER.info("Setup http server");
            final Server server = JettyHttpContainerFactory.createServer(URI.create(BASE_URI), config);

            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                try {
                    LOGGER.info("Shutting down the application...");
                    server.stop();
                    LOGGER.info("Done, exit.");
                } catch (Exception e) {
                    LOGGER.error("Error shutting down the server", e);
                }
            }));

            LOGGER.info("Application started.%nStop the application using CTRL+C");

            // block and wait shut down signal, like CTRL+C
            Thread.currentThread().join();

        } catch (InterruptedException e) {
            LOGGER.error("Error startig up the server", e);
        }
    }
}
