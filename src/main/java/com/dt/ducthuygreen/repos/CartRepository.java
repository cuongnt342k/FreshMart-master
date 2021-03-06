package com.dt.ducthuygreen.repos;

import com.dt.ducthuygreen.entities.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findCartByUserName(String username);
}
