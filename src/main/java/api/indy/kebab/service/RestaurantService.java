package api.indy.kebab.service;


import api.indy.kebab.exceptions.EntityNotFoundException;
import api.indy.kebab.model.Location;
import api.indy.kebab.model.MenuItem;
import api.indy.kebab.model.Restaurant;
import api.indy.kebab.repository.RestaurantRepository;
import api.indy.kebab.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * Service class for managing {@link Restaurant} entities.
 * Provides methods for creating, retrieving, updating, and deleting categories.
 *
 * @see RestaurantRepository
 * @see Restaurant
 */
@Service
public class RestaurantService {
    private static final String IMAGES_PATH = "uploads/images/%s";

    private final RestaurantRepository _restaurantRepository;

    @Autowired
    public RestaurantService(RestaurantRepository restaurantRepository) {
        _restaurantRepository = restaurantRepository;
    }

    /**
     * Creates a new restaurant
     *
     * @param name the name of the restaurant
     * @param description the description of the restaurant
     * @param image the image file representing the restaurant
     * @param phoneNumber the phone number of the restaurant
     * @param website the website link of the restaurant
     * @param location the location of the restaurant
     * @return the created {@link Restaurant} entity
     *
     * @throws IOException if an error occurs while uploading the image.
     * @throws IllegalArgumentException if required fields are null or invalid.
     */
    public Restaurant CreateRestaurant(
            String name, String description, MultipartFile image, String phoneNumber, String website, Location location) throws IOException {
        if (name == null) throw new IllegalArgumentException("Name is null");

        String imageUrl = Util.uploadFile(image, IMAGES_PATH);

        Restaurant restaurant = new Restaurant(name, description, imageUrl, phoneNumber, website, location);

        return _restaurantRepository.save(restaurant);
    }


    /**
     * Retrieves a restaurant by its unique identifier.
     *
     * @param id the unique identifier of the restaurant.
     * @return the {@link Restaurant} with the specified ID, or {@code null} if not found.
     */
    public Restaurant getRestaurant(long id) { return this._restaurantRepository.findRestaurantById(id);}

    /**
     * Retrieves the logo file of a restaurant by its unique identifier.
     *
     * @param id the unique identifier of the restaurant.
     * @return the logo file, or {@code null} if the restaurant does not exist.
     */
    public File getRestaurantLogo(long id) {
        Restaurant restaurant = this._restaurantRepository.findRestaurantById(id);
        if(restaurant == null) return null;

        return Util.retrieveFile(restaurant.getLogoUrl());
    }

    /**
     * Updates a new restaurant
     *
     * @param id the ID of the restaurant to update
     * @param name the new name of the restaurant
     * @param description the new description of the restaurant
     * @param image the new image file representing the restaurant
     * @param phoneNumber the new phone number of the restaurant
     * @param website the new website link of the restaurant
     * @param location the new location of the restaurant
     * @return the updated {@link Restaurant} entity
     *
     * @throws IOException if an error occurs while uploading the image.
     * @throws IllegalArgumentException if required fields are null or invalid.
     */
    public Restaurant updateRestaurant(
            long id, String name,String description, MultipartFile image,String phoneNumber, String website, Location location
    ) throws IOException {
        Restaurant restaurant = this._restaurantRepository.findRestaurantById(id);
        if(restaurant == null) throw new EntityNotFoundException(Restaurant.class, id);

        if (name != null) restaurant.setName(name);
        if (description != null) restaurant.setDescription(description);
        if(image != null && !image.isEmpty()) {
            Util.deleteFile(restaurant.getLogoUrl());
            restaurant.setLogoUrl(Util.uploadFile(image, IMAGES_PATH));
        }
        if(phoneNumber != null) restaurant.setPhoneNumber(phoneNumber);
        if(website != null) restaurant.setWebsite(website);
        if(location != null) restaurant.setLocation(location);
        return _restaurantRepository.save(restaurant);
    }
    /**
     * Deletes a restaurant by its unique identifier.
     *
     * @param id the unique identifier of the restaurant to delete.
     * @throws EntityNotFoundException if the restaurant does not exist.
     */
    public void deleteRestaurant(long id) {
        Restaurant restaurant = this._restaurantRepository.findRestaurantById(id);
        if(restaurant == null) throw new EntityNotFoundException(Restaurant.class, id);

        Util.deleteFile(restaurant.getLogoUrl());
        this._restaurantRepository.delete(restaurant);
    }

    /**
     * Retrieves a list of all {@link Restaurant} entities.
     *
     * @param pageable the {@link Pageable} object containing pagination information.
     * @return a {@link Page} of {@link Restaurant} entities.
     */
    public Page<Restaurant> listRestaurant(Pageable pageable) {
        return this._restaurantRepository.findAll(pageable);
    }
}

