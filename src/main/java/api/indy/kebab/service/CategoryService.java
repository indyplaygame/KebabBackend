package api.indy.kebab.service;

import api.indy.kebab.model.Category;
import api.indy.kebab.repository.CategoryRepository;
import api.indy.kebab.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

/**
 * Service class for managing {@link Category} entities.
 * Provides methods for creating, retrieving, updating, and deleting categories.
 *
 * @see CategoryRepository
 * @see Category
 */
@Service
public class CategoryService {
    private final CategoryRepository _categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this._categoryRepository = categoryRepository;
    }

    /**
     * Uploads an icon file and returns its URL.
     *
     * @param file The {@link MultipartFile} representing the icon to upload.
     * @return The URL of the uploaded icon.
     * @throws IOException If an I/O error occurs during file upload.
     */
    private String uploadIcon(MultipartFile file) throws IOException {
        String fileName = "%s.%s".formatted(UUID.randomUUID(), Util.getFileExtension(file.getOriginalFilename()));
        String iconUrl = "uploads/categories/%s".formatted(fileName);

        Path baseDir = Paths.get(System.getProperty("user.dir"));
        Path uploadPath = baseDir.resolve(iconUrl);
        Files.createDirectories(uploadPath.getParent());

        file.transferTo(uploadPath.toFile());

        return iconUrl;
    }

    /**
     * Deletes an icon file based on its URL.
     *
     * @param iconUrl The URL of the icon to delete.
     * @return True if the icon was successfully deleted, false otherwise.
     */
    private boolean deleteIcon(String iconUrl) {
        if(iconUrl == null || iconUrl.isEmpty()) return false;

        Path baseDir = Paths.get(System.getProperty("user.dir"));
        Path iconPath = baseDir.resolve(iconUrl);
        File iconFile = iconPath.toFile();

        try {
            return iconFile.delete();
        } catch (Exception e) {
            return false;
        }
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

        String iconUrl = this.uploadIcon(icon);

        Category category = new Category(name, iconUrl, description);
        return this._categoryRepository.save(category);
    }

    /**
     * Retrieves a Category entity by its unique identifier.
     *
     * @param id The unique identifier of the category.
     * @return The {@link Category} entity with the specified ID, or null if not found.
     */
    public Category getCategory(long id) {
        return this._categoryRepository.getCategoryById(id);
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
        Category category = this._categoryRepository.getCategoryById(id);

        if(name != null) category.setName(name);
        if(description != null) category.setDescription(description);
        if(icon != null && !icon.isEmpty()) {
            this.deleteIcon(category.getIconUrl());
            category.setIconUrl(this.uploadIcon(icon));
        }

        return this._categoryRepository.save(category);
    }

    /**
     * Deletes a {@link Category} entity by its unique identifier.
     *
     * @param id The unique identifier of the category to delete.
     */
    public void deleteCategory(long id) {
        Category category = this._categoryRepository.getCategoryById(id);

        this.deleteIcon(category.getIconUrl());
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
