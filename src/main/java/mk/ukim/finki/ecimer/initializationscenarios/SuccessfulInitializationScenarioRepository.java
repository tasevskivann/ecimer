package mk.ukim.finki.ecimer.initializationscenarios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SuccessfulInitializationScenarioRepository extends JpaRepository<SuccessfulInitializationScenario, String> {

    boolean existsByInitializationScenarioName(String name);
}
