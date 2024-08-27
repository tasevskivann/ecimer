package mk.ukim.finki.ecimer.web;

import mk.ukim.finki.ecimer.common.annotations.JsonArg;
import mk.ukim.finki.ecimer.core.User;
import mk.ukim.finki.ecimer.core.UserService;
import mk.ukim.finki.ecimer.core.query.QueryUserService;
import mk.ukim.finki.ecimer.core.query.filter.UserFilter;
import mk.ukim.finki.ecimer.web.responsebody.UserMapper;
import mk.ukim.finki.ecimer.web.responsebody.UserSearch;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final QueryUserService queryUserService;
    private final UserMapper userMapper;

    public UserController(UserService userService, QueryUserService queryUserService) {
        this.userService = userService;
        this.queryUserService = queryUserService;
        this.userMapper = UserMapper.INSTANCE;
    }

    @PutMapping("/{userUuid}")
    @PreAuthorize("@securityService.isTheSameUser(authentication, #userUuid)")
    public void updateUser(@PathVariable String userUuid, @JsonArg String email, @JsonArg String password,
                           @JsonArg String firstName, @JsonArg String lastName, @JsonArg String phoneNumber, Authentication authentication) {
        userService.updateUser(
                userUuid,
                email,
                password,
                firstName,
                lastName,
                phoneNumber);
    }

    @GetMapping("/my-profile")
    public mk.ukim.finki.ecimer.web.responsebody.User myProfile(Authentication authentication) {
        User principal = getPrincipal(authentication);
        return userMapper.map(userService.getByUuid(principal.getUuid()));
    }

    @GetMapping("/{userUuid}")
    public mk.ukim.finki.ecimer.web.responsebody.User getByUuid(@PathVariable String userUuid) {
        return userMapper.map(userService.getByUuid(userUuid));
    }

    @GetMapping("/search")
    public Page<UserSearch> search(
            @RequestParam(name = "pageNumber", required = true) int pageNumber,
            @RequestParam(name = "pageSize", required = true) int pageSize,
            @RequestParam(name = "email", required = false) String name,
            @RequestParam(name = "firstName", required = false) String firstName,
            @RequestParam(name = "lastName", required = false) String lastName,
            @RequestParam(name = "phoneNumber", required = false) String phoneNumber) {
        UserFilter userFilter = new UserFilter(pageNumber, pageSize, name, firstName, lastName, phoneNumber);
        return queryUserService.search(userFilter).map(userMapper::mapSearch);
    }

    private User getPrincipal(Authentication authentication){
        return (User) authentication.getPrincipal();
    }
}
