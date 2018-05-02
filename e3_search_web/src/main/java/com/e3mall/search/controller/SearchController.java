package com.e3mall.search.controller;

import com.e3mall.common.pojo.SearchResult;
import com.e3mall.search.service.SearchService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author WJX
 * @since 2018/5/2 20:48
 */
@Controller
public class SearchController {
    @Autowired
    private SearchService searchService;

    @Value("${SEARCH_RESULT_ROWS}")
    private Integer SEARCH_RESULT_ROWS;

    @ApiOperation(notes = "搜索并进入搜索页",value = "/search")
    @RequestMapping("/search")
    public String searchItemList(String keyword,@RequestParam(defaultValue="1") Integer page, Model model) throws Exception {
        keyword = new String(keyword.getBytes("iso-8859-1"), "utf-8");
        //查询商品列表
        SearchResult searchResult = searchService.search(keyword, page, SEARCH_RESULT_ROWS);
        //把结果传递给页面
        model.addAttribute("query", keyword);
        model.addAttribute("totalPages", searchResult.getTotalPages());
        model.addAttribute("page", page);
        model.addAttribute("recordCount", searchResult.getRecordCount());
        model.addAttribute("itemList", searchResult.getItemList());
        //返回逻辑视图
        return "search";
    }
}
