package mk.ukim.finki.ecimer;

import mk.ukim.finki.ecimer.core.City;
import mk.ukim.finki.ecimer.core.Municipality;
import mk.ukim.finki.ecimer.core.exceptions.MunicipalityAlreadyExistsException;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertAll;


public class CityTest {

    @Test
    public void should_AddMunicipality_When_NoMunicipalitiesExists() {

        // given
        String municipalityName = "Bitola";
        City city = new City("Bitola");
        city.setMunicipalities(new ArrayList<>());

        // when
        city.addMunicipality(municipalityName);

        // then
        assertEquals(municipalityName, city.getMunicipalities().get(0).getName());
    }

    @Test(expected = MunicipalityAlreadyExistsException.class)
    public void should_ThrowException_When_MunicipalityExists() {
        // given
        String municipalityName = "Bitola";
        City city = new City(municipalityName);
        city.setMunicipalities(Collections.singletonList(new Municipality(municipalityName)));

        // when
        city.addMunicipality(municipalityName);

        // then
    }

    @Test
    public void should_AddMunicipality_When_MunicipalitiesExist() {
        // given
        String municipalityNameToAdd = "Mogila";
        String municipalityName = "Bitola";
        City city = new City(municipalityName);
        List<Municipality> municipalities = new ArrayList<>();
        municipalities.add(new Municipality(municipalityName));
        city.setMunicipalities(municipalities);

        // when
        city.addMunicipality(municipalityNameToAdd);

        // then
        assertAll(
                () -> assertEquals(city.getMunicipalities().size(), 2),
                () -> assertEquals(city.getMunicipalities().get(0).getName(), municipalityName),
                () -> assertEquals(city.getMunicipalities().get(1).getName(), municipalityNameToAdd)
        );

    }

    @Test
    public void should_UpdateMunicipality_When_NameDoesNotExist() {
        // given
        String municipalityName = "Karpos";
        String municipalityUuid = "1";
        City city = new City("Skopje");
        city.setUuid("skopje1");
        Municipality municipality = new Municipality(municipalityName);
        municipality.setUuid(municipalityUuid);
        List<Municipality> municipalities = new ArrayList<>(Arrays.asList(municipality));
        city.setMunicipalities(municipalities);

        // when
        city.updateMunicipality("Aerodrom", "1");

        // then
        assertAll(
                () -> assertEquals(city.getMunicipalities().size(), 1),
                () -> assertEquals(city.getMunicipalities().get(0).getName(), "Aerodrom")
        );
    }

    @Test
    public void should_ThrowMunicipalityAlreadyExistsException_When_MunicipalityNameExists() {
        // given
        String municipalityName = "Karpos";
        String municipalityUuid = "1";
        City city = new City("Skopje");
        city.setUuid("skopje1");
        Municipality municipality = new Municipality(municipalityName);
        municipality.setUuid(municipalityUuid);
        List<Municipality> municipalities = new ArrayList<>(Arrays.asList(municipality));
        city.setMunicipalities(municipalities);

        // when

        Throwable throwable = assertThrows(MunicipalityAlreadyExistsException.class, () -> city.addMunicipality(municipalityName));

        assertEquals("Municipality with name Karpos already exists for city skopje1", throwable.getMessage());
    }
}
