package com.e3mall.common.enums;

/**
 * @author WJX
 * @since 2018/5/2 17:22
 */

/**
 * 分类状态枚举
 */
public enum ContentCategoryStatusEnum {
    CONTENT_CATEGORY_NORMAL(1,"正常"),
    CONTENT_CATEGORY_DELETED(2,"删除");

    /**
     * 状态码
     */
    private Integer status;

    /**
     * 状态描述
     */
    private String description;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    ContentCategoryStatusEnum(Integer status, String description) {
        this.status = status;
        this.description = description;
    }
}
