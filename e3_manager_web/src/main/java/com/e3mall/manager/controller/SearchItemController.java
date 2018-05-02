package com.e3mall.manager.controller;

import com.e3mall.common.util.E3Result;
import com.e3mall.search.service.SearchItemService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author WJX
 * @since 2018/5/2 21:30
 */
@Controller
public class SearchItemController {
    @Autowired
    private SearchItemService searchItemService;

    @ApiOperation(notes = "导入索引库",value = "/index/item/import")
    @RequestMapping("/index/item/import")
    @ResponseBody
    public E3Result importItemList() {
        E3Result e3Result = searchItemService.importAllItems();
        return e3Result;
    }
}
