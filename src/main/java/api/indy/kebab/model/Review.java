package api.indy.kebab.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
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
    private String _title;
    private String _description;
    private String _imageUrl;
    private String _createdAt;
    private String _updatedAt;
    private User _user;
    private boolean _anonymous;
    private float _rating;
    private long _likes;

    protected Review() {}

    public Review(
        String title,
        String description,
        String imageUrl,
        String createdAt,
        String updatedAt,
        User user,
        boolean anonymous,
        float rating,
        long likes
        ) {
        this._title = title;
        this._description = description;
        this._imageUrl = imageUrl;
        this._createdAt = createdAt;
        this._updatedAt = updatedAt;
        this._user = user;
        this._anonymous = anonymous;
        this._rating = rating;
        this._likes = likes;
    }


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reviewId", nullable = false)
    public long getReviewId() { return this._reviewId; }
    protected void setReviewId(long reviewId) { this._reviewId = reviewId; }

    @Column(name="title", nullable = true ,length = 100)
    public String getTitle() { return this._title; }
    public void setTitle(String title) { this._title = title; }

    @Column(name = "description", nullable = true,  length = 1000)
    public String getDescription() { return this._description; }
    public void setDescription(String description) { this._description = description; }

    @Column(name = "imageUrl", nullable = true)
    public String getImageUrl() { return this._imageUrl; }
    public void setImageUrl(String imageUrl) { this._imageUrl = imageUrl; }

    @Column(name = "createdAt", nullable = false)
    public String getCreatedAt() { return this._createdAt; }
    public void setCreatedAt(String createdAt) { this._createdAt = createdAt; }

    @Column(name = "updatedAt", nullable = true)
    public String getUpdatedAt() { return this._updatedAt; }
    public void setUpdatedAt(String updatedAt) { this._updatedAt = updatedAt; }

    @JsonIgnore
    @ManyToOne(optional = true)
    @JoinColumn(name = "userId", nullable = true)
    public User getUser() { return this._user; }
    public void setUser(User user) { this._user = user; }

    @Column(name = "anonymous", nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    public boolean isAnonymous() { return this._anonymous; }
    public void setAnonymous(boolean anonymous) { this._anonymous = anonymous; }

    @Min(0)
    @Max(5)
    @Column(name = "rating", nullable = false, columnDefinition = "FLOAT DEFAULT 0")
    public float getRating() { return this._rating; }
    public void setRating(float rating) { this._rating = rating; }

    @Column(name = "likes", nullable = false, columnDefinition = "BIGINT DEFAULT 0")
    public long getLikes() { return this._likes; }
    public void setLikes(long likes) { this._likes = likes; }

    @Transient
    @JsonProperty
    public long getUserId() { return this._user != null ? this._user.getUserId() : 0; }
}
