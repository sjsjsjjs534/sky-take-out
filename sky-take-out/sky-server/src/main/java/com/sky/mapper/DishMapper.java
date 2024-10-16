package com.sky.mapper;

import com.sky.annotation.AutoFill;
import com.sky.entity.Dish;
import com.sky.enumeration.OperationType;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @BelongsProject: sky-take-out
 * @Author: 魏莱的老公
 * @CreateTime: 2024-10-14  16:06
 * @Description: TODO
 * @Version: 1.0
 */
@Mapper
public interface DishMapper {
    /*
    * 根据分类id查询菜品数量
    * */
    @Select("select count(id) from dish where category_id=#{categoryId}")
    Integer countByCategoryId(Integer categoryId);

    @AutoFill(OperationType.INSERT)
    void insert(Dish dish);
}
