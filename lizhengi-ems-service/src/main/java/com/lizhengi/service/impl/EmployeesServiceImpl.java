package com.lizhengi.service.impl;

import com.alibaba.fastjson2.JSON;
import com.lizhengi.api.api_redis.service.RedisService;
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
import org.apache.logging.log4j.util.Strings;
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

    private static final String EMPLOYEES_LIST_CACHE = "EmployeesListCaChe";
    private static final String EMPLOYEES_LIST_COUNT_CACHE = "EmployeesListCountCaChe";

    private EmployeesMapper mapper;

    @Autowired
    public void setMapper(EmployeesMapper mapper) {
        this.mapper = mapper;
    }

    private RedisService redisService;

    @Autowired
    public void setRedisService(RedisService redisService) {
        this.redisService = redisService;
    }

    /**
     * 查询员工信息
     *
     * @param req req
     * @return resp
     */
    @Override
    public ListEmployeeResp listEmployees(ListEmployeeReq req) {
        // 记录请求日志
        log.info("EmployeesServiceImpl.listEmployees req: " + JSON.toJSONString(req));

        // 从缓存中获取员工列表，如果缓存不存在则查询数据库
        List<EmployeeVO> voList = getEmployeeVoListFromCacheOrDb(req);

        // 从缓存中获取分页信息，如果缓存不存在则查询数据库
        PageVO pageVO = getPageVoFromCacheOrDb(req);

        // 构建响应对象并返回
        ListEmployeeResp resp = buildEmployeeListResponse(voList, pageVO);

        // 记录响应日志
        log.info("EmployeesServiceImpl.listEmployees resp: " + JSON.toJSONString(resp));
        return resp;
    }

    /**
     * 从缓存中获取员工信息列表，如果缓存不存在，则从数据库查询并更新缓存。
     *
     * @param req 请求参数
     * @return 员工信息列表
     */
    private List<EmployeeVO> getEmployeeVoListFromCacheOrDb(ListEmployeeReq req) {
        String jsonStringKey = JSON.toJSONString(req);
        String jsonStringValue = (String) redisService.getFromHash(EMPLOYEES_LIST_CACHE, jsonStringKey);

        if (Strings.isNotBlank(jsonStringValue)) {
            return JSON.parseArray(jsonStringValue, EmployeeVO.class);
        } else {
            return queryAndCacheEmployeeVoList(req, jsonStringKey);
        }
    }

    /**
     * 查询员工信息列表并缓存结果。
     *
     * @param req      请求参数
     * @param cacheKey 缓存键
     * @return 员工信息列表
     */
    private List<EmployeeVO> queryAndCacheEmployeeVoList(ListEmployeeReq req, String cacheKey) {
        EmployeesQuery query = EmployeesConvert.convertDtoToQuery(req.getQueryInfo(), req.getPage());
        List<EmployeeDO> doList = mapper.listEmployees(query);
        List<EmployeeVO> voList = EmployeesConvert.convertBo2VoList(EmployeesConvert.convertDo2BoList(doList));

        redisService.putToHash(EMPLOYEES_LIST_CACHE, cacheKey, JSON.toJSONString(voList));
        return voList;
    }

    /**
     * 从缓存中获取分页信息，如果缓存不存在，则从数据库查询并更新缓存。
     *
     * @param req 请求参数
     * @return 分页信息
     */
    private PageVO getPageVoFromCacheOrDb(ListEmployeeReq req) {
        String cacheKey = JSON.toJSONString(req);
        String cacheValue = (String) redisService.getFromHash(EMPLOYEES_LIST_COUNT_CACHE, cacheKey);

        if (Strings.isNotBlank(cacheValue)) {
            return JSON.parseObject(cacheValue, PageVO.class);
        } else {
            return queryAndCachePageVo(req, cacheKey);
        }
    }

    /**
     * 查询分页信息并缓存结果。
     *
     * @param req      请求参数
     * @param cacheKey 缓存键
     * @return 分页信息
     */
    private PageVO queryAndCachePageVo(ListEmployeeReq req, String cacheKey) {
        EmployeesQuery query = EmployeesConvert.convertDtoToQuery(req.getQueryInfo(), req.getPage());
        int count = mapper.countEmployees(query);
        PageVO pageVO = EmployeesConvert.converPageVo(count, req.getPage());

        redisService.putToHash(EMPLOYEES_LIST_COUNT_CACHE, cacheKey, JSON.toJSONString(pageVO));
        return pageVO;
    }

    /**
     * 构建员工列表响应对象。
     *
     * @param voList 员工信息列表
     * @param pageVO 分页信息
     * @return 员工列表响应对象
     */
    private ListEmployeeResp buildEmployeeListResponse(List<EmployeeVO> voList, PageVO pageVO) {
        ListEmployeeResp resp = new ListEmployeeResp();
        resp.setData(voList);
        resp.setPage(pageVO);
        return resp;
    }

}
