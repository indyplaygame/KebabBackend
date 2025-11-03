package api.indy.kebab.model;

import api.indy.kebab.persistence.converter.OrderItemsConverter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import java.util.Map;


/**
 * Entity representing a order in the application.
 * Mapped to the {@code orders} table in the database.
 */
@Entity
@Access(AccessType.PROPERTY)
@Table(name="orders")
@SuppressWarnings("AssociationNotMarkedInspection")
public class Order {
    private long _orderId;
    private User _user;
    private String _phoneNumber;
    private String _orderPlacementDate;
    private String _notes;
    private Location _location;
    private Status _status;
    private PaymentMethod _paymentMethod;
    private Map<MenuItem, Integer> _items;
    private boolean _paid;

    public enum Status {
        RECEIVED,
        PREPARING,
        READY_FOR_DELIVERY,
        COMPLETED,
        CANCELLED,
        REFUNDED
    }

    public enum PaymentMethod {
        CASH,
        CARD,
        BLIK,
        APPLE_PAY,
        GOOGLE_PAY,
        TRANSFER
    }

    protected Order() {}

    public Order(
        User user,
        String phoneNumber,
        String orderPlacementDate,
        String notes,
        Location location,
        Status status,
        PaymentMethod paymentMethod,
        Map<MenuItem, Integer> items,
        boolean paid
    ){
        this._user = user;
        this._phoneNumber = phoneNumber;
        this._orderPlacementDate = orderPlacementDate;
        this._notes = notes;
        this._location = location;
        this._status = status;
        this._paymentMethod = paymentMethod;
        this._items = items;
        this._paid = paid;
    }


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "orderId", nullable = false)
    public long getOrderId() { return this._orderId; }
    protected void setOrderId(long orderId) { this._orderId = orderId; }

    @JsonIgnore
    @ManyToOne(optional = true)
    @JoinColumn(name = "userId", nullable = true)
    public User getUser() { return this._user; }
    public void setUser(User user) { this._user = user; }

    @Column(name = "phoneNumber", nullable = false)
    public String getPhoneNumber() { return this._phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this._phoneNumber = phoneNumber; }

    @Column(name = "orderPlacementDate", nullable = false)
    public String getOrderDatePlaced() { return this._orderPlacementDate; }
    public void setOrderDatePlaced(String orderDatePlaced) { this._orderPlacementDate = orderDatePlaced; }

    @Column(name = "notes", nullable = false)
    public String getNotes() { return this._notes; }
    public void setNotes(String notes) { this._notes = notes; }

    @Embedded
    public Location getLocation() { return this._location; }
    public void setLocation(Location location) { this._location = location; }

    @Column(name = "status", nullable = false)
    public Status getStatus() { return this._status; }
    public void setStatus(Status status) { this._status = status; }

    @Column(name = "paymentMethod", nullable = false)
    public PaymentMethod getPaymentMethod() { return this._paymentMethod; }
    public void setPaymentMethod(PaymentMethod paymentMethod) { this._paymentMethod = paymentMethod; }

    @Convert(converter = OrderItemsConverter.class)
    @Column(name = "orderedItems", nullable = false)
    public Map<MenuItem, Integer> getItems() { return this._items; }
    public void setItems(Map<MenuItem, Integer> items) { this._items = items; }

    @Column(name = "paid", nullable = false)
    public boolean isPayed() { return this._paid; }
    public void setPayed(boolean paid) { this._paid = paid; }

    @Transient
    @JsonProperty("totalPrice")
    public double getTotalPrice() {
        return this._items.entrySet().stream()
            .mapToDouble(e -> e.getKey().getPrice() * e.getValue()).sum();
    }
}
