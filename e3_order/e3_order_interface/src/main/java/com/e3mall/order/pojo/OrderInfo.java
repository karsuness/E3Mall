package com.e3mall.order.pojo;

import com.e3mall.common.pojo.TbOrder;
import com.e3mall.common.pojo.TbOrderItem;
import com.e3mall.common.pojo.TbOrderShipping;

import java.io.Serializable;
import java.util.List;

/**
 * @author WJX
 * @since 2018/5/4 11:01
 */
public class OrderInfo extends TbOrder implements Serializable {
    /**
     * 订单商品
     */
    private List<TbOrderItem> orderItems;
    /**
     *订单物流
     */
    private TbOrderShipping orderShipping;

    public List<TbOrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<TbOrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public TbOrderShipping getOrderShipping() {
        return orderShipping;
    }

    public void setOrderShipping(TbOrderShipping orderShipping) {
        this.orderShipping = orderShipping;
    }
}
