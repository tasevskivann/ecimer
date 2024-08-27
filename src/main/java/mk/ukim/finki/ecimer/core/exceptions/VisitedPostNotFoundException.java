package mk.ukim.finki.ecimer.core.exceptions;

public class VisitedPostNotFoundException extends RuntimeException {
    public final String visitedPostUuid;

    public VisitedPostNotFoundException(String visitedPostUuid) {
        super(String.format("Visited post %s does not exist!", visitedPostUuid));
        this.visitedPostUuid = visitedPostUuid;
    }
}
