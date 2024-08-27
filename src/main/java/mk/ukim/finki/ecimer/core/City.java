package mk.ukim.finki.ecimer.core;

import lombok.Data;
import lombok.EqualsAndHashCode;
import mk.ukim.finki.ecimer.core.exceptions.MunicipalityAlreadyExistsException;
import mk.ukim.finki.ecimer.core.exceptions.MunicipalityDoesNotExistsException;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "ec_city")
@Data
@EqualsAndHashCode(callSuper = true)
public class City extends mk.ukim.finki.ecimer.domain.Entity {

    private String name;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "cityUuid", referencedColumnName = "uuid")
    private List<Municipality> municipalities;

    public City() {

    }

    public City(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Municipality> getMunicipalities() {
        return municipalities;
    }

    public void addMunicipality(String municipalityName) {
        if (!municipalities.isEmpty()) {
            boolean anyMatch = checkIfNewMunicipalityNameAlreadyExists(municipalityName);
            if (!anyMatch) {
                Municipality municipality = new Municipality();
                municipality.setCityUuid(this.getUuid());
                municipality.setName(municipalityName);
                municipalities.add(municipality);
            } else {
                throw new MunicipalityAlreadyExistsException(this.getUuid(), municipalityName);
            }
        } else {
            Municipality municipality = new Municipality();
            municipality.setName(municipalityName);
            municipality.setCityUuid(this.getUuid());
            municipalities.add(municipality);
        }
    }

    public void updateMunicipality(String municipalityName, String municipalityUuid) {
        boolean municipalityExists = checkIfNewMunicipalityNameAlreadyExists(municipalityName);

        if (!municipalityExists) {
            Municipality municipality = findMunicipalityByUuid(municipalityUuid);
            if (municipality != null) {
                municipality.setName(municipalityName);
            } else {
                throw new MunicipalityDoesNotExistsException(municipalityUuid);
            }
        } else {
            throw new MunicipalityAlreadyExistsException(this.getUuid(), municipalityName);
        }
    }

    private Municipality findMunicipalityByUuid(String municipalityUuid) {
        return municipalities.stream()
                .filter(municipality1 -> municipality1.getUuid().equals(municipalityUuid))
                .findAny()
                .orElse(null);
    }

    private boolean checkIfNewMunicipalityNameAlreadyExists(String municipalityName) {
        return municipalities.stream().anyMatch(municipality -> municipality.getName().equals(municipalityName));
    }
}
