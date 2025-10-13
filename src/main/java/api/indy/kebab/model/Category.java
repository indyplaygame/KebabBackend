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

    public Category() {}

    public Category(String name, String iconUrl, String description) {
        this._name = name;
        this._iconUrl = iconUrl;
        this._description = description;
    }

    /**
     * Gets the category ID.
     *
     * @return Category ID.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "categoryId", nullable = false)
    public long getCategoryId() {
        return this._categoryId;
    }

    /**
     * Sets the category ID.
     *
     * @param categoryId Category ID.
     */
    public void setCategoryId(long categoryId) {
        this._categoryId = categoryId;
    }

    /**
     * Gets the name of the category.
     *
     * @return Category name.
     */
    @Column(name = "name", nullable = false, length = 30)
    public String getName() {
        return this._name;
    }

    /**
     * Sets the name of the category.
     *
     * @param name Category name.
     */
    public void setName(String name) {
        this._name = name;
    }

    /**
     * Gets the icon URL of the category.
     *
     * @return Icon URL.
     */
    @Column(name = "iconUrl", nullable = false)
    public String getIconUrl() {
        return this._iconUrl;
    }

    /**
     * Sets the icon URL of the category.
     *
     * @param iconUrl Icon URL.
     */
    public void setIconUrl(String iconUrl) {
        this._iconUrl = iconUrl;
    }

    /**
     * Gets the description of the category.
     *
     * @return Category description.
     */
    @Column(name = "description", nullable = true, length = 1000)
    public String getDescription() {
        return this._description;
    }

    /**
     * Sets the description of the category.
     *
     * @param description Category description.
     */
    public void setDescription(String description) {
        this._description = description;
    }
}
