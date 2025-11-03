package api.indy.kebab.model;

import jakarta.persistence.*;

@Entity
@Access(AccessType.PROPERTY)
@Table(name = "order_items")
@SuppressWarnings("AssociationNotMarkedInspection")
public class OrderItem {
    private long _orderItemId;
    private Order _order;
    private MenuItem _menuItem;
    private int _quantity;

    protected OrderItem() {}

    public OrderItem(Order order, MenuItem menuItem, int quantity) {
        this._order = order;
        this._menuItem = menuItem;
        this._quantity = quantity;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "orderItemId", nullable = false)
    public long getOrderItemId() { return this._orderItemId; }
    protected void setOrderItemId(long orderItemId) { this._orderItemId = orderItemId; }

    @ManyToOne(optional = false)
    @JoinColumn(name = "orderId", nullable = false)
    public Order getOrder() { return this._order; }
    public void setOrder(Order order) { this._order = order; }

    @ManyToOne(optional = false)
    @JoinColumn(name = "menuItemId", nullable = false)
    public MenuItem getMenuItem() { return this._menuItem; }
    public void setMenuItem(MenuItem menuItem) { this._menuItem = menuItem; }

    @Column(name = "quantity", nullable = false)
    public int getQuantity() { return this._quantity; }
    public void setQuantity(int quantity) { this._quantity = quantity; }
}
