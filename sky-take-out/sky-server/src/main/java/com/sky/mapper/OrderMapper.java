package com.sky.mapper;

import com.sky.entity.Orders;
import org.apache.ibatis.annotations.Mapper;

/**
 * @BelongsProject: sky-take-out
 * @Author: 张宇若
 * @CreateTime: 2024-11-01  16:01
 * @Description: TODO
 * @Version: 1.0
 */
@Mapper
public interface OrderMapper {

    /*
    * 插入订单数据
    * */
    void insert(Orders orders);
}
