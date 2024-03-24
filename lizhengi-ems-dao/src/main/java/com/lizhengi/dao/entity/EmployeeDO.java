package com.lizhengi.dao.entity;

import lombok.Data;

import java.sql.Date;

/**
 * 持久化对象：员工信息表（MySQL:employees）
 *
 * @author 栗筝i
 */
@Data
public class EmployeeDO {
    /**
     * 员工编号
     */
    private Integer empNo;
    /**
     * 名字
     */
    private String firstName;
    /**
     * 姓氏
     */
    private String lastName;
    /**
     * 性别
     */
    private String gender;
    /**
     * 出生日期
     */
    private Date birthDate;
    /**
     * 入职日期
     */
    private Date hireDate;
    /**
     * 离职日期（未离职则为 `9999-01-01`）
     */
    private Date toDate;
    /**
     * 在职 1 离职 0
     */
    private Integer onTheJob;
    /**
     * 职位
     */
    private String title;
    /**
     * 部门名称
     */
    private String deptName;
}