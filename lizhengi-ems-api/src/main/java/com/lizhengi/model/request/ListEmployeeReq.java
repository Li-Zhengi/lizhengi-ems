package com.lizhengi.model.request;

import com.lizhengi.model.dto.EmployeeReqDTO;
import com.lizhengi.model.dto.PageDTO;
import lombok.Data;

/**
 * 批量获取员工信息 Req
 *
 * @author 栗筝i
 */
@Data
public class ListEmployeeReq {
    // 查询信息
    private EmployeeReqDTO queryInfo;
    // 分页信息
    private PageDTO page;
}
