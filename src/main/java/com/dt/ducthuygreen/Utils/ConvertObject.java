package com.dt.ducthuygreen.Utils;

import com.dt.ducthuygreen.dto.CartDTO;
import com.dt.ducthuygreen.dto.ItemDTO;
import com.dt.ducthuygreen.dto.OrderDTO;
import com.dt.ducthuygreen.dto.ProductDTO;
import com.dt.ducthuygreen.dto.UserDTO;
import com.dt.ducthuygreen.entities.Cart;
import com.dt.ducthuygreen.entities.Item;
import com.dt.ducthuygreen.entities.Order;
import com.dt.ducthuygreen.entities.Product;
import com.dt.ducthuygreen.entities.User;

public class ConvertObject {
	public static User convertUserDTOToUser(User user, UserDTO userDTO) {
		if(userDTO.getUsername()!=null)
        user.setUsername(userDTO.getUsername());
		
		if(userDTO.getEmail()!=null)
        user.setEmail(userDTO.getEmail());
		
		if(userDTO.getFullName()!=null)
        user.setFullName(userDTO.getFullName());
		
		if(userDTO.getDescription()!=null)
        user.setDescription(userDTO.getDescription());
		
		if(userDTO.getFullName()!=null)
        user.setFullName(userDTO.getFullName());
		
		if(userDTO.getStatus()!=null)
        user.setStatus(userDTO.getStatus());

		if(userDTO.getCart()!=null)
			user.setCart(userDTO.getCart());

        return user;
    }
	
	public static Product convertProductDTOToProduct(Product product, ProductDTO productDTO) {
		if(productDTO.getProductName()!=null)
		product.setProductName(productDTO.getProductName());
		
		if(productDTO.getImage()!=null)
		product.setImage(productDTO.getImage());
		
		if(productDTO.getPrice()!=null)
		product.setPrice(productDTO.getPrice());
		
		if(productDTO.getDescription()!=null)
		product.setDescription(productDTO.getDescription());
		
		if(productDTO.getQuantity()!=null)
		product.setQuantity(productDTO.getQuantity());
		
		if(productDTO.getSold()!=null)
		product.setSold(productDTO.getSold());
		
		return product;
	}
	
	public static Cart convertCartDTOTOCart(CartDTO cartDTO) {
		Cart cart = new Cart();
		if (cartDTO.getQuantity()!=null)
		cart.setQuantity(cartDTO.getQuantity());
		
		if (cartDTO.getItems()!=null)
		cart.setItems(cartDTO.getItems());
		return cart;
	}
	
	public static Item convertItemDTOTOItem(ItemDTO itemDTO) {
		return new Item(itemDTO.getQuantity(), itemDTO.getPrice(), itemDTO.getStatus());
	}
	
	public static Order convertOrderDTOToOrder( Order order,OrderDTO orderDTO) {
		if (orderDTO.getEmail()!=null)
		order.setEmail(orderDTO.getEmail());
		
		if (orderDTO.getFirstName()!=null)
		order.setFirstName(orderDTO.getFirstName());
		
		if (orderDTO.getLastName()!=null)
		order.setLastName(orderDTO.getLastName());
		
		if (orderDTO.getAddress()!=null)
		order.setAddress(orderDTO.getAddress());
		
		if (orderDTO.getPhone()!=null)
		order.setPhone(orderDTO.getPhone());
		
		if (orderDTO.getPostcode()!=null)
		order.setPostcode(orderDTO.getPostcode());
		
		if (orderDTO.getTotalPrice()!=null)
		order.setTotalPrice(orderDTO.getTotalPrice());
		
		if (orderDTO.getTotalQuantity()!=null)
		order.setTotalQuantity(orderDTO.getTotalQuantity());
		
		return order;
	}
}