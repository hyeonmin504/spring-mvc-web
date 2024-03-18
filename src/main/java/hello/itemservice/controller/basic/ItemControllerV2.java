package hello.itemservice.controller.basic;

import hello.itemservice.domain.Item;
import hello.itemservice.domain.dto.ItemDto;
import hello.itemservice.repository.ItemRepository;
import hello.itemservice.service.ItemService;
import jakarta.annotation.PostConstruct;
import jdk.jfr.Frequency;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/basicV2/items")
public class ItemControllerV2 {

    private final ItemRepository itemRepository;
    private final ItemService itemService;
    private final ItemValidator itemValidator;

    @InitBinder
    public void init(WebDataBinder dataBinder){
        dataBinder.addValidators(itemValidator);
    }

    @GetMapping
    public String mainPage(Model model) {
        List<Item> items = itemRepository.findAll();

        model.addAttribute("items",items);
        return "basicV2/items";
    }

    @PostMapping
    @PostConstruct
    public void init() {
        itemRepository.save(new Item("itemA", 10000, 10));
        itemRepository.save(new Item("itemB", 20000, 20));
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable Long itemId, Model model) {
        model.addAttribute("deliveryCodes", ItemService.deliveryCodes());
        model.addAttribute("itemTypes", ItemService.itemTypes());
        model.addAttribute("region", ItemService.region());
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "basicV2/item";
    }

    @GetMapping("/add")
    public String addItem(Model model) {
        model.addAttribute("deliveryCodes", ItemService.deliveryCodes());
        model.addAttribute("itemTypes", ItemService.itemTypes());
        model.addAttribute("region", ItemService.region());
        model.addAttribute("item", new Item());
        return "basicV2/addForm";
    }

    /*@PostMapping("/add")
    public String addItemV1(@ModelAttribute Item item, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
        log.info("item.open={}", item.getOpen());
        log.info("item.regions={}", item.getRegions());
        log.info("item.itemType={}", item.getItemType());

        if(!StringUtils.hasText(item.getItemName())){
            bindingResult.addError(new FieldError("item", "itemName", "상품 이름은 필수입니다"));
        }
        if (item.getPrice() == null || item.getPrice()<1000 || item.getPrice() > 1000000) {
            bindingResult.addError(new FieldError("item", "itemName", "가격은 1000 ~ 1,000,000 까지 허용합니다"));
        }
        if (item.getQuantity() == null || item.getQuantity() >= 9999){
            bindingResult.addError(new FieldError("item", "itemName", "수량은 쵀대 9999개까지 입니다"));
        }

        //복합 룰
        if (item.getPrice() != null && item.getQuantity() != null) {
            int resultPrice = item.getPrice() * item.getQuantity();
            if (resultPrice < 10000) {
                bindingResult.addError(new ObjectError("item", "가격 * 수량의 합은 10,000원 이상이어야 합니다. 현재 값 = "+ resultPrice));
            }
        }

        //검증 실패시 다시 입력 폼으로
        if (bindingResult.hasErrors()){
            log.info("errors={}", bindingResult);
            return "basicV2/addForm";
        }

        //성공 로직

        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId",savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/basicV2/items/{itemId}";
    }*/

    /*@PostMapping("/add")
    public String addItemV2(@ModelAttribute Item item, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
        log.info("item.open={}", item.getOpen());
        log.info("item.regions={}", item.getRegions());
        log.info("item.itemType={}", item.getItemType());

        if(!StringUtils.hasText(item.getItemName())){
            //bindingResult.addError(new FieldError("item", "itemName",item.getItemName(),false ,null,null, "상품 이름은 필수입니다"));
            bindingResult.rejectValue("itemName","required",null);
        }
        if (item.getPrice() == null || item.getPrice()<1000 || item.getPrice() > 1000000) {
            //bindingResult.addError(new FieldError("item", "price",item.getPrice(),false ,null,null, "상품 이름은 필수입니다"));
            bindingResult.rejectValue("price",null ,null,"가격 값이 너무 적거나 많아요");
        }
        if (item.getQuantity() == null || item.getQuantity() >= 9999){
            //bindingResult.addError(new FieldError("item", "quantity",item.getQuantity(),false ,null,null, "상품 이름은 필수입니다"));
            bindingResult.rejectValue("quantity",null,null,"수량은 9999개 이상 금지");
        }

        //복합 룰
        if (item.getPrice() != null && item.getQuantity() != null) {
            int resultPrice = item.getPrice() * item.getQuantity();
            if (resultPrice < 10000) {
                //bindingResult.addError(new ObjectError("item", null,null,"가격 * 수량의 합은 10,000원 이상이어야 합니다. 현재 값 = "+ resultPrice));
                bindingResult.rejectValue("price",null,null, "가격 * 수량의 합은 10,000원 이상이어야 합니다. 현재 값 = "+ resultPrice);
            }
        }

        //검증 실패시 다시 입력 폼으로
        if (bindingResult.hasErrors()){
            log.info("errors={}", bindingResult);
            return "basicV2/addForm";
        }

        //성공 로직

        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId",savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/basicV2/items/{itemId}";
    }*/

    /*@PostMapping("/add")
    public String addItemV3(@ModelAttribute Item item, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {

        if (itemValidator.supports(item.getClass())){
            itemValidator.validate(item,bindingResult);
        }

        //검증 실패시 다시 입력 폼으로
        if (bindingResult.hasErrors()){
            log.info("errors={}", bindingResult);
            return "basicV2/addForm";
        }

        //성공 로직

        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId",savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/basicV2/items/{itemId}";
    }*/

    @PostMapping("/add")
    public String addItemV3(@Validated @ModelAttribute Item item, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {

        //검증 실패시 다시 입력 폼으로
        if (bindingResult.hasErrors()){
            log.info("errors={}", bindingResult);
            return "basicV2/addForm";
        }

        //성공 로직

        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId",savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/basicV2/items/{itemId}";
    }

    @GetMapping("/remove/{itemId}")
    public String removeItem(@PathVariable(name = "itemId")long itemId, Model model) {
        log.info("item.itemId={}", itemId);
        itemRepository.removeItem(itemId);
        return "redirect:/basicV2/items";
    }


    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model){
        model.addAttribute("deliveryCodes", ItemService.deliveryCodes());
        model.addAttribute("itemTypes", ItemService.itemTypes());
        model.addAttribute("region", ItemService.region());
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item",item);
        return "basicV2/editForm";
    }


    @PostMapping("/{itemId}/edit")
    public String editForm(
            @PathVariable long itemId, Model model,
            @ModelAttribute ItemDto itemDto) {

        itemService.update(itemId,itemDto);
        log.info("end");
        return "redirect:/basicV2/items/{itemId}";
    }
}
