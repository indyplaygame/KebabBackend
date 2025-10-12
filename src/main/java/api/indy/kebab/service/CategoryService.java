package api.indy.kebab.service;

import api.indy.kebab.model.Category;
import api.indy.kebab.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CategoryService {
    private final CategoryRepository _categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this._categoryRepository = categoryRepository;
    }

    public Category createCategory(String name, String iconUrl, String description) {
        if(name == null || iconUrl == null)
            throw new IllegalArgumentException("Name and icon url cannot be null");

        Category category = new Category(name, iconUrl, description);
        return this._categoryRepository.save(category);
    }

    public Category getCategory(long id) {
        return this._categoryRepository.getCategoryById(id);
    }

    public Category updateCategory(long id, String name, String iconUrl, String description) {
        Category category = this._categoryRepository.getCategoryById(id);

        if(name != null) category.setName(name);
        if(iconUrl != null) category.setIconUrl(iconUrl);
        if(description != null) category.setDescription(description);

        return this._categoryRepository.save(category);
    }

    public void deleteCategory(long id) {
        this._categoryRepository.deleteById(id);
    }

    public List<Category> listCategories() {
        return this._categoryRepository.findAll();
    }
}
