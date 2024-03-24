package com.lizhengi.service.impl;

import com.alibaba.fastjson2.JSON;
import com.lizhengi.bo.EmployeeBO;
import com.lizhengi.convert.EmployeesConvert;
import com.lizhengi.dao.entity.EmployeeDO;
import com.lizhengi.dao.mapper.EmployeesMapper;
import com.lizhengi.dao.query.EmployeesQuery;
import com.lizhengi.model.request.ListEmployeeReq;
import com.lizhengi.model.response.ListEmployeeResp;
import com.lizhengi.model.vo.EmployeeVO;
import com.lizhengi.model.vo.PageVO;
import com.lizhengi.service.EmployeesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 员工信息 ServiceImpl
 *
 * @author 栗筝i
 */
@Service
@Slf4j
public class EmployeesServiceImpl implements EmployeesService {

    private EmployeesMapper mapper;

    @Autowired
    public void setMapper(EmployeesMapper mapper) {
        this.mapper = mapper;
    }


    /**
     * 查询员工信息
     * @param req req
     * @return resp
     */
    @Override
    public ListEmployeeResp listEmployees(ListEmployeeReq req) {
        log.info("EmployeesServiceImpl.listEmployees req: " + JSON.toJSONString(req));
        EmployeesQuery query = EmployeesConvert.convertDtoToQuery(req.getQueryInfo(), req.getPage());
        // 数据查询
        List<EmployeeDO> doList = mapper.listEmployees(query);
        List<EmployeeBO> boList = EmployeesConvert.convertDo2BoList(doList);
        List<EmployeeVO> voList = EmployeesConvert.convertBo2VoList(boList);
        // 分页信息查询
        int count = mapper.countEmployees(query);
        PageVO pageVO = EmployeesConvert.converPageVo(count, req.getPage());
        // Resp
        ListEmployeeResp resp = new ListEmployeeResp();
        resp.setData(voList);
        resp.setPage(pageVO);
        log.info("EmployeesServiceImpl.listEmployees resp: " + JSON.toJSONString(resp));
        return resp;
    }

}
