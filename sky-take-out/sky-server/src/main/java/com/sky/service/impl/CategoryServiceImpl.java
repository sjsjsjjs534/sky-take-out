package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.context.BaseContext;
import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.CategoryMapper;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.result.PageResult;
import com.sky.service.CategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @BelongsProject: sky-take-out
 * @Author: 魏莱的老公
 * @CreateTime: 2024-10-13  12:53
 * @Description: TODO
 * @Version: 1.0
 */
@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    CategoryMapper categoryMapper;
    @Autowired
    DishMapper dishMapper;
    @Autowired
    SetmealMapper setmealMapper;

    /*
    * 新增分类
    * */
    @Override
    public void add(CategoryDTO categoryDTO) {
        Category category=new Category();
        BeanUtils.copyProperties(categoryDTO,category);
        //设置状态，默认为1（启用）
        category.setStatus(1);
//        //设置创建时间
//        category.setCreateTime(LocalDateTime.now());
//        //设置创建人id
//        category.setCreateUser(BaseContext.getCurrentId());
//        //设置修改时间
//        category.setUpdateTime(LocalDateTime.now());
//        //设置修改人id
//        category.setUpdateUser(BaseContext.getCurrentId());
        categoryMapper.add(category);
    }

    /*
    * 分页查询
    * */
    @Override
    public PageResult page(CategoryPageQueryDTO categoryPageQueryDTO) {
        //设置分页参数
        PageHelper.startPage(categoryPageQueryDTO.getPage(),categoryPageQueryDTO.getPageSize());

        //返回分页结果
        List<Category> list=categoryMapper.list(categoryPageQueryDTO);
        Page<Category> page= (Page<Category>) list;
        PageResult pageResult=new PageResult(page.getTotal(),page.getResult());
        return pageResult;
    }



    /*
    * 修改员工分类状态
    * */
    @Override
    public void modifyStatus(Long id, Integer status) {
        categoryMapper.modifyStatus(id,status);
    }

    /*
    * 根据id删除分类
    * */
    @Override
    public void deleteById(Integer id) {
        //查询当前分类中是否关联了菜品
        Integer count= dishMapper.countByCategoryId(id);
        if (count>0){
            //当前分类下有菜品，不能删除
            throw  new DeletionNotAllowedException(MessageConstant.CATEGORY_BE_RELATED_BY_DISH);
        }
        count= setmealMapper.countByCategoryId(id);
        if (count>0){
            throw  new DeletionNotAllowedException(MessageConstant.CATEGORY_BE_RELATED_BY_SETMEAL);
        }
        categoryMapper.deleteById(id);
    }

    /*
    * 修改分类信息
    * */
    @Override
    public void modify(CategoryDTO categoryDTO) {
        Category category = new Category();
        BeanUtils.copyProperties(categoryDTO,category);

//        category.setUpdateUser(BaseContext.getCurrentId());
//        category.setUpdateTime(LocalDateTime.now());
        categoryMapper.modify(category);
    }

    /*
    * 根据分类类别查询类别列表
    * */
    @Override
    public List list(Integer type) {
        List<Category> list= categoryMapper.getByType(type);
        return list;
    }
}
