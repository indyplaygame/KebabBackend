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

/**
 * Controller class for handling authentication-related endpoints.
 * Provides endpoints for user registration, login, and logout.
 *
 * @see AuthService
 * @see User
 */
@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService _authService;

    @Autowired
    public AuthController(AuthService authService) {
        this._authService = authService;
    }

    /**
     * Handles user registration requests.
     *
     * <p>Registers a new user with the provided details. Returns a success message
     * if the registration is successful, or an error response if the user already exists
     * or if there is an illegal argument.</p>
     *
     * @param body the {@link RegisterRequest} containing user registration details.
     * @param session the {@link HttpSession} associated with the request.
     * @return a {@link ResponseEntity} containing a success or error response.
     */
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

    /**
     * Handles user login requests.
     *
     * <p>Authenticates a user with the provided login credentials. Returns a success message
     * if the login is successful, or an error response if the credentials are invalid.</p>
     *
     * @param body the {@link LoginRequest} containing login credentials.
     * @param session the {@link HttpSession} associated with the request.
     * @return a {@link ResponseEntity} containing a success or error response.
     */
    @PostMapping("/login")
    public ResponseEntity<Object> login(@Valid @RequestBody LoginRequest body, HttpSession session) {
        try {
            User user = this._authService.loginWithUsernameOrEmail(session, body.login(), body.password());

            return new ResponseEntity<>(new MessageResponse("Logged in successfully"), HttpStatus.OK);
        } catch(InvalidLoginCredentialsException e) {
            return new ResponseEntity<>(new ErrorResponse("Invalid login credentials"), HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     * Handles user logout requests.
     *
     * <p>Logs out the currently authenticated user. Returns a success message
     * upon successful logout.</p>
     *
     * @param session the {@link HttpSession} associated with the request.
     * @return a {@link ResponseEntity} containing a success response.
     */
    @PostMapping("/logout")
    public ResponseEntity<Object> logout(HttpSession session) {
        this._authService.logout(session);
        return new ResponseEntity<>(new MessageResponse("Logged out successfully"), HttpStatus.OK);
    }
}
