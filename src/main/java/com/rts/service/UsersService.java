package com.rts.service;

import com.rts.entity.Users;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author rts
 * @since 2024-07-15 17:13:36
 */
public interface UsersService extends IService<Users> {
    /**
     * 用户注册
     * @param user
     * @return
     */
    Boolean registerUser(Users user);

    /**
     * 用户登录
     * @param username
     * @param password
     * @return
     */
    String loginUser(String username, String password);

    /**
     * 获取当前用户信息（需要认证）
     * @param username
     * @return
     */
    Users getCurrentAuthenticatedUser(String username);

}
