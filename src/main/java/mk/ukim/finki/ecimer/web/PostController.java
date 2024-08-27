package mk.ukim.finki.ecimer.web;

import mk.ukim.finki.ecimer.common.annotations.JsonArg;
import mk.ukim.finki.ecimer.core.PostService;
import mk.ukim.finki.ecimer.core.User;
import mk.ukim.finki.ecimer.core.query.filter.PostFilter;
import mk.ukim.finki.ecimer.core.query.QueryPostService;
import mk.ukim.finki.ecimer.web.responsebody.Post;
import mk.ukim.finki.ecimer.web.responsebody.PostCreate;
import mk.ukim.finki.ecimer.web.responsebody.PostMapper;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;
    private final QueryPostService queryPostService;
    private final PostMapper postMapper;

    public PostController(PostService postService, QueryPostService queryPostService) {
        this.postService = postService;
        this.queryPostService = queryPostService;
        this.postMapper = PostMapper.INSTANCE;
    }

    @PostMapping
    public PostCreate createPost(@JsonArg String title, @JsonArg String description, @JsonArg String cityUuid,
                                 @JsonArg String municipalityUuid, Authentication authentication) {
        User user = getPrincipal(authentication);
        return postMapper.mapCreate(postService.createPost(title, description, user.getUuid(), cityUuid, municipalityUuid));
    }

    @PutMapping("/{postUuid}")
    @PreAuthorize("@securityService.isOwnerOfThePost(authentication, #postUuid)")
    public void updatePost(@PathVariable String postUuid, @JsonArg String title, @JsonArg String description,
                           @JsonArg String cityUuid, @JsonArg String municipalityUuid) {
        postService.updatePost(postUuid, title, description, cityUuid, municipalityUuid);
    }

    @DeleteMapping("/{postUuid}")
    @PreAuthorize("@securityService.isOwnerOfThePostOrAdmin(authentication, #postUuid)")
    public void deletePost(@PathVariable String postUuid) {
        postService.deleteByUuid(postUuid);
    }

    @PostMapping("/{postUuid}/comments")
    public void addComment(@PathVariable String postUuid, @JsonArg String content, Authentication authentication) {
        User user = getPrincipal(authentication);
        postService.addComment(postUuid,
                user.getUuid(),
                content);
    }

    @DeleteMapping("/{postUuid}/comments/{commentUuid}")
    @PreAuthorize("@securityService.isOwnerOfThePostOrTheCommentOrHasRoleAdmin(authentication, #postUuid, #commentUuid)")
    public void deleteComment(@PathVariable String postUuid, @PathVariable String commentUuid, Authentication authentication) {
        postService.deleteCommentByUuid(postUuid, commentUuid);
    }

    @GetMapping("/search")
    public Page<Post> searchPosts(
            @RequestParam(name = "pageNumber", required = true) int pageNumber,
            @RequestParam(name = "pageSize", required = true) int pageSize,
            @RequestParam(name = "userUuid", required = false) String userUuid,
            @RequestParam(name = "cityUuid", required = false) String cityUuid,
            @RequestParam(name = "municipalityUuid", required = false) String municipalityUuid,
            @RequestParam(name = "title", required = false) String title
    ) {
        PostFilter postFilter = new PostFilter(pageNumber, pageSize, userUuid, cityUuid, municipalityUuid, title);
        return queryPostService.search(postFilter).map(postMapper::map);
    }

    @GetMapping("/{postUuid}")
    public Post getByUuid(@PathVariable String postUuid, Authentication authentication){
        User principal = getPrincipal(authentication);
        return postMapper.map(postService.getByUuid(postUuid, true, principal.getUuid()));
    }

    private User getPrincipal(Authentication authentication){
        return (User) authentication.getPrincipal();
    }
}
