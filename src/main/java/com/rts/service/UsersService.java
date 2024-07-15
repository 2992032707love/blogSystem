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

    Boolean register(Users user);
    Map<String, Object> login(String username, String password);
    Users getCurrentUser(String username);

}
