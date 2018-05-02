package com.e3mall.manager.service;

/**
 * @author WJX
 * @since 2018/5/1 13:02
 */

import com.e3mall.common.pojo.EasyUIDataGridResult;
import com.e3mall.common.pojo.TbItem;
import com.e3mall.common.pojo.TbItemDesc;
import com.e3mall.common.util.E3Result;

import java.util.List;

/**
 * item操作类
 */
public interface ItemService {
    /**
     * 通过id查询item
     * @param itemId itemid
     * @return item实例
     */
    TbItem getItemById(long itemId);

    /**
     * 分页查询商品信息
     * @param page 页号
     * @param rows 每页显示行数
     * @return 适合easyui的数据源
     */
    EasyUIDataGridResult getItemList(int page, int rows);

    /**
     * 后台添加商品
     * @param item 商品实例
     * @param desc 商品描述
     * @return 封装结果
     */
    E3Result addItem(TbItem item, String desc);

    /**
     * 后台修改商品信息
     * @param item 商品实例
     * @param desc 商品描述
     * @return 封装结果
     */
    E3Result updateItem(TbItem item,String desc);

    /**
     * 通过id删除商品
     * @param ids id的list
     * @return 封装结果
     */
    E3Result deleteItems(List<Long> ids);

    /**
     * 通过id商品上架
     * @param ids id的list
     * @return 封装结果
     */
    E3Result itemInstock(List<Long> ids);

    /**
     * 通过id商品下架
     * @param ids id的list
     * @return 封装结果
     */
    E3Result itemReshelf(List<Long> ids);

    /**
     * 通过itemid获取商品介绍
     * @param itemId item id
     * @return 商品介绍
     */
    TbItemDesc getItemDescById(long itemId);
}
