package mk.ukim.finki.ecimer.security.api;

import mk.ukim.finki.ecimer.core.User;
import mk.ukim.finki.ecimer.security.core.JwtTokenGenerator;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotEmpty;

@Service
public class AuthenticationService implements AuthenticationServiceDefinition {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenGenerator jwtTokenGenerator;

    public AuthenticationService(AuthenticationManager authenticationManager, JwtTokenGenerator jwtTokenGenerator) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenGenerator = jwtTokenGenerator;
    }

    @Override
    public JwtTokenResponse login(@NotEmpty String username, @NotEmpty String password) {
        try {
            Authentication authenticate = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(username, password));
            User user = (User) authenticate.getPrincipal();
            String token = jwtTokenGenerator.generateAccessToken(user);
            return new JwtTokenResponse(token, user.getUuid());
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException(e.getMessage());
        }
    }
}
