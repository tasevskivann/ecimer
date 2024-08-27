package mk.ukim.finki.ecimer.core.exceptions;

public class PostNotFoundException extends RuntimeException {
    public final String postUuid;

    public PostNotFoundException(String postUuid) {
        super(String.format("Post %s not found", postUuid));
        this.postUuid = postUuid;
    }
}
