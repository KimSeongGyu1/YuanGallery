package yuan.gallery.gallery.user.ui.auth;


import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;
import static yuan.gallery.gallery.user.domain.UserTest.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import yuan.gallery.gallery.user.dto.UserTokenInfo;
import yuan.gallery.gallery.user.exception.NotAuthorizedException;
import yuan.gallery.gallery.user.jwt.JwtTokenProvider;

@ExtendWith(MockitoExtension.class)
class AuthInterceptorTest {

    private AuthInterceptor authInterceptor;

    @Mock
    JwtTokenProvider jwtTokenProvider;

    @BeforeEach
    void setUp() {
        authInterceptor = new AuthInterceptor(jwtTokenProvider);
    }

    @DisplayName("토큰 없을 때 그냥 통과 테스트")
    @Test
    void preHandleWithoutToken() {
        MockHttpServletRequest initialRequest = new MockHttpServletRequest();
        authInterceptor.preHandle(initialRequest, new MockHttpServletResponse(), new Object());
        assertThat(initialRequest.getAttribute("userId")).isNull();
        assertThat(initialRequest.getAttribute("isAdmin")).isNull();
    }

    @DisplayName("권한 없을 때 예외 테스트")
    @Test
    void preHandleWithNotAuthorizedUser() {
        UserTokenInfo userTokenInfo = UserTokenInfo.from(NOT_ADMIN_USER);
        given(jwtTokenProvider.extractUserInfo(anyString())).willReturn(userTokenInfo);

        MockHttpServletRequest initialRequest = new MockHttpServletRequest();
        initialRequest.addHeader("Authorization", "yuanToken asdf");

        assertThatThrownBy(() -> authInterceptor.preHandle(initialRequest, new MockHttpServletResponse(), new Object()))
            .isInstanceOf(NotAuthorizedException.class);
    }

    @DisplayName("권한 정상 확인 테스트")
    @Test
    void preHandleWhenValidToken() {
        UserTokenInfo userTokenInfo = UserTokenInfo.from(ADMIN_USER);
        given(jwtTokenProvider.extractUserInfo(anyString())).willReturn(userTokenInfo);

        MockHttpServletRequest initialRequest = new MockHttpServletRequest();
        initialRequest.addHeader("Authorization", "yuanToken asdf");
        assertThat(initialRequest.getAttribute("userId")).isNull();
        assertThat(initialRequest.getAttribute("isAdmin")).isNull();

        authInterceptor.preHandle(initialRequest, new MockHttpServletResponse(), new Object());

        assertThat(initialRequest.getAttribute("userId")).isEqualTo(userTokenInfo.getId());
        assertThat(initialRequest.getAttribute("isAdmin")).isEqualTo(userTokenInfo.isAdmin());
    }
}