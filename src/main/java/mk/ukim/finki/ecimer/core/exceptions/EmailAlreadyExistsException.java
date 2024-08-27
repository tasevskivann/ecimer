package mk.ukim.finki.ecimer.core.exceptions;

public class EmailAlreadyExistsException extends RuntimeException {
    public final String email;

    public EmailAlreadyExistsException(String email) {
        super(String.format("User with email %s has already been created", email));
        this.email = email;
    }
}
