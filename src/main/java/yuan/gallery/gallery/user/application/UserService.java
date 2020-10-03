package yuan.gallery.gallery.user.application;

import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import yuan.gallery.gallery.user.domain.User;
import yuan.gallery.gallery.user.domain.UserRepository;
import yuan.gallery.gallery.user.dto.LoginRequest;
import yuan.gallery.gallery.user.dto.LoginResponse;
import yuan.gallery.gallery.user.dto.UserTokenInfo;
import yuan.gallery.gallery.user.exception.NotExistUserException;
import yuan.gallery.gallery.user.jwt.JwtTokenProvider;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public LoginResponse login(LoginRequest loginRequest) {
        Optional<User> user = userRepository.findByNameAndPassword(loginRequest.getName(), loginRequest.getPassword());
        User presentUser = getIfPresent(user);

        String token = jwtTokenProvider.createToken(UserTokenInfo.from(presentUser));
        return new LoginResponse(token, presentUser.getName(), presentUser.isAdmin());
    }

    private User getIfPresent(Optional<User> user) {
        if (user.isPresent()) {
            return user.get();
        }
        throw new NotExistUserException();
    }
}
