package api.indy.kebab.repository;

import api.indy.kebab.model.Order;
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
}
