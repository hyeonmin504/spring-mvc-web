package hello.itemservice.controller.form;

import hello.itemservice.domain.Item;
import hello.itemservice.domain.ItemType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.*;

import java.util.List;

@Data
public class ItemSaveForm {

    @NotBlank
    private String itemName;
    @NotNull
    @Max(value = 9999)
    private Integer quantity;
    @NotNull
    @Range(min = 1000, max = 1000000)
    private Integer price;

    private Boolean open;

    private List<String> regions;

    @Enumerated(EnumType.STRING)
    private ItemType itemType;


    private String deliveryCode;
}
