package id.veintechnology.apps.library.id.veintechnology.apps.api.auth;

import id.veintechnology.apps.library.id.veintechnology.apps.api.CredentialToken;
import id.veintechnology.apps.library.id.veintechnology.apps.api.auth.dto.LoginPayload;
import id.veintechnology.apps.library.id.veintechnology.apps.api.auth.dto.RegisterPayload;
import id.veintechnology.apps.library.id.veintechnology.apps.api.auth.dto.SessionDto;
import id.veintechnology.apps.library.id.veintechnology.apps.dao.AuthenticationUser;
import id.veintechnology.apps.library.id.veintechnology.apps.dao.Member;
import id.veintechnology.apps.library.id.veintechnology.apps.error_handler.response.ErrorResponseRequest;
import id.veintechnology.apps.library.id.veintechnology.apps.security.User;
import id.veintechnology.apps.library.id.veintechnology.apps.service.auth.AuthService;
import id.veintechnology.apps.library.id.veintechnology.apps.service.auth.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthApi {

    private final AuthService authService;

    @Autowired
    public AuthApi(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping(path = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public SessionDto login(@RequestBody @Valid LoginPayload payload){
        Session session = authService.validateLogin(payload.getUsername(), payload.getPassword());
        return new SessionDto(session.getToken());
    }

    @PostMapping(path = "/register", produces = MediaType.APPLICATION_JSON_VALUE)
    public SessionDto register(@Valid @RequestBody  RegisterPayload payload){
        Member member = AuthMapper.mapToMember(payload);
        Session session = authService.registerNewMember(member);
        return new SessionDto(session.getToken());
    }

    @PostMapping(path = "/logout", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity logout(@AuthenticationPrincipal User user, @CredentialToken String token){
        authService.logout(token);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Anda berhasil logout.");
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/check-token", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity checkToken(){
        return ResponseEntity.ok().build();
    }

    /**
     * Admin Login
     * @param payload
     * @return
     */

    @PostMapping(path = "/admin/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public SessionDto adminLogin(@RequestBody @Valid LoginPayload payload){
        Session session = authService.validateAdminLogin(payload.getUsername(), payload.getPassword());
        return new SessionDto(session.getToken());
    }

    @GetMapping(path = "/admin/check-token", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity checkTokenAdmin(@AuthenticationPrincipal User user){
        if(!user.getStatus().equals(AuthenticationUser.Status.ADMIN)){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }
}
