package api.indy.kebab.model;

import api.indy.kebab.auth.Permission;
import api.indy.kebab.persistence.converter.PermissionsConverter;
import jakarta.persistence.*;

import java.util.Set;

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
    private Set<Permission> _permissions;

    protected User() {}

    public User(String username, String email, String firstName, String middleName, String lastName, String passwordHash, String dateOfBirth) {
        this._username = username;
        this._email = email;
        this._firstName = firstName;
        this._middleName = middleName;
        this._lastName = lastName;
        this._passwordHash = passwordHash;
        this._dateOfBirth = dateOfBirth;
        this._permissions = Set.of();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userId", nullable = false)
    public long getUserId() { return this._userId; }
    protected void setUserId(long userId) { this._userId = userId; }

    @Column(name = "username", nullable = false, unique = true, length = 20)
    public String getUsername() { return this._username; }
    public void setUsername(String username) { this._username = username; }

    @Column(name = "email", nullable = false, unique = true, length = 100)
    public String getEmail() { return this._email; }
    public void setEmail(String email) { this._email = email; }

    @Column(name = "firstName", nullable = false, length = 50)
    public String getFirstName() { return this._firstName; }
    public void setFirstName(String firstName) { this._firstName = firstName; }

    @Column(name = "middleName", nullable = true, length = 50)
    public String getMiddleName() { return this._middleName; }
    public void setMiddleName(String middleName) { this._middleName = middleName; }

    @Column(name = "lastName", nullable = false, length = 50)
    public String getLastName() { return this._lastName; }
    public void setLastName(String lastName) { this._lastName = lastName; }

    @Column(name = "passwordHash", nullable = false, length = 200)
    public String getPasswordHash() { return this._passwordHash; }
    public void setPasswordHash(String passwordHash) { this._passwordHash = passwordHash; }

    @Column(name = "dateOfBirth", nullable = false, length = 10)
    public String getDateOfBirth() { return this._dateOfBirth; }
    public void setDateOfBirth(String dateOfBirth) { this._dateOfBirth = dateOfBirth; }

    @Convert(converter = PermissionsConverter.class)
    @Column(name = "permissions", nullable = false, length = 1000)
    public Set<Permission> getPermissions() { return this._permissions; }
    public void setPermissions(Set<Permission> permissions) { this._permissions = permissions; }

    @Transient
    public boolean hasPermission(Permission permission) {
        return this._permissions != null && this._permissions.contains(permission);
    }

    @Transient
    public void grantPermissions(Set<Permission> permissions) {
        this._permissions.addAll(permissions);
    }

    @Transient
    public void revokePermissions(Set<Permission> permissions) {
        this._permissions.removeAll(permissions);
    }
}
