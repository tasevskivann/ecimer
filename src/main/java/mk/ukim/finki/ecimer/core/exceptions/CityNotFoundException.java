package mk.ukim.finki.ecimer.core.exceptions;

public class CityNotFoundException extends RuntimeException {
    public final String cityUuid;

    public CityNotFoundException(String cityUuid) {
        super(String.format("City %s not found", cityUuid));
        this.cityUuid = cityUuid;
    }
}
