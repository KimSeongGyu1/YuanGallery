package yuan.gallery.gallery.user.application;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;
import static yuan.gallery.gallery.user.domain.UserTest.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import yuan.gallery.gallery.user.domain.UserRepository;
import yuan.gallery.gallery.user.dto.LoginRequest;
import yuan.gallery.gallery.user.dto.LoginResponse;
import yuan.gallery.gallery.user.exception.NotExistUserException;
import yuan.gallery.gallery.user.jwt.JwtTokenProvider;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    private UserService userService;

    @Mock
    UserRepository userRepository;

    @Mock
    JwtTokenProvider jwtTokenProvider;

    @BeforeEach
    void setUp() {
        userService = new UserService(userRepository, jwtTokenProvider);
    }

    @DisplayName("로그인 테스트")
    @Test
    void login() {
        given(userRepository.findByNameAndPassword(anyString(), anyString())).willReturn(Optional.of(USER));
        given(jwtTokenProvider.createToken(any())).willReturn("token");

        LoginRequest loginRequest = new LoginRequest("name", "password");
        LoginResponse loginResponse = userService.login(loginRequest);

        assertAll(
            () -> assertThat(loginResponse.getToken()).isEqualTo("token"),
            () -> assertThat(loginResponse.getName()).isEqualTo(USER_NAME),
            () -> assertThat(loginResponse.isAdmin()).isEqualTo(IS_ADMIN)
        );
    }

    @DisplayName("로그인 실패 테스트")
    @Test
    void loginFail() {
        given(userRepository.findByNameAndPassword(anyString(), anyString())).willReturn(Optional.empty());

        LoginRequest loginRequest = new LoginRequest("name", "password");
        assertThatThrownBy(() -> userService.login(loginRequest))
            .isInstanceOf(NotExistUserException.class);
    }
}