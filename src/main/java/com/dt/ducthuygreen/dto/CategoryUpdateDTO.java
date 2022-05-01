package com.dt.ducthuygreen.dto;

import java.util.Date;

import com.dt.ducthuygreen.entities.BaseModel;

import lombok.*;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CategoryUpdateDTO extends BaseModel {

	@NotBlank
	private String categoryName;

    private String description;

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public CategoryUpdateDTO(Long id, Boolean deleted, Date createdDate, String createdBy, Date updatedDate,
			String updatedBy, String categoryName, String description) {
		super(id, deleted, createdDate, createdBy, updatedDate, updatedBy);
		this.categoryName = categoryName;
		this.description = description;
	}

	public CategoryUpdateDTO(Long id, Boolean deleted, Date createdDate, String createdBy, Date updatedDate,
			String updatedBy) {
		super(id, deleted, createdDate, createdBy, updatedDate, updatedBy);
	}

}
