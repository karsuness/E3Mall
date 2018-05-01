package com.e3mall.controller;

import com.e3mall.pojo.EasyUITreeNode;
import com.e3mall.service.ItemCatService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author WJX
 * @since 2018/5/1 16:23
 */
@Controller
@RequestMapping("/item/cat")
public class ItemCatController {
    @Autowired
    private ItemCatService itemCatService;

    @ApiOperation(notes = "通过父节点获取子节点列表", value = "/list")
    @RequestMapping("/list")
    @ResponseBody
    public List<EasyUITreeNode> getItemCatList(@RequestParam(value="id", defaultValue="0")Long parentId ){
        return itemCatService.getItemCatList(parentId);
    }
}
