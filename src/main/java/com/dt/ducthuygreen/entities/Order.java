package com.dt.ducthuygreen.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Orders")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Order extends BaseModel implements Serializable {

    @Column(name = "email")
    private String email;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "status")
    private Boolean status;

    @Column(name = "confirm")
    private Integer confirm;

    @Column(name = "address")
    private String address;

    @Column(name = "phone")
    private String phone;

    @Column(name = "postcode")
    private String postcode;

    @Column(name = "total_price")
    private Long totalPrice;

    @Column(name = "total_quantity")
    private Long totalQuantity;

    @Column(name = "user_name")
    private String userName;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    @JsonIgnoreProperties(value={"hibernateLazyInitializer"})
    private List<Item> items = new ArrayList<>();


}
