package com.sky.service;

import com.sky.vo.OrderReportVO;
import com.sky.vo.SalesTop10ReportVO;
import com.sky.vo.TurnoverReportVO;
import com.sky.vo.UserReportVO;

import java.time.LocalDate;

/**
 * @BelongsProject: sky-take-out
 * @Author: 张宇若
 * @CreateTime: 2024-11-12  15:53
 * @Description: TODO
 * @Version: 1.0
 */
public interface ReportService {
    /*
    * 营业额统计
    * */
    TurnoverReportVO turnoverStatistics(LocalDate begin, LocalDate end);

    /*
    * 用户数量统计
    * */
    UserReportVO userStatistics(LocalDate begin, LocalDate end);

    /*
    * 订单统计
    * */
    OrderReportVO ordersStatistics(LocalDate begin, LocalDate end);

    /*
    * 查询销量排名top10
    * */
    SalesTop10ReportVO top10(LocalDate begin, LocalDate end);

}
