package com.rts.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rts.config.MyException;
import com.rts.entity.Posts;
import com.rts.entity.Users;
import com.rts.mapper.PostsMapper;
import com.rts.mapper.UsersMapper;
import com.rts.service.PostsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;



/**
 * <p>
 * 文章表 服务实现类
 * </p>
 *
 * @author rts
 * @since 2024-07-15 17:17:26
 */
@Service
@Slf4j
public class PostsServiceImpl extends ServiceImpl<PostsMapper, Posts> implements PostsService {

    private static final String USERS_TABLE_FIELD_USERNAME = "username";

    private static final String POSTS_TABLE_FIELD_USER_ID = "user_id";

    private static final String POSTS_TABLEFIELD_CREATED = "created";
    @Resource
    private PostsMapper postsMapper;

    @Resource
    private UsersMapper usersMapper;

    @Override
    public Boolean createPost(String username, Posts post) {
        Users users = usersMapper.selectOne(new QueryWrapper<Users>().eq(USERS_TABLE_FIELD_USERNAME, username));
        if (users != null) {
            post.setUserId(users.getUserId());

            return this.save(post);
        } else {
            throw new MyException("此用户不存在！！");
        }
    }

    @Override
    public IPage<Posts> getPostsByUser(Integer uid, Integer pageNo, Integer pageSize) {
        QueryWrapper<Posts> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc(POSTS_TABLEFIELD_CREATED);
        queryWrapper.eq(POSTS_TABLE_FIELD_USER_ID, uid);
        if (pageNo != null && pageSize != null) {
            return this.page(new Page<>(pageNo,pageSize),queryWrapper);
        } else {
            return this.page(new Page<>(1,Integer.MAX_VALUE),queryWrapper);
        }
    }

    @Override
    public Posts getPostById(Integer id) {
        return postsMapper.selectById(id);
    }

    @Override
    public Boolean updatePostById(String username, Integer id, Posts posts) {
        Posts existingPost = postsMapper.selectById(id);
        if (existingPost != null && existingPost.getUserId().equals(getUserIdByUsername(username))) {
            posts.setPostId(id);
            posts.setUserId(existingPost.getUserId());

            return this.updateById(posts);
        } else {
            throw new MyException("无该文章或无权修改他人文章！");
        }
    }

    @Override
    public Boolean deletePostById(String username, Integer id) {
        Posts post = postsMapper.selectById(id);
        if (post != null && post.getUserId().equals(getUserIdByUsername(username))) {
            return this.removeById(id);
        } else {
            throw new MyException("无该文章或无权删除他人文章！");
        }
    }

    private Integer getUserIdByUsername(String username) {
        Users user = usersMapper.selectOne(new QueryWrapper<Users>().eq(USERS_TABLE_FIELD_USERNAME, username));
        return user != null ? user.getUserId() : null;
    }
}
