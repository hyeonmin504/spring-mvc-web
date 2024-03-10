package hello.itemservice.controller.basic;

import hello.itemservice.domain.Item;
import hello.itemservice.domain.dto.ItemDto;
import hello.itemservice.repository.ItemRepository;
import hello.itemservice.service.ItemService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/basic/items")
public class ItemController {

    private final ItemRepository itemRepository;
    private final ItemService itemService;

    @GetMapping
    public String mainpage(Model model) {
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
    public String item(@PathVariable long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "basic/item";
    }

    @GetMapping("/add")
    public String addItem() {
        return "basic/addForm";
    }

    @PostMapping("/add")
    public String addItemV0(@ModelAttribute Item item, RedirectAttributes redirectAttributes) {
        Item savedItem = itemRepository.save(item);
        Long Itemids = 3L;
        redirectAttributes.addAttribute("itemId",savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/basic/items/{itemIds}";
    }

    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model){
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item",item);
        return "basic/editForm";
    }



    @PostMapping("/{itemId}/edit")
    public String editForm(
            @PathVariable long itemId, Model model,
            @ModelAttribute ItemDto itemDto) {
        itemService.update(itemId,itemDto);
        return "redirect:/basic/items/{itemId}";
    }
}
