package com.sky.service;

import com.sky.dto.*;
import com.sky.result.PageResult;
import com.sky.vo.OrderPaymentVO;
import com.sky.vo.OrderStatisticsVO;
import com.sky.vo.OrderSubmitVO;
import com.sky.vo.OrderVO;

/**
 * @BelongsProject: sky-take-out
 * @Author: 张宇若
 * @CreateTime: 2024-11-01  16:00
 * @Description: TODO
 * @Version: 1.0
 */
public interface OrderService {
    /*
    * 用户提交订单
    * */
    OrderSubmitVO submit(OrdersSubmitDTO ordersSubmitDTO);
    /**
     * 订单支付
     * @param ordersPaymentDTO
     * @return
     */
    OrderPaymentVO payment(OrdersPaymentDTO ordersPaymentDTO) throws Exception;

    /**
     * 支付成功，修改订单状态
     * @param outTradeNo
     */
    void paySuccess(String outTradeNo);

    /*
    *
    * 查看当前用户历史订单记录
    * */
    PageResult history(OrdersPageQueryDTO ordersPageQueryDTO);

    /*
    * 根据订单编号查询订单详情
    * */
    OrderVO getDetailByOrderId(Long id);

    /*
    *
    * 根据id取消订单
    * */
    void cancel(Long id);

    /*
    * 再来一单
    * */
    void repeat(Long id);

    /*
    * 搜索订单（按条件搜索）
    * */
    PageResult search(OrdersPageQueryDTO ordersPageQueryDTO);

    /*
    * 统计各状态订单数量
    * */
    OrderStatisticsVO statistics();

    /*
    * 查看订单详情
    * */
    OrderVO details(Long id);

    /*
    * 接单
    * */

    void confirm(OrdersConfirmDTO ordersConfirmDTO);

    /*
    * 拒单
    * */
    void reject(OrdersRejectionDTO ordersRejectionDTO);

    /*
    * 管理端取消订单
    * */
    void adminCancel(OrdersCancelDTO ordersCancelDTO);

    /*
    * 派送订单
    * */
    void delivery(Long id);

    /*
    * 完成订单
    * */
    void complete(Long id);
}
