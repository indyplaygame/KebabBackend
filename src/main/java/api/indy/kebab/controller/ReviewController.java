package api.indy.kebab.controller;

import api.indy.kebab.auth.AuthRequired;
import api.indy.kebab.decorators.pagination.Paginated;
import api.indy.kebab.exceptions.EntityNotFoundException;
import api.indy.kebab.model.request.CreateReviewRequest;
import api.indy.kebab.model.response.ErrorResponse;
import api.indy.kebab.model.response.NotFoundResponse;
import api.indy.kebab.model.response.PageResponse;
import api.indy.kebab.util.Util;
import api.indy.kebab.validation.ValidationGroups;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import api.indy.kebab.model.Review;
import api.indy.kebab.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Controller for managing reviews.
 * Provides endpoints for creating, retrieving, updating, deleting, and listing reviews
 *
 * @see ReviewController
 * @see Review
 */
@RestController
@RequestMapping("/reviews")
public class ReviewController {
    private final ReviewService _reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this._reviewService = reviewService;
    }

    /**
     * Handles request to create a new review.
     *
     * @param body the {@link CreateReviewRequest} object containing new review data.
     * @return a {@link ResponseEntity} containing the created review or an error
     */
    @AuthRequired
    @PostMapping("/create")
    public ResponseEntity<Object> createReview(@Validated(ValidationGroups.OnCreate.class) @ModelAttribute CreateReviewRequest body, HttpSession httpSession) {
        try {
            Review review = this._reviewService.createReview(
                httpSession,
                body.title(),
                body.description(),
                body.image(),
                body.rating(),
                body.anonymous()
            );

            return new ResponseEntity<>(review, HttpStatus.CREATED);
        } catch(IOException e) {
            return new ResponseEntity<>(new ErrorResponse("Failed to upload image: %s".formatted(e.getMessage())), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch(IllegalArgumentException e) {
            return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Handles requests to retrieve a review by its ID.
     *
     * @param id the review identifier.
     * @return a {@link ResponseEntity} containing the review or an error.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Object> getReview(@PathVariable long id) {
        Review review = this._reviewService.getReview(id);

        if(review == null)
            return new ResponseEntity<>(new NotFoundResponse(Review.class, id), HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(review, HttpStatus.OK);
    }

    /**
     * Handles requests to retrieve a review's image by its ID.
     *
     * @param id the review identifier.
     * @return a {@link ResponseEntity} containing the image as a byte array resource or an error.
     */
    @GetMapping("/{id}/image")
    public ResponseEntity<Object> getReviewImage(@PathVariable long id) {
        try {
            File imageFile = this._reviewService.getReviewImage(id);
            if(imageFile == null || !imageFile.exists())
                return new ResponseEntity<>(new ErrorResponse("Couldn't find image for review with the provided ID"), HttpStatus.NOT_FOUND);

            return Util.createResourceResponse(imageFile);
        } catch(IOException e) {
            return new ResponseEntity<>(new ErrorResponse("Failed to retrieve image: %s".formatted(e.getMessage())), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch(EntityNotFoundException e) {
            return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Handles requests to delete a review's image by its ID.
     *
     * @param id the identifier of the review whose image is to be deleted.
     * @return a {@link ResponseEntity} with status code.
     */
    @AuthRequired
    @DeleteMapping("/{id}/image/delete")
    public ResponseEntity<Object> deleteReviewImage(@PathVariable long id) {
        try {
            this._reviewService.deleteReviewImage(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch(EntityNotFoundException e) {
            return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Handles requests to update an existing review.
     *
     * @param id the identifier of the review to update.
     * @param body the {@link CreateReviewRequest} object containing new review data.
     * @return a {@link ResponseEntity} containing the updated review or an error.
     */
    @AuthRequired
    @PutMapping("/{id}/update")
    public ResponseEntity<Object> updateReview(@PathVariable long id, @Valid @ModelAttribute CreateReviewRequest body) {
        try {
            Review updatedReview = this._reviewService.updateReview(
                id,
                body.title(),
                body.description(),
                body.image(),
                body.rating(),
                body.anonymous()
            );

            return new ResponseEntity<>(updatedReview, HttpStatus.OK);
        } catch(IOException e) {
            return new ResponseEntity<>(new ErrorResponse("Failed to upload image: %s".formatted(e.getMessage())), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch(EntityNotFoundException e) {
            return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.NOT_FOUND);
        } catch(IllegalArgumentException e) {
            return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Handles requests to delete a review by its ID.
     *
     * @param id the identifier of the review to delete.
     * @return a {@link ResponseEntity} with status code.
     */
    @AuthRequired
    @DeleteMapping("/{id}/delete")
    public ResponseEntity<Object> deleteReview(@PathVariable long id) {
        try {
            this._reviewService.deleteReview(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch(EntityNotFoundException e) {
            return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Handles requests to list all reviews.
     *
     * @param pageable the {@link Pageable} object containing pagination information.
     * @return a {@link ResponseEntity} containing the paginated list of reviews.
     */
    @Paginated(defaultSize = 20)
    @GetMapping("/list")
    public ResponseEntity<Object> listReviews(Pageable pageable) {
        return new ResponseEntity<>(PageResponse.from(this._reviewService.listReviews(pageable)), HttpStatus.OK);
    }

}
