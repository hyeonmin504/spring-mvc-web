package hello.itemservice.domain.dto;

import hello.itemservice.domain.DeliveryCode;
import hello.itemservice.domain.Item;
import hello.itemservice.domain.ItemType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ItemDto {

    private String itemName;
    private Integer quantity;
    private Integer price;
    private Boolean open;
    private String deliveryCode;
    private ItemType itemType;
    private List<String> regions = new LinkedList<>();

    public ItemDto(String itemName, Integer quantity, Integer price, Boolean open, String deliveryCode, List<String> region, ItemType itemType) {
        region = new LinkedList<>();
        this.itemName = itemName;
        this.quantity = quantity;
        this.price = price;
        this.open = open;
        this.deliveryCode = deliveryCode;
        this.regions.addAll(region);
        this.itemType = itemType;
    }


}
