package com.lizhengi.service;

import com.lizhengi.model.request.ListEmployeeReq;
import com.lizhengi.model.response.ListEmployeeResp;

/**
 * 员工信息 Service
 *
 * @author 栗筝i
 */
public interface EmployeesService {

    /**
     * 查询员工信息
     * @param req req
     * @return resp
     */
    ListEmployeeResp listEmployees(ListEmployeeReq req);

}
