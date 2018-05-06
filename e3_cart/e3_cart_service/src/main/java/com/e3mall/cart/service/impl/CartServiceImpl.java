package com.e3mall.cart.service.impl;

import com.e3mall.cart.service.CartService;
import com.e3mall.common.jedis.JedisHelper;
import com.e3mall.common.pojo.TbItem;
import com.e3mall.common.util.E3Result;
import com.e3mall.common.util.JsonUtils;
import com.e3mall.mapper.TbItemMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author WJX
 * @since 2018/5/3 14:59
 */
@Service("cartService")
public class CartServiceImpl implements CartService {
    @Autowired
    private JedisHelper jedisHelper;

    @Value("${REDIS_CART_PRE}")
    private String REDIS_CART_PRE;

    @Autowired
    private TbItemMapper tbItemMapper;

    @Override
    public E3Result addCart(long userId, long itemId, int num) {
        //向redis中添加购物车。
        //数据类型是hash key：用户id field：商品id value：商品信息
        //判断商品是否存在
        Boolean hexists = jedisHelper.hexists(REDIS_CART_PRE + ":" + userId, itemId + "");
        //如果存在数量相加
        if (hexists) {
            String json = jedisHelper.hget(REDIS_CART_PRE + ":" + userId, itemId + "");
            //把json转换成TbItem
            TbItem item = JsonUtils.jsonToPojo(json, TbItem.class);
            item.setNum(item.getNum() + num);
            //写回redis
            jedisHelper.hset(REDIS_CART_PRE + ":" + userId, itemId + "", JsonUtils.objectToJson(item));
            return E3Result.ok();
        }
        //如果不存在，根据商品id取商品信息
        TbItem item = tbItemMapper.selectByPrimaryKey(itemId);
        //设置购物车数据量
        item.setNum(num);
        //取一张图片
        String image = item.getImage();
        if (StringUtils.isNotBlank(image)) {
            item.setImage(image.split(",")[0]);
        }
        //添加到购物车列表
        jedisHelper.hset(REDIS_CART_PRE + ":" + userId, itemId + "", JsonUtils.objectToJson(item));
        return E3Result.ok();
    }

    @Override
    public E3Result mergeCart(long userId, List<TbItem> itemList) {
        //遍历商品列表
        //把列表添加到购物车。
        //判断购物车中是否有此商品
        //如果有，数量相加
        //如果没有添加新的商品
        for (TbItem tbItem : itemList) {
            addCart(userId, tbItem.getId(), tbItem.getNum());
        }
        //返回成功
        return E3Result.ok();
    }

    @Override
    public List<TbItem> getCartList(long userId) {
        //根据用户id查询购车列表
        List<String> jsonList = jedisHelper.hvals(REDIS_CART_PRE + ":" + userId);
        List<TbItem> itemList = new ArrayList<>();
        for (String string : jsonList) {
            //创建一个TbItem对象
            TbItem item = JsonUtils.jsonToPojo(string, TbItem.class);
            //添加到列表
            itemList.add(item);
        }
        return itemList;
    }

    @Override
    public E3Result updateCartNum(long userId, long itemId, int num) {
        //从redis中取商品信息
        String json = jedisHelper.hget(REDIS_CART_PRE + ":" + userId, itemId + "");
        //更新商品数量
        TbItem tbItem = JsonUtils.jsonToPojo(json, TbItem.class);
        tbItem.setNum(num);
        //写入redis
        jedisHelper.hset(REDIS_CART_PRE + ":" + userId, itemId + "", JsonUtils.objectToJson(tbItem));
        return E3Result.ok();
    }

    @Override
    public E3Result deleteCartItem(long userId, long itemId) {
        // 删除购物车商品
        jedisHelper.hdel(REDIS_CART_PRE + ":" + userId, itemId + "");
        return E3Result.ok();
    }

    @Override
    public E3Result deleteCartItems(long userId, List<String> itemIds) {
        for(String itemId:itemIds){
            jedisHelper.hdel(REDIS_CART_PRE + ":" + userId, itemId + "");
        }
        return E3Result.ok();
    }

    @Override
    public E3Result clearCartItem(long userId) {
        //删除购物车信息
        jedisHelper.del(REDIS_CART_PRE + ":" + userId);
        return E3Result.ok();
    }
}
