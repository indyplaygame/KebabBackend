package api.indy.kebab.controller;

import api.indy.kebab.auth.AuthRequired;
import api.indy.kebab.auth.Permission;
import api.indy.kebab.decorators.pagination.Paginated;
import api.indy.kebab.exceptions.EntityNotFoundException;
import api.indy.kebab.model.Category;
import api.indy.kebab.model.MenuItem;
import api.indy.kebab.model.request.CreateCategoryRequest;
import api.indy.kebab.model.request.CreateMenuItemRequest;
import api.indy.kebab.model.response.ErrorResponse;
import api.indy.kebab.model.response.NotFoundResponse;
import api.indy.kebab.model.response.PageResponse;
import api.indy.kebab.service.CategoryService;
import api.indy.kebab.service.MenuService;
import api.indy.kebab.util.Util;
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
 * Controller for managing menu.
 * Provides endpoints for creating, retrieving, updating, deleting, and listing menu entries.
 *
 * @see MenuService
 * @see MenuItem
 */
@RestController
@RequestMapping("/menu")
public class MenuController {
    private final MenuService _menuService;

    @Autowired
    public MenuController(MenuService menuService) {
        this._menuService = menuService;
    }

    /**
     * Handles requests to create a new menu entry.
     * Requires the {@link Permission#MENU_CREATE} permission to access.
     *
     * @param body the {@link CreateMenuItemRequest} object containing new menu entry data.
     * @return a {@link ResponseEntity} containing the created menu entry or an error.
     */
    @AuthRequired(requiredPermission = Permission.MENU_CREATE)
    @PostMapping("/create")
    public ResponseEntity<Object> createMenuItem(@Validated(ValidationGroups.OnCreate.class) @ModelAttribute CreateMenuItemRequest body) {
        try {
            MenuItem menuItem = this._menuService.createMenuItem(
                body.name(),
                body.description(),
                body.image(),
                body.categoryId(),
                body.available(),
                body.price(),
                body.deliveryFee()
            );

            return new ResponseEntity<>(menuItem, HttpStatus.CREATED);
        } catch(IOException e) {
            return new ResponseEntity<>(new ErrorResponse("Failed to upload image: %s".formatted(e.getMessage())), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch(EntityNotFoundException e) {
            return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.NOT_FOUND);
        } catch(IllegalArgumentException e) {
            return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Handles requests to retrieve a menu entry by its ID.
     *
     * @param id the menu entry identifier.
     * @return a {@link ResponseEntity} containing the menu entry or an error.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Object> getMenuItem(@PathVariable long id) {
        MenuItem menuItem = this._menuService.getMenuItem(id);

        if(menuItem == null)
            return new ResponseEntity<>(new NotFoundResponse(MenuItem.class, id), HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(menuItem, HttpStatus.OK);
    }

    /**
     * Handles requests to retrieve a menu entry's image by its ID.
     *
     * @param id the menu entry identifier.
     * @return a {@link ResponseEntity} containing the image as a {@link ByteArrayResource} or an error.
     */
    @GetMapping("/{id}/image")
    public ResponseEntity<Object> getMenuItemImage(@PathVariable long id) {
        try {
            File imageFile = this._menuService.getMenuItemImage(id);
            if(imageFile == null || !imageFile.exists())
                return new ResponseEntity<>(new ErrorResponse("Couldn't find image for menu entry with the provided ID"), HttpStatus.NOT_FOUND);

            return Util.createResourceResponse(imageFile);
        } catch(IOException e) {
            return new ResponseEntity<>(new ErrorResponse("Failed to retrieve image: %s".formatted(e.getMessage())), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch(EntityNotFoundException e) {
            return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Handles requests to update an existing menu entry.
     * Requires the {@link Permission#MENU_UPDATE} permission to access.
     *
     * @param id the identifier of the menu entry to update.
     * @param body the {@link CreateMenuItemRequest} object containing new menu entry data.
     * @return a {@link ResponseEntity} containing the updated menu entry or an error.
     */
    @AuthRequired(requiredPermission = Permission.MENU_UPDATE)
    @PutMapping("/{id}/update")
    public ResponseEntity<Object> updateMenuItem(@PathVariable long id, @Valid @ModelAttribute CreateMenuItemRequest body){
        try {
            MenuItem updatedMenuItem = this._menuService.updateMenuItem(id,
                body.name(),
                body.description(),
                body.image(),
                body.categoryId(),
                body.available(),
                body.price(),
                body.deliveryFee()
            );

            return new ResponseEntity<>(updatedMenuItem, HttpStatus.OK);
        } catch(IOException e) {
            return new ResponseEntity<>(new ErrorResponse("Failed to upload image: %s".formatted(e.getMessage())), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch(EntityNotFoundException e) {
            return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.NOT_FOUND);
        } catch(IllegalArgumentException e) {
            return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Handles requests to delete a menu entry by its ID.
     * Requires the {@link Permission#MENU_DELETE} permission to access.
     *
     * @param id the identifier of the menu entry to delete.
     * @return a {@link ResponseEntity} with status code.
     */
    @AuthRequired(requiredPermission = Permission.MENU_DELETE)
    @DeleteMapping("/{id}/delete")
    public ResponseEntity<Object> deleteMenuItem(@PathVariable long id) {
        try {
            this._menuService.deleteMenuItem(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch(EntityNotFoundException e) {
            return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Handles requests to list all menu entries.
     *
     * @param pageable the {@link Pageable} object containing pagination information.
     * @return a {@link ResponseEntity} containing a paginated list of menu entries.
     */
    @Paginated(defaultSize = 20)
    @GetMapping("/list")
    public ResponseEntity<Object> listMenuItems(Pageable pageable) {
        return new ResponseEntity<>(PageResponse.from(this._menuService.listMenuItems(pageable)), HttpStatus.OK);
    }
}
