package com.rts.mapper;

import com.rts.entity.Users;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author rts
 * @since 2024-07-15 17:13:36
 */
@Mapper
public interface UsersMapper extends BaseMapper<Users> {

}
