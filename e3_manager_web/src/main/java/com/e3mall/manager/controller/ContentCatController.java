package com.e3mall.manager.controller;

import com.e3mall.common.pojo.EasyUITreeNode;
import com.e3mall.common.util.E3Result;
import com.e3mall.content.service.ContentCategoryService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author WJX
 * @since 2018/5/2 17:34
 */
@Controller
@RequestMapping("/content/category")
public class ContentCatController {
    @Autowired
    private ContentCategoryService contentCategoryService;

    @ApiOperation(notes = "展示列表",value = "/list")
    @RequestMapping("/list")
    @ResponseBody
    public List<EasyUITreeNode> getContentCatList(@RequestParam(name = "id", defaultValue = "0") Long parentId) {
        List<EasyUITreeNode> list = contentCategoryService.getContentCatList(parentId);
        return list;
    }

    @ApiOperation(notes = "添加分类节点",value = "/list")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public E3Result createContentCategory(Long parentId, String name) {
        //调用服务添加节点
        E3Result e3Result = contentCategoryService.addContentCategory(parentId, name);
        return e3Result;
    }

    @ApiOperation(notes = "对分类节点进行重命名",value = "/update")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public E3Result updateContentCategory(Long id, String name) {
        //调用服务添加节点
        E3Result e3Result = contentCategoryService.updateContentCategory(id, name);
        return e3Result;
    }

    @ApiOperation(notes = "删除分类节点",value = "/delete")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public E3Result deleteContentCategory(Long id) {
        //调用服务添加节点
        E3Result e3Result = contentCategoryService.deleteContentCategory(id);
        return e3Result;
    }
}
