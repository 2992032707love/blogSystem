package com.rts.config;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * JWT配置类
 * @Author: RTS
 * @CreateDateTime: 2024/7/16 20:02
 **/
@Configuration
@ConfigurationProperties(prefix = "spring.jwt",ignoreUnknownFields = false)
public class JWTProperties {

    private String secretKey = "rentingsheng"; // JWT的私钥
    private long expirationTime = 86400000;  // 默认值为24小时，最小为30分钟 JWT的有效时间   单位毫秒
    private long updateThreshold = 86400000 / 3; // JWT更新阈值，默认和最小都为有效时间的三分之一，不得小于 即最小值为10分钟   单位毫秒

    private String claimKey = "username"; // JWT中保存的用户信息的key

    // Getters and setters

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public long getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(long expirationTime) {
        if (expirationTime < 1800000) { // 最小值30分钟
            this.expirationTime = 1800000;
        } else {
            this.expirationTime = expirationTime;
        }
        setUpdateThreshold(this.expirationTime / 3); // 更新对应的阈值
    }

    public long getUpdateThreshold() {
        return updateThreshold;
    }

    public void setUpdateThreshold(long updateThreshold) {
        if (updateThreshold < expirationTime / 3) { // 最小值：expirationTime 的三分之一
            this.updateThreshold = expirationTime / 3;
        } else {
            this.updateThreshold = updateThreshold;
        }
    }

    public String getClaimKey() {
        return claimKey;
    }

    public void setClaimKey(String claimKey) {
        this.claimKey = claimKey;
    }
}
