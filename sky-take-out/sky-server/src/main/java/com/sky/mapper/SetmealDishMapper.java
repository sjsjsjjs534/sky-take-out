package com.sky.mapper;

import com.sky.entity.SetmealDish;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

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

    @Insert("insert into setmeal_dish (setmeal_id,dish_id,name,price,copies) values (#{setmealId},#{dishId},#{name},#{price},#{copies})")
    void insert(SetmealDish setmealDish);

    /*
    * 根据套餐id删除相关数据
    * */
    @Delete("delete from setmeal_dish where setmeal_id=#{id}")
    void deleteBySetmealId(Long id);

    /*
    * 根据套餐id返回线管数据
    * */
    @Select("select * from setmeal_dish where setmeal_id=#{setmealId} ")
    List<SetmealDish> selectBySetmealId(Long setmealId);
}