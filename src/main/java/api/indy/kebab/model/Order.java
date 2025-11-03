package api.indy.kebab.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


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
    private List<OrderItem> _items;
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
    ) {
        this._user = user;
        this._phoneNumber = phoneNumber;
        this._orderPlacementDate = orderPlacementDate;
        this._notes = notes;
        this._location = location;
        this._status = status;
        this._paymentMethod = paymentMethod;
        this._paid = paid;

        this._items = items.entrySet().stream().map(e -> new OrderItem(this, e.getKey(), e.getValue())).toList();
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

    @Column(name = "phoneNumber", nullable = false, length = 15)
    public String getPhoneNumber() { return this._phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this._phoneNumber = phoneNumber; }

    @Column(name = "orderPlacementDate", nullable = false, length = 19)
    public String getOrderDatePlaced() { return this._orderPlacementDate; }
    public void setOrderDatePlaced(String orderDatePlaced) { this._orderPlacementDate = orderDatePlaced; }

    @Column(name = "notes", nullable = true, length = 255)
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

    @JsonIgnore
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    protected List<OrderItem> getOrderItems() { return this._items; }
    protected void setOrderItems(List<OrderItem> items) { this._items = items; }

    @Column(name = "paid", nullable = false)
    public boolean isPaid() { return this._paid; }
    public void setPaid(boolean paid) { this._paid = paid; }

    @Transient
    public Map<MenuItem, Integer> getItems() {
        return this._items.stream().collect(Collectors.toMap(OrderItem::getMenuItem, OrderItem::getQuantity));
    }

    @Transient
    @JsonProperty("userId")
    public Long getUserId() {
        return this._user != null ? this._user.getUserId() : null;
    }

    @Transient
    @JsonProperty("items")
    protected Map<Long, Integer> getItemIds() {
        return this._items.stream().collect(Collectors.toMap(
            item -> item.getMenuItem().getMenuItemId(),
            OrderItem::getQuantity
        ));
    }

    @Transient
    @JsonProperty("totalPrice")
    public double getTotalPrice() {
        return this._items.stream().mapToDouble(item -> item.getMenuItem().getPrice() * item.getQuantity()).sum();
    }
}
