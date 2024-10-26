package com.sky.mapper;

import com.sky.annotation.AutoFill;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @BelongsProject: sky-take-out
 * @Author: 魏莱的老公
 * @CreateTime: 2024-10-13  12:54
 * @Description: TODO
 * @Version: 1.0
 */
@Mapper
public interface CategoryMapper {
    /*
    * 新增分类
    * */
    @AutoFill(value = OperationType.INSERT)
    @Insert("insert into category (id,type,name,sort,status,create_time,update_time,create_user,update_user)" +
            "values (#{id},#{type},#{name},#{sort},#{status},#{createTime},#{updateTime},#{createUser},#{updateUser})")
    void add(Category category);
    /*
    * 分页查询
    * */
    List<Category> list(CategoryPageQueryDTO categoryPageQueryDTO);

    /*
    * 启用禁用分类
    * */

    @Update("update category set status=#{status} where id=#{id}")
    void modifyStatus(Long id, Integer status);

    /*
    * 根据id删除分类
    * */
    @Delete("delete from category where id=#{id}")
    void deleteById(Integer id);


    /*
    * 修改分类信息
    * */
    @AutoFill(value = OperationType.UPDATE)
    @Update("update category set name=#{name},sort=#{sort},update_time=#{updateTime},update_user=#{updateUser} where id=#{id}")
    void modify(Category category);

    /*
    * 根据分类类别查询分类列表
    * */

    List<Category> list(Integer type);
}
