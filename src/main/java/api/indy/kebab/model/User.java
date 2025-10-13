package api.indy.kebab.model;

import jakarta.persistence.*;

/**
 * Entity representing a user in the application.
 * Mapped to the {@code users} table in the database.
 */
@Entity
@Access(AccessType.PROPERTY)
@Table(name = "users")
public class User {
    private long _userId;
    private String _username;
    private String _email;
    private String _firstName;
    private String _middleName;
    private String _lastName;
    private String _passwordHash;
    private String _dateOfBirth;

    public User() {}

    public User(String username, String email, String firstName, String middleName, String lastName, String passwordHash, String dateOfBirth) {
        this._username = username;
        this._email = email;
        this._firstName = firstName;
        this._middleName = middleName;
        this._lastName = lastName;
        this._passwordHash = passwordHash;
        this._dateOfBirth = dateOfBirth;
    }

    /**
     * Gets the user ID.
     *
     * @return User ID.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userId", nullable = false)
    public long getUserId() {
        return this._userId;
    }

    /**
     * Sets the user ID.
     *
     * @param userId User ID.
     */
    public void setUserId(long userId) {
        this._userId = userId;
    }

    /**
     * Gets the username of the user.
     *
     * @return Username.
     */
    @Column(name = "username", nullable = false, unique = true, length = 20)
    public String getUsername() {
        return this._username;
    }

    /**
     * Sets the username of the user.
     *
     * @param username Username.
     */
    public void setUsername(String username) {
        this._username = username;
    }

    /**
     * Gets the email address of the user.
     *
     * @return Email address.
     */
    @Column(name = "email", nullable = false, unique = true, length = 100)
    public String getEmail() {
        return this._email;
    }

    /**
     * Sets the email address of the user.
     *
     * @param email Email address.
     */
    public void setEmail(String email) {
        this._email = email;
    }

    /**
     * Gets the first name of the user.
     *
     * @return First name.
     */
    @Column(name = "firstName", nullable = false, length = 50)
    public String getFirstName() {
        return this._firstName;
    }

    /**
     * Sets the first name of the user.
     *
     * @param firstName First name.
     */
    public void setFirstName(String firstName) {
        this._firstName = firstName;
    }

    /**
     * Gets the middle name of the user.
     *
     * @return Middle name.
     */
    @Column(name = "middleName", nullable = true, length = 50)
    public String getMiddleName() {
        return this._middleName;
    }

    /**
     * Sets the middle name of the user.
     *
     * @param middleName Middle name.
     */
    public void setMiddleName(String middleName) {
        this._middleName = middleName;
    }

    /**
     * Gets the last name of the user.
     *
     * @return Last name.
     */
    @Column(name = "lastName", nullable = false, length = 50)
    public String getLastName() {
        return this._lastName;
    }

    /**
     * Sets the last name of the user.
     *
     * @param lastName Last name.
     */
    public void setLastName(String lastName) {
        this._lastName = lastName;
    }

    /**
     * Gets the hashed password of the user.
     *
     * @return Hashed password.
     */
    @Column(name = "passwordHash", nullable = false, length = 200)
    public String getPasswordHash() {
        return this._passwordHash;
    }

    /**
     * Sets the hashed password of the user.
     *
     * @param passwordHash Hashed password.
     */
    public void setPasswordHash(String passwordHash) {
        this._passwordHash = passwordHash;
    }

    /**
     * Gets the date of birth of the user.
     *
     * @return Date of birth in the format YYYY-MM-DD.
     */
    @Column(name = "dateOfBirth", nullable = false, length = 10)
    public String getDateOfBirth() {
        return this._dateOfBirth;
    }

    /**
     * Sets the date of birth of the user.
     *
     * @param dateOfBirth Date of birth in the format YYYY-MM-DD.
     */
    public void setDateOfBirth(String dateOfBirth) {
        this._dateOfBirth = dateOfBirth;
    }
}
