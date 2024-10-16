package com.sky.mapper;

import com.sky.entity.DishFlavor;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @BelongsProject: sky-take-out
 * @Author: 张宇若
 * @CreateTime: 2024-10-16  21:01
 * @Description: TODO
 * @Version: 1.0
 */
@Mapper
public interface DishFlavorMapper {
    /*
    * 插入口味
    * */
    @Insert("insert into dish_flavor (dish_id,name,value)values (#{dishId},#{name},#{value})")
    void insert(DishFlavor dishFlavor);

    /*
    * 批量插入口味数据
    * */
    void insertBatch(List<DishFlavor> flavors);
}
