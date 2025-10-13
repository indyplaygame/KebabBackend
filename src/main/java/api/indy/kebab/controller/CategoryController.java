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
    public ResponseEntity<Object> getCategory(@PathVariable String id) {
        if(!id.matches("\\d+"))
            return new ResponseEntity<>(new ErrorResponse("Invalid category id format"), HttpStatus.BAD_REQUEST);

        long categoryId = Long.parseLong(id);
        Category category = this._categoryService.getCategory(categoryId);

        if(category == null)
            return new ResponseEntity<>(new ErrorResponse("Category not found"), HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(category, HttpStatus.OK);
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<Object> updateCategory(@PathVariable String id, @Valid @ModelAttribute CreateCategoryRequest body) {
        if(!id.matches("\\d+"))
            return new ResponseEntity<>(new ErrorResponse("Invalid category id format"), HttpStatus.BAD_REQUEST);

        long categoryId = Long.parseLong(id);
        Category category = this._categoryService.getCategory(categoryId);

        try {
            Category updatedCategory = this._categoryService.updateCategory(
                    categoryId,
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
    public ResponseEntity<Object> deleteCategory(@PathVariable String id) {
        if(!id.matches("\\d+"))
            return new ResponseEntity<>(new ErrorResponse("Invalid category id format"), HttpStatus.BAD_REQUEST);

        long categoryId = Long.parseLong(id);
        Category category = this._categoryService.getCategory(categoryId);

        if(category == null)
            return new ResponseEntity<>(new ErrorResponse("Category not found"), HttpStatus.NOT_FOUND);

        this._categoryService.deleteCategory(categoryId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/list")
    public ResponseEntity<Object> listCategories() {
        return new ResponseEntity<>(this._categoryService.listCategories(), HttpStatus.OK);
    }
}
