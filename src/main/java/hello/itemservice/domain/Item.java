package hello.itemservice.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Entity(name = "item")
@Data
public class Item {

    @Id
    private Long id;

    private String itemName;
    private Integer quantity;
    private Integer price;

    private Boolean open;

    private List<String> regions;

    @Enumerated(EnumType.STRING)
    private ItemType itemType;


    private String deliveryCode;

    public Item() {
    }

    public Item(String itemName, Integer price, Integer quantity) {
        this.itemName = itemName;
        this.quantity = quantity;
        this.price = price;
    }
}
