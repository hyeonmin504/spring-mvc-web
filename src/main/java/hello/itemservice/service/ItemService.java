package hello.itemservice.service;

import hello.itemservice.domain.Item;
import hello.itemservice.domain.dto.ItemDto;
import hello.itemservice.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ItemService {

    @Autowired
    ItemRepository itemRepository;

    @Transactional
    public void update(Long itemId, ItemDto itemDto) {
        Item item = itemRepository.findById(itemId);
        item.setItemName(itemDto.getItemName());
        item.setPrice(itemDto.getPrice());
        item.setQuantity(itemDto.getQuantity());
    }
}
