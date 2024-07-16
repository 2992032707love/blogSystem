package com.rts.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.rts.config.JWTProperties;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @Author: RTS
 * @CreateDateTime: 2024/7/15 17:35
 **/
@Service
@Slf4j
public class JwtUtil {

    @Resource
    private JWTProperties jwtProperties;

//    private static final String SECRET_KEY = "rentingsheng";
//    private static final long EXPIRATION_TIME = 86400000; // 1 day
//    private static final long UPDATE_THRESHOLD = EXPIRATION_TIME / 3; // 过期时间的三分之一

    /**
     * 生成token
     * @param username
     * @return
     */
    public String generateToken(String username) {
        return JWT.create()
                .withClaim(jwtProperties.getClaimKey(),username)
                .withExpiresAt(new Date(System.currentTimeMillis() + jwtProperties.getExpirationTime()))
                .withIssuedAt(new Date(System.currentTimeMillis()))
                .sign(Algorithm.HMAC256(jwtProperties.getSecretKey()));
    }

    /**
     * 从token中获取 username 的值
     * @param token
     * @return
     */
    public String getUsernameFromToken(String token) {

        DecodedJWT verify = JWT.require(Algorithm.HMAC256(jwtProperties.getSecretKey())).build().verify(token);
        String username = verify.getClaim(jwtProperties.getClaimKey()).asString();
        Date expiresAt = verify.getExpiresAt();
        Date issuedAt = verify.getIssuedAt();
        log.info("issuedAt:发布时间:  " + issuedAt.getTime());
        log.info("当前时间:" + new Date(System.currentTimeMillis()).getTime());
        log.info("expiresAt:过期时间:   "+ expiresAt.getTime());
        return username;

    }

    /**
     * 当token的有效时间少于三分之一时，更新token
     * @param token
     * @return
     */
    public String updateToken(String token) {
        DecodedJWT verify = JWT.require(Algorithm.HMAC256(jwtProperties.getSecretKey())).build().verify(token);
        Date expiresAt = verify.getExpiresAt();
        long remainingTime = expiresAt.getTime() - System.currentTimeMillis();
//        log.info("jwt的更新阈值: " + jwtProperties.getUpdateThreshold());
//        log.info("JWT的剩余有效时间: " + remainingTime);
        if (remainingTime < jwtProperties.getUpdateThreshold()) {
            String username = verify.getClaim(jwtProperties.getClaimKey()).asString();
            return JWT.create()
                    .withClaim(jwtProperties.getClaimKey(), username)
                    .withExpiresAt(new Date(System.currentTimeMillis() + jwtProperties.getExpirationTime()))
                    .withIssuedAt(new Date(System.currentTimeMillis()))
                    .sign(Algorithm.HMAC256(jwtProperties.getSecretKey()));
        } else {
            return token;
        }
    }




    /**
     * 验证token
     * @param token
     */
    public void verify(String token){
        JWT.require(Algorithm.HMAC256(jwtProperties.getSecretKey())).build().verify(token);
    }
}
