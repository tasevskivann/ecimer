package mk.ukim.finki.ecimer.core.adminuserinitializationscenario;

import mk.ukim.finki.ecimer.core.UserService;
import mk.ukim.finki.ecimer.initializationscenarios.InitializationScenario;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component("AdminUserInitializationScenario")
public class AdminUserInitializationScenario implements InitializationScenario {
    private final String AdminUserInitializationScenario = "AdminUserInitializationScenario";

    private final AdminUserConfigurationProperties configurationProperties;
    private final UserService userService;

    public AdminUserInitializationScenario(AdminUserConfigurationProperties configurationProperties, UserService userService) {
        this.configurationProperties = configurationProperties;
        this.userService = userService;
    }

    @Override
    public String getInitializationScenarioName() {
        return AdminUserInitializationScenario;
    }

    @Override
    @Transactional
    public void createInitializationScenario() {
        userService.registerAdminUser(configurationProperties.getEmail(), configurationProperties.getPassword());
    }
}
