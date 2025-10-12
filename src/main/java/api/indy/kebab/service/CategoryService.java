package api.indy.kebab.service;

import api.indy.kebab.model.Category;
import api.indy.kebab.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CategoryService {

    private CategoryRepository _categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this._categoryRepository = categoryRepository;
    }

    public Category createCategory(Category category) {
        return _categoryRepository.save(category);
    }

    public Category getCategory(long id) {
        return _categoryRepository.getCategoryById(id);
    }

    public Category updateCategory(long id,String name,String iconUrl,String description) {
        Category category = _categoryRepository.getCategoryById(id);
        if (name != null) {
            category.setName(name);
        }
        if (iconUrl != null) {
            category.setIconUrl(iconUrl);
        }
        if (description != null) {
            category.setDescription(description);
        }
        return _categoryRepository.save(category);

    }

    public void deleteCategory(long id) {
        _categoryRepository.deleteById(id);
    }

    public List<Category> listCategories() {
        return _categoryRepository.findAll();
    }

}
