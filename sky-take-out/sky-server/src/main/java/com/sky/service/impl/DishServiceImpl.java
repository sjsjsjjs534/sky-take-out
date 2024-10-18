package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.entity.Setmeal;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.DishFlavorMapper;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealDishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.result.PageResult;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
    @Autowired
    SetmealMapper setmealMapper;
    @Autowired
    SetmealDishMapper setmealDishMapper;
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

    /*
    * 批量删除菜品
    * */
    @Transactional
    @Override
    public void deleteByIds(Long[] ids) {
        //先判断当前要删除的菜品是否为启售中
        for (Long id:ids){
            Dish dish=dishMapper.selectById(id);
            if (dish.getStatus()== StatusConstant.ENABLE){
                throw new DeletionNotAllowedException(MessageConstant.DISH_ON_SALE);
            }
        }

        //再判断当前要删除的菜品是否被套餐关联了
        List<Long> setmealIds= setmealMapper.getSetmealIdsByDishIds(ids);
        if (setmealIds!=null&&setmealIds.size()!=0){
            //如果关联了，抛出业务异常
            throw new DeletionNotAllowedException(MessageConstant.DISH_BE_RELATED_BY_SETMEAL);
        }

        dishMapper.deleteByIds(ids);
        for(Long id:ids){
            dishFlavorMapper.delete(id);
        }

    }

    /*
    * 根据id查询菜品
    * */
    @Override
    public DishVO selectById(Long id) {
        Dish dish= dishMapper.selectById(id);
        List<DishFlavor> dishFlavors=dishFlavorMapper.getByDishId(id);
        DishVO dishVo=new DishVO();
        BeanUtils.copyProperties(dish,dishVo);
        dishVo.setFlavors(dishFlavors);
        return dishVo;
    }

    @Transactional
    @Override
    public void update(DishDTO dishDTO) {
        //更新菜品表
        Dish dish=new Dish();
        BeanUtils.copyProperties(dishDTO,dish);
        dishMapper.update(dish);

        //删除当前菜品关联的口味数据
        dishFlavorMapper.delete(dish.getId());

        //插入最新的口味数据
        List<DishFlavor> dishFlavors=dishDTO.getFlavors();
        if (dishFlavors!=null&&dishFlavors.size()>0){
            dishFlavors.forEach(dishFlavor -> {
                dishFlavor.setDishId(dish.getId());
            });
        }
        dishFlavorMapper.insertBatch(dishFlavors);


    }

    /*
    * 更新起售或者停售
    * */
    @Override
    public void updateStatus(DishDTO dishDTO) {
        //先更新菜品表
        Dish dish=new Dish();
        BeanUtils.copyProperties(dishDTO,dish);
        dishMapper.update(dish);
        //然后停售包含该菜品的所有套餐
        if (dish.getStatus()==StatusConstant.DISABLE){
            Long dishId = dish.getId();
            //根据菜品id停售相关套餐
            List<Long> dishIds=new ArrayList<>();
            dishIds.add(dishId);
            List<Long> setmealIds = setmealDishMapper.getSetmealIdsByDishIds(dishIds);
            if (setmealIds!=null&&setmealIds.size()>0){
                for (Long setmealId:setmealIds){
                    Setmeal setmeal = Setmeal.builder().status(StatusConstant.DISABLE).id(setmealId).build();
                    setmealMapper.update(setmeal);
                }
            }

        }


    }

    /*
    * 根据分类id查询菜品列表
    * */
    @Override
    public List<Dish> list(Long categoryId) {
        Dish dish=Dish.builder().categoryId(categoryId).status(StatusConstant.ENABLE).build();
        return dishMapper.list(dish);
    }
}
