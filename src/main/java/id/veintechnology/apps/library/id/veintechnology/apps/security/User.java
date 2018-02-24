package id.veintechnology.apps.library.id.veintechnology.apps.security;

import id.veintechnology.apps.library.id.veintechnology.apps.dao.AuthenticationUser;
import id.veintechnology.apps.library.id.veintechnology.apps.dao.Member;

import javax.annotation.Nullable;

public class User {
    private long userId;
    private String username;
    private AuthenticationUser.Status status;
    private Member member;

    public User() {
    }

    private User(long userId, String username, AuthenticationUser.Status status, Member member) {
        this.userId = userId;
        this.username = username;
        this.status = status;
        this.member = member;
    }

    public static User generateFromAuthenticationUser(AuthenticationUser authenticationUser){
        return new User(
                authenticationUser.getId(),
                authenticationUser.getUsername(),
                authenticationUser.getStatus(),
                authenticationUser.getMember()
        );
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public AuthenticationUser.Status getStatus() {
        return status;
    }

    public void setStatus(AuthenticationUser.Status status) {
        this.status = status;
    }

    @Nullable
    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }
}
