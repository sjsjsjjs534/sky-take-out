package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.mapper.DishFlavorMapper;
import com.sky.mapper.DishMapper;
import com.sky.result.PageResult;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @BelongsProject: sky-take-out
 * @Author: 张宇若
 * @CreateTime: 2024-10-16  20:47
 * @Description: TODO
 * @Version: 1.0
 */
@Service
public class DishServiceImpl implements DishService {
    @Autowired
    DishMapper dishMapper;
    @Autowired
    DishFlavorMapper dishFlavorMapper;
    /*
    * 新增菜品以及保存菜品相关的口味
    * */
    @Transactional
    public void saveWithFlavors(DishDTO dishDTO) {
        //保存菜品
        Dish dish=new Dish();
        BeanUtils.copyProperties(dishDTO,dish);
        dishMapper.insert(dish);

        //注意在此时我们的flavors中没有dishId，我们要给他赋值
        //我们要在xml文件中将这个属性进行赋值 useGeneratedKeys="true"才能使得我们的dish对象有id值，因为我们插入之前dish对象没有id，插入之后自动增加，设置这个属性了才会返回到这个对象中来
        Long id = dish.getId();

        //保存口味信息
        List<DishFlavor> flavors = dishDTO.getFlavors();
        //设置dishId
        for (DishFlavor flavor : flavors) {
            flavor.setDishId(id);
        }
        if (flavors!=null&&flavors.size()!=0){
            dishFlavorMapper.insertBatch(flavors);
        }

    }

    /*
    * 分页查询
    * */
    @Override
    public PageResult page(DishPageQueryDTO dishPageQueryDTO) {
        //开启分页查询
        PageHelper.startPage(dishPageQueryDTO.getPage(),dishPageQueryDTO.getPageSize());
        Page<DishVO> page= (Page<DishVO>) dishMapper.page(dishPageQueryDTO);
        PageResult pageResult=new PageResult(page.getTotal(),page.getResult());
        return pageResult;


    }
}
