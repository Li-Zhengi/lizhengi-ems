package com.lizhengi.model.enums;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * 部门名称枚举
 *
 * @author 栗筝i
 */
@Getter
@Slf4j
public enum EmployeeDeptEnum {
    CUSTOMER_SERVICE("Customer Service", "客户服务"),
    DEVELOPMENT("Development", "开发部"),
    FINANCE("Finance", "财务部"),
    HUMAN_RESOURCES("Human Resources", "人力资源"),
    MARKETING("Marketing", "市场部"),
    PRODUCTION("Production", "生产部"),
    QUALITY_MANAGEMENT("Quality Management", "质量管理"),
    RESEARCH("Research", "研究部"),
    SALES("Sales", "销售部");

    private final String code;
    private final String name;

    EmployeeDeptEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    /**
     * 通过 Code 获取 Enum
     *
     * @param code Code
     * @return -
     */
    public static EmployeeDeptEnum fromCode(String code) {
        for (EmployeeDeptEnum value : EmployeeDeptEnum.values()) {
            if (value.getCode().equalsIgnoreCase(code)) {
                return value;
            }
        }
        log.error("No enum constant for code: " + code);
        return null;
    }
}
