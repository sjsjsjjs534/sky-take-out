package com.sky.mapper;

import com.sky.entity.ShoppingCart;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @BelongsProject: sky-take-out
 * @Author: 张宇若
 * @CreateTime: 2024-10-31  14:12
 * @Description: TODO
 * @Version: 1.0
 */
@Mapper
public interface ShoppingCartMapper {
    /*
    * 查询满足条件的购物车数据
    * */
    List<ShoppingCart> list(ShoppingCart shoppingCart);

    /*
    * 更新数量
    * */
    @Update("update shopping_cart set number=#{number} where id=#{id}")
    void updateNumberById(ShoppingCart cart);

    /*
    * 插入购物车数据
    * */
    void insert(ShoppingCart shoppingCart);

    /*
    * 根据用户id删除其相关的所有购物车数据
    * */
    @Delete("delete from shopping_cart where user_id=#{userId}")
    void deleteByUserId(Long currentId);

}
