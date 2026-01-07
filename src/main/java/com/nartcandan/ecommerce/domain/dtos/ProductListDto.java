package com.nartcandan.ecommerce.domain.dtos;

import com.nartcandan.ecommerce.domain.enums.ProductStatus;
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
public class ProductListDto {
    private UUID id;
    private String name;
    private String shortDescription;
    private BigDecimal price;
    private Integer quantity;
    private String imageUrl;
    private ProductStatus status;
    private UUID categoryId;
    private String categoryName;
}
