package mk.ukim.finki.ecimer.core;

import java.util.List;
import java.util.Optional;

public interface CityRepository {

    Optional<City> findById(String cityUuid);

    City save(City city);

    boolean existsByName(String name);

    boolean existsByUuid(String cityUuid);

    List<City> findAllByNameContains(String name);
}
