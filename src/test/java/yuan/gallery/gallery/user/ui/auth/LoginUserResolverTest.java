package yuan.gallery.gallery.user.ui.auth;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;
import static yuan.gallery.gallery.user.domain.UserTest.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;

import yuan.gallery.gallery.user.domain.UserRepository;
import yuan.gallery.gallery.user.exception.NotExistUserException;

@ExtendWith(MockitoExtension.class)
class LoginUserResolverTest {

    private LoginUserResolver loginUserResolver;

    @Mock
    UserRepository userRepository;

    @Mock
    MethodParameter parameter;

    @Mock
    ModelAndViewContainer mavContainer;

    @Mock
    NativeWebRequest webRequest;

    @Mock
    WebDataBinderFactory binderFactor;

    @BeforeEach
    void setUp() {
        loginUserResolver = new LoginUserResolver(userRepository);
    }

    @DisplayName("존재하지 않는 유저에 대한 토큰이 들어왔을 때 예외 테스트")
    @Test
    void resolveArgumentWhenException() {
        given(webRequest.getAttribute(anyString(), anyInt())).willReturn(1L);
        given(userRepository.findById(anyLong())).willReturn(Optional.empty());

        assertThatThrownBy(() -> loginUserResolver.resolveArgument(parameter, mavContainer, webRequest, binderFactor))
            .isInstanceOf(NotExistUserException.class);
    }

    @DisplayName("정상적인 resolver 테스트")
    @Test
    void resolveArgument() {
        given(webRequest.getAttribute(anyString(), anyInt())).willReturn(1L);
        given(userRepository.findById(anyLong())).willReturn(Optional.of(ADMIN_USER));

        assertThat(loginUserResolver.resolveArgument(parameter, mavContainer, webRequest, binderFactor))
            .isEqualTo(ADMIN_USER);
    }
}