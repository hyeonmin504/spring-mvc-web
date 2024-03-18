package hello.itemservice.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import java.util.List;
import java.util.Map;

@Entity(name = "item")
@Data
public class Item {

    @Id
    private Long id;

    @NotBlank
    private String itemName;

    @NotNull
    @Max(9999)
    private Integer quantity;
    @NotNull
    @Range(min = 1000, max = 1000000)
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
