package com.sky.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @BelongsProject: sky-take-out
 * @Author: 张宇若
 * @CreateTime: 2024-10-18  16:24
 * @Description: TODO
 * @Version: 1.0
 */


@Mapper
public interface SetmealDishMapper {

    /*
    * 根据dishId查询相关套餐，并返回套餐id
    * */
    List<Long> getSetmealIdsByDishIds(List<Long> dishIds);
}
