package com.sky.mapper;

import com.sky.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @BelongsProject: sky-take-out
 * @Author: 张宇若
 * @CreateTime: 2024-10-25  21:45
 * @Description: TODO
 * @Version: 1.0
 */
@Mapper
public interface UserMapper {
    @Select("select * from user where openid=#{openId}")
    User getByOpenid(String openId);

    //插入用户信息
    void insert(User user);

    @Select("select * from user where id=#{userId}")
    User getById(Long userId);
}
