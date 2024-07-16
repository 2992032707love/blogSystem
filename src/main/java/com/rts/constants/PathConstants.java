package com.rts.constants;

/**
 * 路径常量类
 * @Author: RTS
 * @CreateDateTime: 2024/7/16 22:20
 **/
public class PathConstants {

    public static final String INTERCEPTOR_PATH = "/api/**"; // 要拦截的路径
    public static final String AUTH_REGISTER = "/api/auth/register"; // 放行的用户注册路径
    public static final String AUTH_LOGIN = "/api/auth/login"; // 放行的用户登录路径
    public static final String GET_POSTS = "/api/posts"; // 排除的获取某个用户的所有文章列表 支持分页/按创建时间正/倒叙 路径
    public static final String GET_POST_BY_ID = "/api/posts/*"; // 排除的获取单篇文章详情的路径
}
