package com.dt.ducthuygreen.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CategoryDTO {

    @NotBlank
	private String categoryName;

    private String description;
}
