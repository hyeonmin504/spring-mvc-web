package hello.itemservice.repository;

import hello.itemservice.domain.Item;
import hello.itemservice.domain.ItemType;
import hello.itemservice.domain.dto.ItemDto;
import hello.itemservice.service.ItemService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class ItemRepositoryTest {

    ItemRepository itemRepository = new ItemRepository();
    ItemService itemService = new ItemService();

    void afterEach() {
        itemRepository.clearStore();
    }

    @Test
    public void save() throws Exception {
        //given
        Item item = new Item("itemA", 10000, 10);
        //when
        Item savedItem = itemRepository.save(item);
        //then
        Item findItem = itemRepository.findById(item.getId());
        assertThat(savedItem).isEqualTo(findItem);
    }

    @Test
    public void findAll() throws Exception {
        //given
        Item item1 = new Item("item1", 10000, 10);
        Item item2 = new Item("item2", 20000, 20);

        itemRepository.save(item1);
        itemRepository.save(item2);
        //when
        List<Item> all = itemRepository.findAll();
        //then
        assertThat(all.size()).isEqualTo(2);
        assertThat(all).contains(item1,item2);
    }

    @Test
    public void updateItem() throws Exception {
        //given
        Item item = new Item("item1", 10000, 10);

        Item savedItem = itemRepository.save(item);

        List<String> regions = new ArrayList<>();
        regions.add("서울");

        //when

        //then
    }

}