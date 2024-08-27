package mk.ukim.finki.ecimer.core.exceptions;

public class MunicipalityDoesNotExistsException extends RuntimeException {
    public final String municipalityUuid;

    public MunicipalityDoesNotExistsException(String municipalityUuid) {
        super(String.format("Municipality %s does not exists", municipalityUuid));
        this.municipalityUuid = municipalityUuid;
    }
}
