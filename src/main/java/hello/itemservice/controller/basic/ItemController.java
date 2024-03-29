package hello.itemservice.controller.basic;

import hello.itemservice.domain.DeliveryCode;
import hello.itemservice.domain.Item;
import hello.itemservice.domain.ItemType;
import hello.itemservice.domain.dto.ItemDto;
import hello.itemservice.repository.ItemRepository;
import hello.itemservice.service.ItemService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/basicV1/items")
public class ItemController {

    private final ItemRepository itemRepository;
    private final ItemService itemService;

    @GetMapping
    public String mainPage(Model model) {
        List<Item> items = itemRepository.findAll();

        model.addAttribute("items",items);
        return "basic/items";
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
        return "basic/item";
    }

    @GetMapping("/add")
    public String addItem(Model model) {
        model.addAttribute("deliveryCodes", ItemService.deliveryCodes());
        model.addAttribute("itemTypes", ItemService.itemTypes());
        model.addAttribute("region", ItemService.region());
        model.addAttribute("item", new Item());
        return "basic/addForm";
    }

    @PostMapping("/add")
    public String addItemV0(@ModelAttribute Item item, RedirectAttributes redirectAttributes, Model model) {
        log.info("item.open={}", item.getOpen());
        log.info("item.regions={}", item.getRegions());
        log.info("item.itemType={}", item.getItemType());

        Map<String, String> errors = new HashMap<>();

        if(!StringUtils.hasText(item.getItemName())){
            errors.put("itemName", "상품 이름은 필수입니다");
        }
        if (item.getPrice() == null || item.getPrice()<1000 || item.getPrice() > 1000000) {
            errors.put("price", "가격은 1000 ~ 1,000,000 까지 허용합니다");
        }
        if (item.getQuantity() == null || item.getQuantity() >= 9999){
            errors.put("quantity", "수량은 쵀대 9999개까지 입니다");
        }

        //복합 룰
        if (item.getPrice() != null && item.getQuantity() != null) {
            int resultPrice = item.getPrice() * item.getQuantity();
            if (resultPrice < 10000) {
                errors.put("globalError", "가격 * 수량의 합은 10,000원 이상이어야 합니다. 현재 값 = "+ resultPrice);
            }
        }

        //검증 실패시 다시 입력 폼으로
        if (!errors.isEmpty()){
            model.addAttribute("errors", errors);
            log.info("errors={}", errors);
            return "basic/addForm";
        }

        //성공 로직

        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId",savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/basic/items/{itemId}";
    }

    @GetMapping("/remove/{itemId}")
    public String removeItem(@PathVariable(name = "itemId")long itemId, Model model) {
        log.info("item.itemId={}", itemId);
        itemRepository.removeItem(itemId);
        return "redirect:/basic/items";
    }


    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model){
        model.addAttribute("deliveryCodes", ItemService.deliveryCodes());
        model.addAttribute("itemTypes", ItemService.itemTypes());
        model.addAttribute("region", ItemService.region());
        Item item = itemRepository.findById(itemId);
        log.info("itemId={}", itemId);
        model.addAttribute("item",item);
        return "basic/editForm";
    }


    @PostMapping("/{itemId}/edit")
    public String editForm(
            @PathVariable long itemId, Model model,
            @ModelAttribute ItemDto itemDto) {

        Item item = new Item(itemDto);

        itemService.update(itemId,item);
        log.info("end");
        return "redirect:/basic/items/{itemId}";
    }
}
