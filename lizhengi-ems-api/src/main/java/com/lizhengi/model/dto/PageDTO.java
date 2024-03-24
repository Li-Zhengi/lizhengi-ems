package com.lizhengi.model.dto;

import lombok.Data;

/**
 * Req 分页信息的 DTO 对象
 *
 * @author 栗筝i
 */
@Data
public class PageDTO {
    /**
     * 分页查询-偏移量
     */
    private int pageNo;
    /**
     * 分页查询-页大小
     */
    private int pageSize;
}
