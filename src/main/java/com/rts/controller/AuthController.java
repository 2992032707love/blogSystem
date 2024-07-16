package com.rts.controller;

import com.rts.common.ResultJson;
import com.rts.config.JWTProperties;
import com.rts.entity.Users;
import com.rts.service.UsersService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: RTS
 * @CreateDateTime: 2024/7/15 17:52
 **/
@Slf4j
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Resource
    private UsersService usersService;

    @Resource
    private JWTProperties jwtProperties;

    @PostMapping("/register")
    public ResultJson<String> register(@RequestBody Users users) {
        return ResultJson.success(usersService.registerUser(users) ? "注册成功！":"注册失败！");
    }

    @PostMapping("/login")
    public ResultJson<String> login(@RequestBody Users users) {

        return ResultJson.success(usersService.loginUser(users.getUsername(), users.getPassword()));
    }
    @GetMapping("/me")
    public ResultJson<Users> getCurrentUser(HttpServletRequest request) {
        return ResultJson.success(usersService.getCurrentAuthenticatedUser((String) request.getAttribute(jwtProperties.getClaimKey())));
    }
}
