package api.indy.kebab.service;

import api.indy.kebab.exceptions.EntityNotFoundException;
import api.indy.kebab.model.Review;
import api.indy.kebab.model.User;
import api.indy.kebab.repository.ReviewRepository;
import api.indy.kebab.util.Util;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Service class for managing {@link Review} entities.
 * Provides methods for creating, retrieving, updating, and deleting reviews.
 *
 * @see ReviewRepository
 * @see Review
 */
@Service
public class ReviewService {
    private static final String IMAGES_PATH = "uploads/reviews/%s";

    private final ReviewRepository _reviewRepository;
    private final AuthService _authService;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository, AuthService authService) {
        this._reviewRepository = reviewRepository;
        this._authService = authService;
    }

    /**
     * Create a new {@link Review} entity and saves it to the repository.
     *
     * @param session     the current HTTP session to identify the user creating the review.
     * @param title       the title of review.
     * @param description a brief description of the review.
     * @param image       the {@link MultipartFile} representing the review's image.
     * @param rating      numeric rating between 0 and 5
     * @param anonymous   whether the review is anonymous
     * @return the created {@link Review} entity.
     *
     * @throws IOException if an I/O error occurs during icon upload.
     */
    public Review createReview(HttpSession session, String title, String description, MultipartFile image, float rating, Boolean anonymous) throws IOException {
        if(rating % 0.5F != 0F) throw new IllegalArgumentException("Rating must be in increments of 0.5");
        if(rating < 0F || rating > 5F) throw new IllegalArgumentException("Rating must be a number between 0 and 5");

        String imageUrl = image != null && !image.isEmpty() ? Util.uploadFile(image, IMAGES_PATH) : null;
        String createdAt = Util.getTimestamp();
        User user = this._authService.getActiveUser(session);

        Review review = new Review(
            title,
            description,
            imageUrl,
            createdAt,
            user,
            rating,
            anonymous
        );

        return this._reviewRepository.save(review);
    }

    /**
     * Retrieves a {@link Review} entity by its unique identifier.
     *
     * @param id the unique identifier of the review.
     * @return the {@link Review} entity with the specified ID, or null if not found.
     */
    public Review getReview(long id) {
        return this._reviewRepository.findByReviewId(id);
    }

    /**
     * Retrieves a review's image file by the review's unique identifier.
     *
     * @param id the unique identifier of the review.
     * @return the {@link File} representing the review's image, or null if not found.
     *
     * @throws EntityNotFoundException if the review with the specified ID does not exist.
     */
    public File getReviewImage(long id) {
        Review review = this._reviewRepository.findByReviewId(id);
        if(review == null) throw new EntityNotFoundException(Review.class, id);

        return Util.retrieveFile(review.getImageUrl());
    }

    /**
     * Updates an existing {@link Review} entity with new values.
     *
     * @param id          the unique identifier of the review to update.
     * @param title       the new name of the review (optional).
     * @param description the new description of the review (optional).
     * @param image       the new {@link MultipartFile} representing the review's image (optional).
     * @param rating      the new rating (optional).
     * @param anonymous   whether review is anonymous (optional)
     * @return the updated {@link Review} entity.
     *
     * @throws IOException if an I/O error occurs during icon upload.
     * @throws EntityNotFoundException if the review with the specified ID does not exist.
     */
    public Review updateReview(long id, String title, String description, MultipartFile image, Float rating, Boolean anonymous) throws IOException {
        Review review = this._reviewRepository.findByReviewId(id);
        if(review == null) throw new EntityNotFoundException(Review.class, id);

        if(title != null && !title.isEmpty()) review.setTitle(title);
        if(description != null && !description.isEmpty()) review.setDescription(description);
        if(anonymous != null) review.setAnonymous(anonymous);
        if(image != null && !image.isEmpty()) {
            Util.deleteFile((review.getImageUrl()));
            review.setImageUrl(Util.uploadFile(image, IMAGES_PATH));
        }
        if(rating != null) {
            if(rating % 0.5F != 0F) throw new IllegalArgumentException("Rating must be in increments of 0.5");
            if(rating < 0F || rating > 5F) throw new IllegalArgumentException("Rating must be a number between 0 and 5");
            review.setRating(rating);
        }

        review.setUpdatedAt(Util.getTimestamp());

        return this._reviewRepository.save(review);
    }

    /**
     * Deletes a {@link Review} entity by its unique identifier.
     *
     * @param id the unique identifier of the review to delete.
     */
    public void deleteReview(long id) {
        Review review = this._reviewRepository.findByReviewId(id);

        if(review == null) throw new EntityNotFoundException(Review.class, id);

        Util.deleteFile(review.getImageUrl());
        this._reviewRepository.delete(review);
    }

    /**
     * Retrieves a list of all {@link Review} entities.
     *
     * @return a list of all reviews.
     */
    public List<Review> listReviews() {
        return this._reviewRepository.findAll();
    }

}
