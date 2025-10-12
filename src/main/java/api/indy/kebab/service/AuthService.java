package api.indy.kebab.service;

import api.indy.kebab.exceptions.InvalidLoginCredentialsException;
import api.indy.kebab.exceptions.UserExistsException;
import api.indy.kebab.model.User;
import api.indy.kebab.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final String ACTIVE_USER_SESSION_KEY = "activeUser";

    private final UserRepository _userRepository;
    private final BCryptPasswordEncoder _encoder;

    @Autowired
    public AuthService(UserRepository userRepository) {
        this._userRepository = userRepository;
        this._encoder = new BCryptPasswordEncoder();
    }

    public String hashPassword(String password) {
        return password != null ? this._encoder.encode(password) : null;
    }

    public boolean verifyPassword(String password, String hash) {
        return this._encoder.matches(password, hash);
    }

    public boolean userWithUsernameOrEmailExists(String username, String email) {
        return this._userRepository.findByUsernameOrEmail(username, email) != null;
    }

    public User registerUser(HttpSession session, String username, String email, String password, String firstName, String middleName, String lastName, String dateOfBirth) {
        if(username == null || email == null || password == null || firstName == null || lastName == null || dateOfBirth == null)
            throw new IllegalArgumentException("Username, email, password, first name, last name, and date of birth cannot be null");

        if(this.userWithUsernameOrEmailExists(username, email))
            throw new UserExistsException();

        String passwordHash = this.hashPassword(password);
        User user = new User(username, email, firstName, middleName, lastName, passwordHash, dateOfBirth);

        this.setActiveUser(session, user);

        return this._userRepository.save(user);
    }

    public User loginWithUsernameOrEmail(HttpSession session, String username, String password) {
        User user = this._userRepository.findByUsernameOrEmail(username, username);

        if(user == null || !this.verifyPassword(password, user.getPasswordHash()))
            throw new InvalidLoginCredentialsException();

        this.setActiveUser(session, user);

        return user;
    }

    public void logout(HttpSession session) {
        if(session != null) session.invalidate();
    }

    public User getActiveUser(HttpSession session) {
        if(session == null) return null;
        return (User) session.getAttribute(this.ACTIVE_USER_SESSION_KEY);
    }

    public void setActiveUser(HttpSession session, User user) {
        if(session != null) session.setAttribute(this.ACTIVE_USER_SESSION_KEY, user);
    }
}
