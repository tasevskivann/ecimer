package mk.ukim.finki.ecimer.security.api;

public class JwtTokenResponse {

    private String token;
    private String userUuid;

    public JwtTokenResponse() {

    }

    public JwtTokenResponse(String token, String userUuid) {
        this.token = token;
        this.userUuid = userUuid;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserUuid() {
        return userUuid;
    }

    public void setUserUuid(String userUuid) {
        this.userUuid = userUuid;
    }
}
