
package mk.ukim.finki.emt_lab1.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class JwtHelper {
    public JwtHelper() {
    }

    private Key getSignIn() {
        byte[] keyBytes = (byte[])Decoders.BASE64.decode("1cebd833d8f1cb032622bcbcc9a75bd6affa02f1a55e0a7fa323404df1790a8d");
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private Claims extractAllClaims(String token) {
        return (Claims)Jwts.parserBuilder().setSigningKey(this.getSignIn()).build().parseClaimsJws(token).getBody();
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        Claims allClaims = this.extractAllClaims(token);
        return claimsResolver.apply(allClaims);
    }

    public String extractUsername(String token) {
        return (String)this.extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return (Date)this.extractClaim(token, Claims::getExpiration);
    }

    private String buildToken(Map<String, Object> extraClaims, String subject, Long expiration) {
        return Jwts.builder().setClaims(extraClaims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis())).setExpiration(new Date(System.currentTimeMillis() + JwtConstants.EXPIRATION_TIME)).signWith(this.getSignIn(), SignatureAlgorithm.HS256).compact();
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> extraClaims = new HashMap();
        extraClaims.put("roles", userDetails.getAuthorities());
        return this.buildToken(extraClaims, userDetails.getUsername(), JwtConstants.EXPIRATION_TIME);
    }

    private boolean isExpired(String token) {
        return this.extractExpiration(token).before(new Date());
    }

    public boolean isValid(String token, UserDetails userDetails) {
        String username = this.extractUsername(token);
        return !this.isExpired(token) && username.equals(userDetails.getUsername());
    }
}
