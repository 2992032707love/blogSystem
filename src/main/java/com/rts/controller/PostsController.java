package com.rts.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.rts.common.ResultJson;
import com.rts.config.JWTProperties;
import com.rts.entity.Posts;
import com.rts.service.PostsService;
import com.rts.util.JwtUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 文章表 前端控制器
 * </p>
 *
 * @author rts
 * @since 2024-07-15 17:17:26
 */
@Slf4j
@RestController
@RequestMapping("/api/posts")
public class PostsController {
    @Resource
    private PostsService postService;

    private static final String TOKEN_NAME ="token";

    @Resource
    private JwtUtil jwtUtil;

    @Resource
    private JWTProperties jwtProperties;



    @PostMapping
    public ResultJson<String> createPost(HttpServletRequest request, @RequestHeader(TOKEN_NAME) String token, @RequestBody Posts posts) {
        String username = null;
//        username = JwtUtil.getUsernameFromToken(token);
        username =(String) request.getAttribute(jwtProperties.getClaimKey());

        return ResultJson.success(postService.createPost(username, posts) ? "新建文章成功！" : "新建文章失败！");
    }

    @GetMapping
    public ResultJson<IPage<Posts>> getPosts(@RequestParam("uid") Integer uid, @RequestParam(value = "pageNo",required = false) Integer pageNo, @RequestParam(value = "pageSize",required = false) Integer pageSize) {

        return ResultJson.success(postService.getPostsByUser(uid,pageNo,pageSize));
    }

    @GetMapping("/{id}")
    public ResultJson<Posts> getPost(@PathVariable("id") Integer id) {
        return ResultJson.success(postService.getPostById(id));
    }

    @PutMapping("/{id}")
    public ResultJson<String> updatePost(@RequestHeader(TOKEN_NAME) String token, @PathVariable("id") Integer id, @RequestBody Posts posts) {
        String username = jwtUtil.getUsernameFromToken(token);
        return ResultJson.success(postService.updatePostById(username, id, posts) ? "修改成功！" : "修改失败，请一会儿再试！");
    }

    @DeleteMapping("/{id}")
    public ResultJson<String> deletePost(@RequestHeader(TOKEN_NAME) String token, @PathVariable("id") Integer id) {
        String username = jwtUtil.getUsernameFromToken(token);

        return ResultJson.success(postService.deletePostById(username, id) ? "删除成功！" : "删除失败，请一会儿再试！");
    }

}
