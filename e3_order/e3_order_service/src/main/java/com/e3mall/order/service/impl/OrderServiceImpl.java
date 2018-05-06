package com.e3mall.order.service.impl;

import com.e3mall.common.enums.OrderShippingStatusEnum;
import com.e3mall.common.jedis.JedisHelper;
import com.e3mall.common.pojo.TbOrderItem;
import com.e3mall.common.pojo.TbOrderShipping;
import com.e3mall.common.util.E3Result;
import com.e3mall.mapper.TbOrderItemMapper;
import com.e3mall.mapper.TbOrderMapper;
import com.e3mall.mapper.TbOrderShippingMapper;
import com.e3mall.order.pojo.OrderInfo;
import com.e3mall.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author WJX
 * @since 2018/5/4 11:43
 */
@Service("orderService")
public class OrderServiceImpl implements OrderService {
    @Autowired
    private TbOrderMapper orderMapper;
    @Autowired
    private TbOrderItemMapper orderItemMapper;
    @Autowired
    private TbOrderShippingMapper orderShippingMapper;
    @Autowired
    private JedisHelper jedisHelper;

    @Value("${ORDER_ID_GEN_KEY}")
    private String ORDER_ID_GEN_KEY;
    @Value("${ORDER_ID_START}")
    private String ORDER_ID_START;
    @Value("${ORDER_DETAIL_ID_GEN_KEY}")
    private String ORDER_DETAIL_ID_GEN_KEY;

    @Override
    public E3Result createOrder(OrderInfo orderInfo) {
        //生成订单号。使用redis的incr生成。
        if (!jedisHelper.exists(ORDER_ID_GEN_KEY)) {
            jedisHelper.set(ORDER_ID_GEN_KEY, ORDER_ID_START);
        }
        String orderId = jedisHelper.incr(ORDER_ID_GEN_KEY).toString();
        //补全orderInfo的属性
        orderInfo.setOrderId(orderId);
        orderInfo.setStatus(OrderShippingStatusEnum.TOBEPAY.getStatus());
        orderInfo.setCreateTime(new Date());
        orderInfo.setUpdateTime(new Date());
        //插入订单表
        orderMapper.insert(orderInfo);
        //向订单明细表插入数据。
        List<TbOrderItem> orderItems = orderInfo.getOrderItems();
        for (TbOrderItem tbOrderItem : orderItems) {
            //生成明细id
            String odId = jedisHelper.incr(ORDER_DETAIL_ID_GEN_KEY).toString();
            //补全pojo的属性
            tbOrderItem.setId(odId);
            tbOrderItem.setOrderId(orderId);
            //向明细表插入数据
            orderItemMapper.insert(tbOrderItem);
        }
        //向订单物流表插入数据
        TbOrderShipping orderShipping = orderInfo.getOrderShipping();
        orderShipping.setOrderId(orderId);
        orderShipping.setCreated(new Date());
        orderShipping.setUpdated(new Date());
        orderShippingMapper.insert(orderShipping);
        //返回E3Result，包含订单号
        return E3Result.ok(orderId);
    }
}
