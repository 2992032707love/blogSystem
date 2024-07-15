package com.rts.controller;

import cn.hutool.http.server.HttpServerRequest;
import com.rts.common.ResultJson;
import com.rts.entity.Users;
import com.rts.service.UsersService;
import com.rts.util.JwtUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

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

    @PostMapping("/register")
    public ResultJson<String> register(@RequestBody Users users) {
        return ResultJson.success(usersService.register(users) ? "注册成功！":"注册失败！");
    }

    @PostMapping("/login")
    public ResultJson<Map<String, Object>> login(@RequestBody Users users) {
        Map<String, Object> map = usersService.login(users.getUsername(), users.getPassword());
        return ResultJson.success(map);
    }
    @GetMapping("/me")
    public ResultJson<Users> getCurrentUser(HttpServletRequest request) {
        return ResultJson.success(usersService.getCurrentUser((String) request.getAttribute("username")));
    }
}
