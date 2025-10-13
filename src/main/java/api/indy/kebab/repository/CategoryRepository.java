package api.indy.kebab.repository;

import api.indy.kebab.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Repository interface for managing {@link Category} entities.
 * Extends {@link JpaRepository} to provide basic CRUD operations.
 *
 * @see Category
 */
@SuppressWarnings("NewClassNamingConvention")
public interface CategoryRepository extends JpaRepository<Category, Long> {

    /**
     * Finds a category by its unique identifier.
     *
     * @param id The unique identifier of the category.
     * @return The {@link Category} entity with the specified ID, or null if not found.
     */
    @Query("SELECT c FROM Category c WHERE c.categoryId = ?1")
    public Category getCategoryById(long id);
}
