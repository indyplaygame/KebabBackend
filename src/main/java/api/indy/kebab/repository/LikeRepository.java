package api.indy.kebab.repository;

import api.indy.kebab.model.Category;
import api.indy.kebab.model.Like;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for managing {@link Like} entities.
 * Extends {@link JpaRepository} to provide basic CRUD operations.
 *
 * @see Like
 */
@SuppressWarnings("NewClassNamingConvention")
public interface LikeRepository extends JpaRepository<Like, Long> {

    /**
     * Finds a Like by its unique identifier
     *
     * @param id the unique identifier of the like
     * @return the {@link Like} entity with the specified ID of null if not found
     */
    public Like findByLikeId(long id);
}
