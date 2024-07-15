package com.rts.controller;

import com.rts.common.ResultJson;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author rts
 * @since 2024-07-15 17:13:36
 */
@RestController
@RequestMapping("/users")
public class UsersController {
    @GetMapping("/test")
    public ResultJson<String> test(){
        return ResultJson.success("成功");
    }

}
