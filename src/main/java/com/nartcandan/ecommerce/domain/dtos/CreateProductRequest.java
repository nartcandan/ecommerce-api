package com.nartcandan.ecommerce.domain.dtos;

import com.nartcandan.ecommerce.domain.enums.ProductStatus;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateProductRequest {
    @NotBlank(message = "Product name is reqired")
    @Size(min = 2, max = 50)
    @Pattern(regexp = "^[\\w\\s-]+$", message = "Category name can only contain letters, numbers, space and hyphens")
    private String name;
    @NotBlank
    private String description;
    @NotNull(message = "Price cannot be null")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
    private BigDecimal price;
    @NotNull @PositiveOrZero(message = "quantity cannot be negative")
    Integer quantity;
    @NotBlank
    private String imageUrl;
    @NotNull
    private UUID categoryId;
    private ProductStatus status;
}
