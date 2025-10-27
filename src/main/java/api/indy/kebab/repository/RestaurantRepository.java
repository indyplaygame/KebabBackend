package api.indy.kebab.repository;

import api.indy.kebab.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for managing {@link api.indy.kebab.model.Restaurant} entities.
 * Extends {@link JpaRepository} to provide basic CRUD operations.
 *
 * @see Restaurant
 */
@SuppressWarnings("NewClassNamingConvention")
public interface RestaurantRepository  extends JpaRepository<Restaurant, Long> {

    /**
     * Finds a review by its unique identifier.
     *
     * @param restaurantId the unique identifier of the review.
     * @return the {@link Restaurant} entity with the specified ID, or null if not found.
     */
    public Restaurant findRestaurantById(long restaurantId);
}