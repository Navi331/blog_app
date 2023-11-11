package com.demo.blog_app.Security;

import com.demo.blog_app.Exception.MyCustomException;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {
    @Value("${jwt.secret-key}")
    private String jwt_secret;
    @Value("${jwt.expiration-milliseconds}")
    private int expiration;

    public String generateToken(Authentication authentication){
     String name =   authentication.getName();
        Date currentDate= new Date();
        Date expirationDate = new Date(currentDate.getTime()+ expiration);
      return   Jwts.builder()
                .setSubject(name)
                .setIssuedAt(new Date())
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512,jwt_secret)
                .compact();
    }

    public String getUserNameFromJWT(String token){
        Claims body = Jwts.parser()
                .setSigningKey(jwt_secret)
                .parseClaimsJws(token)
                .getBody();
       return body.getSubject();
    }

    public boolean validateToken(String token){
         try{   Jwts.parser()
                    .setSigningKey(jwt_secret)
                    .parseClaimsJws(token);
            return true;
    } catch (SignatureException e) {
        throw new MyCustomException("Invalid JWT Signature");
    } catch (MalformedJwtException e) {
        throw new MyCustomException("Malformed JWT token");
    } catch (ExpiredJwtException e) {
        throw new MyCustomException("Expired JWT token");
    }catch (UnsupportedJwtException e){
        throw new MyCustomException("Unsupported JWT token");
    }catch (IllegalArgumentException e){
        throw new MyCustomException("JWT token string is empty");
    }
    }
}
