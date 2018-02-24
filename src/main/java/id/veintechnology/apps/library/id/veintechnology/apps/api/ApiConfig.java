package id.veintechnology.apps.library.id.veintechnology.apps.api;

import id.veintechnology.apps.library.id.veintechnology.apps.api.spring.MemberArgumentResolver;
import id.veintechnology.apps.library.id.veintechnology.apps.dao.Member;
import id.veintechnology.apps.library.id.veintechnology.apps.security.User;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.annotation.Nullable;

import java.util.List;

@Configuration
@ComponentScan
public class ApiConfig extends WebMvcConfigurerAdapter {
    @Bean
    @Scope("request")
    @CredentialToken
    public @Nullable
    String getCredentialToken() {
        SecurityContext ctx = SecurityContextHolder.getContext();
        Authentication auth = ctx.getAuthentication();

        return (String) auth.getCredentials();
    }

    @Bean
    @Scope("request")
    @AuthenticatedMember
    public @Nullable
    Member getUserPrincipal(){
        SecurityContext ctx = SecurityContextHolder.getContext();
        Authentication authentication = ctx.getAuthentication();
        User user = (User) authentication.getPrincipal();
        return user.getMember();
    }

    @Autowired
    ObjectFactory<MemberArgumentResolver> memberArgumentResolverObjectFactory;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(memberArgumentResolverObjectFactory.getObject());
    }
}
