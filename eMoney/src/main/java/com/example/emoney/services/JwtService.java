package com.example.emoney.services;

import com.example.emoney.models.Role;
import com.example.emoney.models.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class JwtService {

    private final String SECRET = "932dc44310f34a0e977b1fd2c1e83ab7ec991d04b04e78c9fefb397749c087e1";


    public String extractUsernameFromAuthHeader(String authHeader){
        Claims claims = extractAllClaims(authHeader.substring(7));
        return claims.getSubject();
    }
    public String extractUsername(String token){
        Claims claims = extractAllClaims(token);
        return claims.getSubject();
    }

    public List<Role> extractRoles(String token, RoleService roleService){
        String roles  = extractAllClaims(token).get("roles", String.class);
        List<Role> roleList = User.stringToRoles(roles, roleService).stream().toList();
        return roleList;
    }

    private boolean isTokenExpired(String token){
        return (extractAllClaims(token).getExpiration().before(new Date()));
    }


    public boolean isValid(UserDetails user, String token){
        return (extractUsername(token).compareTo(user.getUsername()) == 0 || isTokenExpired(token));
    }
    private Claims extractAllClaims(String token){
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
    public String generateToken(User user){

        Map<String, String> payload = new HashMap<>();
        payload.put("roles", User.rolesToString(user.getRoles()));

        String token = Jwts.builder()
                .subject(user.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000))
                .claims(payload)
                .signWith(getSigningKey())
                .compact();
        return token;
    }

    private SecretKey getSigningKey() {
        byte[] bytes = Decoders.BASE64URL.decode(SECRET);
        return Keys.hmacShaKeyFor(bytes);
    }

}
