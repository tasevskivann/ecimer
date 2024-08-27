package mk.ukim.finki.ecimer.core.exceptions;

public class CityAlreadyExistsException extends RuntimeException {
    public final String cityName;

    public CityAlreadyExistsException(String name) {
        super(String.format("City with name %s already exists!", name));
        this.cityName = name;
    }
}
