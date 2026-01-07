package com.nartcandan.ecommerce.domain.dtos;

import com.nartcandan.ecommerce.domain.enums.ProductStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDetailDto {

    private UUID id;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer quantity;
    private String imageUrl;
    private ProductStatus status;
    private CategoryInfo category;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CategoryInfo {
        private UUID id;
        private String name;
    }
}
