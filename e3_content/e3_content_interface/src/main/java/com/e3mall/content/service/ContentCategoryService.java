package com.e3mall.content.service;

/**
 * @author WJX
 * @since 2018/5/2 17:10
 */

import com.e3mall.common.pojo.EasyUITreeNode;
import com.e3mall.common.util.E3Result;

import java.util.List;

/**
 * 内容管理接口
 */
public interface ContentCategoryService {
    /**
     * 获取内容节点列表
     * @param parentId 父节点id
     * @return EasyUITreeNode的list
     */
    List<EasyUITreeNode> getContentCatList(long parentId);

    /**
     * 添加新的子节点
     * @param parentId 父节点id
     * @param name 父节点名称
     * @return 封装结果
     */
    E3Result addContentCategory(long parentId, String name);

    /**
     * 节点重命名
     * @param id 节点id
     * @param name 节点名
     * @return 封装结果
     */
    E3Result updateContentCategory(Long id, String name);

    /**
     * 删除节点
     * @param id 节点id
     * @return 封装结果
     */
    E3Result deleteContentCategory(Long id);
}
