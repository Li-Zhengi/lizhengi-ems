package com.lizhengi.web;

import com.lizhengi.model.request.ListEmployeeReq;
import com.lizhengi.model.response.ListEmployeeResp;
import com.lizhengi.service.EmployeesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 员工信息 Controller
 *
 * @author 栗筝i
 */
@RestController
@RequestMapping("/api/lizhengi/employees")
public class EmployeesController {

    EmployeesService service;

    @Autowired
    public void setService(EmployeesService service) {
        this.service = service;
    }

    /**
     * 查询员工信息
     *
     * @param req req
     * @return resp
     */
    @RequestMapping(path = {"/list"}, method = RequestMethod.POST)
    public ListEmployeeResp listEmployees(@RequestBody ListEmployeeReq req) {
        return service.listEmployees(req);
    }

}
