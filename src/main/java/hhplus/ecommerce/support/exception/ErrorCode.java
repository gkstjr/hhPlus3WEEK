package hhplus.ecommerce.support.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    //사용자 포인트
    POINT_NOT_FOUND(HttpStatus.BAD_REQUEST, "존재하지 않는 사용자의 포인트입니다."),
    INVALID_POINT_AMOUNT(HttpStatus.BAD_REQUEST , "유효하지 않은 포인트 금액입니다."),
    OVER_POINT_LIMIT(HttpStatus.BAD_REQUEST,"포인트 보유한도를 초과했습니다." ),
    OUT_OF_POINT(HttpStatus.BAD_REQUEST,"보유한 포인트 금액이 부족합니다."),

    //상품
    PRODUCT_NOT_FOUND(HttpStatus.BAD_REQUEST,"존재하지 않는 상품입니다."),
    INVALID_QUANTITY(HttpStatus.BAD_REQUEST,"상품 구매 수량은 1개 이상부터 가능합니다." ),

    //사용자
    USER_NOT_FOUND(HttpStatus.BAD_REQUEST,"존재하지 않는 사용자입니다."),
    OUT_OF_STOCK(HttpStatus.BAD_REQUEST,"상품 재고가 부족합니다." ),
    FILTER_TEST_FAIL(HttpStatus.BAD_REQUEST,"전처리에서 유저정보를 조회하지 않습니다." ),


    //쿠폰
    COUPON_NOT_FOUND(HttpStatus.BAD_REQUEST,"쿠폰이 존재하지 않습니다." ),
    COUPON_MAX_ISSUE(HttpStatus.BAD_REQUEST, "쿠폰 발급인원이 가득 찼습니다."),
    COUPON_EXPIRED_ISSUE(HttpStatus.BAD_REQUEST, "쿠폰 유효기간이 지났습니다."),
    ALREADY_ISSUE_COUPON(HttpStatus.BAD_REQUEST,"이미 발급받은 쿠폰입니다."),
    ISSUEDCOUPON_NOT_FOUND(HttpStatus.BAD_REQUEST,"발급받은 쿠폰이 없습니다."),
    ALREADY_USE_COUPON(HttpStatus.BAD_REQUEST,"이미 사용한 쿠폰입니다." ),

    //주문&결제
    ORDER_NOT_FOUND(HttpStatus.BAD_REQUEST, "결제에 필요한 주문이 존재하지 않습니다." );


    private final HttpStatus httpStatus;
    private final String message;
    ErrorCode(HttpStatus httpStatus , String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }
}
