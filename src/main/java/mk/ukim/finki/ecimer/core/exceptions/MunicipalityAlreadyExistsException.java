package mk.ukim.finki.ecimer.core.exceptions;

public class MunicipalityAlreadyExistsException extends RuntimeException {
    public final String cityUuid;
    public final String municipalityName;

    public MunicipalityAlreadyExistsException(String cityUuid, String municipalityName) {
        super(String.format("Municipality with name %s already exists for city %s", municipalityName, cityUuid));
        this.cityUuid = cityUuid;
        this.municipalityName = municipalityName;
    }
}
