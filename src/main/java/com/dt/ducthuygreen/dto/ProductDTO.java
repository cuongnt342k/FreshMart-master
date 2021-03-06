package com.dt.ducthuygreen.dto;

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
public class ProductDTO {

    private Long id;

    @NotNull
    @NotBlank
    private String productName;

    private String image;

    @NotNull
    private Long price;

    private String description;

    @NotNull
    private Integer quantity;

    private Integer sold;

}
