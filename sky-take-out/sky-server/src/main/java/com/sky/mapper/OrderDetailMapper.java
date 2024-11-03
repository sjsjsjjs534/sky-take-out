package com.sky.mapper;

import com.sky.entity.OrderDetail;
import com.sky.entity.ShoppingCart;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @BelongsProject: sky-take-out
 * @Author: 张宇若
 * @CreateTime: 2024-11-01  16:01
 * @Description: TODO
 * @Version: 1.0
 */
@Mapper
public interface OrderDetailMapper {
    /*
    * 批量插入订单明细数据
    * */
    void insertBatch(List<OrderDetail> orderDetailList);

    //根据订单编号查询详情
    @Select("select * from order_detail where order_id=#{orderId}")
    List<OrderDetail> getByOrderId(Long orderId);
}
