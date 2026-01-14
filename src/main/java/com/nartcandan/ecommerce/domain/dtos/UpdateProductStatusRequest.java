package com.nartcandan.ecommerce.domain.dtos;

import com.nartcandan.ecommerce.domain.enums.ProductStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateProductStatusRequest {

    @NotNull(message = "Status can not be null")
    private ProductStatus status;
}
