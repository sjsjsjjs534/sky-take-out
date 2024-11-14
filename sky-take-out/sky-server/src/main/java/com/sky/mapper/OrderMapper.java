package com.sky.mapper;

import com.sky.dto.GoodsSalesDTO;
import com.sky.dto.OrdersPageQueryDTO;
import com.sky.entity.Orders;
import com.sky.vo.OrderVO;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;
import java.util.List;

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

    /**
     * 根据订单号查询订单
     * @param orderNumber
     */
    @Select("select * from orders where number = #{orderNumber}")
    Orders getByNumber(String orderNumber);

    /**
     * 修改订单信息
     * @param orders
     */
    void update(Orders orders);

    @Select("select * from orders where number = #{orderNumber} and user_id= #{userId}")
    Orders getByNumberAndUserId(String orderNumber, Long userId);

    List<Orders> list(OrdersPageQueryDTO ordersPageQueryDTO);

    @Select("select * from orders where id=#{id}")
    Orders getById(Long id);

    @Select("select count(id) from orders where status = #{status}")
    Integer countStatus(Integer toBeConfirmed);

    @Select("select * from orders where status=#{status} and order_time <#{time}")
    List<Orders> getByStatusAndOrderTimeLT(Integer status, LocalDateTime time);

    @Select("select sum(amount) from orders where order_time > #{beginTime} and order_time < #{endTime} and status = #{status}")
    Double selectByTimeAndStatus(LocalDateTime beginTime, LocalDateTime endTime, Integer status);

    /*
    * 统计指定时间段指定状态的订单数量
    * */
    Integer count(LocalDateTime beginTime, LocalDateTime endTime, Integer status);

    /*
    * 统计销量排行前十的商品
    * */
    List<GoodsSalesDTO> getSalesTop10(LocalDateTime beginTime, LocalDateTime endTime);
}
