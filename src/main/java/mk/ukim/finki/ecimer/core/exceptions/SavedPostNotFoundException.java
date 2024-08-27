package mk.ukim.finki.ecimer.core.exceptions;

public class SavedPostNotFoundException extends RuntimeException {
    public final String savedPostUuid;

    public SavedPostNotFoundException(String savedPostUuid) {
        super(String.format("Saved post %s does not exist!", savedPostUuid));
        this.savedPostUuid = savedPostUuid;
    }
}
