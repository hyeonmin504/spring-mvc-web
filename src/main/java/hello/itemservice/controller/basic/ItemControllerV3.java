package hello.itemservice.controller.basic;

import hello.itemservice.domain.DeliveryCode;
import hello.itemservice.domain.Item;
import hello.itemservice.domain.SaveCheck;
import hello.itemservice.domain.dto.ItemDto;
import hello.itemservice.repository.ItemRepository;
import hello.itemservice.service.ItemService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/basicV3/items")
public class ItemControllerV3 {

    private final ItemRepository itemRepository;
    private final ItemService itemService;


    @GetMapping
    public String mainPage(Model model) {
        List<Item> items = itemRepository.findAll();

        model.addAttribute("items",items);
        return "basicV3/items";
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
        return "basicV3/item";
    }

    @GetMapping("/add")
    public String addItem(Model model) {
        model.addAttribute("deliveryCodes", ItemService.deliveryCodes());
        model.addAttribute("itemTypes", ItemService.itemTypes());
        model.addAttribute("region", ItemService.region());
        model.addAttribute("item", new Item());
        return "basicV3/addForm";
    }

    /*
    @PostMapping("/add")
    public String addItem(@Validated @ModelAttribute Item item, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {

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
            log.info("item.regions={}",item.getRegions());

            model.addAttribute("deliveryCodes", ItemService.deliveryCodes());
            model.addAttribute("itemTypes", ItemService.itemTypes());
            model.addAttribute("region", ItemService.region());

            log.info("item.regions={}", item.getRegions());

            return "basicV3/addForm";
        }

        //성공 로직

        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId",savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/basicV3/items/{itemId}";
    }*/

    @PostMapping("/add")
    public String addItem2(@Validated(value = SaveCheck.class) @ModelAttribute Item item, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {

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
            log.info("item.regions={}",item.getRegions());

            model.addAttribute("deliveryCodes", ItemService.deliveryCodes());
            model.addAttribute("itemTypes", ItemService.itemTypes());
            model.addAttribute("region", ItemService.region());

            log.info("item.regions={}", item.getRegions());

            return "basicV3/addForm";
        }

        //성공 로직

        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId",savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/basicV3/items/{itemId}";
    }

    @GetMapping("/remove/{itemId}")
    public String removeItem(@PathVariable(name = "itemId")long itemId, Model model) {
        log.info("item.itemId={}", itemId);
        itemRepository.removeItem(itemId);
        return "redirect:/basicV3/items";
    }


    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model){
        model.addAttribute("deliveryCodes", ItemService.deliveryCodes());
        model.addAttribute("itemTypes", ItemService.itemTypes());
        model.addAttribute("region", ItemService.region());
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item",item);
        return "basicV3/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String editForm2(
            @PathVariable long itemId, Model model,
            @Validated @ModelAttribute ItemDto itemDto,
            BindingResult bindingResult) {

        log.info("itemDto={}",itemDto);

        if (itemDto.getPrice() != null && itemDto.getQuantity() != null) {
            int resultPrice = itemDto.getPrice() * itemDto.getQuantity();
            if (resultPrice < 10000) {
                //bindingResult.addError(new ObjectError("item", null,null,"가격 * 수량의 합은 10,000원 이상이어야 합니다. 현재 값 = "+ resultPrice));
                bindingResult.rejectValue("price",null,null, "가격 * 수량의 합은 10,000원 이상이어야 합니다. 현재 값 = "+ resultPrice);
            }
        }

        if (bindingResult.hasErrors()){
            log.info("errors={}",bindingResult);
            return "basicV3/editForm";
        }

        Item item = new Item(itemDto);

        itemService.update(itemId,item);
        log.info("end");
        return "redirect:/basicV3/items/{itemId}";
    }

//    @PostMapping("/{itemId}/edit")
//    public String editForm(
//            @PathVariable long itemId, Model model,
//            @Validated @ModelAttribute ItemDto itemDto,
//            BindingResult bindingResult) {
//
//        log.info("itemDto={}",itemDto);
//
//        if (itemDto.getPrice() != null && itemDto.getQuantity() != null) {
//            int resultPrice = itemDto.getPrice() * itemDto.getQuantity();
//            if (resultPrice < 10000) {
//                //bindingResult.addError(new ObjectError("item", null,null,"가격 * 수량의 합은 10,000원 이상이어야 합니다. 현재 값 = "+ resultPrice));
//                bindingResult.rejectValue("price",null,null, "가격 * 수량의 합은 10,000원 이상이어야 합니다. 현재 값 = "+ resultPrice);
//            }
//        }
//
//        if (bindingResult.hasErrors()){
//            log.info("errors={}",bindingResult);
//            return "basicV3/editForm";
//        }
//
//        itemService.update(itemId,itemDto);
//        log.info("end");
//        return "redirect:/basicV3/items/{itemId}";
//    }
}
