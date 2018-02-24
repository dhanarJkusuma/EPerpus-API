package id.veintechnology.apps.library.id.veintechnology.apps.service.token;

import id.veintechnology.apps.library.id.veintechnology.apps.dao.AuthenticationToken;
import id.veintechnology.apps.library.id.veintechnology.apps.dao.AuthenticationUser;
import id.veintechnology.apps.library.id.veintechnology.apps.repository.TokenRepository;
import id.veintechnology.apps.library.id.veintechnology.apps.repository.UserRepository;
import id.veintechnology.apps.library.id.veintechnology.apps.service.token.TokenService;
import org.hashids.Hashids;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Calendar;
import java.util.Date;

@Service
public class DbTokenService implements TokenService {

    private final TokenRepository tokenRepository;
    private final Hashids hashids;


    private final SecureRandom rng = new SecureRandom();

    @Value("${auth.token.expiredInDays}")
    private int expiredTokenDays;

    @Autowired
    public DbTokenService(TokenRepository tokenRepository, Hashids hashids) {
        this.tokenRepository = tokenRepository;
        this.hashids = hashids;
    }

    @Override
    public AuthenticationUser validateToken(String token) {
        AuthenticationToken authenticationToken = tokenRepository.findOneByToken(token);
        if(authenticationToken == null){
            return null;
        }
        return authenticationToken.getAuthUser();
    }

    @Override
    public AuthenticationToken generateNewToken(AuthenticationUser user) {
        Date now = new Date();
        AuthenticationToken authenticationToken = new AuthenticationToken();
        authenticationToken.setAuthUser(user);
        authenticationToken.setLastAccessOn(now);
        authenticationToken.setCreatedOn(now);
        authenticationToken.setExpiredAt(getExpiredToken(now));
        authenticationToken.setToken(generateToken(user.getId()));

        return tokenRepository.save(authenticationToken);
    }

    @Override
    public String generateNewTokenSession(AuthenticationUser user) {
        Date now = new Date();
        AuthenticationToken authenticationToken = new AuthenticationToken();
        authenticationToken.setAuthUser(user);
        authenticationToken.setLastAccessOn(now);
        authenticationToken.setCreatedOn(now);
        authenticationToken.setExpiredAt(getExpiredToken(now));
        String token = generateToken(user.getId());
        authenticationToken.setToken(token);
        tokenRepository.save(authenticationToken);
        return token;
    }

    @Override
    public void removeToken(String token) {
        AuthenticationToken validToken = tokenRepository.findOneByToken(token);
        tokenRepository.delete(validToken);
    }

    private Date getExpiredToken(Date currentDate){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        calendar.add(Calendar.DAY_OF_YEAR, expiredTokenDays);
        Date expiredDate = calendar.getTime();
        return expiredDate;
    }

    private String generateToken(long userId){
        long timestamp = System.currentTimeMillis();
        return hashids.encode(userId, timestamp,
                rng.nextInt(Integer.MAX_VALUE), rng.nextInt(Integer.MAX_VALUE),
                rng.nextInt(Integer.MAX_VALUE), rng.nextInt(Integer.MAX_VALUE));
    }
}
