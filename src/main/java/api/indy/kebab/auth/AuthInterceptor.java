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

@Component
public class AuthInterceptor implements HandlerInterceptor {
    private final AuthService _authService;

    @Autowired
    public AuthInterceptor(AuthService authService) {
        this._authService = authService;
    }

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
