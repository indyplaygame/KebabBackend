package api.indy.kebab.controller;

import api.indy.kebab.auth.AuthRequired;
import api.indy.kebab.decorators.pagination.Paginated;
import api.indy.kebab.exceptions.EntityNotFoundException;
import api.indy.kebab.model.Category;
import api.indy.kebab.model.request.CreateCategoryRequest;
import api.indy.kebab.model.response.ErrorResponse;
import api.indy.kebab.model.response.NotFoundResponse;
import api.indy.kebab.model.response.PageResponse;
import api.indy.kebab.service.CategoryService;
import api.indy.kebab.validation.ValidationGroups;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Controller for managing categories.
 * Provides endpoints for creating, retrieving, updating, deleting, and listing categories.
 *
 * @see CategoryService
 * @see Category
 */
@RestController
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryService _categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this._categoryService = categoryService;
    }

    /**
     * Handles requests to create a new category.
     *
     * @param body the {@link CreateCategoryRequest} object containing new category data.
     * @return a {@link ResponseEntity} containing the created category or an error.
     */
    @AuthRequired
    @PostMapping("/create")
    public ResponseEntity<Object> createCategory(@Validated(ValidationGroups.OnCreate.class) @ModelAttribute CreateCategoryRequest body) {
        try {
            Category category = this._categoryService.createCategory(
                body.name(),
                body.icon(),
                body.description()
            );

            return new ResponseEntity<>(category, HttpStatus.CREATED);
        } catch(IOException e) {
            return new ResponseEntity<>(new ErrorResponse("Failed to upload icon: %s".formatted(e.getMessage())), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch(IllegalArgumentException e) {
            return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Handles requests to retrieve a category by its ID.
     *
     * @param id the category identifier.
     * @return a {@link ResponseEntity} containing the category or an error.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Object> getCategory(@PathVariable long id) {
        Category category = this._categoryService.getCategory(id);

        if(category == null)
            return new ResponseEntity<>(new NotFoundResponse(Category.class, id), HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(category, HttpStatus.OK);
    }

    /**
     * Handles requests to retrieve a category's icon by its ID.
     *
     * @param id the category identifier.
     * @return a {@link ResponseEntity} containing the icon as a {@link ByteArrayResource} or an error.
     */
    @GetMapping("/{id}/icon")
    public ResponseEntity<Object> getCategoryIcon(@PathVariable long id) {
        try {
            File iconFile = this._categoryService.getCategoryIcon(id);
            if(iconFile == null || !iconFile.exists())
                return new ResponseEntity<>(new ErrorResponse("Couldn't find icon for category with the provided ID"), HttpStatus.NOT_FOUND);

            Path path = iconFile.toPath();
            byte[] data = Files.readAllBytes(path);
            ByteArrayResource resource = new ByteArrayResource(data);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.valueOf(Files.probeContentType(path)));
            headers.setContentLength(data.length);

            return new ResponseEntity<>(resource, headers, HttpStatus.OK);
        } catch(IOException e) {
            return new ResponseEntity<>(new ErrorResponse("Failed to retrieve icon: %s".formatted(e.getMessage())), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch(EntityNotFoundException e) {
            return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Handles requests to update an existing category.
     *
     * @param id the identifier of the category to update.
     * @param body the {@link CreateCategoryRequest} object containing new category data.
     * @return a {@link ResponseEntity} containing the updated category or an error.
     */
    @PutMapping("/{id}/update")
    public ResponseEntity<Object> updateCategory(@PathVariable long id, @Valid @ModelAttribute CreateCategoryRequest body) {
        try {
            Category updatedCategory = this._categoryService.updateCategory(id,
                body.name(),
                body.icon(),
                body.description()
            );

            return new ResponseEntity<>(updatedCategory, HttpStatus.OK);
        } catch(IOException e) {
            return new ResponseEntity<>(new ErrorResponse("Failed to upload icon: %s".formatted(e.getMessage())), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch(EntityNotFoundException e) {
            return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.NOT_FOUND);
        } catch(IllegalArgumentException e) {
            return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Handles requests to delete a category by its ID.
     *
     * @param id the identifier of the category to delete.
     * @return a {@link ResponseEntity} with status code.
     */
    @DeleteMapping("/{id}/delete")
    public ResponseEntity<Object> deleteCategory(@PathVariable long id) {
        try {
            this._categoryService.deleteCategory(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch(EntityNotFoundException e) {
            return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Handles requests to list all categories.
     *
     * @param pageable the {@link Pageable} object containing pagination information.
     * @return a {@link ResponseEntity} containing the paginated list of categories.
     */
    @Paginated(maxSize = 100)
    @GetMapping("/list")
    public ResponseEntity<Object> listCategories(Pageable pageable) {
        return new ResponseEntity<>(PageResponse.from(this._categoryService.listCategories(pageable)), HttpStatus.OK);
    }
}
