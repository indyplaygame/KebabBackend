package api.indy.kebab.controller;

import api.indy.kebab.model.Category;
import api.indy.kebab.model.request.CreateCategoryRequest;
import api.indy.kebab.model.response.ErrorResponse;
import api.indy.kebab.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryService _categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this._categoryService = categoryService;
    }

    @PostMapping("/create")
    public ResponseEntity<Object> createCategory(@Valid @ModelAttribute CreateCategoryRequest body) {
        try {
            Category category = this._categoryService.createCategory(
                body.name(),
                body.icon(),
                body.description()
            );

            return new ResponseEntity<>(category, HttpStatus.CREATED);
        } catch(IOException e) {
            return new ResponseEntity<>(new ErrorResponse("Failed to upload icon: %s".formatted(e.getMessage())), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getCategory(@PathVariable long id) {
        Category category = this._categoryService.getCategory(id);

        if(category == null)
            return new ResponseEntity<>(new ErrorResponse("Category not found"), HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(category, HttpStatus.OK);
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<Object> updateCategory(@PathVariable long id, @Valid @ModelAttribute CreateCategoryRequest body) {
        Category category = this._categoryService.getCategory(id);

        try {
            Category updatedCategory = this._categoryService.updateCategory(
                    id,
                    body.name(),
                    body.icon(),
                    body.description()
            );

            return new ResponseEntity<>(updatedCategory, HttpStatus.OK);
        } catch(IOException e) {
            return new ResponseEntity<>(new ErrorResponse("Failed to upload icon: %s".formatted(e.getMessage())), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<Object> deleteCategory(@PathVariable long id) {
        Category category = this._categoryService.getCategory(id);

        if(category == null)
            return new ResponseEntity<>(new ErrorResponse("Category not found"), HttpStatus.NOT_FOUND);

        this._categoryService.deleteCategory(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/list")
    public ResponseEntity<Object> listCategories() {
        return new ResponseEntity<>(this._categoryService.listCategories(), HttpStatus.OK);
    }
}
