package com.nartcandan.ecommerce.mapper;

import com.nartcandan.ecommerce.domain.dtos.CreateProductRequest;
import com.nartcandan.ecommerce.domain.dtos.ProductDetailDto;
import com.nartcandan.ecommerce.domain.dtos.ProductListDto;
import com.nartcandan.ecommerce.domain.dtos.UpdateProductRequest;
import com.nartcandan.ecommerce.domain.entities.Category;
import com.nartcandan.ecommerce.domain.entities.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(target = "shortDescription", expression = "java(truncate(product.getDescription(), 150))")
    @Mapping(target = "categoryId", source = "category.id")
    @Mapping(target = "categoryName", source = "category.name")
    ProductListDto toListDto(Product product);

    List<ProductListDto> toListDtos(List<Product> products);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "category", ignore = true)
    Product toEntity(CreateProductRequest request);

    @Mapping(target = "category", expression = "java(mapCategory(product.getCategory()))")
    ProductDetailDto toDetailDto(Product product);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    void updateEntityFromDto(UpdateProductRequest request,
                             @MappingTarget Product product);

    default ProductDetailDto.CategoryInfo mapCategory(Category category) {
        if (category == null) {
            return null;
        }
        return ProductDetailDto.CategoryInfo.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }

    default String truncate(String value, int limit) {
        if (value == null || value.length() <= limit) {
            return value;
        }
        return value.substring(0, limit) + "...";
    }
}