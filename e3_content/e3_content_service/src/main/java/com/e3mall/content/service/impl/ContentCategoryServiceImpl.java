package com.e3mall.content.service.impl;

import com.e3mall.common.enums.ContentCategoryStatusEnum;
import com.e3mall.common.pojo.EasyUITreeNode;
import com.e3mall.common.pojo.TbContentCategory;
import com.e3mall.common.pojo.TbContentCategoryExample;
import com.e3mall.common.util.E3Result;
import com.e3mall.content.service.ContentCategoryService;
import com.e3mall.mapper.TbContentCategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author WJX
 * @since 2018/5/2 17:16
 */
@Service("contentCategoryService")
public class ContentCategoryServiceImpl implements ContentCategoryService {

    @Autowired
    private TbContentCategoryMapper tbContentCategoryMapper;

    @Autowired
    private TbContentCategoryMapper contentCategoryMapper;

    @Override
    public List<EasyUITreeNode> getContentCatList(long parentId) {
        TbContentCategoryExample example = new TbContentCategoryExample();
        TbContentCategoryExample.Criteria criteria = example.createCriteria();
        criteria.andParentIdEqualTo(parentId);
        List<TbContentCategory> list = tbContentCategoryMapper.selectByExample(example);
        List<EasyUITreeNode> result = new ArrayList<>();

        for (TbContentCategory tbContentCategory : list) {
            EasyUITreeNode easyUITreeNode = new EasyUITreeNode();
            easyUITreeNode.setId(tbContentCategory.getId());
            easyUITreeNode.setText(tbContentCategory.getName());
            easyUITreeNode.setState(tbContentCategory.getIsParent() ? "closed" : "open");
            result.add(easyUITreeNode);
        }
        return result;
    }

    @Override
    public E3Result addContentCategory(long parentId, String name) {
        //创建一个tb_content_category表对应的pojo对象
        TbContentCategory contentCategory = new TbContentCategory();
        //设置pojo的属性
        contentCategory.setParentId(parentId);
        contentCategory.setName(name);
        //1(正常),2(删除)
        contentCategory.setStatus(ContentCategoryStatusEnum.CONTENT_CATEGORY_NORMAL.getStatus());
        //默认排序就是1
        contentCategory.setSortOrder(1);
        //新添加的节点一定是叶子节点
        contentCategory.setIsParent(false);
        contentCategory.setCreated(new Date());
        contentCategory.setUpdated(new Date());
        //插入到数据库
        contentCategoryMapper.insert(contentCategory);
        //判断父节点的isparent属性。如果不是true改为true
        //根据parentid查询父节点
        TbContentCategory parent = contentCategoryMapper.selectByPrimaryKey(parentId);
        if (!parent.getIsParent()) {
            parent.setIsParent(true);
            //更新到数据库
            contentCategoryMapper.updateByPrimaryKey(parent);
        }
        //返回结果，返回E3Result，包含pojo
        return E3Result.ok(contentCategory);
    }

    @Override
    public E3Result updateContentCategory(Long id, String name) {
        TbContentCategory tbContentCategory = tbContentCategoryMapper.selectByPrimaryKey(id);
        tbContentCategory.setName(name);
        tbContentCategoryMapper.updateByPrimaryKey(tbContentCategory);
        return E3Result.ok();
    }

    @Override
    public E3Result deleteContentCategory(Long id) {
        TbContentCategory tbContentCategory = tbContentCategoryMapper.selectByPrimaryKey(id);
        //是父节点
        if (tbContentCategory.getIsParent()) {
            //先找到所有的子节点
            TbContentCategoryExample example = new TbContentCategoryExample();
            TbContentCategoryExample.Criteria criteria = example.createCriteria();
            criteria.andParentIdEqualTo(id);
            List<TbContentCategory> tbContentCategories = tbContentCategoryMapper.selectByExample(example);
            //删除子节点
            for (TbContentCategory contentCategory : tbContentCategories) {
                tbContentCategoryMapper.deleteByPrimaryKey(contentCategory.getId());
            }
        }

        //找到父节点
        TbContentCategory parentContentCategory = tbContentCategoryMapper.selectByPrimaryKey(tbContentCategory.getParentId());
        //判断父节点有没有其他子节点
        TbContentCategoryExample example = new TbContentCategoryExample();
        TbContentCategoryExample.Criteria criteria = example.createCriteria();
        criteria.andParentIdEqualTo(parentContentCategory.getId());
        List<TbContentCategory> tbContentCategories = tbContentCategoryMapper.selectByExample(example);
        //要删除的节点的父节点没有其他子节点
        if (tbContentCategories.size() == 1) {
            parentContentCategory.setIsParent(false);
            tbContentCategoryMapper.updateByPrimaryKey(parentContentCategory);
        }
        //删除节点
        tbContentCategoryMapper.deleteByPrimaryKey(id);
        return E3Result.ok();
    }
}
