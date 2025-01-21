package hhplus.ecommerce.integration.user;

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
    private UserRepository userRepository;

    @Test
    public void users로시작하는_URL요청시_User정보조회() throws Exception {
        // Given: 테스트 데이터 준비
        User user = userRepository.save(User.builder().name("사용자1").build());

        // When,then
        mockMvc.perform(get("/users/test")
                        .param("userId", String.valueOf(user.getId()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(String.valueOf(user.getId())));
    }

    @Test
    public void 없는userId로_URL요청시_User정보조회x() throws Exception {
        // Given: 테스트 데이터 준비

        // When,then
        mockMvc.perform(get("/users/test")
                        .param("userId", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(ErrorCode.USER_NOT_FOUND.getMessage()));
    }
}
