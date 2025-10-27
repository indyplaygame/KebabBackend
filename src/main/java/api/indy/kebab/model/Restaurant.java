package api.indy.kebab.model;

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
    private String _imageUrl;
    private String _phoneNumber;
    private String _website;
    private Location _location;

    protected Restaurant() {}

    public Restaurant(
        String name,
        String description,
        String imageUrl,
        String phoneNumber,
        String website,
        Location location
    ) {
        this._name = name;
        this._description = description;
        this._imageUrl = imageUrl;
        this._phoneNumber = phoneNumber;
        this._website = website;
        this._location = location;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "restaurantId", nullable = false)
    public long getRestaurantId() { return this._restaurantId; }
    protected void setRestaurantId(long restaurantId) { this._restaurantId = restaurantId; }

    @Column(name = "name", nullable = false)
    public String getName() { return this._name; }
    public void setName(String name) { this._name = name; }

    @Column(name = "description", nullable = true)
    public String getDescription() { return this._description; }
    public void setDescription(String description) { this._description = description; }

    @Column(name = "imageUrl", nullable = false)
    public String getImageUrl() { return this._imageUrl; }
    public void setImageUrl(String logoUrl) { this._imageUrl = logoUrl; }

    @Column(name = "phoneNumber", nullable = true)
    public String getPhoneNumber() { return this._phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this._phoneNumber = phoneNumber; }

    @Column(name = "website", nullable = true)
    public String getWebsite() { return this._website; }
    public void setWebsite(String website) { this._website = website; }

    @Embedded
    public Location getLocation() { return this._location; }
    public void setLocation(Location location) { this._location = location; }

}
