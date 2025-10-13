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


@Service
public class CategoryService {
    private final CategoryRepository _categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this._categoryRepository = categoryRepository;
    }

    private String uploadIcon(MultipartFile file) throws IOException {
        String fileName = "%s.%s".formatted(UUID.randomUUID(), Util.getFileExtension(file.getOriginalFilename()));
        String iconUrl = "uploads/categories/%s".formatted(fileName);

        Path baseDir = Paths.get(System.getProperty("user.dir"));
        Path uploadPath = baseDir.resolve(iconUrl);
        Files.createDirectories(uploadPath.getParent());

        file.transferTo(uploadPath.toFile());

        return iconUrl;
    }

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

    public Category createCategory(String name, MultipartFile icon, String description) throws IOException {
        if(name == null || icon == null || icon.isEmpty())
            throw new IllegalArgumentException("Name and icon cannot be null");

        String iconUrl = this.uploadIcon(icon);

        Category category = new Category(name, iconUrl, description);
        return this._categoryRepository.save(category);
    }

    public Category getCategory(long id) {
        return this._categoryRepository.getCategoryById(id);
    }

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

    public void deleteCategory(long id) {
        Category category = this._categoryRepository.getCategoryById(id);

        this.deleteIcon(category.getIconUrl());
        this._categoryRepository.deleteById(id);
    }

    public List<Category> listCategories() {
        return this._categoryRepository.findAll();
    }
}
