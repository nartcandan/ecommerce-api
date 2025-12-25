package com.nartcandan.ecommerce.repository;

import com.nartcandan.ecommerce.domain.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CategoryRepository extends JpaRepository<Category, UUID> {
    @Query("SELECT c FROM Category c LEFT JOIN FETCH c.products")
    List<Category> findAllWithProductCount();
    @Query("SELECT c FROM Category c LEFT JOIN FETCH c.products WHERE c.id = :id")
    Optional<Category> findByIdWithProductCount(@Param("id") UUID id);
    boolean existsByNameIgnoreCase(String name);
}
