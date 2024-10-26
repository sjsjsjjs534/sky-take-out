package com.sky.service;

import com.github.pagehelper.Page;
import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.mapper.SetmealMapper;
import com.sky.result.PageResult;
import com.sky.vo.DishItemVO;
import com.sky.vo.SetmealVO;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * @BelongsProject: sky-take-out
 * @Author: 张宇若
 * @CreateTime: 2024-10-18  17:18
 * @Description: TODO
 * @Version: 1.0
 */

public interface SetmealService {

    /*
     * 根据id查询套餐
     * */
    SetmealVO getById(Long id);

    /*
    * 新增套餐
    * */
    void add(SetmealDTO setmealDTO);

    /*
    * 套餐分页查询
    * */
    PageResult page(SetmealPageQueryDTO setmealPageQueryDTO);

    /*
    * 批量删除套餐
    * */
    void deleteByIds(Long[] ids);

    /*
    * 修改套餐
    * */
    void modify(SetmealDTO setmealDTO);

    /*
    * 修改套餐状态
    * */
    void modifyStatus(Integer status,Long id);

    List<Setmeal> list(Setmeal setmeal);

    List<DishItemVO> getDishItemById(Long id);
}
