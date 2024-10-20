package com.sky.controller.admin;

import com.github.pagehelper.Page;
import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.SetmealService;
import com.sky.vo.SetmealVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @BelongsProject: sky-take-out
 * @Author: 张宇若
 * @CreateTime: 2024-10-18  17:08
 * @Description: TODO
 * @Version: 1.0
 */
@RestController
@Api(tags = "套餐相关接口")
@RequestMapping("/admin/setmeal")
@Slf4j
public class SetmealController {
    @Autowired
    SetmealService setmealService;

    @GetMapping("/{id}")
    @ApiOperation("根据id查询套餐")
    public Result getById(@PathVariable Long id){
        SetmealVO setmealVo=setmealService.getById(id);
        return Result.success(setmealVo);
    }

    @PostMapping
    @ApiOperation("新增套餐")
    public Result add(@RequestBody SetmealDTO setmealDTO){
        setmealService.add(setmealDTO);
        return Result.success();
    }

    @GetMapping("/page")
    @ApiOperation("套餐分页查询")
    public Result page(SetmealPageQueryDTO setmealPageQueryDTO){
        PageResult pageResult=setmealService.page(setmealPageQueryDTO);
        return Result.success(pageResult);
    }

    @DeleteMapping
    @ApiOperation("批量删除套餐")
    //将String类型的字符串转换为Long数组
    public Result page(@RequestParam Long[] ids){
        setmealService.deleteByIds(ids);
        return Result.success();
    }

    @PutMapping
    @ApiOperation("修改套餐")
    public Result modify(@RequestBody SetmealDTO setmealDTO){
        setmealService.modify(setmealDTO);
        return Result.success();
    }

}
