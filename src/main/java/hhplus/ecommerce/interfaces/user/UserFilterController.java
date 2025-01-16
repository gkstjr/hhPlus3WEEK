package hhplus.ecommerce.interfaces.user;

import hhplus.ecommerce.domain.user.User;
import hhplus.ecommerce.support.exception.BusinessException;
import hhplus.ecommerce.support.exception.ErrorCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserFilterController {

    @GetMapping("/users/test")
    public ResponseEntity<Long> testUserInjection(User user) {
        // User 객체에서 ID만 반환
        return ResponseEntity.ok(user.getId());
    }

    @GetMapping("/test")
    public ResponseEntity<Long> testNotUserInjection(User user) {
        if (user == null) {
            throw new BusinessException(ErrorCode.FILTER_TEST_FAIL);
        }
        return ResponseEntity.ok(user.getId());
    }
}
