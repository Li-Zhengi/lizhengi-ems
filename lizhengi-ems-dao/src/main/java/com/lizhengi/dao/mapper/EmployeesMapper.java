package com.lizhengi.dao.mapper;

import com.lizhengi.dao.entity.EmployeeDO;
import com.lizhengi.dao.query.EmployeesQuery;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Mapper：员工信息表（MySQL:employees）
 *
 * @author 栗筝i
 */
@Mapper
public interface EmployeesMapper {

    /**
     * 根据分页信息查询员工信息列表
     *
     * @return List<EmployeesPO>
     */
    List<EmployeeDO> listEmployees(EmployeesQuery query);

    /**
     * 根据信息查询符合查询的员工信息总数
     *
     * @return 符合查询的员工信息总数
     */
    int countEmployees(EmployeesQuery query);

}