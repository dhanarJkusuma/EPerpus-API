package id.veintechnology.apps.library.id.veintechnology.apps.service.auth;

import id.veintechnology.apps.library.id.veintechnology.apps.dao.AuthenticationToken;
import id.veintechnology.apps.library.id.veintechnology.apps.dao.AuthenticationUser;
import id.veintechnology.apps.library.id.veintechnology.apps.dao.Member;
import id.veintechnology.apps.library.id.veintechnology.apps.repository.MemberRepository;
import id.veintechnology.apps.library.id.veintechnology.apps.repository.UserRepository;
import id.veintechnology.apps.library.id.veintechnology.apps.service.member.GeneratorMemberIdSequenceService;
import id.veintechnology.apps.library.id.veintechnology.apps.service.token.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class DbAuthService implements AuthService{

    private UserRepository userRepository;
    private MemberRepository memberRepository;
    private TokenService tokenService;
    private GeneratorMemberIdSequenceService memberIdSequenceService;
    private final PasswordEncoder encoder = new BCryptPasswordEncoder();

    @Autowired
    public DbAuthService(UserRepository userRepository, MemberRepository memberRepository, TokenService tokenService, GeneratorMemberIdSequenceService memberIdSequenceService) {
        this.userRepository = userRepository;
        this.memberRepository = memberRepository;
        this.tokenService = tokenService;
        this.memberIdSequenceService = memberIdSequenceService;
    }

    @Override
    public Session validateLogin(String username, String password) {
        AuthenticationUser user = userRepository.findOneByUsername(username);
        if(user == null){
            throw new BadCredentialsException("Invalid username password");
        }
        boolean isValid =  encoder.matches(password, user.getPassword());
        if(!isValid){
            throw new BadCredentialsException("Invalid username password");
        }

        String token = tokenService.generateNewTokenSession(user);
        return new Session(token);
    }

    @Override
    public Session registerNewMember(AuthenticationUser authenticationUser) {
        String passwordHashed = encoder.encode(authenticationUser.getPassword());
        authenticationUser.setPassword(passwordHashed);
        authenticationUser.getMember().setPublicId(memberIdSequenceService.nextIdSequence());
        authenticationUser = userRepository.save(authenticationUser);
        String token = tokenService.generateNewTokenSession(authenticationUser);
        return new Session(token);
    }

    @Override
    public Session registerNewMember(Member member) {
        String passwordHashed = encoder.encode(member.getAuthUser().getPassword());
        member.getAuthUser().setPassword(passwordHashed);
        member.setPublicId(memberIdSequenceService.nextIdSequence());
        member = memberRepository.save(member);
        String token = tokenService.generateNewTokenSession(member.getAuthUser());
        return new Session(token);
    }

    @Override
    public void logout(String token) {
        tokenService.removeToken(token);
    }

}
