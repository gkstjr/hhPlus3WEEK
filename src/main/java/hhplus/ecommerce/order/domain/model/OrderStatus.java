package hhplus.ecommerce.order.domain.model;

public enum OrderStatus {
    PAYMENT_PENDING, //결제 대기
    PAYMENT_COMPLETED, // 결제 완료
    PAYMENT_FAILED //결제 실패
}
