package api.indy.kebab.model.request;

import api.indy.kebab.model.Order;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

import java.util.Map;

public record CreateOrderRequest(
    @NotBlank(message = "Phone number cannot be empty")
    @Pattern(regexp = "^\\+?[1-9](?:[ -]?\\(?\\d\\)?){6,14}$", message = "Phone number format is invalid")
    String phoneNumber,

    @Length(max = 255, message = "Notes cannot exceed 255 characters")
    String notes,

    @Valid
    CreateLocationRequest location,

    @NotBlank(message = "Payment method cannot be empty")
    Order.PaymentMethod paymentMethod,

    @NotEmpty(message = "Order items cannot be empty")
    Map<Long, Integer> items
) {}
