package api.indy.kebab.controller;

import api.indy.kebab.exceptions.InvalidLoginCredentialsException;
import api.indy.kebab.exceptions.UserExistsException;
import api.indy.kebab.model.User;
import api.indy.kebab.model.request.LoginRequest;
import api.indy.kebab.model.request.RegisterRequest;
import api.indy.kebab.model.response.ErrorResponse;
import api.indy.kebab.model.response.MessageResponse;
import api.indy.kebab.service.AuthService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService _authService;

    @Autowired
    public AuthController(AuthService authService) {
        this._authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<Object> register(@Valid @RequestBody RegisterRequest body, HttpSession session) {
        try {
            User user = this._authService.registerUser(session,
                body.username(),
                body.email(),
                body.password(),
                body.firstName(),
                body.middleName(),
                body.lastName(),
                body.dateOfBirth()
            );

            return new ResponseEntity<>(new MessageResponse("Account created successfully"), HttpStatus.CREATED);
        } catch(IllegalArgumentException e) {
            return new ResponseEntity<>(new ErrorResponse("Illegal argument: %s".formatted(e.getMessage())), HttpStatus.BAD_REQUEST);
        } catch(UserExistsException e) {
            return new ResponseEntity<>(new ErrorResponse("Username or email already in use"), HttpStatus.CONFLICT);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@Valid @RequestBody LoginRequest body, HttpSession session) {
        try {
            User user = this._authService.loginWithUsernameOrEmail(session, body.login(), body.password());

            return new ResponseEntity<>(new MessageResponse("Logged in successfully"), HttpStatus.OK);
        } catch(InvalidLoginCredentialsException e) {
            return new ResponseEntity<>(new ErrorResponse("Invalid login credentials"), HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<Object> logout(HttpSession session) {
        this._authService.logout(session);
        return new ResponseEntity<>(new MessageResponse("Logged out successfully"), HttpStatus.OK);
    }
}
