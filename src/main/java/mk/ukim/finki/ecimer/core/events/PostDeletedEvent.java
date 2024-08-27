package mk.ukim.finki.ecimer.core.events;

import org.springframework.context.ApplicationEvent;

public class PostDeletedEvent extends ApplicationEvent {
    private final String postUuid;

    public PostDeletedEvent(Object source, String postUuid) {
        super(source);
        this.postUuid = postUuid;
    }

    public String getPostUuid() {
        return postUuid;
    }
}

