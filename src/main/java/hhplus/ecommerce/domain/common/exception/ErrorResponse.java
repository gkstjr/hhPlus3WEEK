package hhplus.ecommerce.domain.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorResponse {
    private final boolean success = false;
    private final int statusCode;
    private final String message;

    /**
     * 예외 발생 시 ErrorCode에 정의된 메시지 + 상태코드 전달
     *
     * @param errorCode
     * @return
     */
    public static ErrorResponse of(ErrorCode errorCode) {
        return new ErrorResponse(errorCode.getHttpStatus().value(), errorCode.getMessage());
    }

    /**
     * 예외 발생 시 상테코드 + 커스텀 메시지 전달
     *
     * @param errorCode
     * @param customMessage
     * @return
     */
    public static ErrorResponse of(ErrorCode errorCode, String customMessage){
        return new ErrorResponse(errorCode.getHttpStatus().value(), customMessage != null ? customMessage : errorCode.getMessage());
    }
}
