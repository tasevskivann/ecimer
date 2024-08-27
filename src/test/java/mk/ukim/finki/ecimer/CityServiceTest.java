package mk.ukim.finki.ecimer;

import mk.ukim.finki.ecimer.core.City;
import mk.ukim.finki.ecimer.core.CityRepository;
import mk.ukim.finki.ecimer.core.CityService;
import mk.ukim.finki.ecimer.core.Municipality;
import mk.ukim.finki.ecimer.core.exceptions.CityAlreadyExistsException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CityServiceTest {

    @Mock
    CityRepository cityRepository;

    @InjectMocks
    CityService cityService;

    City city;
    String cityUuid;

    @Before
    public void setup() {
        this.cityUuid = "sk1";
        this.city = new City("Skopje");
        this.city.setUuid(cityUuid);
        Municipality municipality = new Municipality("Karpos");
        List<Municipality> municipalities = new ArrayList<>(Arrays.asList(municipality));
        this.city.setMunicipalities(municipalities);
    }

    @Test
    public void contextLoads(){
        assertAll(
                () -> assertNotNull(cityRepository),
                () -> assertNotNull(cityService)
        );
    }

    @Test
    public void should_ThrowCityAlreadyExistsException_When_NameExists(){
        // given
        when(cityRepository.existsByName("Skopje")).thenReturn(true);

        // then
        assertThrows(CityAlreadyExistsException.class, () -> cityService.checkIfCityNameExists("Skopje"));
    }

    @Test
    public void test(){
        when(cityRepository.existsByName(any())).thenReturn(false);
        when(cityRepository.save(any())).thenReturn(city);

        City saved = cityService.registerCity("Skopje");

        assertEquals(saved.getName(), city.getName());
        assertEquals(saved.getUuid(), city.getUuid());
        verify(cityRepository, times(1)).existsByName(any());
        verify(cityRepository,times(1)).save(any());
    }
}
