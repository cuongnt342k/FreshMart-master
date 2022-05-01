package com.dt.ducthuygreen.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderDTO {
    private Long id;

    @Email
    @NotNull
    private String email;

    @NotNull
    @NotBlank
    private String firstName;

    @NotNull
    @NotBlank
    private String lastName;

    @NotNull
    @NotBlank
    private String address;

    @NotNull
    @NotBlank
    private String phone;

    @NotNull
    @NotBlank
    private String postcode;

    @NotNull
    private Long totalPrice;

    @NotNull
    private Long totalQuantity;

    private Boolean status;

    private Integer confirm;

    private String userName;

}
