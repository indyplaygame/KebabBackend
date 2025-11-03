package api.indy.kebab.controller;

import api.indy.kebab.auth.AuthRequired;
import api.indy.kebab.auth.Permission;
import api.indy.kebab.decorators.pagination.Paginated;
import api.indy.kebab.exceptions.EntityNotFoundException;
import api.indy.kebab.exceptions.NotOwnerOfEntityException;
import api.indy.kebab.model.Location;
import api.indy.kebab.model.Order;
import api.indy.kebab.model.request.CreateLocationRequest;
import api.indy.kebab.model.request.CreateOrderRequest;
import api.indy.kebab.model.response.ErrorResponse;
import api.indy.kebab.model.response.NotFoundResponse;
import api.indy.kebab.model.response.PageResponse;
import api.indy.kebab.service.OrderService;
import api.indy.kebab.validation.ValidationGroups;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for managing orders. Provides endpoints for creating, retrieving, updating, and listing order entries.
 *
 * @see OrderService
 * @see Order
 */
@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService _orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this._orderService = orderService;
    }

    /**
     * Handles requests to create a new order.
     *
     * @param body the {@link CreateOrderRequest} object containing order details
     * @param session the {@link HttpSession} of the user
     * @return a {@link ResponseEntity} containing the created order or an error response
     */
    @PostMapping("/create")
    public ResponseEntity<Object> createOrder(@Validated(ValidationGroups.OnCreate.class) @RequestBody CreateOrderRequest body, HttpSession session) {
        try {
            CreateLocationRequest locationBody = body.location();
            Location location = new Location(
                locationBody.latitude(),
                locationBody.longitude(),
                locationBody.country(),
                locationBody.voivodeship(),
                locationBody.postalCode(),
                locationBody.city(),
                locationBody.street(),
                locationBody.buildingNumber()
            );

            Order order = this._orderService.createOrder(
                session,
                body.phoneNumber(),
                body.notes(),
                location,
                body.paymentMethod(),
                body.items()
            );

            return new ResponseEntity<>(order, HttpStatus.OK);
        } catch(EntityNotFoundException e) {
            return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.NOT_FOUND);
        } catch(IllegalArgumentException e) {
            return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch(Exception e) {
            return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Handles requests to retrieve an order by its ID.
     *
     * @param id the order identifier.
     * @return a {@link ResponseEntity} containing the order object or an error
     */
    @AuthRequired
    @GetMapping("/{id}")
    public ResponseEntity<Object> getOrderById(@PathVariable long id, HttpSession session) {
        try {
            Order order = this._orderService.getOrder(session, id);

            if(order == null)
                return new ResponseEntity<>(new NotFoundResponse(Order.class, id), HttpStatus.NOT_FOUND);

            return new ResponseEntity<>(order, HttpStatus.OK);
        } catch(NotOwnerOfEntityException e) {
            return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.FORBIDDEN);
        } catch(Exception e) {
            return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Handles the request to update the phone number associated with an order.
     *
     * @param id the identifier of the order to update
     * @param phoneNumber the new phone number as plain text in the request body
     * @param session the {@link HttpSession} of the authenticated user
     * @return {@link ResponseEntity} containing the updated order or an error response
     *
     * @throws IllegalArgumentException if the phone number is invalid or the order state forbids updating
     * @throws EntityNotFoundException if the order does not exist
     */
    @AuthRequired
    @PatchMapping("/{id}/update-phone")
    public ResponseEntity<Object> updateOrderPhoneNumber(@PathVariable long id, @RequestBody String phoneNumber, HttpSession session) {
        if(phoneNumber == null || phoneNumber.isBlank())
            return new ResponseEntity<>(new ErrorResponse("Phone number cannot be empty"), HttpStatus.BAD_REQUEST);

        if(!phoneNumber.matches("^\\+?[1-9](?:[ -]?\\(?\\d\\)?){6,14}$"))
            return new ResponseEntity<>(new ErrorResponse("Phone number format is invalid"), HttpStatus.BAD_REQUEST);

        try {
            Order order = this._orderService.updatePhoneNumber(session, id, phoneNumber);

            return new ResponseEntity<>(order, HttpStatus.OK);
        } catch(IllegalArgumentException e) {
            return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch(NotOwnerOfEntityException e) {
            return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.FORBIDDEN);
        } catch(EntityNotFoundException e) {
            return new ResponseEntity<>(new NotFoundResponse(Order.class, id), HttpStatus.NOT_FOUND);
        }  catch(Exception e) {
            return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Handles the request to update the status of an order.
     *
     * @param id the identifier of the order to update
     * @param status the new status for the order
     * @param session the {@link HttpSession} of the authenticated user
     * @return {@link ResponseEntity} containing the updated order or an error response
     *
     * @throws IllegalArgumentException if the status transition is invalid
     * @throws EntityNotFoundException if the order does not exist
     */
    @AuthRequired
    @PatchMapping("/{id}/update-status")
    public ResponseEntity<Object> updateOrderStatus(@PathVariable long id, @RequestBody Order.Status status, HttpSession session) {
        try {
            Order order = this._orderService.updateStatus(session, id, status);

            return new ResponseEntity<>(order, HttpStatus.OK);
        } catch(IllegalArgumentException e) {
            return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch(NotOwnerOfEntityException e) {
            return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.FORBIDDEN);
        } catch(EntityNotFoundException e) {
            return new ResponseEntity<>(new NotFoundResponse(Order.class, id), HttpStatus.NOT_FOUND);
        }  catch(Exception e) {
            return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Handles the request to cancel an order.
     *
     * @param id the identifier of the order to cancel
     * @param session the {@link HttpSession} of the authenticated user
     * @return {@link ResponseEntity} containing the canceled order or an error response
     *
     * @throws IllegalArgumentException if the order cannot be canceled
     * @throws EntityNotFoundException if the order does not exist
     */
    @AuthRequired
    @PatchMapping("/{id}/cancel")
    public ResponseEntity<Object> cancelOrder(@PathVariable long id, HttpSession session) {
        try {
            Order order = this._orderService.cancelOrder(session, id);

            return new ResponseEntity<>(order, HttpStatus.OK);
        } catch(IllegalArgumentException e) {
            return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch(NotOwnerOfEntityException e) {
            return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.FORBIDDEN);
        } catch(EntityNotFoundException e) {
            return new ResponseEntity<>(new NotFoundResponse(Order.class, id), HttpStatus.NOT_FOUND);
        }  catch(Exception e) {
            return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Handles the request to pay for an order.
     *
     * @param id the identifier of the order to pay for
     * @param session the {@link HttpSession} of the authenticated user
     * @return {@link ResponseEntity} containing the paid order or an error response
     *
     * @throws IllegalArgumentException if the order cannot be paid for
     * @throws EntityNotFoundException if the order does not exist
     */
    @AuthRequired
    @PatchMapping("/{id}/pay")
    public ResponseEntity<Object> payForOrder(@PathVariable long id, HttpSession session) {
        try {
            Order order = this._orderService.payForOrder(session, id);

            return new ResponseEntity<>(order, HttpStatus.OK);
        } catch(IllegalArgumentException e) {
            return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch(NotOwnerOfEntityException e) {
            return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.FORBIDDEN);
        } catch(EntityNotFoundException e) {
            return new ResponseEntity<>(new NotFoundResponse(Order.class, id), HttpStatus.NOT_FOUND);
        }  catch(Exception e) {
            return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Handles requests to list all orders with pagination.
     *
     * @param pageable the pagination information.
     * @return a {@link ResponseEntity} containing a paginated list of orders.
     */
    @Paginated(maxSize = 20)
    @AuthRequired(requiredPermission = Permission.ORDERS_READ)
    @GetMapping("/list")
    public ResponseEntity<Object> listOrders(Pageable pageable) {
        return new ResponseEntity<>(PageResponse.from(this._orderService.listOrders(pageable)), HttpStatus.OK);
    }

    /**
     * Handles requests to list the authenticated user's orders with pagination.
     *
     * @param pageable the pagination information.
     * @param session the {@link HttpSession} of the user.
     * @return a {@link ResponseEntity} containing a paginated list of the user's orders.
     */
    @Paginated(maxSize = 20)
    @AuthRequired(requiredPermission = Permission.ORDERS_READ)
    @GetMapping("/list/own")
    public ResponseEntity<Object> listOwnOrders(Pageable pageable, HttpSession session) {
        return new ResponseEntity<>(PageResponse.from(this._orderService.listOrders(session, pageable)), HttpStatus.OK);
    }
}
