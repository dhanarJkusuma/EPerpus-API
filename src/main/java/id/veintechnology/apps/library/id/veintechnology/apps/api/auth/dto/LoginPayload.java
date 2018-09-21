package id.veintechnology.apps.library.id.veintechnology.apps.api.auth.dto;

import javax.validation.constraints.NotNull;

public class LoginPayload {

    @NotNull(message = "Username cannot be null.")
    private String username;

    @NotNull(message = "Password cannot be null.")
    private String password;

    public LoginPayload() {
    }

    public LoginPayload(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
