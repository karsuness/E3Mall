package com.e3mall.cart.service;

import com.e3mall.common.pojo.TbItem;
import com.e3mall.common.util.E3Result;

import java.util.List;

/**
 * @author WJX
 * @since 2018/5/3 14:49
 */

/**
 * 购物车接口
 */
public interface CartService {
    /**
     * 添加购物车
     * @param userId 用户id
     * @param itemId 商品id
     * @param num 商品数量
     * @return 封装结果
     */
    E3Result addCart(long userId, long itemId, int num);

    /**
     * cookie中数据与缓存中合并
     * @param userId 用户id
     * @param itemList 商品的list
     * @return 封装结果
     */
    E3Result mergeCart(long userId, List<TbItem> itemList);

    /**
     * 获取购物车列表
     * @param userId 用户id
     * @return 封装结果
     */
    List<TbItem> getCartList(long userId);

    /**
     * 更新购物车商品数量
     * @param userId 用户id
     * @param itemId 商品id
     * @param num 商品数量
     * @return 封装结果
     */
    E3Result updateCartNum(long userId, long itemId, int num);

    /**
     * 删除购物车商品
     * @param userId 用户id
     * @param itemId 商品id
     * @return 封装结果
     */
    E3Result deleteCartItem(long userId, long itemId);

    /**
     * 删除多个购物车中多个商品
     * @param userId
     * @param itemIds
     * @return
     */
    E3Result deleteCartItems(long userId, List<String> itemIds);

    /**
     * 清空购物车商品
     * @param userId 用户id
     * @return 封装结果
     */
    E3Result clearCartItem(long userId);
}
