package com.sky.service.impl;

import com.sky.entity.Orders;
import com.sky.mapper.EmployeeMapper;
import com.sky.mapper.OrderMapper;
import com.sky.mapper.UserMapper;
import com.sky.service.ReportService;
import com.sky.vo.TurnoverReportVO;
import com.sky.vo.UserReportVO;
import io.swagger.models.auth.In;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @BelongsProject: sky-take-out
 * @Author: 张宇若
 * @CreateTime: 2024-11-12  15:53
 * @Description: TODO
 * @Version: 1.0
 */
@Service
public class ReportServiceImpl implements ReportService {
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private UserMapper userMapper;

    /*
    * 统计营业额
    * */
    @Override
    public TurnoverReportVO turnoverStatistics(LocalDate begin, LocalDate end) {
        //首先把dateList构造出来
        List<LocalDate> dateList =new ArrayList<>();
        dateList.add(begin);
        while (!begin.equals(end)){
            begin=begin.plusDays(1);
            dateList.add(begin);
        }

        //再构造turnoverList
        List<Double> turnoverList =new ArrayList<>();
        for(LocalDate date:dateList){
            //select sum(amount) from orders where order_time>beginTime and order_time<endTime and status=5
            LocalDateTime beginTime=LocalDateTime.of(date, LocalTime.MIN);
            LocalDateTime endTime=LocalDateTime.of(date, LocalTime.MAX);
            Double sum=orderMapper.selectByTimeAndStatus(beginTime,endTime, Orders.COMPLETED);
            //营业额没有统计出来会是空
            if (sum==null){
                turnoverList.add(0.0);
            } else turnoverList.add(sum);
        }

        return TurnoverReportVO
                .builder()
                .dateList(StringUtils.join(dateList,","))
                .turnoverList(StringUtils.join(turnoverList,","))
                .build();
    }

    /*
    * 用户数量统计
    * */
    @Override
    public UserReportVO userStatistics(LocalDate begin, LocalDate end) {
        //先构造dateList
        List<LocalDate> dateList =new ArrayList<>();
        dateList.add(begin);
        while (!begin.equals(end)){
            begin=begin.plusDays(1);
            dateList.add(begin);
        }

        //再构造totalUserList和newUserList
        List<Integer> totalUser=new ArrayList<>();
        List<Integer> newUser=new ArrayList<>();
        int preNum=0;
        for(LocalDate date:dateList){
            //select count(id) from user where create_time<endTime
            LocalDateTime beginTime=LocalDateTime.of(date, LocalTime.MIN);
            LocalDateTime endTime=LocalDateTime.of(date, LocalTime.MAX);
            int total=userMapper.countUserNumByTime(endTime);
            int newNum=total-preNum;
            preNum=total;
            totalUser.add(total);
            newUser.add(newNum);
        }
        return UserReportVO.builder()
                .dateList(StringUtils.join(dateList,","))
                .totalUserList(StringUtils.join(totalUser,","))
                .newUserList(StringUtils.join(newUser,","))
                .build();
    }
}
