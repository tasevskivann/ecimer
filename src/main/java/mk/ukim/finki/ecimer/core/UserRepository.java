package mk.ukim.finki.ecimer.core;


import java.util.Optional;

public interface UserRepository {

    User save(User user);

    Optional<User> findById(String userUuid);

    void deleteById(String userUuid);

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);
}
