package hello.itemservice.repository;

import hello.itemservice.domain.Item;
import hello.itemservice.domain.dto.ItemDto;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Transactional
public class ItemRepository {

    @Autowired
    EntityManager em;

    private static final Map<Long, Item> store = new ConcurrentHashMap<>();
    private static long sequence = 0L;

    public Item save(Item item) {
        item.setId(++sequence);
        em.persist(item);
        store.put(item.getId(),item);
        return item;
    }

    public Item findById(Long id) {
        return store.get(id);
    }

    public List<Item> findAll() {
        return new ArrayList<>(store.values());
    }

    @Transactional
    public void update(Long itemId, ItemDto itemDto){
        Item findItem = findById(itemId);
        findItem.setItemName(itemDto.getItemName());
        findItem.setPrice(itemDto.getPrice());
        findItem.setQuantity(itemDto.getQuantity());
    }

    public void creaStore() {
        store.clear();
    }
}
