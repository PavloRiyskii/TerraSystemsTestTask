package com.terrassystem.testtask.security;


import io.jsonwebtoken.*;
import java.io.UnsupportedEncodingException;
import java.util.Date;

/**
 * Created by Павло on 21.06.2017.
 */
public class TokenUtil {

    private static final long expirationPeriod = 1000 * 60 * 60 * 24;
    private static final String key = "secret key";

    public static String generateJWT(String username, String password) {
        try {
            SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

            long nowMillis = System.currentTimeMillis();
            Date expiration = new Date(nowMillis + expirationPeriod);

            JwtBuilder builder = null;
                builder = Jwts.builder()
                        .setIssuer(username)
                        .setSubject(password)
                        .setExpiration(expiration).signWith(signatureAlgorithm, key.getBytes("UTF-8"));
            return builder.compact();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getUserNameFromToken(String jwt) {
        try {
            Claims claims = Jwts.parser().setSigningKey(key.getBytes("UTF-8")).parseClaimsJws(jwt).getBody();
            return claims.getIssuer();
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }

    public static String getPasswordFromToken(String jwt) {
        try {
            Claims claims = Jwts.parser().setSigningKey(key.getBytes("UTF-8")).parseClaimsJws(jwt).getBody();
            return claims.getSubject();
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }

    public static Date getExpirationDate(String jwt) {
        try {
            Claims claims = Jwts.parser().setSigningKey(key.getBytes("UTF-8")).parseClaimsJws(jwt).getBody();
            return claims.getExpiration();
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }

    public static boolean validateJWT(String jwt) {
        try{
            Date date = new Date(System.currentTimeMillis());
            if(getUserNameFromToken(jwt) != null && getPasswordFromToken(jwt) != null && getExpirationDate(jwt).getTime() > date.getTime())
                return true;
        } catch(Exception e) {}
        return false;
    }


}
