package api.indy.kebab.auth;

import java.lang.annotation.*;

/**
 * An annotation to indicate that a method requires authentication.
 * Methods annotated with {@link AuthRequired} will be intercepted by the {@link AuthInterceptor}
 * to ensure that the user is authenticated before accessing the method.
 *
 * @see AuthInterceptor
 */
@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface AuthRequired {
    Permission requiredPermission() default Permission.NONE;
}
