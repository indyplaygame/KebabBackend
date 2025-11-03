package api.indy.kebab.controller;

import api.indy.kebab.auth.AuthRequired;
import api.indy.kebab.auth.Permission;
import api.indy.kebab.decorators.pagination.Paginated;
import api.indy.kebab.exceptions.EntityNotFoundException;
import api.indy.kebab.model.Location;
import api.indy.kebab.model.Restaurant;
import api.indy.kebab.model.request.CreateLocationRequest;
import api.indy.kebab.model.request.CreateRestaurantRequest;
import api.indy.kebab.model.response.ErrorResponse;
import api.indy.kebab.model.response.NotFoundResponse;
import api.indy.kebab.model.response.PageResponse;
import api.indy.kebab.service.RestaurantService;
import api.indy.kebab.util.Util;
import api.indy.kebab.validation.ValidationGroups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;

/**
 * Controller for managing restaurants.
 * Provides endpoints for creating, retrieving, updating, deleting, and listing restaurants.
 *
 * @see RestaurantService
 * @see Restaurant
 */
@RestController
@RequestMapping("/restaurants")
public class RestaurantController {
    private final RestaurantService _restaurantService;

    @Autowired
    public RestaurantController(RestaurantService restaurantService) {
        this._restaurantService = restaurantService;
    }

    /**
     * Handles requests to create a new restaurant.
     * Requires the {@link Permission#RESTAURANTS_CREATE} permission to access.
     *
     * @param body the {@link CreateRestaurantRequest} object containing new restaurant data.
     * @return a {@link ResponseEntity} containing the created restaurnat or an error.
     */
    @AuthRequired(requiredPermission = Permission.RESTAURANTS_CREATE)
    @PostMapping("/create")
    public ResponseEntity<Object> createRestaurant(
        @Validated(ValidationGroups.OnCreate.class) @ModelAttribute CreateRestaurantRequest body,
        @Validated(ValidationGroups.OnCreate.class) @ModelAttribute CreateLocationRequest locationBody
    ) {
        try {
            Location location = new Location(
                locationBody.latitude(),
                locationBody.longitude(),
                locationBody.country(),
                locationBody.voivodeship(),
                locationBody.postalCode(),
                locationBody.city(),
                locationBody.street(),
                locationBody.buildingNumber()
            );

            Restaurant restaurant = this._restaurantService.createRestaurant(
                body.name(),
                body.description(),
                body.image(),
                body.phoneNumber(),
                body.website(),
                location
            );

            return new ResponseEntity<>(restaurant, HttpStatus.CREATED);
        } catch(IOException e) {
            return new ResponseEntity<>(new ErrorResponse("Failed to upload image: %s".formatted(e.getMessage())), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch(IllegalArgumentException e) {
            return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Handles requests to retrieve a restaurant by its ID.
     *
     * @param id the restaurant identifier.
     * @return a {@link ResponseEntity} containing the restaurant or an error.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Object> getRestaurantById(@PathVariable long id) {
        Restaurant restaurant = this._restaurantService.getRestaurant(id);

        if(restaurant == null)
            return new ResponseEntity<>(new NotFoundResponse(Restaurant.class, id), HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }

    /**
     * Handles requests to retrieve a restaurant's image by its ID.
     *
     * @param id the restaurant identifier.
     * @return a {@link ResponseEntity} containing the icon as a {@link ByteArrayResource} or an error.
     */
    @GetMapping("/{id}/image")
    public ResponseEntity<Object> getRestaurantImage(@PathVariable long id) {
        try {
            File imageFile = this._restaurantService.getRestaurantImage(id);
            if(imageFile == null || !imageFile.exists())
                return new ResponseEntity<>(new NotFoundResponse(Restaurant.class, id), HttpStatus.NOT_FOUND);

            return Util.createResourceResponse(imageFile);
        } catch(IOException e) {
            return new ResponseEntity<>(new ErrorResponse("Failed to retrieve image: %s".formatted(e.getMessage())), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch(EntityNotFoundException e) {
            return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Handles requests to update an existing restaurant.
     * Requires the {@link Permission#RESTAURANTS_UPDATE} permission to access.
     *
     * @param id the identifier of the restaurant to update.
     * @param body the {@link CreateRestaurantRequest} object containing new restaurant data.
     * @return a {@link ResponseEntity} containing the updated restaurant or an error.
     */
    @AuthRequired(requiredPermission = Permission.RESTAURANTS_UPDATE)
    @PutMapping("/{id}/update")
    public ResponseEntity<Object> updateRestaurant(
        @PathVariable long id, @ModelAttribute CreateRestaurantRequest body, @ModelAttribute CreateLocationRequest locationBody
    ) {
        try {
            Restaurant restaurant = this._restaurantService.updateRestaurant(id,
                body.name(),
                body.description(),
                body.image(),
                body.phoneNumber(),
                body.website(),
                locationBody
            );

            return new ResponseEntity<>(restaurant, HttpStatus.OK);
        } catch(IOException e) {
            return new ResponseEntity<>(new ErrorResponse("Failed to upload image: %s".formatted(e.getMessage())), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch(EntityNotFoundException e) {
            return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.NOT_FOUND);
        } catch(IllegalArgumentException e) {
            return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Handles requests to delete a restaurant by its ID.
     * Requires the {@link Permission#RESTAURANTS_DELETE} permission to access.
     *
     * @param id the identifier of the restaurant to delete.
     * @return a {@link ResponseEntity} with status code.
     */
    @AuthRequired(requiredPermission = Permission.RESTAURANTS_DELETE)
    @DeleteMapping("/{id}/delete")
    public ResponseEntity<Object> deleteRestaurant(@PathVariable long id) {
        try {
            this._restaurantService.deleteRestaurant(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch(EntityNotFoundException e) {
            return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Handles requests to list all restaurant.
     *
     * @param pageable the {@link Pageable} object containing pagination information.
     * @return a {@link ResponseEntity} containing the paginated list of restaurants.
     */
    @Paginated(defaultSize = 20)
    @GetMapping("/list")
    public ResponseEntity<Object> getRestaurantsList(Pageable pageable) {
        return new ResponseEntity<>(PageResponse.from(this._restaurantService.listRestaurants(pageable)), HttpStatus.OK);
    }
}
