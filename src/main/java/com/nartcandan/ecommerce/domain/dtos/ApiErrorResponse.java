package com.nartcandan.ecommerce.domain.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class ApiErrorResponse {
    private int status;
    private String message;
    private List<FieldError> errors;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Slf4j
    public static class FieldError {
        private String field;
        private String message;
    }
}
