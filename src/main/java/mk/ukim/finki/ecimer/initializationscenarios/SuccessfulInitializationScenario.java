package mk.ukim.finki.ecimer.initializationscenarios;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "ec_successful_initialization_scenario")
public class SuccessfulInitializationScenario extends mk.ukim.finki.ecimer.domain.Entity {

    private String initializationScenarioName;
    private LocalDateTime initializedAt;

    public SuccessfulInitializationScenario() {

    }

    public SuccessfulInitializationScenario(String initializationScenarioName) {
        this.initializationScenarioName = initializationScenarioName;
    }

    public String getInitializationScenarioName() {
        return initializationScenarioName;
    }

    public void setInitializationScenarioName(String initializationScenarioName) {
        this.initializationScenarioName = initializationScenarioName;
    }

    public LocalDateTime getInitializedAt() {
        return initializedAt;
    }

    public void setInitializedAt(LocalDateTime initializedAt) {
        this.initializedAt = initializedAt;
    }

    @Override
    protected void assignUuid() {
        super.assignUuid();
        this.initializedAt = LocalDateTime.now();
    }
}
