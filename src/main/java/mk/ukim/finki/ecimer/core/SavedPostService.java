package mk.ukim.finki.ecimer.core;

import mk.ukim.finki.ecimer.core.events.PostDeletedEvent;
import mk.ukim.finki.ecimer.core.events.UserDeletedEvent;
import mk.ukim.finki.ecimer.core.exceptions.SavedPostNotFoundException;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Service
@Validated
public class SavedPostService {
    private final SavedPostRepository savedPostRepository;

    public SavedPostService(SavedPostRepository savedPostRepository) {
        this.savedPostRepository = savedPostRepository;
    }

    @Transactional(readOnly = true)
    public SavedPost getByUuid(String savedPostUuid) {
        return savedPostRepository.findByUuid(savedPostUuid).orElseThrow(() -> new SavedPostNotFoundException(savedPostUuid));
    }

    @Transactional(readOnly = true)
    public Page<SavedPost> getSavedPostsForUser(
            @NotNull(message = "Page number must not be null!") int pageNumber,
            @NotNull(message = "Page size must not be null!")
            @Min(value = 1, message = "Page size must be greater than 0") int pageSize,
            @NotEmpty(message = "User uuid must not be empty!") String userUuid) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        return savedPostRepository.findAllByUserUuid(userUuid, pageable);
    }

    @Transactional(readOnly = true)
    public List<SavedPost> getSavedPostsForUser(@NotEmpty(message = "User uuid must not be empty!") String userUuid) {
        return savedPostRepository.findByUserUuid(userUuid);
    }

    @Transactional
    public SavedPost registerSavedPost(String userUuid, String postUuid) {
        List<SavedPost> alreadySavedPosts = getSavedPostsForUser(userUuid);

        boolean isAlreadySaved = isAlreadySaved(userUuid, alreadySavedPosts);
        if (!isAlreadySaved) {
            SavedPost savedPost = new SavedPost(userUuid, postUuid);
            return savedPostRepository.save(savedPost);
        } else {
            return null;
        }
    }

    @Transactional
    public void deleteByUuid(String savedPostUuid) {
        savedPostRepository.deleteById(savedPostUuid);

    }

    @Transactional
    public void deleteAllByUserUuid(String userUuid) {
        savedPostRepository.deleteAllByUserUuid(userUuid);
    }

    @Async("threadPoolTaskExecutor")
    @EventListener
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void handlePostDeletedEvent(PostDeletedEvent event) {
        savedPostRepository.deleteAllByPostUuid(event.getPostUuid());
    }

    @Async("threadPoolTaskExecutor")
    @EventListener
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void handleUserDeletedEvent(UserDeletedEvent event) {
        savedPostRepository.deleteAllByUserUuid(event.getUserUuid());
    }

    public boolean isAlreadySaved(String userUuid, List<SavedPost> alreadySavedPosts) {
        return alreadySavedPosts.stream().anyMatch(savedPost -> savedPost.getUserUuid().equals(userUuid));
    }
}
