package yuan.gallery.gallery.user.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import yuan.gallery.gallery.user.domain.User;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class UserTokenInfo {

    private Long id;
    private boolean isAdmin;

    public static UserTokenInfo from(User user) {
        return new UserTokenInfo(user.getId(), user.isAdmin());
    }

    public static UserTokenInfo of(long id, boolean isAdmin) {
        return new UserTokenInfo(id, isAdmin);
    }
}
