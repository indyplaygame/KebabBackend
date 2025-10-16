package api.indy.kebab.repository;

import api.indy.kebab.model.Category;
import api.indy.kebab.model.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for managing {@link MenuItem} entities.
 * Extends {@link JpaRepository} to provide basic CRUD operations.
 *
 * @see MenuItem
 */
@SuppressWarnings("NewClassNamingConvention")
public interface MenuRepository extends JpaRepository<MenuItem, Long> {

    /**
     * Finds a menu item by its unique identifier.
     *
     * @param id the unique identifier of the menu item.
     * @return the {@link MenuItem} entity with the specified ID, or null if not found.
     */
    public MenuItem findByMenuItemId(long id);
}
