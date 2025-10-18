package api.indy.kebab.controller;

import api.indy.kebab.auth.AuthRequired;
import api.indy.kebab.exceptions.EntityNotFoundException;
import api.indy.kebab.model.MenuItem;
import api.indy.kebab.model.request.CreateMenuItemRequest;
import api.indy.kebab.model.response.ErrorResponse;
import api.indy.kebab.service.MenuService;
import api.indy.kebab.validation.ValidationGroups;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
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

@RestController
@RequestMapping("/menu")
public class MenuController {
    private final MenuService _menuService;

    @Autowired
    public MenuController(MenuService menuService) {
        this._menuService = menuService;
    }

    @AuthRequired
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
            return new ResponseEntity<>(new ErrorResponse("Failed to upload icon: %s".formatted(e.getMessage())), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch(EntityNotFoundException e) {
            return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.NOT_FOUND);
        } catch(IllegalArgumentException e) {
            return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getMenuItem(@PathVariable long id) {
        MenuItem menuItem = this._menuService.getMenuItem(id);

        if(menuItem == null)
            return new ResponseEntity<>(new ErrorResponse("No menu entry found with the provided ID"), HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(menuItem, HttpStatus.OK);
    }

    @GetMapping("/{id}/image")
    public ResponseEntity<Object> getMenuItemImage(@PathVariable long id) {
        try {
            File imageFile = this._menuService.getMenuItemImage(id);
            if(imageFile == null || !imageFile.exists())
                return new ResponseEntity<>(new ErrorResponse("Couldn't find image for menu entry with the provided ID"), HttpStatus.NOT_FOUND);

            Path path = imageFile.toPath();
            byte[] data = Files.readAllBytes(path);
            ByteArrayResource resource = new ByteArrayResource(data);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.valueOf(Files.probeContentType(path)));
            headers.setContentLength(data.length);

            return new ResponseEntity<>(resource, headers, HttpStatus.OK);
        } catch(IOException e) {
            return new ResponseEntity<>(new ErrorResponse("Failed to retrieve image: %s".formatted(e.getMessage())), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch(EntityNotFoundException e) {
            return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    @AuthRequired
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

    @AuthRequired
    @DeleteMapping("/{id}/delete")
    public ResponseEntity<Object> deleteMenuItem(@PathVariable long id) {
        try {
            this._menuService.deleteMenuItem(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch(EntityNotFoundException e) {
            return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/list")
    public ResponseEntity<Object> listMenuItems() {
        return new ResponseEntity<>(this._menuService.listMenuItems(), HttpStatus.OK);
    }
}
