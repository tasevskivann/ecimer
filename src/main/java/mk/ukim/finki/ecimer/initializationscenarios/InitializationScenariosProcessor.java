package mk.ukim.finki.ecimer.initializationscenarios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class InitializationScenariosProcessor {

    @Autowired
    private List<InitializationScenario> initializationScenarios;

    @Autowired
    private SuccessfulInitializationScenarioRepository successfulInitializationScenarioRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void executeInitializationScenarios(){
        List<InitializationScenario> toBeExecuted = new ArrayList<>();

        initializationScenarios.forEach(initializationScenario -> {
            if(!successfulInitializationScenarioRepository.existsByInitializationScenarioName(initializationScenario.getInitializationScenarioName())){
                toBeExecuted.add(initializationScenario);
            }
        });

        toBeExecuted.forEach(initializationScenario -> {
            initializationScenario.createInitializationScenario();
            successfulInitializationScenarioRepository.save(new SuccessfulInitializationScenario(initializationScenario.getInitializationScenarioName()));
        });
    }
}
