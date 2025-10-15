package api.indy.kebab.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

/**
 * Entity representing a review in the application.
 * Mapped to the {@code reviews} table in the database.
 */
@Entity
@Access(AccessType.PROPERTY)
@Table(name = "reviews")
@SuppressWarnings("AssociationNotMarkedInspection")
public class Review {
    private long _reviewId;
    private long _userId;
    private long _orderId;
    private float _rating=0.0f;
    private String _title;
    private String _description;
    private String _imageUrl;
    private boolean _anonymous;
    private long _likes=0;
    private String _createdAt;
    private String _updatedAt;

    protected Review() {}

    public Review(
        long reviewId,
        long userId,
        long orderId,
        float rating,
        String title,
        String description,
        String imageUrl,
        boolean anonymous,
        long likes,
        String createdAt,
        String updatedAt
    ) {
        this._reviewId = reviewId;
        this._userId = userId;
        this._orderId = orderId;
        this._rating = rating;
        this._title = title;
        this._description = description;
        this._imageUrl = imageUrl;
        this._anonymous = anonymous;
        this._likes = likes;
        this._createdAt = createdAt;
        this._updatedAt = updatedAt;
    }


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reviewId", nullable = false)
    public long getReviewId() { return this._reviewId; }
    protected void setReviewId(long reviewId) { this._reviewId = reviewId; }

    @Column(name="userId", nullable = false)
    public long getUserId() { return this._userId; }
    protected void setUserId(long userId) { this._userId = userId; }

    @Column(name="orderId", nullable = false)
    public long getOrderId() { return this._orderId; }
    protected void setOrderId(long orderId) { this._orderId = orderId; }

    @Min(0)
    @Max(5)
    @Column(name = "rating", nullable = false)
    public float getRating() { return this._rating; }
    public void setRating(float rating) { this._rating = rating; }

    @Column(name="title",nullable = true ,length = 100)
    public String getTitle() { return this._title; }
    public void setTitle(String title) { this._title = title; }

    @Column(name = "description", nullable = true,  length = 1000)
    public String getDescription() { return this._description; }
    public void setDescription(String description) { this._description = description; }

    @Column(name = "imageUrl",nullable = true)
    public String getImageUrl() { return this._imageUrl; }
    public void setImageUrl(String imageUrl) { this._imageUrl = imageUrl; }

    @Column(name = "anonymous",nullable = false)
    public boolean isAnonymous() { return this._anonymous; }
    public void setAnonymous(boolean anonymous) { this._anonymous = anonymous; }

    @Column(name = "likes",nullable = false)
    public long getLikes() { return this._likes; }
    public void setLikes(long likes) { this._likes = likes; }

    @Column(name = "created_at", nullable = false)
    public String getCreatedAt() { return this._createdAt; }
    public void setCreatedAt(String createdAt) { this._createdAt = createdAt; }

    @Column(name = "updated_at")
    public String getUpdatedAt() { return this._updatedAt; }
    public void setUpdatedAt(String updatedAt) { this._updatedAt = updatedAt; }


}
