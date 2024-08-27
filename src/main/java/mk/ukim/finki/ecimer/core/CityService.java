package mk.ukim.finki.ecimer.core;

import mk.ukim.finki.ecimer.core.exceptions.CityAlreadyExistsException;
import mk.ukim.finki.ecimer.core.exceptions.CityNotFoundException;
import mk.ukim.finki.ecimer.core.exceptions.MunicipalityIsNotSpecifiedForCity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Service
@Validated
public class CityService {

    private final CityRepository cityRepository;

    public CityService(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    @Transactional(readOnly = true)
    public City getByUuid(@NotEmpty(message = "City uuid must not be empty!") String cityUuid) {
        return cityRepository.findById(cityUuid).orElseThrow(() -> new CityNotFoundException(cityUuid));
    }

    @Transactional
    public City registerCity(@NotEmpty(message = "Name must not be empty!") String name) {
        checkIfCityNameExists(name);
        City city = new City(name);
        city = cityRepository.save(city);
        return city;
    }

    @Transactional(readOnly = true)
    public void checkIfCityNameExists(@NotEmpty(message = "Name must not be empty!") String name){
        boolean nameExist = cityRepository.existsByName(name);
        if(nameExist){
            throw new CityAlreadyExistsException(name);
        }
    }

    @Transactional(readOnly = true)
    public void checkIfCityUuidExists(@NotEmpty(message = "City uuid must not be empty!") String cityUuid){
        boolean cityExists = cityRepository.existsByUuid(cityUuid);
        if (!cityExists){
            throw new CityNotFoundException(cityUuid);
        }
    }

    @Transactional(readOnly = true)
    public void checkIfMunicipalityExistsForCity(@NotEmpty(message = "City uuid must not be empty!") String cityUuid,
                                                 @NotEmpty(message = "Municipality uuid must not be empty!") String municipalityUuid) {
        City city = getByUuid(cityUuid);
        List<Municipality> municipalities = city.getMunicipalities();
        boolean municipalityExists = municipalities.stream().anyMatch(municipality -> municipality.getUuid().equals(municipalityUuid));
        if(!municipalityExists){
            throw new MunicipalityIsNotSpecifiedForCity(cityUuid, municipalityUuid);
        }
    }

    @Transactional
    public void addMunicipality(@NotEmpty(message = "City uuid must not be empty!") String cityUuid,
                                @NotEmpty(message = "Municipality name must not be empty!") String municipalityName) {
        City city = getByUuid(cityUuid);
        city.addMunicipality(municipalityName);
        cityRepository.save(city);
    }

    @Transactional
    public void updateMunicipality(@NotEmpty(message = "City uuid must not be empty!") String cityUuid,
                                   @NotEmpty(message = "Municipality uuid must not be empty!") String municipalityUuid,
                                   @NotEmpty(message = "Municipality name must not be empty!") String municipalityName) {
        City city = getByUuid(cityUuid);
        city.updateMunicipality(municipalityName, municipalityUuid);
        cityRepository.save(city);
    }

    @Transactional(readOnly = true)
    public List<City> searchByName(@NotEmpty(message = "Name must not be empty!") String name) {
        return cityRepository.findAllByNameContains(name);
    }
}
