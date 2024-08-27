package mk.ukim.finki.ecimer.core.exceptions;

public class CityDoesNotConsistsMunicipalitiesException extends RuntimeException {
    public final String cityUuid;
    public CityDoesNotConsistsMunicipalitiesException(String cityUuid){
        super(String.format("City with uuid %s does not consists any municipalities", cityUuid));
        this.cityUuid = cityUuid;
    }
}
