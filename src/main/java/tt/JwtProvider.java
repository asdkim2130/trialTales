package tt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtProvider {

    // 에러 로깅을 위해 로거 준비
    private static final Logger logger = LoggerFactory.getLogger(JwtProvider.class);

    // JWT secret을 저장할 변수
    private final SecretKey secretKey;

    // 토큰 만료 시간을 저장할 변수
    private final Long expirationInMilliseconds;

    // 생성자 함수
    public JwtProvider(
            @Value("${jwt.secret}") String secretKey,
            @Value("${jwt.expiration-time}") Long expirationInMilliseconds) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.secretKey = Keys.hmacShaKeyFor(keyBytes);
        this.expirationInMilliseconds = expirationInMilliseconds;
    }

    // 토큰을 만들어 내는 함수
    public String createToken(String payload) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + this.expirationInMilliseconds);
        Claims claims = Jwts.claims()
                .setSubject(payload)       // "sub": "abc@gmail.com"
                .setIssuedAt(now)          // "iat": 1516239022
                .setExpiration(expiration);// "exp": 1516249022
        return Jwts.builder()
                .setClaims(claims)
                .signWith(secretKey)
                .compact();
    }

    // 유효한 토큰인지 검증하는 함수
    public Boolean isValidToken(String token) {
        try {
            parseToken(token); // 토큰 데이터를 읽는 함수를 검증용으로 활용
            return true; // 읽는 도중 에러가 발생하지 않았으면 true를 return
        } catch (ExpiredJwtException e) {
            logger.error("Token expired", e);
        } catch (UnsupportedJwtException e) {
            logger.error("Unsupported JWT token", e);
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token", e);
        } catch (SignatureException e) {
            logger.error("Invalid JWT signature", e);
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty", e);
        }
        return false;
    }

    // 토큰에서 로그인한 사용자의 email을 추출하는 함수
    public String getSubject(String token) {
        return parseToken(token)
                .getSubject();
    }

    // 유효한 토큰의 데이터를 읽는 함수
    private Claims parseToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}