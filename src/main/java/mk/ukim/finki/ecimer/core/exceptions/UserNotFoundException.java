package mk.ukim.finki.ecimer.core.exceptions;

public class UserNotFoundException extends RuntimeException {
    public final String userUuid;

    public UserNotFoundException(String userUuid) {
        super(String.format("User %s not found", userUuid));
        this.userUuid = userUuid;
    }
}
