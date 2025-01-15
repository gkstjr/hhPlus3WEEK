package hhplus.ecommerce.support.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusinessException(BusinessException ex) {
        ErrorResponse errorResponse = ErrorResponse.of(ex.getErrorCode() , ex.getMessage());
        return new ResponseEntity<>(errorResponse , ex.getErrorCode().getHttpStatus());
    }
}
