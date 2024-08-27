package mk.ukim.finki.ecimer.core;

import mk.ukim.finki.ecimer.core.events.PostDeletedEvent;
import mk.ukim.finki.ecimer.core.events.PostVisitedEvent;
import mk.ukim.finki.ecimer.core.events.UserDeletedEvent;
import mk.ukim.finki.ecimer.core.exceptions.VisitedPostNotFoundException;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class VisitedPostService {
    private final VisitedPostRepository visitedPostRepository;

    public VisitedPostService(VisitedPostRepository visitedPostRepository) {
        this.visitedPostRepository = visitedPostRepository;
    }

    @Transactional(readOnly = true)
    public VisitedPost getByUuid(@NotEmpty(message = "Uuid must not be empty!") String visitedPostUuid) {
        return visitedPostRepository.findById(visitedPostUuid).orElseThrow(() -> new VisitedPostNotFoundException(visitedPostUuid));
    }

    @Transactional(readOnly = true)
    public Page<VisitedPost> getVisitedPostsByUserUuid(@NotNull(message = "Page number must not be null!") int pageNumber,
                                                       @NotNull(message = "Page size must not be null!")
                                                       @Min(value = 1, message = "Page size must be greater than 0!") int pageSize,
                                                       @NotEmpty(message = "User uuid must not be empty!") String userUuid) {
        Sort sort = Sort.by(Sort.Direction.DESC, "visitedAt");
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        return visitedPostRepository.findAllByUserUuid(userUuid, pageable);
    }

    @Transactional
    public List<VisitedPost> getVisitedPostsByUserUuid(@NotEmpty(message = "User uuid must not be empty!") String userUuid) {
        return visitedPostRepository.findAllByUserUuid(userUuid);
    }

    @Transactional
    public void deleteByUuid(@NotEmpty(message = "Uuid must not be empty!") String visitedPostUuid) {
        visitedPostRepository.deleteById(visitedPostUuid);
    }

    @Transactional
    public void deleteAllByUserUuid(@NotEmpty(message = "User uuid must not be empty!") String userUuid) {
        visitedPostRepository.deleteAllByUserUuid(userUuid);
    }

    private VisitedPost visitedPostExistsForUserAndPost(List<VisitedPost> visitedPosts, String userUuid, String postUuid) {
        return visitedPosts.stream()
                .filter(visitedPost -> visitedPost.getUserUuid().equals(userUuid) && visitedPost.getPostUuid().equals(postUuid))
                .findFirst().orElse(null);
    }

    @Async("threadPoolTaskExecutor")
    @EventListener
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void handlePostVisitedEvent(PostVisitedEvent event) {
        List<VisitedPost> visitedPostsForUser = getVisitedPostsByUserUuid(event.getUserUuid());
        VisitedPost alreadyVisitedPost = visitedPostExistsForUserAndPost(visitedPostsForUser, event.getUserUuid(), event.getPostUuid());

        if(alreadyVisitedPost != null){
            //post has been revisited right now.
            alreadyVisitedPost.setVisitedAt(LocalDateTime.now());
            visitedPostRepository.save(alreadyVisitedPost);
        }else {
            VisitedPost visitedPost = new VisitedPost(event.getPostUuid(), event.getUserUuid());
            visitedPostRepository.save(visitedPost);
        }
    }

    @Async("threadPoolTaskExecutor")
    @EventListener
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void handlerUserDeletedEvent(UserDeletedEvent event) {
        visitedPostRepository.deleteAllByUserUuid(event.getUserUuid());
    }

    @Async("threadPoolTaskExecutor")
    @EventListener
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void handlerPostDeletedEvent(PostDeletedEvent event) {
        visitedPostRepository.deleteAllByPostUuid(event.getPostUuid());
    }
}
