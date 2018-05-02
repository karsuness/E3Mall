package com.e3mall.manager.service.impl;

import com.e3mall.manager.service.ItemCatService;
import com.e3mall.mapper.TbItemCatMapper;
import com.e3mall.common.pojo.EasyUITreeNode;
import com.e3mall.common.pojo.TbItemCat;
import com.e3mall.common.pojo.TbItemCatExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author WJX
 * @since 2018/5/1 15:52
 */
@Service("itemCatService")
public class ItemCatServiceImpl implements ItemCatService {
    @Autowired
    private TbItemCatMapper tbItemCatMapper;

    @Override
    public List<EasyUITreeNode> getItemCatList(Long parentId) {
        //根据parentId查询子节点列表
        TbItemCatExample example = new TbItemCatExample();
        TbItemCatExample.Criteria criteria = example.createCriteria();
        criteria.andParentIdEqualTo(parentId);
        //查询数据
        List<TbItemCat> tbItemCats = tbItemCatMapper.selectByExample(example);
        //创建返回结果的list
        List<EasyUITreeNode> easyUITreeNodes = new ArrayList<>();
        if (CollectionUtils.isEmpty(tbItemCats)) {
            return easyUITreeNodes;
        }
        //把TbItemCat列表转换成EasyUITreeNode列表
        for (TbItemCat tbItemCat : tbItemCats) {
            EasyUITreeNode node = new EasyUITreeNode();
            //设置属性
            node.setId(tbItemCat.getId());
            node.setText(tbItemCat.getName());
            node.setState(tbItemCat.getIsParent() ? "closed" : "open");
            //添加到结果列表
            easyUITreeNodes.add(node);
        }
        return easyUITreeNodes;
    }
}
