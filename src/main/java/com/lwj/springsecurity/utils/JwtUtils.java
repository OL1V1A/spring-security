package com.lwj.springsecurity.utils;

import com.lwj.springsecurity.entity.JwtUser;
import com.lwj.springsecurity.entity.SysUser;
import io.jsonwebtoken.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class JwtUtils {

    public static final String TOKEN_HEADER = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String SUBJECT = "olivia";
    public static final long EXPIRATION = 1000*24*60*60*7L;
    public static final String APPSECRET_KEY = "olivia_secret";
    private static final String ROLE_CLAIMS = "rol";

    public static String generateJsonWebToken(JwtUser user){
        if (user.getId() == null || user.getUsername() == null){
            return null;
        }
        Map<String,Object> map = new HashMap<>();
        map.put(ROLE_CLAIMS,"rol");
        String token = Jwts.builder()
                            .setSubject(SUBJECT)
                            .setClaims(map)
                            .claim("id",user.getId())
                            .claim("username",user.getUsername())
                            .setIssuedAt(new Date())
                            .setExpiration(new Date(System.currentTimeMillis() + 10*60*1000L))
                            .signWith(SignatureAlgorithm.HS256,APPSECRET_KEY)
                            .compact();
        return token;
    }

    /**
     * 生成token
     * @param username
     * @param role
     * @return
     */
    public static String createToken(String username,String role){
        Map<String,Object> map = new HashMap<>();
        map.put(ROLE_CLAIMS,role);
        String token = Jwts
                .builder()
                .setSubject(username)
                .setClaims(map)
                .claim("username",username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(SignatureAlgorithm.HS256, APPSECRET_KEY).compact();
        return token;
    }

    public static Claims chenckJwt(String token){
        try {
            final Claims claims = Jwts.parser().setSigningKey(APPSECRET_KEY).parseClaimsJws(token).getBody();
            return claims;
        }catch (ExpiredJwtException e){
//            e.printStackTrace();
            return e.getClaims();
        }
    }

    public static String getUsername(String token){
//        Claims claims = Jwts.parser().setSigningKey(APPSECRET_KEY).parseClaimsJws(token).getBody();
        return chenckJwt(token).get("username").toString();
//        return claims.get("username").toString();
    }

    public static String getRoles(String token){
//        Claims claims = Jwts.parser().setSigningKey(APPSECRET_KEY).parseClaimsJws(token).getBody();
//        return claims.get("rol").toString();
        return chenckJwt(token).get("rol").toString();
    }

    /**
     * token是否过期
     * @param token
     * @return
     */
    public static boolean isExpiration(String token){
        try {
            Claims claims = Jwts.parser().setSigningKey(APPSECRET_KEY).parseClaimsJws(token).getBody();
            return claims.getExpiration().before(new Date());
        }catch (ExpiredJwtException e){
            return e.getClaims().getExpiration().before(new Date());
        }
    }

    public static void main(String[] args) {
        JwtUser user = new JwtUser();
        user.setId(1);
        user.setUsername("lwj");
        user.setPassword("123456");
        String token = generateJsonWebToken(user);
        System.out.println("token---->"+token);

        Claims claims = chenckJwt(token);
        System.out.println(claims.get("id"));
        System.out.println(getUsername(token));
        System.out.println(getRoles(token));
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(isExpiration(token));
    }

}
