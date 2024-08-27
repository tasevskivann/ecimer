package mk.ukim.finki.ecimer.web;

import mk.ukim.finki.ecimer.common.annotations.JsonArg;
import mk.ukim.finki.ecimer.core.UserService;
import mk.ukim.finki.ecimer.security.api.AuthenticationService;
import mk.ukim.finki.ecimer.security.api.JwtTokenResponse;
import mk.ukim.finki.ecimer.web.requestbody.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

@RestController
@RequestMapping(value = "/api/public")
@Validated
public class PublicController {


    private final AuthenticationService authenticationService;
    private final UserService userService;

    public PublicController(AuthenticationService authenticationService, UserService userService) {
        this.authenticationService = authenticationService;
        this.userService = userService;
    }

    @PostMapping("/login")
    public JwtTokenResponse login(@JsonArg @NotEmpty(message = "Email must not be empty") String email,
                                  @JsonArg @NotEmpty(message = "Password must not be empty") String password) {
        return authenticationService.login(email, password);
    }

    @PostMapping("/register")
    public void register(@Valid @RequestBody User user) {
        userService.registerUser(user.getEmail(), user.getPassword(), user.getFirstName(), user.getLastName(), user.getPhoneNumber());
    }
}
