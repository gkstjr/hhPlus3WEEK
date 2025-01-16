package hhplus.ecommerce;

import hhplus.ecommerce.domain.point.PointService;
import hhplus.ecommerce.domain.point.UserPointInfo;
import hhplus.ecommerce.domain.user.User;
import hhplus.ecommerce.domain.user.UserRepository;
import hhplus.ecommerce.support.exception.ErrorCode;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
public class UserFilterInterceptorTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PointService pointService;
    @Autowired
    private UserRepository userRepository;

    @Test
    public void users로시작하는_URL요청시_User정보조회() throws Exception {
        // Given: 테스트 데이터 준비
        User user = User.builder().name("사용자1").build();
        userRepository.save(user);

        // When,then
        mockMvc.perform(get("/users/test")
                        .header("userId", user.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(String.valueOf(user.getId())));
    }

    @Test
    public void users로시작하지않는_URL요청시_User정보조회x() throws Exception {
        // Given: 테스트 데이터 준비
        User user = User.builder().name("사용자1").build();
        userRepository.save(user);

        // When,then
        mockMvc.perform(get("/test")
                        .header("userId", user.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(ErrorCode.FILTER_TEST_FAIL.getMessage()));
    }
}
