package id.veintechnology.apps.library.id.veintechnology.apps.service.token;

import id.veintechnology.apps.library.id.veintechnology.apps.dao.AuthenticationToken;
import id.veintechnology.apps.library.id.veintechnology.apps.dao.AuthenticationUser;

public interface TokenService {
    AuthenticationUser validateToken(String token);
    AuthenticationToken generateNewToken(AuthenticationUser user);
    String generateNewTokenSession(AuthenticationUser user);
    void removeToken(String token);
}
