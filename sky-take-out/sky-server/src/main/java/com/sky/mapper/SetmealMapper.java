package com.sky.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @BelongsProject: sky-take-out
 * @Author: 魏莱的老公
 * @CreateTime: 2024-10-14  16:08
 * @Description: TODO
 * @Version: 1.0
 */
@Mapper
public interface SetmealMapper {
    /*
    * 根据分类id查询套餐的数量
    * */
    @Select("select count(id) from setmeal where category_id=#{categoryId}")
    Integer countByCategoryId(Integer id);
}
