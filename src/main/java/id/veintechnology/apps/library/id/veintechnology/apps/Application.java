package id.veintechnology.apps.library.id.veintechnology.apps;

import id.veintechnology.apps.library.id.veintechnology.apps.api.ApiConfig;
import id.veintechnology.apps.library.id.veintechnology.apps.config.AuthenticationConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Import({
		AuthenticationConfig.class,
		ApiConfig.class
})
@SpringBootApplication
@EnableJpaRepositories
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	static class MainX{
		public static void main(String... x){
			PasswordEncoder encoder = new BCryptPasswordEncoder();
			System.out.print(encoder.encode("tawon123"));
		}
	}
}
