package com.sky.mapper;

import com.sky.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;

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

    /*
    * 根据最后时间查询客户总量
    * */
    @Select("select count(id) from user where create_time<#{endTime}")
    int countUserNumByTime(LocalDateTime endTime);
}
