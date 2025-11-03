package api.indy.kebab.controller;

import api.indy.kebab.auth.AuthRequired;
import api.indy.kebab.auth.Permission;
import api.indy.kebab.decorators.pagination.Paginated;
import api.indy.kebab.exceptions.EntityNotFoundException;
import api.indy.kebab.exceptions.NotOwnerOfEntityException;
import api.indy.kebab.model.Location;
import api.indy.kebab.model.Order;
import api.indy.kebab.model.User;
import api.indy.kebab.model.request.CreateLocationRequest;
import api.indy.kebab.model.request.CreateOrderRequest;
import api.indy.kebab.model.response.ErrorResponse;
import api.indy.kebab.model.response.NotFoundResponse;
import api.indy.kebab.model.response.PageResponse;
import api.indy.kebab.service.OrderService;
import api.indy.kebab.util.Util;
import api.indy.kebab.validation.ValidationGroups;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService _orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this._orderService = orderService;
    }

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

    @AuthRequired
    @PatchMapping("/{id}/update-phone")
    public ResponseEntity<Object> updateOrderPhoneNumber(@PathVariable long id, @RequestParam String phoneNumber, HttpSession session) {
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

    @AuthRequired
    @PatchMapping("/{id}/update-status")
    public ResponseEntity<Object> updateOrderStatus(@PathVariable long id, @RequestParam Order.Status status, HttpSession session) {
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

    @Paginated(maxSize = 20)
    @AuthRequired(requiredPermission = Permission.ORDERS_READ)
    @GetMapping("/list")
    public ResponseEntity<Object> listOrders(Pageable pageable) {
        return new ResponseEntity<>(PageResponse.from(this._orderService.listOrders(pageable)), HttpStatus.OK);
    }

    @Paginated(maxSize = 20)
    @AuthRequired(requiredPermission = Permission.ORDERS_READ)
    @GetMapping("/list/own")
    public ResponseEntity<Object> listOwnOrders(Pageable pageable, HttpSession session) {
        return new ResponseEntity<>(PageResponse.from(this._orderService.listOrders(session, pageable)), HttpStatus.OK);
    }
}
