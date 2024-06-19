package com.assessment.eventbookingsystem.config;

import com.assessment.eventbookingsystem.model.Users;
import com.assessment.eventbookingsystem.repository.UsersRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static com.assessment.eventbookingsystem.utils.MessageUtils.USER_NOT_FOUND;

@Component
@RequiredArgsConstructor
public class JwtConfig {

    private final UsersRepository usersRepository;

    private String token;

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(getSingKey()).build().parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public Key getSingKey(){
        byte[] key = Base64.getDecoder().decode("K52l5lY188GLn+9Mhcbk/6IabbeNEGhh7LODTUwcb2M=");
        return Keys.hmacShaKeyFor(key);
    }

    public Users getUser(String email){
        return usersRepository.findUsersByEmail(email).orElseThrow(()-> new UsernameNotFoundException(USER_NOT_FOUND));
    }

    public String generateToken(UserDetails userDetails) {
        var user = getUser(userDetails.getUsername());
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", user.getId());
        return createToken(claims, user.getEmail());
    }
    private String createToken(Map<String, Object> claims, String email) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(email) // change sub to email
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(SignatureAlgorithm.HS256, getSingKey())
                .compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        this.token = token;
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public Map<String, Object> extractClaimsFromToken() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getCredentials() instanceof String jwt) {
            return extractAllClaims(jwt);
        }
        return extractAllClaims(this.token);
    }
}
