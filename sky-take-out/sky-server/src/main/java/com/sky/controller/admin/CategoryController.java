package com.sky.controller.admin;

import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.CategoryService;
import com.sky.service.impl.CategoryServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.List;

/**
 * @BelongsProject: sky-take-out
 * @Author: 魏莱的老公
 * @CreateTime: 2024-10-13  12:51
 * @Description: TODO
 * @Version: 1.0
 */
@RestController
@Api(tags = "分类相关接口")
@Slf4j
@RequestMapping("/admin/category")
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    /*
    * 新增分类
    * */
    @PostMapping
    @ApiOperation("新增分类")
    public Result add(@RequestBody CategoryDTO categoryDTO){
        log.info("新增的分类为:{}",categoryDTO);
        categoryService.add(categoryDTO);
        return Result.success();
    }

    /*
    * 分页展示
    * */
    @GetMapping("/page")
    @ApiOperation("分类分页查询")
    public Result page(CategoryPageQueryDTO categoryPageQueryDTO){
        log.info("分页查询参数为：{}",categoryPageQueryDTO);
        PageResult pageResult=categoryService.page(categoryPageQueryDTO);
        return Result.success(pageResult);
    }

    /*
    * 启用禁用分类
    * */
    @PostMapping("/status/{status}")
    @ApiOperation("启用禁用分类")
    public Result modifyStatus(@PathVariable Integer status,Long id){
        log.info("id为{}的分类要设置为status:{}",id,status);
        categoryService.modifyStatus(id,status);
        return Result.success();
    }

    /*
    * 根据id删除分类
    * */
    @DeleteMapping
    @ApiOperation("根据id删除分类")
    public Result deleteById(Integer id){
        log.info("要删除的分类id为:{}",id);
        categoryService.deleteById(id);
        return Result.success();
    }

    /*
    * 修改分类信息
    * */
    @PutMapping
    @ApiOperation("修改分类信息")
    public Result modify(@RequestBody CategoryDTO categoryDTO){
        categoryService.modify(categoryDTO);
        return Result.success();
    }

    /*
    * 根据类型查询
    * */
    @GetMapping("/list")
    @ApiOperation("根据类型查询分类")
    public Result list(Integer type){
       List<Category> list= categoryService.list(type);
       return Result.success(list);
    }
}
