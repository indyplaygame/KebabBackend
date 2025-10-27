package api.indy.kebab.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

/**
 * Entity representing a restaurant in the application.
 * Mapped to the {@code restaurants} table in the database.
 */
@Entity
@Access(AccessType.PROPERTY)
@Table(name="restaurants")
public class Restaurant {
    private long _restaurantId;
    private String _name;
    private String _description;
    private String _logoUrl;
    private String _phoneNumber;
    private String _website;
    private Location _location;

    protected Restaurant() {}

    public Restaurant(
            String name,
            String description,
            String logoUrl,
            String phoneNumber,
            String website,
            Location location
    ) {
        this._name = name;
        this._description = description;
        this._logoUrl = logoUrl;
        this._phoneNumber = phoneNumber;
        this._website = website;
        this._location = location;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="restaurantId", nullable=false)
    public long getRestaurantId() { return _restaurantId; }
    protected void setRestaurantId(long restaurantId) { _restaurantId = restaurantId; }

    @Column(name="name", nullable=false)
    public String getName() { return _name; }
    public void setName(String name) { _name = name; }

    @Column(name="description", nullable = true)
    public String getDescription() { return _description; }
    public void setDescription(String description) { _description = description; }

    @Column(name="logoUrl", nullable=true)
    public String getLogoUrl() { return _logoUrl; }
    public void setLogoUrl(String logoUrl) { _logoUrl = logoUrl; }

    @Column(name="phoneNumber", nullable = true)
    public String getPhoneNumber() { return _phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { _phoneNumber = phoneNumber; }

    @Column(name="website",nullable = true)
    public String getWebsite() { return _website; }
    public void setWebsite(String website) { _website = website; }

    @Embedded
    public Location getLocation() { return _location; }
    public void setLocation(Location location) { _location = location; }

}
