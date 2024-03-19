package hello.itemservice.service;

import hello.itemservice.domain.DeliveryCode;
import hello.itemservice.domain.Item;
import hello.itemservice.domain.ItemType;
import hello.itemservice.domain.dto.ItemDto;
import hello.itemservice.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {

    @Autowired
    ItemRepository itemRepository;

    @Transactional
    public void update(Long itemId, Item item) {
        Item findItem = itemRepository.findById(itemId);
        findItem.setItemName(item.getItemName());
        findItem.setPrice(item.getPrice());
        findItem.setQuantity(item.getQuantity());
        findItem.setOpen(item.getOpen());
        findItem.setRegions(item.getRegions());
        findItem.setItemType(item.getItemType());
        findItem.setDeliveryCode(item.getDeliveryCode());
    }

    public static List<String> region() {
        List<String> regions = new LinkedList<>();
        regions.add("서울");
        regions.add("부산");
        regions.add("제주");
        return regions;
    }

    public static ItemType[] itemTypes() {
        return ItemType.values();
    }

    public static List<DeliveryCode> deliveryCodes() {
        List<DeliveryCode> deliveryCodes = new ArrayList<>();
        deliveryCodes.add(new DeliveryCode("FAST", "빠른 배송"));
        deliveryCodes.add(new DeliveryCode("NORMAL", "일반 배송"));
        deliveryCodes.add(new DeliveryCode("SLOW", "느린 배송"));
        return deliveryCodes;
    }
}
