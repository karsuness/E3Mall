package com.e3mall.common.enums;

/**
 * @author WJX
 * @since 2018/5/3 13:28
 */
public enum RegisterCheckDataEnum {
    USER_NAME(1,"用户名"),
    MOBILE_PHONE(2,"手机号"),
    EMAIL(3,"邮箱");

    /**
     * 类型码
     */
    private Integer code;

    /**
     *描述
     */
    private String description;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    RegisterCheckDataEnum(Integer code, String description) {
        this.code = code;
        this.description = description;
    }
}
