package backend.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private String userName;
    private String itemName;
    private String itemCategory;
    private Double itemPrice;
    private Integer quantity;
    private Double itemTotal;
    private String itemImage;
    private LocalDateTime orderDateTime;

    public CartItem() {}
    @PrePersist
    public void prePersist() {
        if (orderDateTime == null) orderDateTime = LocalDateTime.now();
        if (itemTotal == null && itemPrice != null && quantity != null) {
            itemTotal = itemPrice * quantity;
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemCategory() {
        return itemCategory;
    }

    public void setItemCategory(String itemCategory) {
        this.itemCategory = itemCategory;
    }

    public Double getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(Double itemPrice) {
        this.itemPrice = itemPrice;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getItemTotal() {
        return itemTotal;
    }

    public void setItemTotal(Double itemTotal) {
        this.itemTotal = itemTotal;
    }

    public String getItemImage() {
        return itemImage;
    }

    public void setItemImage(String itemImage) {
        this.itemImage = itemImage;
    }

    public LocalDateTime getOrderDateTime() {
        return orderDateTime;
    }

    public void setOrderDateTime(LocalDateTime orderDateTime) {
        this.orderDateTime = orderDateTime;
    }

    public CartItem(Long id, Long userId, String userName, String itemName, String itemCategory, Double itemPrice, Integer quantity, Double itemTotal, String itemImage, LocalDateTime orderDateTime) {
        this.id = id;
        this.userId = userId;
        this.userName = userName;
        this.itemName = itemName;
        this.itemCategory = itemCategory;
        this.itemPrice = itemPrice;
        this.quantity = quantity;
        this.itemTotal = itemTotal;
        this.itemImage = itemImage;
        this.orderDateTime = orderDateTime;
    }
}
