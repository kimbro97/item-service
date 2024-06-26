package hello.itemservice.web.basic;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/basic/items")
@RequiredArgsConstructor
public class BasicItemController {

    private final ItemRepository itemRepository;

    @GetMapping
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "basic/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "basic/item";
    }

    @GetMapping("/add")
    public String addForm() {
        return "basic/addForm";
    }

//    @PostMapping("/add")
//    public String addItem1(
//            @RequestParam String itemName,
//            @RequestParam int price,
//            @RequestParam int quantity,
//            Model model) {
//        Item item = new Item();
//        item.setItemName(itemName);
//        item.setPrice(price);
//        item.setQuantity(quantity);
//        Item savedItem = itemRepository.save(item);
//        model.addAttribute("item", savedItem);
//
//        return "basic/item";
//    }

//    @PostMapping("/add")
//    public String addItem2(@ModelAttribute("item") Item item) {
//
//        itemRepository.save(item);
//
//        return "basic/item";
//    }

//    @PostMapping("/add")
//    public String addItem3(@ModelAttribute Item item) {
//
//        itemRepository.save(item);
//
//        return "basic/item";
//    }

//    @PostMapping("/add")
//    public String addItem4(Item item) {
//
//        itemRepository.save(item);
//
//        return "basic/item";
//    }

//    @PostMapping("/add")
//    public String addItem5(Item item) {
//
//        itemRepository.save(item);
//
//        return "redirect:/basic/items/" + item.getId();
//    }

    @PostMapping("/add")
    public String addItem5(Item item, RedirectAttributes redirectAttributes) {

        Item savedItem = itemRepository.save(item);

        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);

        return "redirect:/basic/items/{itemId}";
    }

    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "basic/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId, @ModelAttribute Item item) {
        itemRepository.update(itemId, item);
        return "redirect:/basic/items/{itemId}";
    }

    @PostConstruct
    public void init() {
        itemRepository.save(new Item("ItemA", 10000, 10));
        itemRepository.save(new Item("ItemB", 20000, 20));
    }
}
