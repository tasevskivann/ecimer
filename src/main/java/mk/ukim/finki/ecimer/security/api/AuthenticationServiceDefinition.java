package mk.ukim.finki.ecimer.security.api;

public interface AuthenticationServiceDefinition {

    JwtTokenResponse login(String username, String password);
}
