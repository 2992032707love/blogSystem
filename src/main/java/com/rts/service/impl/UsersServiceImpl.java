package com.rts.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rts.config.MyException;
import com.rts.entity.Users;
import com.rts.mapper.UsersMapper;
import com.rts.service.UsersService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rts.util.JwtUtil;
import com.rts.util.PasswordUtil;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author rts
 * @since 2024-07-15 17:13:36
 */
@Service
public class UsersServiceImpl extends ServiceImpl<UsersMapper, Users> implements UsersService {

    @Resource
    private UsersMapper usersMapper;

    @Override
    public Boolean register(Users user) {
        Users users = null;
        QueryWrapper<Users> wrapper = new QueryWrapper<>();
        wrapper.eq("username", user.getUsername());
        users = this.getOne(wrapper);
        if (users == null) {
            QueryWrapper<Users> wrapperTwo = new QueryWrapper<>();
            wrapperTwo.eq("email",user.getEmail());
            users = this.getOne(wrapperTwo);
            if (users == null) {
                user.setPassword(PasswordUtil.encrypt(user.getPassword()));
                return this.save(user);
            } else {
                throw new MyException("邮箱重复，请重新输入");
            }
        } else {
            throw new MyException("用户名重复，请重新输入");
        }
    }

    @Override
    public Map<String, Object> login(String username, String password) {
        Users user = usersMapper.selectOne(new QueryWrapper<Users>().eq("username", username));
        if (user == null && !PasswordUtil.verify(password, user.getPassword())) {
            throw new MyException("用户名或密码错误");
        }
        String token = JwtUtil.generateToken(user.getUsername());
        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        return response;
    }

    @Override
    public Users getCurrentUser(String username) {
//        String username = JwtUtil.getUsernameFromToken(token);
        return usersMapper.selectOne(new QueryWrapper<Users>().eq("username", username));
    }
}
