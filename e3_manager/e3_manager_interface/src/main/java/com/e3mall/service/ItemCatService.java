package com.e3mall.service;

/**
 * @author WJX
 * @since 2018/5/1 15:46
 */

import com.e3mall.common.pojo.EasyUITreeNode;

import java.util.List;

/**
 * 商品分类接口
 */
public interface ItemCatService {
    /**
     * 通过父节点获取子节点的list
     * @param parentId 父节点id
     * @return 子节点list
     */
    List<EasyUITreeNode> getItemCatList(Long parentId);
}
