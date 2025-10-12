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
        _authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody RegisterRequest body, HttpSession session) {
        if(body.username() == null || body.email() == null || body.firstName() == null || body.lastName() == null || body.dateOfBirth() == null || body.password() == null)
            return new ResponseEntity<>(new ErrorResponse("Missing required fields"), HttpStatus.BAD_REQUEST);

        if(!body.username().matches("[a-zA-Z0-9_]{3,20}"))
            return new ResponseEntity<>(new ErrorResponse("Invalid username format"), HttpStatus.BAD_REQUEST);

        if(!body.email().matches("^[a-zA-Z0-9._+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]+$"))
            return new ResponseEntity<>(new ErrorResponse("Invalid email format"), HttpStatus.BAD_REQUEST);

        if(!body.firstName().matches("[a-zA-Z]{1,50}"))
            return new ResponseEntity<>(new ErrorResponse("Invalid first name format"), HttpStatus.BAD_REQUEST);

        if(body.middleName() != null && !body.middleName().matches("[a-zA-Z]{0,50}"))
            return new ResponseEntity<>(new ErrorResponse("Invalid middle name format"), HttpStatus.BAD_REQUEST);

        if(!body.lastName().matches("[a-zA-Z]{1,50}"))
            return new ResponseEntity<>(new ErrorResponse("Invalid last name format"), HttpStatus.BAD_REQUEST);

        if(!body.dateOfBirth().matches("\\d{2}/\\d{2}/\\d{4}"))
            return new ResponseEntity<>(new ErrorResponse("Invalid date of birth format"), HttpStatus.BAD_REQUEST);

        if(!body.password().matches("[a-zA-Z0-9!@#$%^&*\\-_]{6,20}"))
            return new ResponseEntity<>(new ErrorResponse("Invalid password format"), HttpStatus.BAD_REQUEST);

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
    public ResponseEntity<Object> login(@RequestBody LoginRequest body, HttpSession session) {
        if(body.login() == null || body.password() == null)
            return new ResponseEntity<>(new ErrorResponse("Missing required fields"), HttpStatus.BAD_REQUEST);

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
