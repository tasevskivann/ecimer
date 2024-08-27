package mk.ukim.finki.ecimer.web;

import mk.ukim.finki.ecimer.core.User;
import mk.ukim.finki.ecimer.core.VisitedPostService;
import mk.ukim.finki.ecimer.web.responsebody.VisitedPost;
import mk.ukim.finki.ecimer.web.responsebody.VisitedPostMapper;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/visited-posts")
public class VisitedPostController {

    private final VisitedPostService visitedPostService;
    private final VisitedPostMapper visitedPostMapper;

    public VisitedPostController(VisitedPostService visitedPostService) {
        this.visitedPostService = visitedPostService;
        this.visitedPostMapper = VisitedPostMapper.INSTANCE;
    }

    @GetMapping("/{visitedPostUuid}")
    @PreAuthorize("@securityService.isOwnerOfTheVisitedPost(authentication, #visitedPostUuid)")
    public VisitedPost getByUuid(@PathVariable String visitedPostUuid, Authentication authentication) {
        return visitedPostMapper.map(visitedPostService.getByUuid(visitedPostUuid));
    }

    @GetMapping
    public Page<VisitedPost> getVisitedPosts(
            @RequestParam(name = "pageNumber", required = true) int pageNumber,
            @RequestParam(name = "pageSize", required = true) int pageSize,
            Authentication authentication) {
        User principal = getPrincipal(authentication);
        return visitedPostService.getVisitedPostsByUserUuid(pageNumber, pageSize, principal.getUuid()).map(visitedPostMapper::map);
    }

    @DeleteMapping("/{visitedPostUuid}")
    @PreAuthorize("@securityService.isOwnerOfTheVisitedPost(authentication, #visitedPostUuid)")
    public void deleteByUuid(@PathVariable String visitedPostUuid) {
        visitedPostService.deleteByUuid(visitedPostUuid);
    }

    @DeleteMapping
    public void deleteAll(Authentication authentication) {
        User principal = getPrincipal(authentication);
        visitedPostService.deleteAllByUserUuid(principal.getUuid());
    }

    private User getPrincipal(Authentication authentication){
        return (User) authentication.getPrincipal();
    }
}
