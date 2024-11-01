package com.sky.service;

import com.sky.dto.OrdersSubmitDTO;
import com.sky.vo.OrderSubmitVO;

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
}
