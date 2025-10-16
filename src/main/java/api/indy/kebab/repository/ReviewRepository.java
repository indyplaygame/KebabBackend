package api.indy.kebab.repository;

import api.indy.kebab.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for managing {@link Review} entities.
 * Extends {@link JpaRepository} to provide basic CRUD operations.
 *
 * @see Review
 */
@SuppressWarnings("NewClassNamingConvention")
public interface ReviewRepository extends JpaRepository<Review, Long> {

    /**
     * Finds a review by its unique identifier.
     *
     * @param reviewId The unique identifier of the review.
     * @return The {@link Review} entity with the specified ID, or null if not found.
     */
    public Review findByReviewId(long reviewId);
}