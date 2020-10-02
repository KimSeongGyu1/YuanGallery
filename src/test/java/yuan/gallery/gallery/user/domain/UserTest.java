package yuan.gallery.gallery.user.domain;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UserTest {

    public static final String USER_NAME = "user name";
    public static final String USER_PASSWORD = "password";
    public static final boolean IS_ADMIN = true;

    @DisplayName("정적 팩토리 메서드 테스트")
    @Test
    void of() {
        User user = User.of(USER_NAME, USER_PASSWORD, IS_ADMIN);

        assertAll(
            () -> assertThat(user.getName()).isEqualTo(USER_NAME),
            () -> assertThat(user.getPassword()).isEqualTo(USER_PASSWORD),
            () -> assertThat(user.isAdmin()).isEqualTo(IS_ADMIN)
        );
    }
}