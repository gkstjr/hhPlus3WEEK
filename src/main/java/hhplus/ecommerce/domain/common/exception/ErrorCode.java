package hhplus.ecommerce.domain.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    //사용자 포인트
    POINT_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 사용자의 포인트입니다."),
    INVALID_POINT_AMOUNT(HttpStatus.BAD_REQUEST , "유효하지 않은 포인트 금액입니다."),
    OVER_POINT_LIMIT(HttpStatus.BAD_REQUEST,"포인트 보유한도를 초과했습니다." );
    private final HttpStatus httpStatus;
    private final String message;
    ErrorCode(HttpStatus httpStatus , String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }
}
