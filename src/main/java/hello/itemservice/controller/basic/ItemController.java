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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/basic/items")
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
    public String addItemV0(@ModelAttribute Item item, RedirectAttributes redirectAttributes) {
        log.info("item.open={}", item.getOpen());
        log.info("item.regions={}", item.getRegions());
        log.info("item.itemType={}", item.getItemType());

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
        model.addAttribute("item",item);
        return "basic/editForm";
    }


    @PostMapping("/{itemId}/edit")
    public String editForm(
            @PathVariable long itemId, Model model,
            @ModelAttribute ItemDto itemDto) {

        itemService.update(itemId,itemDto);
        log.info("end");
        return "redirect:/basic/items/{itemId}";
    }
}
