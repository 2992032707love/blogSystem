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
    /**
     * 创建新文章
     * @param username
     * @param post
     * @return
     */
    Boolean createPost(String username, Posts post);

    /**
     * 获取该用户的所有文章列表 支持分页/按创建时间正/倒叙
     * @param uid
     * @param pageNo
     * @param pageSize
     * @return
     */
    IPage<Posts> getPostsByUser(Integer uid, Integer pageNo, Integer pageSize);

    /**
     * 获取单篇文章详情
     * @param id
     * @return
     */
    Posts getPostById(Integer id);

    /**
     *  更新文章（需要登录和权限判断）
     * @param username
     * @param id
     * @param posts
     * @return
     */
    Boolean updatePostById(String username, Integer id, Posts posts);

    /**
     * 删除文章（需要登录和权限判断）
     * @param username
     * @param id
     * @return
     */
    Boolean deletePostById(String username, Integer id);
}
