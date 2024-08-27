package mk.ukim.finki.ecimer.rdbms;

import mk.ukim.finki.ecimer.core.City;
import mk.ukim.finki.ecimer.core.CityRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaCityRepository extends JpaRepository<City, String>, CityRepository {

    boolean existsByName(String name);

    boolean existsByUuid(String uuid);

    List<City> findAllByNameContains(String name);
}
