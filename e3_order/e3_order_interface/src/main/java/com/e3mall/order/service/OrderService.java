package com.e3mall.order.service;

/**
 * @author WJX
 * @since 2018/5/4 11:09
 */

import com.e3mall.common.util.E3Result;
import com.e3mall.order.pojo.OrderInfo;

/**
 * 订单接口
 */
public interface OrderService {
    /**
     * 创建订单
     * @param orderInfo 订单信息
     * @return
     */
    E3Result createOrder(OrderInfo orderInfo);
}
