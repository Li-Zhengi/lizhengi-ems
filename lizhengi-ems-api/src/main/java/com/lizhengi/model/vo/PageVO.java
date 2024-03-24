package com.lizhengi.model.vo;

import lombok.Data;

/**
 * 展示层 VO 对象：分页信息
 *
 * @author 栗筝i
 */
@Data
public class PageVO {
    /**
     * 页码
     */
    private int pageNo;
    /**
     * 页大小
     */
    private int pageSize;
    /**
     * 页总数
     */
    private int pageTotal;
    /**
     * 数据总数
     */
    private int count;
}
