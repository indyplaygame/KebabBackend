package api.indy.kebab.service;

import api.indy.kebab.exceptions.EntityNotFoundException;
import api.indy.kebab.model.Category;
import api.indy.kebab.repository.CategoryRepository;
import api.indy.kebab.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Service class for managing {@link Category} entities.
 * Provides methods for creating, retrieving, updating, and deleting categories.
 *
 * @see CategoryRepository
 * @see Category
 */
@Service
public class CategoryService {
    private static final String ICONS_PATH = "uploads/categories/%s";

    private final CategoryRepository _categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this._categoryRepository = categoryRepository;
    }

    /**
     * Creates a new {@link Category} entity and saves it to the repository.
     *
     * @param name The name of the category.
     * @param icon The {@link MultipartFile} representing the category's icon.
     * @param description A brief description of the category.
     * @return The created {@link Category} entity.
     * @throws IOException If an I/O error occurs during icon upload.
     * @throws IllegalArgumentException If the name or icon is null.
     */
    public Category createCategory(String name, MultipartFile icon, String description) throws IOException {
        if(name == null || icon == null || icon.isEmpty())
            throw new IllegalArgumentException("Name and icon cannot be null");

        String iconUrl = Util.uploadFile(icon, ICONS_PATH);

        Category category = new Category(name, iconUrl, description);
        return this._categoryRepository.save(category);
    }

    /**
     * Retrieves a {@link Category} entity by its unique identifier.
     *
     * @param id The unique identifier of the category.
     * @return The {@link Category} entity with the specified ID, or null if not found.
     */
    public Category getCategory(long id) {
        return this._categoryRepository.findByCategoryId(id);
    }

    /**
     * Retrieves a category's icon file by the category's unique identifier.
     *
     * @param id The unique identifier of the category.
     * @return The {@link File} representing the category's icon, or null if not found.
     */
    public File getCategoryIcon(long id) {
        Category category = this._categoryRepository.findByCategoryId(id);
        if(category == null) throw new EntityNotFoundException(Category.class, id);

        return Util.retrieveFile(category.getIconUrl());
    }

    /**
     * Updates an existing {@link Category} entity with new values.
     *
     * @param id The unique identifier of the category to update.
     * @param name The new name of the category (optional).
     * @param icon The new {@link MultipartFile} representing the category's icon (optional).
     * @param description The new description of the category (optional).
     * @return The updated {@link Category} entity.
     * @throws IOException If an I/O error occurs during icon upload.
     */
    public Category updateCategory(long id, String name, MultipartFile icon, String description) throws IOException {
        Category category = this._categoryRepository.findByCategoryId(id);
        if(category == null) throw new EntityNotFoundException(Category.class, id);

        if(name != null) category.setName(name);
        if(description != null) category.setDescription(description);
        if(icon != null && !icon.isEmpty()) {
            Util.deleteFile(category.getIconUrl());
            category.setIconUrl(Util.uploadFile(icon, ICONS_PATH));
        }

        return this._categoryRepository.save(category);
    }

    /**
     * Deletes a {@link Category} entity by its unique identifier.
     *
     * @param id The unique identifier of the category to delete.
     */
    public void deleteCategory(long id) {
        Category category = this._categoryRepository.findByCategoryId(id);

        if(category == null) throw new EntityNotFoundException(Category.class, id);

        Util.deleteFile(category.getIconUrl());
        this._categoryRepository.deleteById(id);
    }

    /**
     * Retrieves a list of all {@link Category} entities.
     *
     * @return A list of all categories.
     */
    public List<Category> listCategories() {
        return this._categoryRepository.findAll();
    }
}
