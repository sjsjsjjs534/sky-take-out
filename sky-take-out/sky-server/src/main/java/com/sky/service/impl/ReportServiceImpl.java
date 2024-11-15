package com.sky.service.impl;

import com.sky.dto.GoodsSalesDTO;
import com.sky.entity.Orders;
import com.sky.mapper.EmployeeMapper;
import com.sky.mapper.OrderMapper;
import com.sky.mapper.UserMapper;
import com.sky.service.ReportService;
import com.sky.service.WorkspaceService;
import com.sky.vo.*;
import io.swagger.models.auth.In;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
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
    @Autowired
    private WorkspaceService workspaceService;

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

    /*
    * 订单统计
    * */
    @Override
    public OrderReportVO ordersStatistics(LocalDate begin, LocalDate end) {
        //先构造dateList
        List<LocalDate> dateList=new ArrayList<>();
        dateList.add(begin);
        LocalDate fakeBegin=begin;
        while (!begin.equals(end)){
            begin=begin.plusDays(1);
            dateList.add(begin);
        }
        //还原begin
        begin=fakeBegin;

        //构造订单数列表（每天的对应）orderCountList
        List<Integer> orderCountList=new ArrayList<>();
        for (LocalDate date:dateList){
            LocalDateTime beginTime=LocalDateTime.of(date,LocalTime.MIN);
            LocalDateTime endTime=LocalDateTime.of(date,LocalTime.MAX);
            //select count(id) from orders where order_time>beginTime and order_time<endTime
            Integer count = orderMapper.count(beginTime, endTime, null);
            if (count==null){
                count=0;
            }
            orderCountList.add(count);
        }
        //构造订单数列表（每天的对应）orderCountList
        List<Integer> validOrderCountList=new ArrayList<>();
        for (LocalDate date:dateList){
            LocalDateTime beginTime=LocalDateTime.of(date,LocalTime.MIN);
            LocalDateTime endTime=LocalDateTime.of(date,LocalTime.MAX);
            //select count(id) from orders where order_time>beginTime and order_time<endTime
            Integer count = orderMapper.count(beginTime, endTime, Orders.COMPLETED);
            if (count==null){
                count=0;
            }
            validOrderCountList.add(count);
        }
        //获取订单总数
        int totalOrderCount=orderMapper.count(LocalDateTime.of(begin,LocalTime.MIN),LocalDateTime.of(end,LocalTime.MAX),null);
        //获取有效订单总数
        int validOrderCount=orderMapper.count(LocalDateTime.of(begin,LocalTime.MIN),LocalDateTime.of(end,LocalTime.MAX), Orders.COMPLETED);
        //订单完成率
        //当订单为0的时候完成率默认设置为100%
        double orderCompletionRate=1.0;
        if (totalOrderCount!=0){
            orderCompletionRate=(double) validOrderCount/(double) totalOrderCount;
        }

        return OrderReportVO.builder()
                .dateList(StringUtils.join(dateList,","))
                .orderCompletionRate(orderCompletionRate)
                .orderCountList(StringUtils.join(orderCountList,","))
                .validOrderCountList(StringUtils.join(validOrderCountList,","))
                .totalOrderCount(totalOrderCount)
                .validOrderCount(validOrderCount)
                .build();
    }

    /*
     * 查询销量排名top10（指定时间区间内）
     *
     *  */
    @Override
    public SalesTop10ReportVO top10(LocalDate begin, LocalDate end) {
        //select od.name,sum(od.number) from order_detail od,orders o where od.order_id=o.id and o.status=5 and o.order_time>beginTime and o.order_time<endTime group by od.name order by number desc limit 0,10
        LocalDateTime beginTime=LocalDateTime.of(begin,LocalTime.MIN);
        LocalDateTime endTime=LocalDateTime.of(end,LocalTime.MAX);
        List<GoodsSalesDTO> list=orderMapper.getSalesTop10(beginTime,endTime);
        List<String> nameList=new ArrayList<>();
        List<String> numberList=new ArrayList<>();
        for (GoodsSalesDTO goodsSalesDTO:list){
            nameList.add(goodsSalesDTO.getName());
            numberList.add(String.valueOf(goodsSalesDTO.getNumber()));
        }
        return SalesTop10ReportVO.builder()
                .nameList(StringUtils.join(nameList,","))
                .numberList(StringUtils.join(numberList,","))
                .build();
    }

    /**导出近30天的运营数据报表
     * @param response
     **/
    public void exportBusinessData(HttpServletResponse response) {
        LocalDate begin = LocalDate.now().minusDays(30);
        LocalDate end = LocalDate.now().minusDays(1);
        //查询概览运营数据，提供给Excel模板文件
        BusinessDataVO businessData = workspaceService.getBusinessData(LocalDateTime.of(begin,LocalTime.MIN), LocalDateTime.of(end, LocalTime.MAX));
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("template/运营数据报表模板.xlsx");
        try {
            //基于提供好的模板文件创建一个新的Excel表格对象
            XSSFWorkbook excel = new XSSFWorkbook(inputStream);
            //获得Excel文件中的一个Sheet页
            XSSFSheet sheet = excel.getSheet("Sheet1");

            sheet.getRow(1).getCell(1).setCellValue(begin + "至" + end);
            //获得第4行
            XSSFRow row = sheet.getRow(3);
            //获取单元格
            row.getCell(2).setCellValue(businessData.getTurnover());
            row.getCell(4).setCellValue(businessData.getOrderCompletionRate());
            row.getCell(6).setCellValue(businessData.getNewUsers());
            row = sheet.getRow(4);
            row.getCell(2).setCellValue(businessData.getValidOrderCount());
            row.getCell(4).setCellValue(businessData.getUnitPrice());
            for (int i = 0; i < 30; i++) {
                LocalDate date = begin.plusDays(i);
                //准备明细数据
                businessData = workspaceService.getBusinessData(LocalDateTime.of(date,LocalTime.MIN), LocalDateTime.of(date, LocalTime.MAX));
                row = sheet.getRow(7 + i);
                row.getCell(1).setCellValue(date.toString());
                row.getCell(2).setCellValue(businessData.getTurnover());
                row.getCell(3).setCellValue(businessData.getValidOrderCount());
                row.getCell(4).setCellValue(businessData.getOrderCompletionRate());
                row.getCell(5).setCellValue(businessData.getUnitPrice());
                row.getCell(6).setCellValue(businessData.getNewUsers());
            }
            //通过输出流将文件下载到客户端浏览器中
            ServletOutputStream out = response.getOutputStream();
            excel.write(out);
            //关闭资源
            out.flush();
            out.close();
            excel.close();

        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
