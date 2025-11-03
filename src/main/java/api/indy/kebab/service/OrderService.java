package api.indy.kebab.service;

import api.indy.kebab.auth.Permission;
import api.indy.kebab.exceptions.EntityNotFoundException;
import api.indy.kebab.exceptions.NotOwnerOfEntityException;
import api.indy.kebab.model.Location;
import api.indy.kebab.model.MenuItem;
import api.indy.kebab.model.Order;
import api.indy.kebab.model.User;
import api.indy.kebab.repository.OrderRepository;
import api.indy.kebab.util.Util;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Service class for managing {@link Order} entities.
 * Provides methods for creating, retrieving, updating, and deleting orders.
 *
 * @see OrderRepository
 * @see Order
 */
@Service
public class OrderService {
    private final OrderRepository _orderRepository;
    private final AuthService _authService;
    private final MenuService _menuService;

    @Autowired
    public OrderService(OrderRepository orderRepository, AuthService authService, MenuService menuService) {
        this._orderRepository = orderRepository;
        this._authService = authService;
        this._menuService = menuService;
    }

    /**
     * Creates an order.
     *
     * @param session the HTTP session of the user placing the order.
     * @param phoneNumber the phone number associated with the order.
     * @param notes any additional notes for the order.
     * @param location the delivery location for the order.
     * @param paymentMethod the payment method for the order.
     * @param items a map of {@link MenuItem} entities and their corresponding quantities.
     * @return the created {@link Order} entity.
     */
    public Order createOrder(HttpSession session, String phoneNumber, String notes, Location location, Order.PaymentMethod paymentMethod, Map<Long, Integer> itemIds) {
        User user = this._authService.getActiveUser(session);

        if(phoneNumber == null || location == null || itemIds == null || phoneNumber.isEmpty() || itemIds.isEmpty())
            throw new IllegalArgumentException("Phone number, location and items cannot be null or empty");

        Map<MenuItem, Integer> items = itemIds.entrySet().stream().collect(Collectors.toMap(
            entry -> {
                MenuItem item = this._menuService.getMenuItem(entry.getKey());
                if(item == null) throw new EntityNotFoundException(MenuItem.class, entry.getKey());

                return item;
            },
        Map.Entry::getValue));

        Order order = new Order(
            user,
            phoneNumber,
            Util.getTimestamp(),
            notes,
            location,
            Order.Status.RECEIVED,
            paymentMethod,
            items,
            false
        );

        return this._orderRepository.save(order);
    }

    /**
     * Retrieves an order by its unique identifier. Verifies that the requesting user owns the order or has the necessary permissions.
     *
     * @param id the unique identifier of the order.
     * @return the {@link Order} with the specified ID, or {@code null} if not found.
     */
    public Order getOrder(HttpSession session, long id) throws NotOwnerOfEntityException {
        Order order = this._orderRepository.findByOrderId(id);

        User user = this._authService.getActiveUser(session);
        if(!Util.verifyOwnership(order.getUserId(), Permission.ORDERS_READ, user))
            throw new NotOwnerOfEntityException(Order.class);

        return order;
    }

    /**
     * Retrieves an order by its unique identifier.
     *
     * @param id the unique identifier of the order.
     * @return the {@link Order} with the specified ID, or {@code null} if not found.
     */
    private Order getOrder(long id) {
        return this._orderRepository.findByOrderId(id);
    }

    /**
     * Updates the phone number associated with an order.
     *
     * @param id the unique identifier of the order.
     * @param phoneNumber the new phone number to associate with the order.
     * @return the updated {@link Order} entity.
     *
     * @throws EntityNotFoundException if the order with the specified ID does not exist.
     * @throws IllegalArgumentException if the order is completed, cancelled, or refunded.
     */
    public Order updatePhoneNumber(HttpSession session, long id, String phoneNumber) throws NotOwnerOfEntityException {
        Order order = this.getOrder(id);
        if(order == null) throw new EntityNotFoundException(Order.class, id);

        User user = this._authService.getActiveUser(session);
        if(!Util.verifyOwnership(order.getUserId(), Permission.ORDERS_UPDATE, user))
            throw new NotOwnerOfEntityException(Order.class);

        if(List.of(Order.Status.COMPLETED, Order.Status.CANCELLED, Order.Status.REFUNDED).contains(order.getStatus()))
            throw new IllegalArgumentException("Cannot update phone number for completed, cancelled or refunded orders");

        order.setPhoneNumber(phoneNumber);
        return this._orderRepository.save(order);
    }

    /**
     * Updates the status of an order.
     *
     * @param id the unique identifier of the order.
     * @param status the new status to set for the order.
     * @return the updated {@link Order} entity.
     *
     * @throws EntityNotFoundException if the order with the specified ID does not exist.
     * @throws IllegalArgumentException if attempting to set the status to CANCELLED or REFUNDED.
     */
    public Order updateStatus(HttpSession session, long id, Order.Status status) throws NotOwnerOfEntityException {
        if(status.equals(Order.Status.CANCELLED) || status.equals(Order.Status.REFUNDED))
            throw new IllegalArgumentException("You cannot set the order status to CANCELLED or REFUNDED using this method");

        Order order = this.getOrder(id);
        if(order == null) throw new EntityNotFoundException(Order.class, id);

        User user = this._authService.getActiveUser(session);
        if(!Util.verifyOwnership(order.getUserId(), Permission.ORDERS_UPDATE, user))
            throw new NotOwnerOfEntityException(Order.class);

        if(List.of(Order.Status.CANCELLED, Order.Status.REFUNDED).contains(order.getStatus()))
            throw new IllegalArgumentException("Cannot update status for cancelled or refunded orders");

        order.setStatus(status);
        return this._orderRepository.save(order);
    }

    /**
     * Cancels an order.
     *
     * @param id the unique identifier of the order.
     * @return the updated {@link Order} entity with status set to CANCELLED or REFUNDED.
     *
     * @throws EntityNotFoundException if the order with the specified ID does not exist.
     * @throws IllegalArgumentException if the order cannot be cancelled at its current stage.
     */
    public Order cancelOrder(HttpSession session, long id) throws NotOwnerOfEntityException {
        Order order = this.getOrder(id);
        if(order == null) throw new EntityNotFoundException(Order.class, id);

        User user = this._authService.getActiveUser(session);
        if(!Util.verifyOwnership(order.getUserId(), Permission.ORDERS_UPDATE, user))
            throw new NotOwnerOfEntityException(Order.class);

        if(List.of(Order.Status.COMPLETED, Order.Status.READY_FOR_DELIVERY, Order.Status.PREPARING).contains(order.getStatus()))
            throw new IllegalArgumentException("Order cannot be cancelled at this stage");
        if(order.getStatus().equals(Order.Status.CANCELLED) || order.getStatus().equals(Order.Status.REFUNDED))
            throw new IllegalArgumentException("Order is already cancelled or refunded");

        if(!order.isPaid()) order.setStatus(Order.Status.CANCELLED);
        else order.setStatus(Order.Status.REFUNDED);

        return this._orderRepository.save(order);
    }

    /**
     * Simulates payment logic for an order and marks it as paid.
     *
     * @param id the unique identifier of the order.
     * @return the updated {@link Order} entity with paid status set to true.
     *
     * @throws EntityNotFoundException if the order with the specified ID does not exist.
     * @throws IllegalArgumentException if the order is already paid.
     */
    public Order payForOrder(HttpSession session, long id) throws NotOwnerOfEntityException {
        Order order = this.getOrder(id);
        if(order == null) throw new EntityNotFoundException(Order.class, id);
        if(order.getStatus().equals(Order.Status.CANCELLED) || order.getStatus().equals(Order.Status.REFUNDED))
            throw new IllegalArgumentException("Cannot pay for a cancelled or refunded order");

        User user = this._authService.getActiveUser(session);
        if(!Util.verifyOwnership(order.getUserId(), Permission.ORDERS_UPDATE, user))
            throw new NotOwnerOfEntityException(Order.class);

        if(order.isPaid()) throw new IllegalArgumentException("Order is already paid");

        order.setPaid(true);
        return this._orderRepository.save(order);
    }

    /**
     * Retrieves a list of all {@link Order} entities.
     *
     * @param pageable the {@link Pageable} object containing pagination information.
     * @return a {@link Page} of {@link Order} entities.
     */
    public Page<Order> listOrders(Pageable pageable) {
        return this._orderRepository.findAll(pageable);
    }

    /**
     * Retrieves a list of {@link Order} entities placed by the currently authenticated user.
     *
     * @param session the HTTP session of the user.
     * @param pageable the {@link Pageable} object containing pagination information.
     * @return a {@link Page} of {@link Order} entities placed by the user.
     */
    public Page<Order> listOrders(HttpSession session, Pageable pageable) {
        User user = this._authService.getActiveUser(session);
        return this._orderRepository.findByUser(user, pageable);
    }
}
