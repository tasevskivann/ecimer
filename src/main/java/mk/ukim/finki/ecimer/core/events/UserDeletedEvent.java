package mk.ukim.finki.ecimer.core.events;

import org.springframework.context.ApplicationEvent;

public class UserDeletedEvent extends ApplicationEvent {

    private final String userUuid;

    public UserDeletedEvent(Object source, String userUuid) {
        super(source);
        this.userUuid = userUuid;
    }

    public String getUserUuid() {
        return userUuid;
    }
}
