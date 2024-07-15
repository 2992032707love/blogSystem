package com.rts.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.rts.common.ResultJson;
import com.rts.entity.Posts;
import com.rts.service.PostsService;
import com.rts.util.JwtUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

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

    @PostMapping
    public ResultJson<String> createPost(HttpServletRequest request, @RequestHeader("token") String token, @RequestBody Posts posts) {
//        String username = JwtUtil.getUsernameFromToken(token);
        String username1 =(String) request.getAttribute("username");

        return ResultJson.success(postService.createPost(username1, posts) ? "新建文章成功！" : "新建文章失败！");
    }

    @GetMapping
    public ResultJson<IPage<Posts>> getPosts(@RequestParam Integer uid, @RequestParam(required = false) Integer pageNo, @RequestParam(required = false) Integer pageSize) {
        return ResultJson.success(postService.getPosts(uid,pageNo,pageSize));
    }

    @GetMapping("/{id}")
    public ResultJson<Posts> getPost(@PathVariable("id") Integer id) {
        return ResultJson.success(postService.getPost(id));
    }

    @PutMapping("/{id}")
    public ResultJson<String> updatePost(@RequestHeader("token") String token, @PathVariable("id") Integer id, @RequestBody Posts posts) {
        String username = JwtUtil.getUsernameFromToken(token);
        return ResultJson.success(postService.updatePost(username, id, posts) ? "修改成功！" : "修改失败，请一会儿再试！");
    }

    @DeleteMapping("/{id}")
    public ResultJson<String> deletePost(@RequestHeader("token") String token, @PathVariable("id") Integer id) {
        String username = JwtUtil.getUsernameFromToken(token);

        return ResultJson.success(postService.deletePost(username, id) ? "删除成功！" : "删除失败，请一会儿再试！");
    }

}
