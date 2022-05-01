package com.dt.ducthuygreen.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Review")
public class Review extends BaseModel implements Serializable {

    @Column
    private Integer evaluate;

    @Column
    private String comment;

    @ManyToOne( cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    @JsonIgnore
    Product product = new Product();

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @JsonIgnore
    User user = new User();
}
