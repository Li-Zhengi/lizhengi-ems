package com.lizhengi.model.enums;

import lombok.Getter;

/**
 * 职位名称枚举
 *
 * @author 栗筝i
 */
@Getter
public enum EmployeeTitleEnum {
    SENIOR_ENGINEER("Senior Engineer", "高级工程师"),
    STAFF("Staff", "员工"),
    ENGINEER("Engineer", "工程师"),
    SENIOR_STAFF("Senior Staff", "高级员工"),
    ASSISTANT_ENGINEER("Assistant Engineer", "助理工程师"),
    TECHNIQUE_LEADER("Technique Leader", "技术领导"),
    MANAGER("Manager", "经理");

    private final String code;
    private final String name;

    EmployeeTitleEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    /**
     * 通过 Code 获取 Enum
     *
     * @param code Code
     * @return -
     */
    public static EmployeeTitleEnum fromCode(String code) {
        for (EmployeeTitleEnum value : EmployeeTitleEnum.values()) {
            if (value.getCode().equalsIgnoreCase(code)) {
                return value;
            }
        }
        throw new IllegalArgumentException("No enum constant for code: " + code);
    }
}
