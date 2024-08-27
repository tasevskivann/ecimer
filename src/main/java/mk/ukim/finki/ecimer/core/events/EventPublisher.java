package mk.ukim.finki.ecimer.core.events;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class EventPublisher {
    private Logger logger = LoggerFactory.getLogger(EventPublisher.class);

    private final ApplicationEventPublisher applicationEventPublisher;

    public EventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public void publishUserDeletedEvent(final String userUuid){
        UserDeletedEvent userDeletedEvent = new UserDeletedEvent(this, userUuid);
        applicationEventPublisher.publishEvent(userDeletedEvent);
        logger.info("User deleted event published for user with uuid " + userUuid);
    }

    public void publishPostDeletedEvent(final String postUuid){
        PostDeletedEvent postDeletedEvent = new PostDeletedEvent(this, postUuid);
        applicationEventPublisher.publishEvent(postDeletedEvent);
        logger.info("Post deleted event published for post with uuid " + postUuid);
    }

    public void publishPostVisitedEvent(final String postUuid, final String userUuid){
        PostVisitedEvent postVisitedEvent = new PostVisitedEvent(this, postUuid, userUuid);
        applicationEventPublisher.publishEvent(postVisitedEvent);
        logger.info("Post visited event published for post " + postUuid + " and user " + userUuid);
    }
}
