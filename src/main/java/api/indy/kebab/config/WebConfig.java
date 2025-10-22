package api.indy.kebab.config;

import api.indy.kebab.auth.AuthInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuration class for setting up web-specific configurations.
 * Implements the {@link WebMvcConfigurer} interface to customize the Spring MVC configuration.
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
    private final AuthInterceptor _authInterceptor;

    @Autowired
    public WebConfig(AuthInterceptor authInterceptor) {
        this._authInterceptor = authInterceptor;
    }

    /**
     * Adds the {@link AuthInterceptor} to the interceptor registry.
     *
     * <p>This method is called by the Spring framework to register custom interceptors
     * for pre-processing and post-processing of HTTP requests.</p>
     *
     * @param registry the {@link InterceptorRegistry} used to register interceptors.
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(this._authInterceptor);
    }

    /**
     * Configures Cross-Origin Resource Sharing (CORS) settings.
     *
     * <p>This method is called by the Spring framework to define CORS mappings
     * for the application, allowing cross-origin requests from specified origins.</p>
     *
     * @param registry the {@link CorsRegistry} used to configure CORS mappings.
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
            .allowedHeaders("*")
            .allowedOrigins("*", "http://localhost:*", "http://localhost")
            .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS");
    }
}
