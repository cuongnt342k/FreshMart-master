package com.dt.ducthuygreen.repos;

import com.dt.ducthuygreen.entities.Item;
import com.dt.ducthuygreen.entities.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    //	Order getTopByStatusIsFalseAndCart_id(Long cartID);
    Page<Order> findAllByDeletedIsFalse(Pageable pageable);

    Page<Order> findAllByUserNameAndDeletedFalse(Pageable pageable,String userName);

    Page<Order> findAllByDeletedIsFalseAndAddressContainsOrDeletedIsFalseAndEmailContainsOrDeletedIsFalseAndPostcodeContainsOrDeletedIsFalseAndFirstNameContainsOrDeletedIsFalseAndLastNameContains(Pageable pageable, String address, String email, String postcode, String firstName, String lastName);
}
