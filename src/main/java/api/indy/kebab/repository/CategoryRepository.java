package api.indy.kebab.repository;

import api.indy.kebab.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

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
     * @param id the unique identifier of the category.
     * @return the {@link Category} entity with the specified ID, or null if not found.
     */
    public Category findByCategoryId(long id);
}
