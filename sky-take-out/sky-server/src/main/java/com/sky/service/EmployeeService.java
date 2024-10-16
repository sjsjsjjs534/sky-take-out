package com.sky.service;

import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.result.PageResult;

public interface EmployeeService {

    /**
     * 员工登录
     * @param employeeLoginDTO
     * @return
     */
    Employee login(EmployeeLoginDTO employeeLoginDTO);

    /*
    * 新增员工
    * */
    void save(EmployeeDTO employeeDTO);

    /*
    * 分页查询员工
    * */
    PageResult Page(EmployeePageQueryDTO employeePageQueryDTO);

    /*
    * 修改员工状态
    * */
    void modifyStatus(Integer status, Integer id);

    /*
    * 修改员工信息
    * */
    void modifyEmp(EmployeeDTO employeeDTO);

    /*
    * 根据id查询员工
    * */
    Employee getById(Integer id);
}
