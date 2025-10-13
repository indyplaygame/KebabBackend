package api.indy.kebab.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Configuration class for development profile.
 * This class disables CSRF protection and allows all HTTP requests
 * without authentication, making it suitable for development purposes.
 */
@Profile("dev")
@Configuration
public class DevelopmentConfig {

    /**
     * Configures the security filter chain for the development environment.
     *
     * <p>Disables CSRF protection and permits all incoming HTTP requests
     * without requiring authentication.</p>
     *
     * @param http the {@link HttpSecurity} object used to configure web-based security.
     * @return the configured {@link SecurityFilterChain}.
     *
     * @throws Exception if an error occurs while configuring security.
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable).authorizeHttpRequests(auth -> auth.anyRequest().permitAll());
        return http.build();
    }
}
