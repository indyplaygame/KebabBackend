package api.indy.kebab.repository;

import api.indy.kebab.model.Order;
import api.indy.kebab.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for managing {@link Order} entities.
 * Extends {@link JpaRepository} to provide basic CRUD operations.
 *
 * @see Order
 */
@SuppressWarnings("NewClassNamingConvention")
public interface OrderRepository extends JpaRepository<Order, Long> {

    /**
     * Finds an order by its unique identifier.
     *
     * @param id the unique identifier of the order.
     * @return the {@link Order} entity with the specified ID, or null if not found.
     */
    public Order findByOrderId(long id);

    /**
     * Finds orders by the user who placed them.
     *
     * @param user the unique identifier of the user.
     * @param pageable the pagination information.
     * @return a list of {@link Order} entities placed by the specified user.
     */
    public Page<Order> findByUser(User user, Pageable pageable);
}
