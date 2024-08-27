package mk.ukim.finki.ecimer.core.events;

import org.springframework.context.ApplicationEvent;

public class PostVisitedEvent extends ApplicationEvent {

    private final String postUuid;
    private final String userUuid;

    public PostVisitedEvent(Object source, String postUuid, String userUuid) {
        super(source);
        this.postUuid = postUuid;
        this.userUuid = userUuid;
    }

    public String getPostUuid() {
        return postUuid;
    }

    public String getUserUuid() {
        return userUuid;
    }
}
