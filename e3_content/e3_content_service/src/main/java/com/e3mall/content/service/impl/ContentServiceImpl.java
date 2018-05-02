package com.e3mall.content.service.impl;

import com.e3mall.common.jedis.JedisHelper;
import com.e3mall.common.pojo.EasyUIDataGridResult;
import com.e3mall.common.pojo.TbContent;
import com.e3mall.common.pojo.TbContentExample;
import com.e3mall.common.util.E3Result;
import com.e3mall.common.util.JsonUtils;
import com.e3mall.content.service.ContentService;
import com.e3mall.mapper.TbContentMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author WJX
 * @since 2018/5/2 17:47
 */
@Service("contentService")
public class ContentServiceImpl implements ContentService {
    @Autowired
    private TbContentMapper tbContentMapper;

    @Autowired
    private JedisHelper jedisHelper;

    @Value("${CONTENT_LIST}")
    private String CONTENT_LIST;

    @Override
    public E3Result addContent(TbContent tbContent) {
        //将内容数据插入到内容表
        tbContent.setCreated(new Date());
        tbContent.setUpdated(new Date());
        //插入到数据库
        tbContentMapper.insert(tbContent);
        //缓存同步,删除缓存中对应的数据
        jedisHelper.hdel(CONTENT_LIST, tbContent.getCategoryId().toString());
        return E3Result.ok();
    }

    @Override
    public List<TbContent> getContentListByCid(long cid) {
        //查询缓存
        List<TbContent> list = new ArrayList<>();
        try {
            //如果缓存中有直接响应结果
            String json = jedisHelper.hget(CONTENT_LIST, cid + "");
            if (StringUtils.isNoneBlank(json)) {
                list = JsonUtils.jsonToList(json, TbContent.class);
                return list;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //如果没有查询数据库
        TbContentExample example = new TbContentExample();
        TbContentExample.Criteria criteria = example.createCriteria();
        criteria.andCategoryIdEqualTo(cid);
        list = tbContentMapper.selectByExampleWithBLOBs(example);
        //把结果添加到缓存
        try {
            String json = JsonUtils.objectToJson(list);
            jedisHelper.hset(CONTENT_LIST, cid + "", json);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public EasyUIDataGridResult showContentList(Long categoryId, Integer page, Integer rows) {
        // 设置分页信息
        PageHelper.startPage(page, rows);
        // 执行查询
        TbContentExample example = new TbContentExample();
        TbContentExample.Criteria criteria = example.createCriteria();
        criteria.andCategoryIdEqualTo(categoryId);
        List<TbContent> list = tbContentMapper.selectByExample(example);
        // 创建一个返回值对象
        EasyUIDataGridResult result = new EasyUIDataGridResult();
        result.setRows(list);
        // 取分页结果
        PageInfo<TbContent> pageInfo = new PageInfo<>(list);
        // 取总记录数
        long total = pageInfo.getTotal();
        result.setTotal(total);
        return result;
    }
}
