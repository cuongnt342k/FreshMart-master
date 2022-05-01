package com.dt.ducthuygreen.services.impl;

import java.util.Optional;

import com.dt.ducthuygreen.repos.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dt.ducthuygreen.Utils.ConvertObject;
import com.dt.ducthuygreen.dto.CartDTO;
import com.dt.ducthuygreen.entities.Cart;
import com.dt.ducthuygreen.entities.User;
import com.dt.ducthuygreen.exception.NotFoundException;
import com.dt.ducthuygreen.repos.CartRepository;
import com.dt.ducthuygreen.services.ICartService;
import com.dt.ducthuygreen.services.IUserService;

@Service
public class CartServiceImpl implements ICartService {

    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private IUserService IUserService;
    @Autowired
    private ItemRepository itemRepository;

    @Override
    public Cart getById(Long id) {
        // TODO Auto-generated method stub
        Optional<Cart> cart = cartRepository.findById(id);
        return cart.isPresent() ? cart.get() : null;
    }

    @Override
    public Cart createNewCart(CartDTO cartDTO, Long userId) {
        User user = IUserService.findById(userId);
        if (user == null) {
            throw new NotFoundException("UserId is not containt");
        }

//		cartDTO.setUserId(userId);
        Cart cart = ConvertObject.convertCartDTOTOCart(cartDTO);
        cart.setUserName(user.getUsername());
        return cartRepository.save(cart);
    }

    @Override
    public Cart getCartByUserName(String userName) {
        return cartRepository.findCartByUserName(userName);
    }

    @Override
    public void deleteCartById(Long id) {
        Cart cart = getById(id);

        if (cart == null) {
            throw new NotFoundException("Can not find cartId: " + id);
        }

        cartRepository.deleteById(cart.getId());
    }

    @Override
    public void deleteItemByUserId(Long userId) {
        Cart cart = IUserService.findById(userId).getCart();
//		carts = carts.stream().filter(item -> item.getUser_id() == userId).collect(Collectors.toList());
        itemRepository.deleteAllByCart(cart);

    }

    @Override
    public Cart save(Cart cart) {
        return cartRepository.save(cart);
    }

}
