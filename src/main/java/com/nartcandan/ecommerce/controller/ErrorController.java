package com.nartcandan.ecommerce.controller;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.nartcandan.ecommerce.domain.dtos.ApiErrorResponse;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
@Slf4j
public class ErrorController {
    // ===============================
    // 400 - IllegalArgument
    // ===============================
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
        log.warn("IllegalArgumentException: {}", ex.getMessage());

        ApiErrorResponse error = ApiErrorResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message(ex.getMessage())
                .build();

        return ResponseEntity.badRequest().body(error);
    }

    // ===============================
    // 409 - IllegalState
    // ===============================
    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ApiErrorResponse> handleIllegalStateException(IllegalStateException ex) {
        log.warn("IllegalStateException: {}", ex.getMessage());

        ApiErrorResponse error = ApiErrorResponse.builder()
                .status(HttpStatus.CONFLICT.value())
                .message(ex.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    // ===============================
    // 400 - @Valid DTO Errors
    // ===============================
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {

        List<ApiErrorResponse.FieldError> fieldErrors =
                ex.getBindingResult()
                        .getFieldErrors()
                        .stream()
                        .map(f -> new ApiErrorResponse.FieldError(
                                f.getField(),
                                f.getDefaultMessage()))
                        .toList();

        ApiErrorResponse error = ApiErrorResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message("Validation failed")
                .errors(fieldErrors)
                .build();

        return ResponseEntity.badRequest().body(error);
    }

    // ===============================
    // 400 - @RequestParam / @PathVariable type mismatch
    // ===============================
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiErrorResponse> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex) {

        String fieldName = ex.getName();
        String requiredType =
                ex.getRequiredType() != null
                        ? ex.getRequiredType().getSimpleName()
                        : "Unknown";

        ApiErrorResponse.FieldError fieldError =
                new ApiErrorResponse.FieldError(
                        fieldName,
                        requiredType + " Formatting error"
                );

        ApiErrorResponse error = ApiErrorResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message("Invalid request parameter")
                .errors(List.of(fieldError))
                .build();

        return ResponseEntity.badRequest().body(error);
    }

    // ===============================
    // 400 - JSON parse errors (UUID, Integer, Enum, Date...)
    // ===============================
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiErrorResponse> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {

        List<ApiErrorResponse.FieldError> fieldErrors = new ArrayList<>();

        Throwable cause = ex.getMostSpecificCause();

        if (cause instanceof InvalidFormatException ife) {

            for (JsonMappingException.Reference ref : ife.getPath()) {
                String fieldName = ref.getFieldName();
                String targetType = ife.getTargetType().getSimpleName();

                fieldErrors.add(
                        new ApiErrorResponse.FieldError(
                                fieldName,
                                targetType + " Formatting error"
                        )
                );
            }
        }

        ApiErrorResponse error = ApiErrorResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message("Invalid request body")
                .errors(fieldErrors.isEmpty() ? null : fieldErrors)
                .build();

        return ResponseEntity.badRequest().body(error);
    }


    // ===============================
    // 500 - Catch ALL
    // ===============================
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleException(Exception ex) {
        log.error("Unexpected Exception", ex);

        ApiErrorResponse error = ApiErrorResponse.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message("An unexpected error occurred")
                .build();

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(error);
    }
}