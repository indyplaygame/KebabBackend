package api.indy.kebab.service;

import api.indy.kebab.exceptions.EntityNotFoundException;
import api.indy.kebab.model.Category;
import api.indy.kebab.model.MenuItem;
import api.indy.kebab.repository.CategoryRepository;
import api.indy.kebab.repository.MenuRepository;
import api.indy.kebab.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class MenuService {
    private static final String IMAGES_PATH = "uploads/menu/%s";

    private final MenuRepository _menuRepository;
    private final CategoryRepository _categoryRepository;

    @Autowired
    public MenuService(MenuRepository menuRepository, CategoryRepository categoryRepository) {
        this._menuRepository = menuRepository;
        this._categoryRepository = categoryRepository;
    }

    public MenuItem createMenuItem(
        String name, String description, MultipartFile image, long categoryId, boolean available, double price, double deliveryFee
    ) throws IOException {
        if(name == null || description == null || image == null || image.isEmpty())
            throw new IllegalArgumentException("Name, description, and image cannot be null");

        if(price <= 0 || deliveryFee < 0)
            throw new IllegalArgumentException("Price must be greater than 0 and delivery fee cannot be negative");

        String imageUrl = Util.uploadFile(image, IMAGES_PATH);
        Category category = this._categoryRepository.findByCategoryId(categoryId);

        if(categoryId > 0 && category == null)
            throw new IllegalArgumentException("Category with ID %d does not exist".formatted(categoryId));

        MenuItem menuItem = new MenuItem(name, description, imageUrl, category, available, price, deliveryFee);
        return this._menuRepository.save(menuItem);
    }

    public MenuItem getMenuItem(long id) {
        return this._menuRepository.findByMenuItemId(id);
    }

    public File getMenuItemImage(long id) {
        MenuItem menuItem = this._menuRepository.findByMenuItemId(id);
        if(menuItem == null) return null;

        return Util.retrieveFile(menuItem.getImageUrl());
    }

    public MenuItem updateMenuItem(
        long id, String name, String description, MultipartFile image, long categoryId, boolean available, double price, double deliveryFee
    ) throws IOException {
        MenuItem menuItem = this._menuRepository.findByMenuItemId(id);
        if(menuItem == null) throw new EntityNotFoundException(MenuItem.class, id);

        if(name != null) menuItem.setName(name);
        if(description != null) menuItem.setDescription(description);
        if(price > 0) menuItem.setPrice(price);
        if(deliveryFee >= 0) menuItem.setDeliveryFee(deliveryFee);
        if(image != null && !image.isEmpty()) {
            Util.deleteFile(menuItem.getImageUrl());
            menuItem.setImageUrl(Util.uploadFile(image, IMAGES_PATH));
        }
        if(categoryId > 0) {
            Category category = this._categoryRepository.findByCategoryId(categoryId);
            if(category == null) throw new EntityNotFoundException(Category.class, categoryId);

            menuItem.setCategory(category);
        }

        menuItem.setAvailable(available);
        return this._menuRepository.save(menuItem);
    }

    public void deleteMenuItem(long id) {
        MenuItem menuItem = this._menuRepository.findByMenuItemId(id);

        Util.deleteFile(menuItem.getImageUrl());
        this._menuRepository.delete(menuItem);
    }

    public List<MenuItem> listMenuItems() {
        return this._menuRepository.findAll();
    }
}
