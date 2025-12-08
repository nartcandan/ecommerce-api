package com.nartcandan.ecommerce.domain.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateCategoryRequest {
//    @NotBlank(message = "Category name is required")
//    @Size(min = 2, max = 50, message = "Category name must be between {min} and {max} characters")
//    @Pattern(regexp = "^[\\w\\s-]+$", message = "Category bane can only contain letters, numbers, space and hyphens")
    private String name;

}
