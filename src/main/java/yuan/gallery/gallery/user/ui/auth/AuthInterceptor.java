package yuan.gallery.gallery.user.ui.auth;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import lombok.RequiredArgsConstructor;
import yuan.gallery.gallery.user.dto.UserTokenInfo;
import yuan.gallery.gallery.user.exception.NotAuthorizedException;
import yuan.gallery.gallery.user.jwt.JwtTokenProvider;

@Component
@RequiredArgsConstructor
public class AuthInterceptor implements HandlerInterceptor {

    private static final String NO_TOKEN_FOUND = "no token found";

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String token = extractToken(request);
        if (token.equals(NO_TOKEN_FOUND)) {
            return true;
        }

        UserTokenInfo userTokenInfo = jwtTokenProvider.extractUserInfo(token);
        if (!userTokenInfo.isAdmin()) {
            throw new NotAuthorizedException();
        }

        request.setAttribute("userId", userTokenInfo.getId());
        request.setAttribute("isAdmin", userTokenInfo.isAdmin());
        return true;
    }

    private String extractToken(HttpServletRequest request) {
        Enumeration<String> headers = request.getHeaders("Authorization");
        while(headers.hasMoreElements()) {
            String header = headers.nextElement().trim();
            if (header.startsWith("yuanToken")) {
                return header.split(" ")[1];
            }
        }

        return NO_TOKEN_FOUND;
    }
}
