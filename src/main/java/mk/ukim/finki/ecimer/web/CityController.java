package mk.ukim.finki.ecimer.web;

import mk.ukim.finki.ecimer.common.annotations.JsonArg;
import mk.ukim.finki.ecimer.core.CityService;
import mk.ukim.finki.ecimer.web.responsebody.City;
import mk.ukim.finki.ecimer.web.responsebody.CityMapper;
import mk.ukim.finki.ecimer.web.responsebody.CityRegister;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cities")
public class CityController {

    private final CityService cityService;
    private final CityMapper cityMapper;

    public CityController(CityService cityService) {
        this.cityService = cityService;
        this.cityMapper = CityMapper.INSTANCE;
    }

    @GetMapping("/{cityUuid}")
    public City getCityByUuid(@PathVariable String cityUuid) {
        return cityMapper.map(cityService.getByUuid(cityUuid));
    }

    @GetMapping
    public List<City> searchByName(@JsonArg String name){
        return cityMapper.map(cityService.searchByName(name));
    }

    @PostMapping
    @PreAuthorize("@securityService.hasRoleAdmin(authentication)")
    public CityRegister registerCity(@JsonArg String name, Authentication authentication) {
        return cityMapper.mapRegister(cityService.registerCity(name));
    }

    @PostMapping("/{cityUuid}/municipalities")
    @PreAuthorize("@securityService.hasRoleAdmin(authentication)")
    public void addMunicipality(@PathVariable String cityUuid, @JsonArg String municipalityName, Authentication authentication) {
        cityService.addMunicipality(cityUuid, municipalityName);
    }

    @PutMapping("/{cityUuid}/municipalities/{municipalityUuid}")
    @PreAuthorize("@securityService.hasRoleAdmin(authentication)")
    public void updateMunicipality(@PathVariable String cityUuid, @PathVariable String municipalityUuid, @JsonArg String municipalityName, Authentication authentication) {
        cityService.updateMunicipality(cityUuid, municipalityUuid, municipalityName);
    }
}
