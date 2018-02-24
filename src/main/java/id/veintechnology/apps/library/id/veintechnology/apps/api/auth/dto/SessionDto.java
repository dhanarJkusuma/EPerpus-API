package id.veintechnology.apps.library.id.veintechnology.apps.api.auth.dto;

public class SessionDto {
    private String token;

    public SessionDto(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
