package api.indy.kebab.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

/**
 * Entity representing a menu item in the application.
 * Mapped to the {@code menu_items} table in the database.
 */
@Entity
@Access(AccessType.PROPERTY)
@Table(name = "menu_items")
@SuppressWarnings("AssociationNotMarkedInspection")
public class MenuItem {
    private long _menuItemId;
    private String _name;
    private String _description;
    private String _imageUrl;
    private Category _category;
    private boolean _available;
    private double _rating;
    private double _price;
    private double _deliveryFee;

    protected MenuItem() {}

    public MenuItem(String name, String description, String imageUrl, Category category, boolean available, double price, double deliveryFee) {
        this._name = name;
        this._description = description;
        this._imageUrl = imageUrl;
        this._category = category;
        this._available = available;
        this._price = price;
        this._deliveryFee = deliveryFee;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "menuItemId", nullable = false)
    public long getMenuItemId() { return this._menuItemId; }
    protected void setMenuItemId(long menuItemId) { this._menuItemId = menuItemId; }

    @Column(name = "name", nullable = false, length = 100)
    public String getName() { return this._name; }
    public void setName(String name) { this._name = name; }

    @Column(name = "description", nullable = true, length = 1000)
    public String getDescription() { return this._description; }
    public void setDescription(String description) { this._description = description; }

    @Column(name = "imageUrl", nullable = false, length = 255)
    public String getImageUrl() { return this._imageUrl; }
    public void setImageUrl(String imageUrl) { this._imageUrl = imageUrl; }

    @JsonIgnore
    @ManyToOne(optional = true)
    @JoinColumn(name = "categoryId", nullable = true)
    public Category getCategory() { return this._category; }
    public void setCategory(Category category) { this._category = category; }

    @Column(name = "available", nullable = false, columnDefinition = "BOOLEAN DEFAULT TRUE")
    public boolean isAvailable() { return this._available; }
    public void setAvailable(boolean available) { this._available = available; }

    @Min(0)
    @Max(5)
    @Column(name = "rating", nullable = false, columnDefinition = "DOUBLE DEFAULT 0.0")
    public double getRating() { return this._rating; }
    public void setRating(double rating) { this._rating = rating; }

    @Column(name = "price", nullable = false)
    public double getPrice() { return this._price; }
    public void setPrice(double price) { this._price = price; }

    @Column(name = "deliveryFee", nullable = false)
    public double getDeliveryFee() { return this._deliveryFee; }
    public void setDeliveryFee(double deliveryFee) { this._deliveryFee = deliveryFee; }

    @Transient
    @JsonProperty("categoryId")
    public long getCategoryId() { return this._category != null ? this._category.getCategoryId() : 0; }
}
