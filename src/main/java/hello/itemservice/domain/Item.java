package hello.itemservice.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity(name = "item")
@Data
public class Item {

    @Id
    private Long id;

    private String itemName;
    private Integer quantity;
    private Integer price;

    public Item() {
    }

    public Item(String itemName, Integer price, Integer quantity) {
        this.itemName = itemName;
        this.quantity = quantity;
        this.price = price;
    }
}
