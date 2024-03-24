package com.lizhengi.convert;

import com.alibaba.fastjson2.JSON;
import com.lizhengi.bo.EmployeeBO;
import com.lizhengi.dao.entity.EmployeeDO;
import com.lizhengi.dao.query.EmployeesQuery;
import com.lizhengi.model.dto.EmployeeReqDTO;
import com.lizhengi.model.dto.PageDTO;
import com.lizhengi.model.enums.EmployeeDeptEnum;
import com.lizhengi.model.enums.EmployeeTitleEnum;
import com.lizhengi.model.vo.EmployeeVO;
import com.lizhengi.model.vo.PageVO;
import com.lizhengi.utils.BeanCopyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author 栗筝i
 */
@Slf4j
public class EmployeesConvert {

    /**
     * DTO 转 Query
     *
     * @param employeeDTO DTO 对象
     * @param pageDTO     page DTO 对象
     * @return Query 对象
     */
    public static EmployeesQuery convertDtoToQuery(EmployeeReqDTO employeeDTO, PageDTO pageDTO) {
        log.info("EmployeeConvert.convertDtoToQuery employeeDTO:{} , pageDTO: {} "
                , JSON.toJSONString(employeeDTO), JSON.toJSONString(pageDTO));
        // 查询条件 BeanCopyUtil
        EmployeesQuery employeesQuery = BeanCopyUtil.copy(employeeDTO, EmployeesQuery.class);
        // 查询条件 可能为空
        if (employeesQuery == null) {
            employeesQuery = new EmployeesQuery();
        }
        // 分页信息
        employeesQuery.setStart((pageDTO.getPageNo() - 1) * pageDTO.getPageSize());
        employeesQuery.setSize(pageDTO.getPageSize());
        log.info("EmployeeConvert.convertDtoToQuery employeesQuery:{} ", JSON.toJSONString(employeesQuery));
        return employeesQuery;
    }

    /**
     * DO 转 BO
     *
     * @param employeeDO DO 对象
     * @return BO 对象
     */
    public static EmployeeBO convertDo2Bo(EmployeeDO employeeDO) {
        // DO 转 BO BeanCopyUtil
        EmployeeBO employeeBO = BeanCopyUtil.copy(employeeDO, EmployeeBO.class);
        // 全称
        employeeBO.setFullName(employeeDO.getFirstName() + " " + employeeDO.getLastName());
        // 性别 - 中文表示
        employeeBO.setGenderChineseName(
                Objects.equals(employeeDO.getGender(), "L") ? "男" : "女");
        // 日期相关 Date 转 String
        employeeBO.setBirthDate(employeeDO.getBirthDate().toString());
        employeeBO.setHireDate(employeeDO.getHireDate().toString());
        employeeBO.setToDate(employeeDO.getToDate().toString());
        employeeBO.setOnTheJob(employeeDO.getOnTheJob().equals(1));
        // 部门名称 - 中文表示
        employeeBO.setDeptChineseName(
                Objects.requireNonNull(EmployeeDeptEnum.fromCode(employeeDO.getDeptName())).getName());
        // 职位名称 - 中文表示
        employeeBO.setTitleChineseName(
                Objects.requireNonNull(EmployeeTitleEnum.fromCode(employeeDO.getTitle())).getName());
        return employeeBO;
    }

    /**
     * DO 批量转 BO
     *
     * @param doList DO 对象
     * @return BO 对象
     */
    public static List<EmployeeBO> convertDo2BoList(List<EmployeeDO> doList) {
        log.info("EmployeeConvert.convertDo2BoList doList: {}", JSON.toJSONString(doList));
        List<EmployeeBO> boList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(doList)) {
            for (EmployeeDO entity : doList) {
                // 复制单个对象并添加到目标对象列表中
                boList.add(EmployeesConvert.convertDo2Bo(entity));
            }
        }
        log.info("EmployeeConvert.convertDo2BoList boList:{} ", JSON.toJSONString(boList));
        return boList;
    }

    /**
     * BO 转 VO
     *
     * @param employeeBO BO 对象
     * @return VO 对象
     */
    public static EmployeeVO convertBo2Vo(EmployeeBO employeeBO) {
        return BeanCopyUtil.copy(employeeBO, EmployeeVO.class);
    }

    /**
     * BO 批量转 VO
     *
     * @param boList BO 对象
     * @return VO 对象
     */
    public static List<EmployeeVO> convertBo2VoList(List<EmployeeBO> boList) {
        log.info("EmployeeConvert.convertDo2BoList doList: {}", JSON.toJSONString(boList));
        List<EmployeeVO> voList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(boList)) {
            for (EmployeeBO bo : boList) {
                // 复制单个对象并添加到目标对象列表中
                voList.add(EmployeesConvert.convertBo2Vo(bo));
            }
        }
        log.info("EmployeeConvert.convertDo2BoList boList:{} ", JSON.toJSONString(voList));
        return voList;
    }

    /**
     * 查询返回时，分页信息转换
     *
     * @param count   数据量
     * @param pageDTO 分页查询 DTO
     * @return 分页信息
     */
    public static PageVO converPageVo(int count, PageDTO pageDTO) {
        PageVO pageVO = BeanCopyUtil.copy(pageDTO, PageVO.class);
        // 只要有任何余数存在，都会使得整体结果增加一，从而实现了向上取整的效果
        pageVO.setPageTotal((count + pageDTO.getPageSize() - 1) / pageDTO.getPageSize());
        pageVO.setCount(count);
        return pageVO;
    }

}
