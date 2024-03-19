package hello.itemservice.controller.basic;

import hello.itemservice.controller.form.ItemSaveForm;
import hello.itemservice.controller.form.ItemUpdateForm;
import hello.itemservice.domain.Item;
import hello.itemservice.domain.Member;
import hello.itemservice.repository.ItemRepository;
import hello.itemservice.login.repository.MemberRepository;
import hello.itemservice.service.ItemService;
import hello.itemservice.login.service.MemberService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/basicV4/items")
public class ItemControllerV4 {

    private final ItemRepository itemRepository;
    private final ItemService itemService;
    private final MemberService memberService;
    private final MemberRepository memberRepository;


    @GetMapping
    public String mainPage(Model model) {
        List<Item> items = itemRepository.findAll();

        model.addAttribute("items",items);
        return "basicV4/items";
    }

    @PostMapping
    @PostConstruct
    public void init() {
        itemRepository.save(new Item("itemA", 10000, 10));
        itemRepository.save(new Item("itemB", 20000, 20));
        memberService.saveMembers(new Member("111", "111", "111"));
        memberService.saveMembers(new Member("test2", "name2", "password2"));
        memberService.saveMembers(new Member("test3", "name3", "password3"));
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable Long itemId, Model model) {
        model.addAttribute("deliveryCodes", ItemService.deliveryCodes());
        model.addAttribute("itemTypes", ItemService.itemTypes());
        model.addAttribute("region", ItemService.region());
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "basicV4/item";
    }

    @GetMapping("/add")
    public String addItem(Model model) {
        model.addAttribute("deliveryCodes", ItemService.deliveryCodes());
        model.addAttribute("itemTypes", ItemService.itemTypes());
        model.addAttribute("region", ItemService.region());
        model.addAttribute("item", new Item());
        return "basicV4/addForm";
    }

    @PostMapping("/add")
    public String addItem(@Validated @ModelAttribute("item") ItemSaveForm form, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {

        if (form.getPrice() != null && form.getQuantity() != null) {
            int resultPrice = form.getPrice() * form.getQuantity();
            if (resultPrice < 10000) {
                //bindingResult.addError(new ObjectError("item", null,null,"가격 * 수량의 합은 10,000원 이상이어야 합니다. 현재 값 = "+ resultPrice));
                bindingResult.rejectValue("price",null,null, "가격 * 수량의 합은 10,000원 이상이어야 합니다. 현재 값 = "+ resultPrice);
            }
        }

        //검증 실패시 다시 입력 폼으로
        if (bindingResult.hasErrors()){
            log.info("errors={}", bindingResult);

            model.addAttribute("deliveryCodes", ItemService.deliveryCodes());
            model.addAttribute("itemTypes", ItemService.itemTypes());
            model.addAttribute("region", ItemService.region());

            return "basicV4/addForm";
        }

        //성공 로직
        Item item = new Item(form);

        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId",savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/basicV4/items/{itemId}";
    }

    @GetMapping("/remove/{itemId}")
    public String removeItem(@PathVariable(name = "itemId")long itemId, Model model) {
        log.info("item.itemId={}", itemId);
        itemRepository.removeItem(itemId);
        return "redirect:/basicV4/items";
    }


    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model){
        model.addAttribute("deliveryCodes", ItemService.deliveryCodes());
        model.addAttribute("itemTypes", ItemService.itemTypes());
        model.addAttribute("region", ItemService.region());
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item",item);
        return "basicV4/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String editForm2(
            @PathVariable long itemId, Model model,
            @Validated @ModelAttribute("item") ItemUpdateForm itemUpdateForm,
            BindingResult bindingResult) {

        log.info("itemUpdateForm={}",itemUpdateForm);

        if (itemUpdateForm.getPrice() != null && itemUpdateForm.getQuantity() != null) {
            int resultPrice = itemUpdateForm.getPrice() * itemUpdateForm.getQuantity();
            if (resultPrice < 10000) {
                //bindingResult.addError(new ObjectError("item", null,null,"가격 * 수량의 합은 10,000원 이상이어야 합니다. 현재 값 = "+ resultPrice));
                bindingResult.rejectValue("price",null,null, "가격 * 수량의 합은 10,000원 이상이어야 합니다. 현재 값 = "+ resultPrice);
            }
        }

        if (bindingResult.hasErrors()){
            log.info("errors={}",bindingResult);

            model.addAttribute("deliveryCodes", ItemService.deliveryCodes());
            model.addAttribute("itemTypes", ItemService.itemTypes());
            model.addAttribute("region", ItemService.region());

            return "basicV4/editForm";
        }

        Item item = new Item(itemUpdateForm);

        itemService.update(itemId,item);
        log.info("end");
        return "redirect:/basicV4/items/{itemId}";
    }
}
