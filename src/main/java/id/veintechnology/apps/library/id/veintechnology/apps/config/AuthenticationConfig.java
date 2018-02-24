package id.veintechnology.apps.library.id.veintechnology.apps.config;

import org.hashids.Hashids;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuthenticationConfig {
    @Bean
    public Hashids generateHashIds(
            @Value("${auth.hash.seed}") String seed) {
        return new Hashids(seed, 40);
    }
}
