package com.e3mall.common.enums;

/**
 * @author WJX
 * @since 2018/5/4 12:15
 */
public enum OrderShippingStatusEnum {
    TOBEPAY(1,"待支付"),
    PAY(2,"已付款"),
    TOBESENT(3, "待发货"),
    SEND(4, "已发货"),
    SUCCESS(5, "交易成功"),
    FAILURE(6, "交易关闭")
    ;
    /**
     * 状态
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

    OrderShippingStatusEnum(Integer status, String description) {
        this.status = status;
        this.description = description;
    }
}
