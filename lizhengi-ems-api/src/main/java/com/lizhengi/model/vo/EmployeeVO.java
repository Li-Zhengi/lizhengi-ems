package com.lizhengi.model.vo;

import lombok.Data;


/**
 * 展示层 VO 对象：员工信息
 *
 * @author 栗筝i
 */
@Data
public class EmployeeVO {
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
     * 名字（全称）
     */
    private String fullName;
    /**
     * 性别
     */
    private String gender;
    /**
     * 性别
     */
    private String genderChineseName;
    /**
     * 出生日期
     */
    private String birthDate;
    /**
     * 年龄
     */
    private Integer age;
    /**
     * 入职日期
     */
    private String hireDate;
    /**
     * 离职日期（未离职则为 `9999-01-01`）
     */
    private String toDate;
    /**
     * 是否在职
     */
    private Boolean onTheJob;
    /**
     * 职位
     */
    private String title;
    /**
     * 职位（中文）
     */
    private String titleChineseName;
    /**
     * 部门名称
     */
    private String deptName;
    /**
     * 职位（中文）
     */
    private String deptChineseName;
}