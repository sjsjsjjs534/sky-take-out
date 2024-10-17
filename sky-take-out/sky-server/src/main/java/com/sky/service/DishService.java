package com.sky.service;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.vo.DishVO;

/**
 * @BelongsProject: sky-take-out
 * @Author: 张宇若
 * @CreateTime: 2024-10-16  20:46
 * @Description: TODO
 * @Version: 1.0
 */
public interface DishService {
    /*
    * 保存菜品信息和口味
    * */
    public void saveWithFlavors(DishDTO dishDTO);

    /*
    * 分页查询
    * */
    PageResult page(DishPageQueryDTO dishPageQueryDTO);

    /*
    * 批量删除菜品
    * */
    void deleteByIds(Long[] ids);

    /*
    * 根据id查询菜品
    * */
    DishVO selectById(Long id);
}
