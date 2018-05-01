package com.e3mall.service;

/**
 * @author WJX
 * @since 2018/5/1 13:02
 */

import com.e3mall.pojo.EasyUIDataGridResult;
import com.e3mall.pojo.TbItem;

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
}
