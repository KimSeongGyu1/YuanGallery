package yuan.gallery.gallery.user.jwt;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static yuan.gallery.gallery.user.domain.UserTest.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import yuan.gallery.gallery.user.domain.User;
import yuan.gallery.gallery.user.dto.UserTokenInfo;
import yuan.gallery.gallery.user.exception.InvalidTokenException;

class JwtTokenProviderTest {

    private JwtTokenProvider jwtTokenProvider;

    @BeforeEach
    void setUp() {
        this.jwtTokenProvider = new JwtTokenProvider("secretKey", 100000);
    }

    @DisplayName("토큰 만들고 해독하기 테스트")
    @Test
    void createAndExtractToken() {
        User user = USER;
        UserTokenInfo userTokenInfo = UserTokenInfo.from(user);

        String token = jwtTokenProvider.createToken(userTokenInfo);
        UserTokenInfo extractedInfo = jwtTokenProvider.extractUserInfo(token);

        assertAll(
            () -> assertThat(extractedInfo.getId()).isEqualTo(userTokenInfo.getId()),
            () -> assertThat(extractedInfo.isAdmin()).isEqualTo(userTokenInfo.isAdmin())
        );
    }

    @DisplayName("적절하지 않은 토큰 테스트")
    @Test
    void invalidTokenExtraction() {
        assertThatThrownBy(() -> jwtTokenProvider.extractUserInfo("invalid"))
            .isInstanceOf(InvalidTokenException.class);
    }
}