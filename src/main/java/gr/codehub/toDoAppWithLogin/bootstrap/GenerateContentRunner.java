package gr.codehub.toDoAppWithLogin.bootstrap;

import gr.codehub.toDoAppWithLogin.base.AbstractLogEntity;
import gr.codehub.toDoAppWithLogin.service.InitiationService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GenerateContentRunner extends AbstractLogEntity implements CommandLineRunner {

    private final InitiationService initiationService;

    @Override
    public void run(String... args) {
        initiationService.initiateDatabase();
        logger.info("Initiated database with credentials etc.");
    }
}
