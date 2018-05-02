package com.e3mall.search.service;

import com.e3mall.common.util.E3Result;

/**
 * @author WJX
 * @since 2018/5/2 20:25
 */
public interface SearchItemService {
    /**
     * 将所有商品导入索引库
     * @return 封装结果
     */
    E3Result importAllItems();
}
