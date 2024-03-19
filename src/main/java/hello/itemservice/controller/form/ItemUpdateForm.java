package hello.itemservice.controller.form;

import hello.itemservice.domain.ItemType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import java.util.List;

@Data
public class ItemUpdateForm {

    @NotNull
    private Long id;
    @NotBlank
    private String itemName;

    @NotNull
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
