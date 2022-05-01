package com.dt.ducthuygreen.dto;

import com.dt.ducthuygreen.entities.Product;
import com.dt.ducthuygreen.entities.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ReviewDTO {

    @NotNull
    private Integer evaluate;

    @NotBlank
    private String comment;

    ProductDTO product = new ProductDTO();

    UserDTO user = new UserDTO();
}
