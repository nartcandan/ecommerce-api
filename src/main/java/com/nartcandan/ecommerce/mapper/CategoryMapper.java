package com.nartcandan.ecommerce.mapper;

import com.nartcandan.ecommerce.domain.dtos.CategoryDto;
import com.nartcandan.ecommerce.domain.dtos.CreateCategoryRequest;
import com.nartcandan.ecommerce.domain.entities.Category;
import com.nartcandan.ecommerce.domain.entities.Product;
import com.nartcandan.ecommerce.domain.enums.ProductStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CategoryMapper {

    @Mapping(target = "productCount", source = "products", qualifiedByName = "calculateProductCount")
    CategoryDto toDto(Category category);

    Category toEntity(CreateCategoryRequest createCategoryRequest);

    @Named("calculateProductCount")
    default long calculatePostCount(List<Product> posts){
        if (posts == null){
            return 0;
        }

        return posts.stream().filter(product -> ProductStatus.ACTIVE.equals(product.getStatus()))
                .count();
    }

}
