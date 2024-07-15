package com.rts.util;


import org.mindrot.jbcrypt.BCrypt;

/**
 * @Author: RTS
 * @CreateDateTime: 2024/7/15 17:35
 **/
public class PasswordUtil {
    /**
     * 加密
     * @param password
     * @return
     */
    public static String encrypt(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    /**
     * 解密
     * @param password
     * @param hash
     * @return
     */
    public static boolean verify(String password, String hash) {
        return BCrypt.checkpw(password, hash);
    }
}
