package com.e3mall.manager.service.impl;

import com.e3mall.common.enums.ItemStatusEnum;
import com.e3mall.common.jedis.JedisHelper;
import com.e3mall.common.pojo.*;
import com.e3mall.common.util.E3Result;
import com.e3mall.common.util.IDUtils;
import com.e3mall.common.util.JsonUtils;
import com.e3mall.mapper.TbItemDescMapper;
import com.e3mall.mapper.TbItemMapper;
import com.e3mall.manager.service.ItemService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

/**
 * @author WJX
 * @since 2018/5/1 13:39
 */
@Service("itemService")
public class ItemServiceImpl implements ItemService {
    @Autowired
    private TbItemMapper tbItemMapper;

    @Autowired
    private TbItemDescMapper tbItemDescMapper;

    @Autowired
    private JedisHelper jedisHelper;

    /**
     * item在redis中前缀
     */
    @Value("${REDIS_ITEM_PRE}")
    private String REDIS_ITEM_PRE;
    /**
     * redis过期时间
     */
    @Value("${ITEM_CACHE_EXPIRE}")
    private Integer ITEM_CACHE_EXPIRE;

    @Override
    public TbItem getItemById(long itemId) {
        // 查询缓存
        try {
            String json = jedisHelper.get(REDIS_ITEM_PRE + ":" + itemId + ":BASE");
            if (StringUtils.isNotBlank(json)) {
                TbItem tbItem = JsonUtils.jsonToPojo(json, TbItem.class);
                return tbItem;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 缓存中没有，查询数据库
        TbItemExample example = new TbItemExample();
        TbItemExample.Criteria criteria = example.createCriteria();
        // 设置查询条件
        criteria.andIdEqualTo(itemId);
        // 执行查询
        List<TbItem> list = tbItemMapper.selectByExample(example);
        if (list != null && list.size() > 0) {
            // 把结果添加到缓存
            try {
                jedisHelper.set(REDIS_ITEM_PRE + ":" + itemId + ":BASE", JsonUtils.objectToJson(list.get(0)));
                // 设置过期时间
                jedisHelper.expire(REDIS_ITEM_PRE + ":" + itemId + ":BASE", ITEM_CACHE_EXPIRE);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return list.get(0);
        }
        return null;
    }

    @Override
    public EasyUIDataGridResult getItemList(int page, int rows) {
        //设置分页信息
        PageHelper.startPage(page, rows);
        //执行查询
        TbItemExample example = new TbItemExample();
        List<TbItem> list = tbItemMapper.selectByExample(example);
        //创建一个返回值对象
        EasyUIDataGridResult result = new EasyUIDataGridResult();
        result.setRows(list);
        //取分页结果
        PageInfo<TbItem> pageInfo = new PageInfo<>(list);
        //取总记录数
        long total = pageInfo.getTotal();
        result.setTotal(total);
        return result;
    }

    @Override
    public E3Result addItem(TbItem item, String desc) {
        //生成商品id
        long itemId = IDUtils.genItemId();
        //补全item的属性
        item.setId(itemId);
        //1-正常，2-下架，3-删除
        item.setStatus(ItemStatusEnum.ITEM_ON_SALE.getStatus());
        item.setCreated(new Date());
        item.setUpdated(new Date());
        //向商品表插入数据
        tbItemMapper.insert(item);
        //创建一个商品描述表对应的pojo对象。
        TbItemDesc itemDesc = new TbItemDesc();
        //补全属性
        itemDesc.setItemId(itemId);
        itemDesc.setItemDesc(desc);
        itemDesc.setCreated(new Date());
        itemDesc.setUpdated(new Date());
        //向商品描述表插入数据
        tbItemDescMapper.insert(itemDesc);
        //返回成功
        return E3Result.ok();
    }

    @Override
    public E3Result updateItem(TbItem item, String desc) {
        return null;
    }

    @Override
    public E3Result deleteItems(List<Long> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return E3Result.ok();
        }
        for (Long id : ids) {
            tbItemMapper.deleteByPrimaryKey(id);
            tbItemDescMapper.deleteByPrimaryKey(id);
        }
        return E3Result.ok();
    }

    @Override
    public E3Result itemInstock(List<Long> ids) {
        for (Long id : ids) {
            TbItem tbItem = tbItemMapper.selectByPrimaryKey(id);
            tbItem.setStatus(ItemStatusEnum.ITEM_UNDER_SALE.getStatus());
            tbItemMapper.updateByPrimaryKey(tbItem);
        }
        return E3Result.ok();
    }

    @Override
    public E3Result itemReshelf(List<Long> ids) {
        for (Long id : ids) {
            TbItem tbItem = tbItemMapper.selectByPrimaryKey(id);
            tbItem.setStatus(ItemStatusEnum.ITEM_ON_SALE.getStatus());
            tbItemMapper.updateByPrimaryKey(tbItem);
        }
        return E3Result.ok();
    }

    @Override
    public TbItemDesc getItemDescById(long itemId) {
        // 查询缓存
        try {
            String json = jedisHelper.get(REDIS_ITEM_PRE + ":" + itemId + ":DESC");
            if (StringUtils.isNotBlank(json)) {
                TbItemDesc tbItemDesc = JsonUtils.jsonToPojo(json, TbItemDesc.class);
                return tbItemDesc;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        TbItemDesc itemDesc = tbItemDescMapper.selectByPrimaryKey(itemId);
        // 把结果添加到缓存
        try {
            jedisHelper.set(REDIS_ITEM_PRE + ":" + itemId + ":DESC", JsonUtils.objectToJson(itemDesc));
            // 设置过期时间
            jedisHelper.expire(REDIS_ITEM_PRE + ":" + itemId + ":DESC", ITEM_CACHE_EXPIRE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return itemDesc;
    }
}
