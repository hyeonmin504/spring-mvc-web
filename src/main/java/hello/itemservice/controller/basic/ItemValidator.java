package hello.itemservice.controller.basic;

import hello.itemservice.domain.Item;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Slf4j
@Component
public class ItemValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Item.class.isAssignableFrom(clazz);
        //item == clazz
        //item == subItem
    }

    @Override
    public void validate(Object target, Errors errors) {
        Item item = (Item) target;

        log.info("item.open={}", item.getOpen());
        log.info("item.regions={}", item.getRegions());
        log.info("item.itemType={}", item.getItemType());

        if(!StringUtils.hasText(item.getItemName())){
            //bindingResult.addError(new FieldError("item", "itemName",item.getItemName(),false ,null,null, "상품 이름은 필수입니다"));
            errors.rejectValue("itemName","required",null);
        }
        if (item.getPrice() == null || item.getPrice()<1000 || item.getPrice() > 1000000) {
            //bindingResult.addError(new FieldError("item", "price",item.getPrice(),false ,null,null, "상품 이름은 필수입니다"));
            errors.rejectValue("price",null ,null,"가격 값이 너무 적거나 많아요");
        }
        if (item.getQuantity() == null || item.getQuantity() >= 9999){
            //bindingResult.addError(new FieldError("item", "quantity",item.getQuantity(),false ,null,null, "상품 이름은 필수입니다"));
            errors.rejectValue("quantity",null,null,"수량은 9999개 이상 금지");
        }

        //복합 룰
        if (item.getPrice() != null && item.getQuantity() != null) {
            int resultPrice = item.getPrice() * item.getQuantity();
            if (resultPrice < 10000) {
                //bindingResult.addError(new ObjectError("item", null,null,"가격 * 수량의 합은 10,000원 이상이어야 합니다. 현재 값 = "+ resultPrice));
                errors.rejectValue("price",null,null, "가격 * 수량의 합은 10,000원 이상이어야 합니다. 현재 값 = "+ resultPrice);
            }
        }
    }
}
