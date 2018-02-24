package id.veintechnology.apps.library.id.veintechnology.apps.security;

import id.veintechnology.apps.library.id.veintechnology.apps.dao.AuthenticationUser;
import id.veintechnology.apps.library.id.veintechnology.apps.service.token.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class TokenAuthenticationProvider implements AuthenticationProvider {

    private final TokenService tokenService;


    @Autowired
    public TokenAuthenticationProvider(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        AuthenticationToken authenticationToken = (AuthenticationToken) authentication;
        AuthenticationUser authenticationUser = tokenService.validateToken(authenticationToken.getToken());
        if(authenticationUser == null){
            throw new BadCredentialsException("Invalid Token");
        }
        User user = User.generateFromAuthenticationUser(authenticationUser);
        return new AuthenticatedUser(user, authenticationToken.getToken());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return AuthenticationToken.class.equals(authentication);
    }
}
