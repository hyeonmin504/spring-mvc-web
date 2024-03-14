package hello.itemservice.repository;

import hello.itemservice.domain.Item;
import hello.itemservice.domain.dto.ItemDto;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class ItemRepository {

    @Autowired
    EntityManager em;

    private static long sequence = 0L;

    public Item save(Item item) {
        item.setId(++sequence);
        em.persist(item);
        return item;
    }

    public Item findById(Long id) {
        return em.find(Item.class,id);
    }

    public List<Item> findAll() {
        return em.createQuery("select i from item i",Item.class)
                .getResultList();
    }

    public void removeItem(Long itemId) {
        Item findItem = findById(itemId);
        em.remove(findItem);
    }

    public void clearStore() {
        em.remove(findAll());
    }
}
