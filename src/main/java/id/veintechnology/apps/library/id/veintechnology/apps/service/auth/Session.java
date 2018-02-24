package id.veintechnology.apps.library.id.veintechnology.apps.service.auth;

public class Session {
    private String token;

    public Session(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
