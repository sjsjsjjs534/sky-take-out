package com.sky.service;

import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.ShoppingCart;

import java.util.List;

/**
 * @BelongsProject: sky-take-out
 * @Author: 张宇若
 * @CreateTime: 2024-10-31  14:12
 * @Description: TODO
 * @Version: 1.0
 */
public interface ShoppingCartService {
    /*
    * 新增购物车商品
    * */
    public void add(ShoppingCartDTO shoppingCartDTO);

    /*
    * 查看购物车
    * */
    List<ShoppingCart> list();


    /*
    * 清空购物车
    * */
    void clean();

}
