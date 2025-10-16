package api.indy.kebab.service;


import api.indy.kebab.exceptions.EntityNotFoundException;
import api.indy.kebab.model.Review;
import api.indy.kebab.model.User;
import api.indy.kebab.repository.ReviewRepository;
import api.indy.kebab.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.Instant;
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
    private static final String IMAGES_PATH="uploads/reviews/%s";

    private final ReviewRepository _reviewRepository;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository) { this._reviewRepository = reviewRepository; }

    /**
     * Create and persist a new Review.
     *
     * @param title       The title of review (optional).
     * @param description A brief description of the review (optional).
     * @param image       The {@link MultipartFile} representing the review's image (optional).
     * @param rating      numeric rating between 0 and 5
     * @param user        authoring user
     * @param anonymous   whether review is anonymous
     * @return saved Review
     * @throws IOException If an I/O error occurs during icon upload.
     */
    public Review createReview(String title, String description, MultipartFile image, float rating, User user, boolean anonymous) throws IOException {
        if (rating < 0f || rating > 5f) {
            throw new IllegalArgumentException("rating must be between 0 and 5");
        }

        String imageUrl = null;
        if (image != null && !image.isEmpty()) {
            imageUrl = Util.uploadFile(image, IMAGES_PATH);
        }

        String now = Instant.now().toString();

        long likes = 0L;

        Review review = new Review(
                title,
                description,
                imageUrl,
                now,     // createdAt
                now,     // updatedAt
                user,
                anonymous,
                rating,
                likes
        );

        return this._reviewRepository.save(review);
    }

    /**
     * Retrieves a {@link Review} entity by its unique identifier.
     *
     * @param id The unique identifier of the review.
     * @return The {@link Review} entity with the specified ID, or null if not found.
     */
    public Review getReview(long id) {return this._reviewRepository.findByReviewId(id);}

    /**
     * Retrieves a review's image file by the review's unique identifier.
     *
     * @param id The unique identifier of the review.
     * @return The {@link File} representing the review's image, or null if not found.
     */
    public File getReviewIcon(long id) {
        Review review = this._reviewRepository.findByReviewId(id);
        if(review == null) throw new EntityNotFoundException(Review.class, id);

        return Util.retrieveFile(review.getImageUrl());
    }


    /**
     * Updates an existing {@link Review} entity with new values.
     *
     * @param id          The unique identifier of the review to update.
     * @param title       The new name of the review (optional).
     * @param description The new description of the review (optional).
     * @param image       The new {@link MultipartFile} representing the review's image (optional).
     * @param rating      The new rating (optional).
     * @param anonymous   whether review is anonymous (optional)
     * @return The updated {@link Review} entity.
     * @throws IOException If an I/O error occurs during icon upload.
     */
    public Review updateReview(long id, String title, String description, MultipartFile image, Float rating, Boolean anonymous ) throws IOException {
        Review review = this._reviewRepository.findByReviewId(id);
        if(review == null) throw new EntityNotFoundException(Review.class, id);

        if(title != null && !title.isEmpty()) review.setTitle(title);
        if(description != null && !description.isEmpty()) review.setDescription(description);
        if (image != null && !image.isEmpty()) {
            Util.deleteFile((review.getImageUrl()));
            review.setImageUrl(Util.uploadFile(image, IMAGES_PATH));
        }

        if (rating !=null) {
            if (rating < 0f || rating > 5f) throw new IllegalArgumentException("rating must be between 0 and 5");
            review.setRating(rating);
        }

        if (anonymous!= null) review.setAnonymous(anonymous);

        review.setUpdatedAt(Instant.now().toString());

        return this._reviewRepository.save(review);
    }

    /**
     * Deletes a {@link Review} entity by its unique identifier.
     *
     * @param id The unique identifier of the review to delete.
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
     * @return A list of all reviews.
     */
    public List<Review> listReviews() {
        return this._reviewRepository.findAll();
    }

}
