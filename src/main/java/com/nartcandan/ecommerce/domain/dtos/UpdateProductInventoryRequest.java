package com.nartcandan.ecommerce.domain.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateProductInventoryRequest {

    @NotNull(message = "Operation cannot be a null")
    private InventoryOperation operation;

    @NotNull
    @Positive(message = "Amount must be a positive")
    private Integer amount;

    public enum InventoryOperation {
        INCREASE,
        DECREASE
    }
}
