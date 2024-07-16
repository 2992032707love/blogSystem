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

    private static final String USERS_TABLE_FIELD_USERNAME = "username";

    private static final String USERS_TABLE_FIELD_EMAIL = "email";

    @Resource
    private UsersMapper usersMapper;

    @Resource
    private JwtUtil jwtUtil;

    @Override
    public Boolean registerUser(Users user) {
        Users users = null;
        QueryWrapper<Users> wrapper = new QueryWrapper<>();
        wrapper.eq(USERS_TABLE_FIELD_USERNAME, user.getUsername());
        users = this.getOne(wrapper);
        if (users == null) {
            QueryWrapper<Users> wrapperTwo = new QueryWrapper<>();
            wrapperTwo.eq(USERS_TABLE_FIELD_EMAIL,user.getEmail());
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
    public String loginUser(String username, String password) {
        Users user = usersMapper.selectOne(new QueryWrapper<Users>().eq(USERS_TABLE_FIELD_USERNAME, username));
        if (user == null && !PasswordUtil.verify(password, user.getPassword())) {
            throw new MyException("用户名或密码错误");
        }
        String token = jwtUtil.generateToken(user.getUsername());
        return token;
    }

    @Override
    public Users getCurrentAuthenticatedUser(String username) {
//        String username = JwtUtil.getUsernameFromToken(token);
        return usersMapper.selectOne(new QueryWrapper<Users>().eq(USERS_TABLE_FIELD_USERNAME, username));
    }
}
