package com.rts.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;

/**
 * @Author: RTS
 * @CreateDateTime: 2024/7/15 17:35
 **/
public class JwtUtil {
    private static final String SECRET_KEY = "rentingsheng";
    private static final long EXPIRATION_TIME = 86400000; // 1 day
    private static final long UPDATE_THRESHOLD = EXPIRATION_TIME / 3; // 过期时间的三分之一

    /**
     * 生成token
     * @param username
     * @return
     */
    public static String generateToken(String username) {
        return JWT.create()
                .withClaim("username",username)
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .withIssuedAt(new Date(System.currentTimeMillis()))
                .sign(Algorithm.HMAC256(SECRET_KEY));
    }

    /**
     * 从token中获取 username 的值
     * @param token
     * @return
     */
    public static String getUsernameFromToken(String token) {

        DecodedJWT verify = JWT.require(Algorithm.HMAC256(SECRET_KEY)).build().verify(token);
        String username = verify.getClaim("username").asString();
        Date expiresAt = verify.getExpiresAt();
        Date issuedAt = verify.getIssuedAt();
        System.out.println("issuedAt:当前时间" + issuedAt);
        System.out.println( "expiresAt:过期时间   "+ expiresAt);
        return username;

    }

    /**
     * 当token的有效时间少于三分之一时，更新token
     * @param token
     * @return
     */
    public static String updateToken(String token) {
        DecodedJWT verify = JWT.require(Algorithm.HMAC256(SECRET_KEY)).build().verify(token);
        Date expiresAt = verify.getExpiresAt();
        long remainingTime = expiresAt.getTime() - System.currentTimeMillis();
        if (remainingTime < UPDATE_THRESHOLD) {
            String username = verify.getClaim("username").asString();
            return JWT.create()
                    .withClaim("username", username)
                    .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                    .withIssuedAt(new Date(System.currentTimeMillis()))
                    .sign(Algorithm.HMAC256(SECRET_KEY));
        } else {
            return token;
        }
    }




    /**
     * 验证token
     * @param token
     */
    public static void verify(String token){
        JWT.require(Algorithm.HMAC256(SECRET_KEY)).build().verify(token);
    }
}
