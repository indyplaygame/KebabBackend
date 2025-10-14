package api.indy.kebab.model;

import jakarta.persistence.*;

/**
 * Entity representing a category in the application.
 * Mapped to the {@code categories} table in the database.
 */
@Entity
@Access(AccessType.PROPERTY)
@Table(name = "categories")
public class Category {
    private long _categoryId;
    private String _name;
    private String _iconUrl;
    private String _description;

    protected Category() {}

    public Category(String name, String iconUrl, String description) {
        this._name = name;
        this._iconUrl = iconUrl;
        this._description = description;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "categoryId", nullable = false)
    public long getCategoryId() { return this._categoryId; }
    protected void setCategoryId(long categoryId) { this._categoryId = categoryId; }

    @Column(name = "name", nullable = false, length = 30)
    public String getName() { return this._name; }
    public void setName(String name) { this._name = name; }

    @Column(name = "iconUrl", nullable = false)
    public String getIconUrl() { return this._iconUrl;}
    public void setIconUrl(String iconUrl) { this._iconUrl = iconUrl; }

    @Column(name = "description", nullable = true, length = 1000)
    public String getDescription() { return this._description; }
    public void setDescription(String description) { this._description = description; }
}
