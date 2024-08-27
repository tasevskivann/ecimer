package mk.ukim.finki.ecimer.core;

import mk.ukim.finki.ecimer.core.events.EventPublisher;
import mk.ukim.finki.ecimer.core.exceptions.PostNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Service
@Validated
public class PostService {
    private final PostRepository postRepository;
    private final CityService cityService;
    private final EventPublisher eventPublisher;

    public PostService(PostRepository postRepository, CityService cityService, EventPublisher eventPublisher) {
        this.postRepository = postRepository;
        this.cityService = cityService;
        this.eventPublisher = eventPublisher;
    }

    @Transactional(readOnly = true)
    public Post getByUuid(@NotEmpty(message = "Post uuid must not be empty!") String postUuid, boolean calledFromClient,
                          String userUuid) {
        Post post = postRepository.findById(postUuid).orElseThrow(() -> new PostNotFoundException(postUuid));
        if (calledFromClient) {
            eventPublisher.publishPostVisitedEvent(postUuid, userUuid);
        }
        return post;
    }

    @Transactional
    public Post createPost(@NotEmpty(message = "Title must not be empty!") String title,
                           @NotEmpty(message = "Description must not be empty!") String description,
                           @NotEmpty(message = "User uuid must not be empty!") String userUuid,
                           String cityUuid, String municipalityUuid) {
        Post post = new Post(title, description, userUuid);
        if (cityUuid != null) {
            cityService.checkIfCityUuidExists(cityUuid);
            post.setCityUuid(cityUuid);
        }
        if (municipalityUuid != null) {
            cityService.checkIfMunicipalityExistsForCity(cityUuid, municipalityUuid);
            post.setMunicipalityUuid(municipalityUuid);
        }
        return postRepository.save(post);
    }

    @Transactional
    public void updatePost(@NotEmpty(message = "Post uuid must not be empty!") String postUuid, String title,
                           String description, String cityUuid, String municipalityUuid) {
        if (title != null && !title.isEmpty()) {
            changePostTitle(postUuid, title);
        }
        if (description != null && !description.isEmpty()) {
            changePostDescription(postUuid, description);
        }
        if (cityUuid != null && !cityUuid.isEmpty()) {
            setPostCity(postUuid, cityUuid);
            if (municipalityUuid != null && !municipalityUuid.isEmpty()) {
                setPostMunicipality(postUuid, cityUuid, municipalityUuid);
            }
        }

    }

    @Transactional
    public void changePostTitle(@NotEmpty(message = "Post uuid must not be empty!") String postUuid,
                                @NotEmpty(message = "Title must not be empty!") String title) {
        Post post = getByUuid(postUuid, false, null);
        post.setTitle(title);
        postRepository.save(post);
    }

    @Transactional
    public void changePostDescription(@NotEmpty(message = "Post uuid must not be empty!") String postUuid,
                                      @NotEmpty(message = "Description must not be empty!") String description) {
        Post post = getByUuid(postUuid, false, null);
        post.setDescription(description);
        postRepository.save(post);
    }

    @Transactional
    public void setPostCity(@NotEmpty(message = "Post uuid must not be empty!") String postUuid,
                            @NotEmpty(message = "City uuid must not be empty!") String cityUuid) {
        Post post = getByUuid(postUuid, false, null);
        cityService.checkIfCityUuidExists(cityUuid);
        post.setCityUuid(cityUuid);
        post.setMunicipalityUuid(null);
        postRepository.save(post);
    }

    @Transactional
    public void setPostMunicipality(@NotEmpty(message = "Post uuid must not be empty!") String postUuid,
                                    @NotEmpty(message = "City uuid must not be empty!") String cityUuid,
                                    @NotEmpty(message = "Municipality uuid must not be empty!") String municipalityUuid) {
        Post post = getByUuid(postUuid, false, null);
        cityService.checkIfMunicipalityExistsForCity(cityUuid, municipalityUuid);
        post.setMunicipalityUuid(municipalityUuid);
        postRepository.save(post);
    }

    @Transactional
    public void deleteByUuid(@NotEmpty(message = "Post uuid must not be empty!") String postUuid) {
        postRepository.deleteById(postUuid);
        eventPublisher.publishPostDeletedEvent(postUuid);
    }

    @Transactional
    public void addComment(@NotEmpty(message = "Post uuid must not be empty!") String postUuid,
                           @NotEmpty(message = "User uuid must not be empty!") String userUuid,
                           @NotEmpty(message = "Content must not be empty!") String content) {
        Post post = getByUuid(postUuid, false, null);
        Comment comment = new Comment(userUuid, postUuid, content);
        post.addComment(comment);
        postRepository.save(post);
    }

    @Transactional
    public void deleteCommentByUuid(@NotEmpty(message = "Post uuid must not be empty!") String postUuid,
                                    @NotEmpty(message = "Comment uuid must not be empty!") String commentUuid) {
        Post post = getByUuid(postUuid, false, null);
        post.deleteComment(commentUuid);
        postRepository.save(post);
    }

    @Transactional(readOnly = true)
    public List<Post> findAllByUserUuid(@NotEmpty(message = "User uuid must not be empty!") String userUuid) {
        return postRepository.findAllByUserUuid(userUuid);
    }
}
