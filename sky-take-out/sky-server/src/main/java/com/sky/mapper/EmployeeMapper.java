package com.sky.mapper;

import com.sky.annotation.AutoFill;
import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface EmployeeMapper {

    /**
     * 根据用户名查询员工
     * @param username
     * @return
     */
    @Select("select * from employee where username = #{username}")
    Employee getByUsername(String username);

    /*
    * 新增员工
    * */
    @AutoFill(value = OperationType.INSERT)
    @Insert("insert into employee (name,username,password,phone,sex,id_number,create_time,update_time,create_user,update_user,status) " +
            "values " +
            "(#{name},#{username},#{password},#{phone},#{sex},#{idNumber},#{createTime},#{updateTime},#{createUser},#{updateUser},#{status})")
    void save(Employee employee);

    /*
    * 员工信息查询
    * */
    List<Employee> list(EmployeePageQueryDTO employeePageQueryDTO);

    /*
    * 更新员工状态
    * */
    @Update("update employee set status=#{status} where id=#{id}")
    void modifyStatus(Integer status, Integer id);

    /*
    * 修改员工信息
    * */
    @AutoFill(value = OperationType.UPDATE)
    @Update("update  employee set id_number=#{idNumber},name=#{name},phone=#{phone},sex=#{sex},username=#{username},update_time=#{updateTime},update_user=#{updateUser} where id=#{id}")
    void modifyEmp(Employee employee);

    /*
    * 根据id查询员工
    * */

    @Select("SELECT * FROM employee where id=#{id}")
    Employee getById(Integer id);
}
