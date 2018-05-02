package com.e3mall.manager.controller;

import com.e3mall.common.pojo.EasyUIDataGridResult;
import com.e3mall.common.pojo.TbContent;
import com.e3mall.common.util.E3Result;
import com.e3mall.content.service.ContentService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author WJX
 * @since 2018/5/2 17:59
 */
@Controller
@RequestMapping("/content")
public class ContentController {
    @Autowired
    private ContentService contentService;

    @ApiOperation(notes = "保存内容",value = "/save")
    @RequestMapping(value="/save",method=RequestMethod.POST)
    @ResponseBody
    public E3Result addContent(TbContent tbContent){
        E3Result result = contentService.addContent(tbContent);
        return result;
    }

    @ApiOperation(notes = "查询内容列表",value = "/query/list")
    @RequestMapping(value="/query/list",method=RequestMethod.GET)
    @ResponseBody
    public EasyUIDataGridResult showContentList(Long categoryId, Integer page, Integer rows){
        EasyUIDataGridResult result = contentService.showContentList(categoryId,page,rows);
        return result;
    }
}
