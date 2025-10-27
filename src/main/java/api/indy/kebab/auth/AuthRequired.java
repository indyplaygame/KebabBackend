package api.indy.kebab.auth;

import java.lang.annotation.*;

/**
 * An annotation to indicate that a method requires authentication.
 * Methods annotated with {@link AuthRequired} will be intercepted by the {@link AuthInterceptor}
 * to ensure that the user is authenticated and has the required permissions before accessing the method.
 *
 * @see AuthInterceptor
 * @see Permission
 */
@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface AuthRequired {

    /**
     * The permission required to access the annotated method.
     * Defaults to {@link Permission#NONE}, indicating that no special permissions are required.
     *
     * @return The required permission
     */
    Permission requiredPermission() default Permission.NONE;
}
