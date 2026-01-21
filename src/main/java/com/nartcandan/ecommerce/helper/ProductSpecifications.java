package com.nartcandan.ecommerce.helper;

import com.nartcandan.ecommerce.domain.dtos.ProductFilter;
import com.nartcandan.ecommerce.domain.entities.Product;
import com.nartcandan.ecommerce.domain.enums.ProductStatus;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.util.UUID;

public class ProductSpecifications {

    public static Specification<Product> withFilter(ProductFilter filter) {
        return  Specification.allOf(categoryEquals(filter.getCategoryId()))
                .and(statusEquals(filter.getStatus()))
                .and(priceGreaterThanOrEqual(filter.getMinPrice()))
                .and(priceLessThanOrEqual(filter.getMaxPrice()))
                .and(inStockOnly(filter.getInStockOnly()))
                .and(search(filter.getSearch()));
    }

    private static Specification<Product> categoryEquals(UUID categoryId) {
        return (root, query, cb) -> categoryId == null
                ? null
                : cb.equal(root.get("category").get("id"), categoryId);
    }

    private static Specification<Product> statusEquals(ProductStatus status) {
        return (root, query, cb) -> status == null
                ? null
                : cb.equal(root.get("status"), status);
    }

    private static Specification<Product> priceGreaterThanOrEqual(BigDecimal minPrice) {
        return (root, query, cb) -> minPrice == null
                ? null
                : cb.greaterThanOrEqualTo(root.get("price"), minPrice);
    }

    private static Specification<Product> priceLessThanOrEqual(BigDecimal maxPrice) {
        return (root, query, cb) -> maxPrice == null
                ? null
                : cb.lessThanOrEqualTo(root.get("price"), maxPrice);
    }

    private static Specification<Product> inStockOnly(Boolean inStockOnly) {
        return (root, query, cb) -> Boolean.TRUE.equals(inStockOnly)
                ? cb.greaterThan(root.get("quantity"), 0)
                : null;
    }

    private static Specification<Product> search(String search) {
        return (root, query, cb) -> {
            if (search == null || search.isBlank()) {
                return null;
            }
            String like = "%" + search.trim().toLowerCase() + "%";
            return cb.or(
                    cb.like(cb.lower(root.get("name")), like),
                    cb.like(cb.lower(root.get("description")), like)
            );
        };
    }
}
