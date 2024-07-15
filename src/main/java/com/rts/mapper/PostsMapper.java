package com.rts.mapper;

import com.rts.entity.Posts;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 文章表 Mapper 接口
 * </p>
 *
 * @author rts
 * @since 2024-07-15 17:17:26
 */
@Mapper
public interface PostsMapper extends BaseMapper<Posts> {

}
