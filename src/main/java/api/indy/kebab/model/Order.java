package api.indy.kebab.model;

import api.indy.kebab.persistence.converter.OrderConverter;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private Map<MenuItem, Integer> _items;
    private Status _status;
    private String _phoneNumber;
    private Location _location;
    private String _orderDatePlaced;
    private String _notes;
    private PaymentMethod _paymentMethod;
    private boolean _payed;

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
        Map<MenuItem,Integer>  item,
        Status status,
        String phoneNumber,
        Location location,
        String orderDatePlaced,
        String notes,
        PaymentMethod paymentMethod,
        boolean payed
    ){
        this._user = user;
        this._items = item;
        this._status = status;
        this._phoneNumber = phoneNumber;
        this._location = location;
        this._orderDatePlaced = orderDatePlaced;
        this._notes = notes;
        this._paymentMethod = paymentMethod;
        this._payed = payed;
    }


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id", nullable = false)
    public long getOrderId() { return this._orderId; }
    protected void setOrderId(long orderId) { this._orderId = orderId; }

    @JsonIgnore
    @ManyToOne(optional = true)
    @JoinColumn(name ="userID", nullable = true)
    public User getUser() { return this._user; }
    public void setUser(User user) { this._user = user; }

    @Convert(converter = OrderConverter.class)
    @Column(name = "orderedItems", nullable = false)
    public Map<MenuItem, Integer> getItems() { return this._items; }
    public void setItems(Map<MenuItem, Integer> item) { this._items = item; }

    @Column(name = "status", nullable = false)
    public Status getStatus() { return this._status; }
    public void setStatus(Status status) { this._status = status; }

    @Column(name = "phoneNumber", nullable = false)
    public String getPhoneNumber() { return this._phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this._phoneNumber = phoneNumber; }

    @Embedded
    public Location getLocation() {return this._location;}
    public void setLocation(Location location) { this._location = location; }

    @Column(name = "orderDatePlace", nullable = false)
    public String getOrderDatePlaced() { return this._orderDatePlaced; }
    public void setOrderDatePlaced(String orderDatePlaced) { this._orderDatePlaced = orderDatePlaced; }

    @Column(name = "notes", nullable = false)
    public String getNotes() { return this._notes; }
    public void setNotes(String notes) { this._notes = notes; }

    @Column(name = "paymentMethod", nullable = false)
    public PaymentMethod getPaymentMethod() { return this._paymentMethod; }
    public void setPaymentMethod(PaymentMethod paymentMethod) { this._paymentMethod = paymentMethod; }

    @Column(name = "payed", nullable = false)
    public boolean isPayed() { return this._payed; }
    public void setPayed(boolean payed) { this._payed = payed; }


}
