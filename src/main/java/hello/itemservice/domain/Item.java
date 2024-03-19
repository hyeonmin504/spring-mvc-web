package hello.itemservice.domain;

import hello.itemservice.controller.form.ItemSaveForm;
import hello.itemservice.controller.form.ItemUpdateForm;
import hello.itemservice.domain.dto.ItemDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.ScriptAssert;

import java.util.List;
import java.util.Map;

@Entity(name = "item")
@Data
public class Item {

    @Id
    //@NotNull(groups = UpdateCheck.class)
    private Long id;

   // @NotBlank(groups = {SaveCheck.class, UpdateCheck.class})
    private String itemName;

    //@NotNull(groups = {SaveCheck.class, UpdateCheck.class})
    //@Max(value = 9999, groups = SaveCheck.class)
    private Integer quantity;
    //@NotNull(groups = {SaveCheck.class, UpdateCheck.class})
    //@Range(min = 1000, max = 1000000, groups = {SaveCheck.class, UpdateCheck.class})
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

    public Item(ItemSaveForm form) {
        this.itemName = form.getItemName();
        this.quantity = form.getQuantity();
        this.price = form.getPrice();
        this.open = form.getOpen();
        this.regions = form.getRegions();
        this.itemType = form.getItemType();
        this.deliveryCode = form.getDeliveryCode();
    }
    public Item(ItemDto form) {
        this.itemName = form.getItemName();
        this.quantity = form.getQuantity();
        this.price = form.getPrice();
        this.open = form.getOpen();
        this.regions = form.getRegions();
        this.itemType = form.getItemType();
        this.deliveryCode = form.getDeliveryCode();
    }
    public Item(ItemUpdateForm form) {
        this.itemName = form.getItemName();
        this.quantity = form.getQuantity();
        this.price = form.getPrice();
        this.open = form.getOpen();
        this.regions = form.getRegions();
        this.itemType = form.getItemType();
        this.deliveryCode = form.getDeliveryCode();
    }

}
