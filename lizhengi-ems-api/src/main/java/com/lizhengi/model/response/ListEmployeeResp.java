package com.lizhengi.model.response;

import com.lizhengi.model.vo.EmployeeVO;
import com.lizhengi.model.vo.PageVO;
import lombok.Data;

import java.util.List;

/**
 * 批量获取员工信息 Resp
 *
 * @author 栗筝i
 */
@Data
public class ListEmployeeResp {
    // 数据信息
    private List<EmployeeVO> data;
    // 分页信息
    private PageVO page;
}
