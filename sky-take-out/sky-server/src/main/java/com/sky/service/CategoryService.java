package com.sky.service;

import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.result.PageResult;

import java.util.List;

/**
 * @BelongsProject: sky-take-out
 * @Author: 魏莱的老公
 * @CreateTime: 2024-10-13  12:53
 * @Description: TODO
 * @Version: 1.0
 */
public interface CategoryService {
    /*
    * 新增分类
    * */
    void add(CategoryDTO categoryDTO);

    /*
    * 分页查询
    * */
    PageResult page(CategoryPageQueryDTO categoryPageQueryDTO);

    /*
    * 修改分类状态
    * */
    void modifyStatus(Long id, Integer status);

    /*
    * 根据id删除分类
    * */
    void deleteById(Integer id);

    /*
    * 修改分类信息
    * */
    void modify(CategoryDTO categoryDTO);

    /*
    * 根据分类类别查询类别列表
    * */
    List list(Integer type);



}
