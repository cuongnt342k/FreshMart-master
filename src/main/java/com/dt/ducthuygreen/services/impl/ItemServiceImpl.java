package com.dt.ducthuygreen.services.impl;

import com.dt.ducthuygreen.Utils.ConvertObject;
import com.dt.ducthuygreen.dto.ItemDTO;
import com.dt.ducthuygreen.entities.*;
import com.dt.ducthuygreen.exception.NotFoundException;
import com.dt.ducthuygreen.repos.CartRepository;
import com.dt.ducthuygreen.repos.ItemRepository;
import com.dt.ducthuygreen.repos.OrderRepository;
import com.dt.ducthuygreen.services.ICartService;
import com.dt.ducthuygreen.services.IItemService;
import com.dt.ducthuygreen.services.IProductServices;
import com.dt.ducthuygreen.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ItemServiceImpl implements IItemService {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private IProductServices productService;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private IUserService userService;

    @Override
    public Item getItemById(Long id) {
        Optional<Item> optional = itemRepository.findById(id);
        return optional.isPresent() ? optional.get() : null;
    }

    @Override
    public List<Item> getAllItem() {
        return itemRepository.findAll();
    }

    @Override
    public List<Item> getItemByUserName(String userName) {
        return itemRepository.findItemByCartAndStatusIsFalseAndDeletedIsFalse(cartRepository.findCartByUserName(userName));
    }

    @Override
    public Item creatNewItem(ItemDTO itemDTO, Long productId, String username) {
        Product product = productService.getProductById(productId);
        if (product == null) {
            throw new NotFoundException("ProductId is not containt");
        }
        Cart cart = cartRepository.findCartByUserName(username);
        if (cart == null) {
            cart = new Cart();
            cart.setCreatedBy(username);
            cart.setItems(new ArrayList<Item>());
            cart.setQuantity(1L);
            cart.setCreatedBy(username);
            cart.setUserName(username);
            cartRepository.save(cart);
        } else {
            cart.setQuantity(cart.getQuantity() + 1L);
            cart.setUpdatedBy(username);
        }
        Item item = itemRepository.findItemByCartAndProductAndStatusIsFalseAndDeletedIsFalse(cart, product);
        if (item != null) {
            item.setQuantity(item.getQuantity() + itemDTO.getQuantity());
            item.setPrice(product.getPrice() * item.getQuantity());
            item.setStatus(false);
            item.setUpdatedBy(username);
        } else {
            item = ConvertObject.convertItemDTOTOItem(itemDTO);
            item.setProduct(product);
            item.setCart(cart);
            item.setOrder(null);
            item.setDeleted(false);
            item.setStatus(false);
            item.setPrice(product.getPrice() * item.getQuantity());
            item.setCreatedBy(username);
        }

        List<Item> items = cart.getItems();
        items.add(item);
        Item newItem = itemRepository.save(item);
        itemRepository.save(newItem);
        if (product.getQuantity() > 0){
            product.setQuantity(product.getQuantity() - item.getQuantity());
            productService.save(product);
        }
        return newItem;
    }

    @Override
    public Item creatNewItemWithAccountFaceBook(ItemDTO itemDTO, Long productId, String username) {
        Product product = productService.getProductById(productId);
        if (product == null) {
            throw new NotFoundException("ProductId is not containt");
        }
        Cart cart = cartRepository.findCartByUserName(username);
        if (cart == null) {
            cart = new Cart();
            cart.setCreatedBy(username);
            cart.setItems(new ArrayList<Item>());
            cart.setQuantity(1L);
            cart.setCreatedBy(username);
            cartRepository.save(cart);
        } else {
            cart.setQuantity(cart.getQuantity() + 1L);
            cart.setUpdatedBy(username);
        }
        Item item = itemRepository.findItemByCartAndProductAndStatusIsFalseAndDeletedIsFalse(cart, product);
        if (item != null) {
            item.setQuantity(item.getQuantity() + itemDTO.getQuantity());
            item.setPrice(product.getPrice() * item.getQuantity());
            item.setStatus(false);
            item.setUpdatedBy(username);
        } else {
            item = ConvertObject.convertItemDTOTOItem(itemDTO);
            item.setProduct(product);
            item.setCart(cart);
            item.setOrder(null);
            item.setDeleted(false);
            item.setStatus(false);
            item.setPrice(product.getPrice() * item.getQuantity());
            item.setCreatedBy(username);
        }

        List<Item> items = cart.getItems();
        items.add(item);
        Item newItem = itemRepository.save(item);
        itemRepository.save(newItem);
        if (product.getQuantity() > 0){
            product.setQuantity(product.getQuantity() - item.getQuantity());
            productService.save(product);
        }
        return newItem;
    }

    @Override
    public void deleteItemById(Long id) {
        try {
            Item item = itemRepository.findItemById(id);
            item.setDeleted(true);
            itemRepository.save(item);
            Cart cart = cartRepository.getById(item.getCart().getId());
            cart.setQuantity(cart.getQuantity() - item.getQuantity());
            cartRepository.save(cart);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
