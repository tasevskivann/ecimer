package mk.ukim.finki.ecimer.core;

import mk.ukim.finki.ecimer.core.events.EventPublisher;
import mk.ukim.finki.ecimer.core.exceptions.EmailAlreadyExistsException;
import mk.ukim.finki.ecimer.core.exceptions.UserNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;

@Service
@Validated
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EventPublisher eventPublisher;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, EventPublisher eventPublisher) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.eventPublisher = eventPublisher;
    }

    @Transactional
    public User registerUser(@NotEmpty(message = "Email must not be empty!") String email,
                             @NotEmpty(message = "Password must not be empty!") String password,
                             @NotEmpty(message = "First name must not be empty!") String firstName,
                             @NotEmpty(message = "Last name must not be empty!") String lastName,
                             @NotEmpty(message = "Phone number must not be empty!") String phoneNumber) {
        if(existsByEmail(email)){
            throw new EmailAlreadyExistsException(email);
        }
        User user = new User(email, password, firstName, lastName, phoneNumber, passwordEncoder);
        user.appendRole(UserRoles.USER);
        user = userRepository.save(user);
        return user;
    }

    @Transactional(readOnly = true)
    public boolean existsByEmail(@NotEmpty(message = "Email must not be empty!") String email){
        return userRepository.existsByEmail(email);
    }


    @Transactional
    public void registerAdminUser(@NotEmpty(message = "Email must not be empty!") String email,
                                  @NotEmpty(message = "Password must not be empty!") String password) {
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        user.appendRole(UserRoles.ADMIN);
        userRepository.save(user);
    }

    @Transactional
    public void updateUser(@NotEmpty(message = "User uuid must not be empty!") String userUuid, String email, String password, String firstName,
                           String lastName, String phoneNumber) {
        User user = getByUuid(userUuid);
        if(email != null && existsByEmail(email)){
            throw new EmailAlreadyExistsException(email);
        }
        user.update(email, password, firstName, lastName, phoneNumber);
    }

    @Transactional(readOnly = true)
    public User getByUuid(@NotEmpty(message = "User uuid must not be empty!") String userUuid) {
        return userRepository.findById(userUuid).orElseThrow(() -> new UserNotFoundException(userUuid));
    }

    @Transactional
    public void deleteByUuid(@NotEmpty(message = "User uuid must not be empty!") String userUuid) {
        userRepository.deleteById(userUuid);
        eventPublisher.publishUserDeletedEvent(userUuid);
    }
}
