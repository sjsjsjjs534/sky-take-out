package com.sky.service.impl;

import com.sky.context.BaseContext;
import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.Dish;
import com.sky.entity.Setmeal;
import com.sky.entity.ShoppingCart;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.mapper.ShoppingCartMapper;
import com.sky.service.ShoppingCartService;
import org.apache.ibatis.annotations.Update;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @BelongsProject: sky-take-out
 * @Author: 张宇若
 * @CreateTime: 2024-10-31  14:13
 * @Description: TODO
 * @Version: 1.0
 */
@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
    @Autowired
    private ShoppingCartMapper shoppingCartMapper;
    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private SetmealMapper setmealMapper;
    /*
    * 新增购物车商品
    * */
    public void add(ShoppingCartDTO shoppingCartDTO) {
        //先封装一下shoppingCart，要额外添加userId属性
        ShoppingCart shoppingCart = new ShoppingCart();
        BeanUtils.copyProperties(shoppingCartDTO,shoppingCart);
        shoppingCart.setUserId(BaseContext.getCurrentId());
        //先判断该用户的购物车中是否有这个商品
        List<ShoppingCart> list = shoppingCartMapper.list(shoppingCart);
        //有的话数量加1即可
        if (list!=null&&list.size()>0){
            //因为这样查出来的list肯定只有一个
            ShoppingCart cart=list.get(0);
            cart.setNumber(cart.getNumber()+1);
            shoppingCartMapper.updateNumberById(cart);
        } else {
            //若没有，则插入一个数据
            //我们要来完善shoppingCart对象的属性，我们要判断其是套餐还是菜品，然后去查相应的表
            if (shoppingCart.getDishId()!=null){
                Dish dish = dishMapper.selectById(shoppingCart.getDishId());
                shoppingCart.setName(dish.getName());
                shoppingCart.setAmount(dish.getPrice());
                shoppingCart.setImage(dish.getImage());
            }else {
                Setmeal setmeal = setmealMapper.getById(shoppingCart.getSetmealId());
                shoppingCart.setName(setmeal.getName());
                shoppingCart.setAmount(setmeal.getPrice());
                shoppingCart.setImage(setmeal.getImage());
            }
            shoppingCart.setNumber(1);
            shoppingCart.setCreateTime(LocalDateTime.now());
            shoppingCartMapper.insert(shoppingCart);

        }
    }

    /*
    * 查看购物车
    * */
    @Override
    public List<ShoppingCart> list() {
        ShoppingCart shoppingCart=new ShoppingCart();
        shoppingCart.setUserId(BaseContext.getCurrentId());
        //根据用户id查询购物车
        return shoppingCartMapper.list(shoppingCart);
    }

    /*
    * 清空购物车
    * */
    @Override
    public void clean() {
        shoppingCartMapper.deleteByUserId(BaseContext.getCurrentId());
    }
}
