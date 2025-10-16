package api.indy.kebab.auth;

import api.indy.kebab.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;

/**
 * Interceptor to handle authentication for requests.
 * Ensures that endpoints annotated with {@link AuthRequired} are accessed only by authenticated users.
 *
 * @see AuthRequired
 * @see AuthService
 */
@Component
public class AuthInterceptor implements HandlerInterceptor {
    private final AuthService _authService;

    @Autowired
    public AuthInterceptor(AuthService authService) {
        this._authService = authService;
    }

    /**
     * Pre-handle method to check if the request is authorized.
     * Verifies if the handler method or its class is annotated with {@link AuthRequired}.
     * If the session is invalid or the user is not authenticated, responds with HTTP {@code 401 Unauthorized}.
     *
     * @param request  the HTTP request.
     * @param response the HTTP response.
     * @param handler  the handler (controller method) being executed.
     * @return true if the request is authorized, false otherwise.
     * 
     * @throws IOException if an error occurs while sending the error response.
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        if(handler instanceof HandlerMethod method) {
            if(method.getMethodAnnotation(AuthRequired.class) == null && !method.getBeanType().isAnnotationPresent(AuthRequired.class)) return true;

            HttpSession session = request.getSession(false);
            if(session == null || this._authService.getActiveUser(session) == null) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
                return false;
            }
        }

        return true;
    }
}
