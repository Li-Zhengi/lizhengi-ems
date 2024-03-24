package com.lizhengi.dao.query;

import lombok.Data;

import java.util.List;

/**
 * 持久层 Query 对象
 *
 * @author liziheng
 */
@Data
public class EmployeesQuery {
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
     * 出生日期（范围查找-开始）
     */
    private String birthDateStart;
    /**
     * 出生日期（范围查找-结束）
     */
    private String birthDateEnd;
    /**
     * 入职日期（范围查找-开始）
     */
    private String hireDateStart;
    /**
     * 入职日期（范围查找-结束）
     */
    private String hireDateEnd;

    /**
     * 部门（多选）
     */
    private List<String> deptNames;
    /**
     * 职位（多选）
     */
    private List<String> titles;

    /**
     * 分页查询-偏移量
     */
    private int start;
    /**
     * 分页查询-页大小
     */
    private int size;
}