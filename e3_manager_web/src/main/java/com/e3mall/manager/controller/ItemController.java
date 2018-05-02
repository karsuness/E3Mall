package com.e3mall.manager.controller;

import com.e3mall.common.pojo.EasyUIDataGridResult;
import com.e3mall.common.pojo.TbItem;
import com.e3mall.common.util.E3Result;
import com.e3mall.manager.service.ItemService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * @author WJX
 * @since 2018/4/30 23:09
 */
@Controller
@RequestMapping("/item")
public class ItemController {
    @Autowired
    private ItemService itemService;

    @ApiOperation(notes = "根据id查询item信息", value = "/{itemId}")
    @RequestMapping(value = "/{itemId}", method = RequestMethod.GET)
    @ResponseBody
    public TbItem getItemById(@PathVariable Long itemId) {
        TbItem tbItem = itemService.getItemById(itemId);
        return tbItem;
    }

    @ApiOperation(notes = "分页查询商品信息", value = "/list")
    @RequestMapping("/list")
    @ResponseBody
    public EasyUIDataGridResult getItemList(Integer page, Integer rows) {
        //调用服务查询商品列表
        EasyUIDataGridResult result = itemService.getItemList(page, rows);
        return result;
    }

    @ApiOperation(notes = "后台商品添加", value = "/save")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public E3Result addItem(TbItem item, String desc) {
        E3Result result = itemService.addItem(item, desc);
        return result;
    }

    @ApiOperation(notes = "更新商品信息", value = "/update")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public E3Result updateItem(TbItem item, String desc) {
        E3Result result = itemService.updateItem(item, desc);
        return result;
    }

    @ApiOperation(notes = "删除商品",value="/delete")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public E3Result deleteItem(String ids) {
        String[] strings = ids.split(",");
        if (strings == null || strings.length == 0) {
            return E3Result.ok();
        }
        List<Long> idList = new ArrayList<>();
        for (String id : strings) {
            idList.add(Long.valueOf(id));
        }
        E3Result result = itemService.deleteItems(idList);
        return result;
    }

    @ApiOperation(notes = "商品上架",value="/instock")
    @RequestMapping(value = "/instock", method = RequestMethod.POST)
    @ResponseBody
    public E3Result itemInstock(String ids) {
        String[] strings = ids.split(",");
        if (strings == null || strings.length == 0) {
            return E3Result.ok();
        }
        List<Long> idList = new ArrayList<>();
        for (String id : strings) {
            idList.add(Long.valueOf(id));
        }
        E3Result result = itemService.itemInstock(idList);
        return result;
    }

    @ApiOperation(notes = "商品下架",value = "/reshelf")
    @RequestMapping(value = "/reshelf", method = RequestMethod.POST)
    @ResponseBody
    public E3Result itemReshelf(String ids) {
        String[] strings = ids.split(",");
        if (strings == null || strings.length == 0) {
            return E3Result.ok();
        }
        List<Long> idList = new ArrayList<>();
        for (String id : strings) {
            idList.add(Long.valueOf(id));
        }
        E3Result result = itemService.itemReshelf(idList);
        return result;
    }
}
