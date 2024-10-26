package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.enumeration.OperationType;
import com.sky.vo.DishVO;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

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

    List<DishVO> page(DishPageQueryDTO dishPageQueryDTO);

    /*
    * 根据id批量删除菜品
    * */
    void deleteByIds(Long[] ids);

    /*
    * 根据id查询菜品
    * */
    @Select("select * from dish where id=#{id}")
    Dish selectById(Long id);

    @AutoFill(OperationType.UPDATE)
    void update(Dish dish);

    /*
    * 根据dish中参数动态查询dishList
    * */
    List<Dish> list(Dish dish);

}
