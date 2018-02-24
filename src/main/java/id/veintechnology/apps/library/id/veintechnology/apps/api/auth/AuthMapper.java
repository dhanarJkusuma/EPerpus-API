package id.veintechnology.apps.library.id.veintechnology.apps.api.auth;

import id.veintechnology.apps.library.id.veintechnology.apps.api.auth.dto.RegisterPayload;
import id.veintechnology.apps.library.id.veintechnology.apps.dao.AuthenticationUser;
import id.veintechnology.apps.library.id.veintechnology.apps.dao.Member;

public class AuthMapper {
    public static AuthenticationUser mapToUser(RegisterPayload payload){
        AuthenticationUser authenticationUser = new AuthenticationUser();
        authenticationUser.setUsername(payload.getUsername());
        authenticationUser.setPassword(payload.getPassword());
        authenticationUser.setStatus(AuthenticationUser.Status.USER);

        Member member = new Member();
        member.setName(payload.getName());
        member.setGender(payload.getGender());
        member.setPhone(payload.getPhone());
        member.setAddress(payload.getAddress());
        member.setEmail(payload.getEmail());
        member.setCompany(payload.getCompany());
        member.setEmployeeNo(payload.getEmployeeNo());
        member.setWorkUnit(payload.getWorkUnit());

        authenticationUser.setMember(member);
        return authenticationUser;
    }

    public static Member mapToMember(RegisterPayload payload){
        AuthenticationUser authenticationUser = new AuthenticationUser();
        authenticationUser.setUsername(payload.getUsername());
        authenticationUser.setPassword(payload.getPassword());
        authenticationUser.setStatus(AuthenticationUser.Status.USER);

        Member member = new Member();
        member.setName(payload.getName());
        member.setGender(payload.getGender());
        member.setPhone(payload.getPhone());
        member.setAddress(payload.getAddress());
        member.setEmail(payload.getEmail());
        member.setCompany(payload.getCompany());
        member.setEmployeeNo(payload.getEmployeeNo());
        member.setWorkUnit(payload.getWorkUnit());
        member.setAuthUser(authenticationUser);
        return member;
    }
}
