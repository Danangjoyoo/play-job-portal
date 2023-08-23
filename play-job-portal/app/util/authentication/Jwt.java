package util.authentication;

import java.util.Date;

import javax.crypto.SecretKey;

import com.typesafe.config.ConfigFactory;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

public class Jwt {
    private static final long expireDuration = 24L * 3600 * 1000000;
    private static final String secret = ConfigFactory.load().getString("jwtSecret");
    public static String generateAccessToken(String username) {
        SecretKey key = Keys.hmacShaKeyFor(secret.getBytes());
        return Jwts.builder()
                .setSubject(String.format("%s", username))
                .setIssuer("me")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expireDuration))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public static String getUsernameFromToken(String token){
        SecretKey key = Keys.hmacShaKeyFor(secret.getBytes());
        Jws<Claims> jws = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
        Claims claims = jws.getBody();
        return claims.getSubject();
    }

    public static String getTokenFromAuth(String bearerKey) {
        return bearerKey.substring(7);
    }
}
