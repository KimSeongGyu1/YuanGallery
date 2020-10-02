package yuan.gallery.gallery.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class LoginResponse {

    String token;
    String name;
    boolean isAdmin;
}
