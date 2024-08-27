package mk.ukim.finki.ecimer.core.exceptions;

public class MunicipalityIsNotSpecifiedForCity extends RuntimeException {
    public final String cityUuid;
    public final String municipalityUuid;

    public MunicipalityIsNotSpecifiedForCity(String cityUuid, String municipalityUuid) {
        super(String.format("Municipality %s is not specified as municipality in city %s", municipalityUuid, cityUuid));
        this.cityUuid = cityUuid;
        this.municipalityUuid = municipalityUuid;
    }
}
