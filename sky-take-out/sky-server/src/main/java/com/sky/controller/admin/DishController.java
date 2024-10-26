package com.sky.controller.admin;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;


/**
 * @BelongsProject: sky-take-out
 * @Author: 张宇若
 * @CreateTime: 2024-10-16  20:43
 * @Description: TODO
 * @Version: 1.0
 */
@RestController
@RequestMapping("/admin/dish")
@Slf4j
@Api(tags = "菜品相关接口")
public class DishController {
    @Autowired
    private DishService dishService;
    @Autowired
    private RedisTemplate redisTemplate;

    @PostMapping
    @ApiOperation("新增菜品")
    public Result save(@RequestBody DishDTO dishDTO){
        dishService.saveWithFlavors(dishDTO);
        //清理掉指定分类的缓存
        String key="dish_"+dishDTO.getCategoryId();
        cleanCache(key);
        return Result.success();
    }

    @GetMapping("page")
    @ApiOperation("菜品分页查询")
    public Result page(DishPageQueryDTO dishPageQueryDTO){
        log.info("分页查询信息:{}",dishPageQueryDTO);
        PageResult pageResult=dishService.page(dishPageQueryDTO);
        return Result.success(pageResult);
    }

    @DeleteMapping
    @ApiOperation("批量删除菜品")
    public Result deleteByIds(@RequestParam Long[] ids){
        dishService.deleteByIds(ids);

        //将所有的菜品缓存数据清理掉，所有易dish_开头的key
        cleanCache("dish_*");

        return Result.success();
    }

    @GetMapping("/{id}")
    @ApiOperation("根据id查询菜品")
    public Result selectById(@PathVariable Long id){
        DishVO dishVO=dishService.selectById(id);
        return Result.success(dishVO);
    }

    @PutMapping
    @ApiOperation("修改菜品信息")
    public Result update(@RequestBody DishDTO dishDTO){
        dishService.update(dishDTO);
        //将所有的菜品缓存数据清理掉，所有易dish_开头的key
        cleanCache("dish_*");
        return Result.success();
    }
    @PostMapping("/status/{status}")
    public Result modifyStatus(@PathVariable Integer status,Long id){
        DishDTO dishDTO=new DishDTO();
        dishDTO.setStatus(status);
        dishDTO.setId(id);
        dishService.updateStatus(dishDTO);
        //将所有的菜品缓存数据清理掉，所有易dish_开头的key
        cleanCache("dish_*");
        return Result.success();
    }

    @GetMapping("/list")
    @ApiOperation("根据分类查询菜品")
    public Result list(Long categoryId){
        List<Dish> list=dishService.list(categoryId);
        return Result.success(list);
    }

    /*
    * 清理缓存数据
    * */
    public void cleanCache(String pattern){
        Set keys = redisTemplate.keys(pattern);
        redisTemplate.delete(keys);
    }


}
