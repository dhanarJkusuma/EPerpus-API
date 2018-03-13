package id.veintechnology.apps.library.id.veintechnology.apps.security;

import id.veintechnology.apps.library.id.veintechnology.apps.dao.AuthenticationUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity(debug = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final AuthenticationFilter filter;
    private final TokenAuthenticationProvider provider;

    @Autowired
    public SecurityConfig(AuthenticationFilter filter, TokenAuthenticationProvider provider) {
        this.filter = filter;
        this.provider = provider;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        String ADMIN_ROLE = AuthenticationUser.Status.ADMIN.name();
        http
                .authorizeRequests()
                    .antMatchers("/storage/image/**").permitAll()
                .and()
                .antMatcher("/api/v1/**")
                .csrf().disable()
                .addFilterBefore(new CorsConfiguration(), ChannelProcessingFilter.class)
                .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                    .antMatchers(HttpMethod.DELETE,"/api/v1/category/**").hasRole(ADMIN_ROLE)
                    .antMatchers("/v1/ping").permitAll()
                    .antMatchers("/api/v1/auth/admin/login").permitAll()
                    .antMatchers("/api/v1/auth/login").permitAll()
                    .antMatchers("/api/v1/auth/register").permitAll()
                    .anyRequest().authenticated();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(provider);
    }
}
