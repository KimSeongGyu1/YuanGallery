package yuan.gallery.gallery.user.ui;

import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import yuan.gallery.gallery.MvcTest;
import yuan.gallery.gallery.user.application.UserService;
import yuan.gallery.gallery.user.dto.LoginRequest;
import yuan.gallery.gallery.user.dto.LoginResponse;

@WebMvcTest(UserController.class)
class UserControllerTest extends MvcTest {

    @MockBean
    UserService userService;

    @Test
    void login() throws Exception {
        LoginRequest loginRequest = new LoginRequest("name", "password");
        String inputJson = objectMapper.writeValueAsString(loginRequest);

        LoginResponse loginResponse = new LoginResponse("token", "name", true);
        given(userService.login(any())).willReturn(loginResponse);
        given(authInterceptor.preHandle(any(), any(), any())).willReturn(true);

        postAction("/api/login", inputJson)
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.token", is("token")))
            .andExpect(jsonPath("$.name", is("name")))
            .andExpect(jsonPath("$.admin", is(true)));
    }
}