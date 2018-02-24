package id.veintechnology.apps.library.id.veintechnology.apps.service.auth;

import id.veintechnology.apps.library.id.veintechnology.apps.dao.AuthenticationUser;
import id.veintechnology.apps.library.id.veintechnology.apps.dao.Member;

public interface AuthService {
    Session validateLogin(String username, String password);
    Session registerNewMember(AuthenticationUser authenticationUser);
    Session registerNewMember(Member member);
    void logout(String token);
}
