package yuan.gallery.gallery.user.jwt;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import yuan.gallery.gallery.user.dto.UserTokenInfo;
import yuan.gallery.gallery.user.exception.InvalidTokenException;

@Component
public class JwtTokenProvider {

    private static final String USER_ID = "userId";
    private static final String IS_ADMIN = "isAdmin";

    private String secretKey;
    private long validationTime;

    public JwtTokenProvider(
        @Value("${jwt.token.secret-key:sample}") String secretKey,
        @Value("${jwt.token.expire-time:100000}")long validationTime
    ) {
        this.secretKey = secretKey;
        this.validationTime = validationTime;
    }

    public String createToken(UserTokenInfo userTokenInfo) {
        Claims claims = Jwts.claims();
        claims.put(USER_ID, String.valueOf(userTokenInfo.getId()));
        claims.put(IS_ADMIN, String.valueOf(userTokenInfo.isAdmin()));

        Date now = new Date();
        Date expireTime = new Date(now.getTime() + validationTime);

        return Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(now)
            .setExpiration(expireTime)
            .signWith(SignatureAlgorithm.HS256, secretKey)
            .compact();
    }

    public UserTokenInfo extractUserInfo(String token) {
        Claims claims = extractValidClaims(token);

        long id = Long.parseLong(claims.get(USER_ID).toString());
        boolean isAdmin = Boolean.parseBoolean(claims.get(IS_ADMIN).toString());

        return UserTokenInfo.of(id, isAdmin);
    }

    private Claims extractValidClaims(String token) {
        try {
            return extractClaims(token);
        } catch (JwtException | IllegalArgumentException e) {
            throw new InvalidTokenException();
        }
    }

    private Claims extractClaims(String token) {
        return Jwts.parser()
            .setSigningKey(secretKey)
            .parseClaimsJws(token)
            .getBody();
    }
}
