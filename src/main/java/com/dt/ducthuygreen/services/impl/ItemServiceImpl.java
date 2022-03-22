package com.dt.ducthuygreen.services.impl;

import com.dt.ducthuygreen.Utils.ConvertObject;
import com.dt.ducthuygreen.dto.ItemDTO;
import com.dt.ducthuygreen.entities.*;
import com.dt.ducthuygreen.exception.NotFoundException;
import com.dt.ducthuygreen.repos.ItemRepository;
import com.dt.ducthuygreen.repos.OrderRepository;
import com.dt.ducthuygreen.services.IItemService;
import com.dt.ducthuygreen.services.ProductServices;
import com.dt.ducthuygreen.services.UserService;
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
    private ProductServices productService;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UserService userService;

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
        return itemRepository.findItemByCartAndStatusIsFalseAndDeletedIsFalse(userService.findByUsername(userName).getCart());
    }

    @Override
    public Item creatNewItem(ItemDTO itemDTO, Long productId, String userId) {
        Product product = productService.getProductById(productId);
        if (product == null) {
            throw new NotFoundException("ProductId is not containt");
        }

        User user = userService.findByUsername(userId);

        Cart cart = user.getCart();

//        Order order = orderRepository.findTop1ByStatusIsFalseAndCart_id(cart.getId());
//        if (order == null) {
//            order = new Order();
//            order.setUser_id(user.getId());
//            String string = user.getFullName();
//            String[] parts = string.split(" ");
//            order.setFirstName(parts[0]);
//            order.setLastName(parts[0]);
//            order.setEmail(user.getEmail());
//        }

        if (user == null) {
            throw new NotFoundException("User id is not containt");
        }
        //cart k có thì tạo
        if (cart == null) {
            cart = new Cart();
            cart.setCreatedBy(user.getFullName());
            cart.setItems(new ArrayList<Item>());
            cart.setQuantity(1L);
            cart.setCreatedBy(user.getUsername());

        } else {
            cart.setQuantity(cart.getQuantity() + 1L);
            cart.setUpdatedBy(user.getUsername());
        }
        Item item = itemRepository.findItemByCartAndProductAndStatusIsFalseAndDeletedIsFalse(cart, product);
        if (item != null) {
            item.setQuantity(item.getQuantity() + itemDTO.getQuantity());
            item.setPrice(product.getPrice() * item.getQuantity());
            item.setStatus(false);
            item.setUpdatedBy(user.getUsername());
        } else {
            item = ConvertObject.convertItemDTOTOItem(itemDTO);
            item.setProduct(product);
            item.setCart(cart);
            item.setOrder(null);
            item.setDeleted(false);
            item.setStatus(false);
            item.setPrice(product.getPrice() * item.getQuantity());

            item.setCreatedBy(user.getUsername());
        }
//        Item item = ConvertObject.convertItemDTOTOItem(itemDTO);

        List<Item> items = cart.getItems();
        items.add(item);
        Item newItem = itemRepository.save(item);
        itemRepository.save(newItem);
        return newItem;
    }

    @Override
    public void deleteItemById(Long id) {
        try {
           Item item = itemRepository.findItemById(id);
            item.setDeleted(true);
           itemRepository.save(item);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
