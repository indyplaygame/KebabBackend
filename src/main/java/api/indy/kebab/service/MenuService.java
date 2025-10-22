package api.indy.kebab.service;

import api.indy.kebab.exceptions.EntityNotFoundException;
import api.indy.kebab.model.Category;
import api.indy.kebab.model.MenuItem;
import api.indy.kebab.repository.CategoryRepository;
import api.indy.kebab.repository.MenuRepository;
import api.indy.kebab.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * Service class for managing {@link MenuItem} entities.
 * Provides methods for creating, retrieving, updating, and deleting categories.
 *
 * @see MenuRepository
 * @see MenuItem
 */
@Service
public class MenuService {
    private static final String IMAGES_PATH = "uploads/menu/%s";

    private final MenuRepository _menuRepository;
    private final CategoryService _categoryService;

    @Autowired
    public MenuService(MenuRepository menuRepository, CategoryService categoryService) {
        this._menuRepository = menuRepository;
        this._categoryService = categoryService;
    }

    /**
     * Creates a new menu item.
     *
     * @param name the name of the menu item.
     * @param description the description of the menu item.
     * @param image the image file representing the menu item.
     * @param categoryId the ID of the category to which the menu item belongs.
     * @param available whether the menu item is available.
     * @param price the price of the menu item.
     * @param deliveryFee the delivery fee for the menu item.
     * @return the created {@link MenuItem} entity.
     *
     * @throws IOException if an error occurs while uploading the image.
     * @throws IllegalArgumentException if required fields are null or invalid.
     * @throws EntityNotFoundException if the specified category does not exist.
     */
    public MenuItem createMenuItem(
        String name, String description, MultipartFile image, Long categoryId, Boolean available, Double price, Double deliveryFee
    ) throws IOException {
        if(name == null || image == null || image.isEmpty() || price == null || deliveryFee == null)
            throw new IllegalArgumentException("Name, price, delivery fee and image cannot be null");

        if(price < 0 || deliveryFee < 0)
            throw new IllegalArgumentException("Price and delivery fee cannot be negative");

        String imageUrl = Util.uploadFile(image, IMAGES_PATH);

        Category category = null;
        if(categoryId != null) category = this._categoryService.getCategory(categoryId);

        if(categoryId != null && category == null)
            throw new EntityNotFoundException(Category.class, categoryId);

        MenuItem menuItem = new MenuItem(name, description, imageUrl, category, available, price, deliveryFee);
        return this._menuRepository.save(menuItem);
    }

    /**
     * Retrieves a menu item by its unique identifier.
     *
     * @param id the unique identifier of the menu item.
     * @return the {@link MenuItem} with the specified ID, or {@code null} if not found.
     */
    public MenuItem getMenuItem(long id) {
        return this._menuRepository.findByMenuItemId(id);
    }

    /**
     * Retrieves the image file of a menu item by its unique identifier.
     *
     * @param id the unique identifier of the menu item.
     * @return the image file, or {@code null} if the menu item does not exist.
     */
    public File getMenuItemImage(long id) {
        MenuItem menuItem = this._menuRepository.findByMenuItemId(id);
        if(menuItem == null) return null;

        return Util.retrieveFile(menuItem.getImageUrl());
    }

    /**
     * Updates an existing menu item.
     *
     * @param id the ID of the menu item to update.
     * @param name the new name of the menu item.
     * @param description the new description of the menu item.
     * @param image the new image file for the menu item.
     * @param categoryId the new category ID for the menu item.
     * @param available the new availability status of the menu item.
     * @param price the new price of the menu item.
     * @param deliveryFee the new delivery fee for the menu item.
     * @return the updated {@link MenuItem} entity.
     *
     * @throws IOException if an error occurs while uploading the new image.
     * @throws EntityNotFoundException if the menu item or specified category does not exist.
     */
    public MenuItem updateMenuItem(
        long id, String name, String description, MultipartFile image, Long categoryId, Boolean available, Double price, Double deliveryFee
    ) throws IOException {
        MenuItem menuItem = this._menuRepository.findByMenuItemId(id);
        if(menuItem == null) throw new EntityNotFoundException(MenuItem.class, id);

        if(name != null) menuItem.setName(name);
        if(description != null) menuItem.setDescription(description);
        if(available != null) menuItem.setAvailable(available);
        if(price != null && price >= 0) menuItem.setPrice(price);
        if(deliveryFee != null && deliveryFee >= 0) menuItem.setDeliveryFee(deliveryFee);
        if(image != null && !image.isEmpty()) {
            Util.deleteFile(menuItem.getImageUrl());
            menuItem.setImageUrl(Util.uploadFile(image, IMAGES_PATH));
        }
        if(categoryId != null) {
            Category category = this._categoryService.getCategory(categoryId);
            if(category == null) throw new EntityNotFoundException(Category.class, categoryId);

            menuItem.setCategory(category);
        }

        return this._menuRepository.save(menuItem);
    }

    /**
     * Deletes a menu item by its unique identifier.
     *
     * @param id the unique identifier of the menu item to delete.
     * @throws EntityNotFoundException if the menu item does not exist.
     */
    public void deleteMenuItem(long id) {
        MenuItem menuItem = this._menuRepository.findByMenuItemId(id);

        if(menuItem == null) throw new EntityNotFoundException(MenuItem.class, id);

        Util.deleteFile(menuItem.getImageUrl());
        this._menuRepository.delete(menuItem);
    }

    /**
     * Retrieves a list of all {@link MenuItem} entities.
     *
     * @param pageable the {@link Pageable} object containing pagination information.
     * @return a {@link Page} of {@link MenuItem} entities.
     */
    public Page<MenuItem> listMenuItems(Pageable pageable) {
        return this._menuRepository.findAll(pageable);
    }
}
