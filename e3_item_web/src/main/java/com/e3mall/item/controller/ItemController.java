package com.e3mall.item.controller;

/**
 * @author WJX
 * @since 2018/5/3 11:51
 */

import com.e3mall.common.pojo.TbItem;
import com.e3mall.common.pojo.TbItemDesc;
import com.e3mall.item.pojo.Item;
import com.e3mall.manager.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 商品详情页Controller
 */
@Controller
public class ItemController {
    @Autowired
    private ItemService itemService;

    @RequestMapping("/item/{itemId}")
    public String showItemInfo(@PathVariable Long itemId, Model model) {
        //调用服务取商品基本信息
        TbItem tbItem = itemService.getItemById(itemId);
        Item item = new Item(tbItem);
        //取商品描述信息
        TbItemDesc itemDesc = itemService.getItemDescById(itemId);
        //把信息传递给页面
        model.addAttribute("item", item);
        model.addAttribute("itemDesc", itemDesc);
        //返回逻辑视图
        return "item";
    }
}
