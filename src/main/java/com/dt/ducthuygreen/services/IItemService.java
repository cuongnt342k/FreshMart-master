package com.dt.ducthuygreen.services;

import java.util.List;

import com.dt.ducthuygreen.dto.ItemDTO;
import com.dt.ducthuygreen.entities.Item;

public interface IItemService {
    Item getItemById(Long id);

    List<Item> getAllItem();

    List<Item> getItemByUserName(String userName);

    Item creatNewItem(ItemDTO itemDTO, Long productId, String userId);

    Item creatNewItemWithAccountFaceBook(ItemDTO itemDTO, Long productId, String username);

    void deleteItemById(Long id);

    boolean update(ItemDTO itemDTO);
}
