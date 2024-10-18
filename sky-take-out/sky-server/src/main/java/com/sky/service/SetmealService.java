package com.sky.service;

import com.sky.dto.SetmealDTO;
import com.sky.entity.Setmeal;
import com.sky.mapper.SetmealMapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    Setmeal getById(Long id);

    /*
    * 新增套餐
    * */
    void add(SetmealDTO setmealDTO);

}
