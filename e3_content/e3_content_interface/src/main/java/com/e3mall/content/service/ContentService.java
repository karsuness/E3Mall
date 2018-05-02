package com.e3mall.content.service;

/**
 * @author WJX
 * @since 2018/5/2 17:44
 */

import com.e3mall.common.pojo.EasyUIDataGridResult;
import com.e3mall.common.pojo.TbContent;
import com.e3mall.common.util.E3Result;

import java.util.List;

/**
 * 后台内容管理接口
 */
public interface ContentService {
    /**
     * 添加内容
     * @param tbContent 内容
     * @return 封装结果
     */
    E3Result addContent(TbContent tbContent);

    /**
     * 通过cid展示内容列表
     * @param cid
     * @return TbContent的list
     */
    List<TbContent> getContentListByCid(long cid);

    /**
     * 展示内容列表
     * @param categoryId 内容id
     * @param page 分页页号
     * @param rows 分页的页大小
     * @return easyui接受的数据结果
     */
    EasyUIDataGridResult showContentList(Long categoryId, Integer page, Integer rows);
}
