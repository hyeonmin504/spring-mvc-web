package hello.itemservice.controller.basic;

import hello.itemservice.domain.Item;
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
    private final ItemValidator itemValidator;

    @InitBinder
    public void init(WebDataBinder dataBinder){
        dataBinder.addValidators(itemValidator);
    }

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

    @PostMapping("/add")
    public String addItemV3(@Validated @ModelAttribute Item item, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {

        //검증 실패시 다시 입력 폼으로
        if (bindingResult.hasErrors()){
            log.info("errors={}", bindingResult);
            log.info("item.regions={}",item.getRegions());
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
    public String editForm(
            @PathVariable long itemId, Model model,
            @ModelAttribute ItemDto itemDto) {

        itemService.update(itemId,itemDto);
        log.info("end");
        return "redirect:/basicV3/items/{itemId}";
    }
}
