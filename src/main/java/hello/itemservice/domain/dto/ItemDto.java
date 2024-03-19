package hello.itemservice.domain.dto;

import hello.itemservice.domain.DeliveryCode;
import hello.itemservice.domain.Item;
import hello.itemservice.domain.ItemType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import java.lang.reflect.Type;
import java.util.*;

@Data
public class ItemDto {

    @Id
    private Long id;

    @NotBlank
    private String itemName;
    @NotNull
    private Integer quantity;

    @NotNull
    @Range(min = 1000, max = 1000000)
    private Integer price;
    private Boolean open;
    private String deliveryCode;

    @Enumerated(EnumType.STRING)
    private ItemType itemType;
    private List<String> regions = new LinkedList<>();

    public ItemDto() {
    }

    public ItemDto(Item item) {
        this.id = item.getId();
        this.itemName = item.getItemName();
        this.quantity = item.getQuantity();
        this.price = item.getPrice();
        this.open = item.getOpen();
        this.deliveryCode = item.getDeliveryCode();
        this.regions.addAll(item.getRegions());
        this.itemType = item.getItemType();
    }
}
