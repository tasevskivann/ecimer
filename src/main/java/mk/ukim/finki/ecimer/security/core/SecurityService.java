package mk.ukim.finki.ecimer.security.core;

import mk.ukim.finki.ecimer.core.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SecurityService {

    private final PostService postService;
    private final SavedPostService savedPostService;
    private final VisitedPostService visitedPostService;

    public SecurityService(PostService postService, SavedPostService savedPostService, VisitedPostService visitedPostService) {
        this.visitedPostService = visitedPostService;
        this.postService = postService;
        this.savedPostService = savedPostService;
    }

    @Transactional(readOnly = true)
    public boolean isOwnerOfTheVisitedPost(Authentication authentication, String visitedPostUuid) {
        User principal = getPrincipal(authentication);
        VisitedPost visitedPost = visitedPostService.getByUuid(visitedPostUuid);
        return principal.getUuid().equals(visitedPost.getUserUuid());
    }

    @Transactional(readOnly = true)
    public boolean isOwnerOfThePost(Authentication authentication, String postUuid) {
        User principal = getPrincipal(authentication);
        List<Post> principalPosts = postService.findAllByUserUuid(principal.getUuid());
        return principalIsOwnerOfThePost(principalPosts, postUuid);
    }

    @Transactional(readOnly = true)

    public boolean isOwnerOfThePostOrAdmin(Authentication authentication, String postUuid) {
        return hasRoleAdmin(authentication) || isOwnerOfThePost(authentication, postUuid);
    }

    @Transactional(readOnly = true)
    public boolean isOwnerOfThePostOrTheCommentOrHasRoleAdmin(Authentication authentication, String postUuid, String commentUuid) {
        return isOwnerOfThePostOrTheComment(authentication, postUuid, commentUuid) || hasRoleAdmin(authentication);
    }

    @Transactional(readOnly = true)
    public boolean isOwnerOfThePostOrTheComment(Authentication authentication, String postUuid, String commentUuid) {
        User principal = getPrincipal(authentication);
        Post post = postService.getByUuid(postUuid, false, null);
        Comment comment = post.getCommentByUuid(commentUuid);

        boolean result = false;

        if (comment != null) {
            if (principal.getUuid().equals(post.getUserUuid())) {
                // is owner of the post
                result = true;
            } else if (comment.getUserUuid().equals(principal.getUuid())) {
                // is owner of the comment
                result = true;
            }
        }

        return result;
    }

    @Transactional(readOnly = true)
    public boolean isTheSameUser(Authentication authentication, String userUuid) {
        User principal = getPrincipal(authentication);

        return principal.getUuid().equals(userUuid);
    }

    @Transactional(readOnly = true)
    public boolean hasRoleAdmin(Authentication authentication) {
        User principal = getPrincipal(authentication);
        return principal.getAuthorities().stream().anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals(UserRoles.ADMIN.getAuthority()));
    }

    @Transactional(readOnly = true)
    public boolean isOwnerOfTheSavedPost(Authentication authentication, String savedPostUuid) {
        SavedPost savedPost = savedPostService.getByUuid(savedPostUuid);
        User principal = getPrincipal(authentication);

        return savedPost.getUserUuid().equals(principal.getUuid());
    }

    private boolean principalIsOwnerOfThePost(List<Post> principalPosts, String postUuid) {
        return principalPosts.stream().anyMatch(post -> post.getUuid().equals(postUuid));
    }

    private User getPrincipal(Authentication authentication) {
        return (User) authentication.getPrincipal();
    }
}
