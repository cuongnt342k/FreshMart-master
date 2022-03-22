package com.dt.ducthuygreen.repos;

import com.dt.ducthuygreen.entities.Cart;
import com.dt.ducthuygreen.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dt.ducthuygreen.entities.Item;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    void deleteAllByCart(Cart cart);

    Item findItemByCartAndProductAndStatusIsFalseAndDeletedIsFalse(Cart cart, Product product);

    List<Item> findItemByCartAndStatusIsFalseAndDeletedIsFalse(Cart cart);

    void deleteById(Long id);

    Item findItemById(Long id);
}
