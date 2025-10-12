package api.indy.kebab.model;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private long _userId;

    @Column(name = "username", nullable = false, unique = true, length = 20)
    private String _username;

    @Column(name = "email", nullable = false, unique = true, length = 100)
    private String _email;

    @Column(name = "first_name", nullable = false, length = 50)
    private String _firstName;

    @Column(name = "middle_name", nullable = true, length = 50)
    private String _middleName;

    @Column(name = "last_name", nullable = false, length = 50)
    private String _lastName;

    @Column(name = "passwordHash", nullable = false, length = 100)
    private String _passwordHash;

    @Column(name = "date_of_birth", nullable = false, length = 10)
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


    public long getUserId() {
        return this._userId;
    }

    public void setUserId(long userId) {
        this._userId = userId;
    }

    public String getUsername() {
        return this._username;
    }

    public void setUsername(String username) {
        this._username = username;
    }

    public String getEmail() {
        return this._email;
    }

    public void setEmail(String email) {
        this._email = email;
    }

    public String getFirstName() {
        return this._firstName;
    }

    public void setFirstName(String firstName) {
        this._firstName = firstName;
    }

    public String getMiddleName() {
        return this._middleName;
    }

    public void setMiddleName(String middleName) {
        this._middleName = middleName;
    }

    public String getLastName() {
        return this._lastName;
    }

    public void setLastName(String lastName) {
        this._lastName = lastName;
    }

    public String getPasswordHash() {
        return this._passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this._passwordHash = passwordHash;
    }

    public String getDateOfBirth() {
        return this._dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this._dateOfBirth = dateOfBirth;
    }
}
