package com.rts.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.rts.entity.Posts;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Base64;
import java.util.List;

/**
 * <p>
 * 文章表 服务类
 * </p>
 *
 * @author rts
 * @since 2024-07-15 17:17:26
 */
public interface PostsService extends IService<Posts> {
    Boolean createPost(String username, Posts post);

    IPage<Posts> getPosts(Integer uid, Integer pageNo, Integer pageSize);

    Posts getPost(Integer id);

    Boolean updatePost(String username, Integer id, Posts posts);

    Boolean deletePost(String username, Integer id);
}
