package com.e3mall.common.enums;

/**
 * @author WJX
 * @since 2018/5/1 17:14
 */

/**
 * 商品状态枚举
 */
public enum ItemStatusEnum {
    ITEM_ON_SALE((byte) 1, "商品正常"),
    ITEM_UNDER_SALE((byte) 2, "商品下架"),
    ITEM_DELETE((byte) 3, "商品删除");

    /**
     * 状态码
     */
    private Byte status;

    /**
     * 状态描述
     */
    private String description;

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    ItemStatusEnum(Byte status, String description) {
        this.status = status;
        this.description = description;
    }
}
