package com.dt.ducthuygreen.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Product")
public class Product extends BaseModel implements Serializable {
	@Column(name = "product_name")
    private String productName;

    @Column(name = "image")
    private String image;

    @Column(name = "price")
    private Long price;

    @Column(name = "description")
    private String description;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "sold")
    private Integer sold;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    @JsonIgnore
    Category category = new Category();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Review> reviews = new ArrayList<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnore
    private List<Item> items = new ArrayList<>();

}
