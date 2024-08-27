package mk.ukim.finki.ecimer.web;

import mk.ukim.finki.ecimer.common.annotations.JsonArg;
import mk.ukim.finki.ecimer.core.SavedPostService;
import mk.ukim.finki.ecimer.core.User;
import mk.ukim.finki.ecimer.web.responsebody.SavedPost;
import mk.ukim.finki.ecimer.web.responsebody.SavedPostMapper;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/saved-posts")
public class SavedPostController {

    private final SavedPostService savedPostService;
    private final SavedPostMapper savedPostMapper;

    public SavedPostController(SavedPostService savedPostService) {
        this.savedPostService = savedPostService;
        this.savedPostMapper = SavedPostMapper.INSTANCE;
    }

    @GetMapping("/{savedPostUuid}")
    @PreAuthorize("@securityService.isOwnerOfTheSavedPost(authentication, #savedPostUuid)")
    public SavedPost getByUuid(@PathVariable String savedPostUuid, Authentication authentication) {
        return savedPostMapper.map(savedPostService.getByUuid(savedPostUuid));
    }

    @GetMapping
    public Page<SavedPost> getSavedPosts(
            @RequestParam(name = "pageNumber", required = true) int pageNumber,
            @RequestParam(name = "pageSize", required = true) int pageSize,
            Authentication authentication) {
        User principal = getPrincipal(authentication);
        return savedPostService.getSavedPostsForUser(pageNumber, pageSize, principal.getUuid()).map(savedPostMapper::map);
    }

    @PostMapping
    public SavedPost register(@JsonArg String postUuid, Authentication authentication) {
        User principal = getPrincipal(authentication);
        return savedPostMapper.map(savedPostService.registerSavedPost(principal.getUuid(), postUuid));
    }

    @DeleteMapping("/{savedPostUuid}")
    @PreAuthorize("@securityService.isOwnerOfTheSavedPost(authentication, #savedPostUuid)")
    public void deleteSavedPost(@PathVariable String savedPostUuid, Authentication authentication) {
        savedPostService.deleteByUuid(savedPostUuid);
    }

    @DeleteMapping()
    public void deleteSavedPostsForUser(Authentication authentication) {
        User principal = getPrincipal(authentication);
        savedPostService.deleteAllByUserUuid(principal.getUuid());
    }

    private User getPrincipal(Authentication authentication) {
        return (User) authentication.getPrincipal();
    }
}
