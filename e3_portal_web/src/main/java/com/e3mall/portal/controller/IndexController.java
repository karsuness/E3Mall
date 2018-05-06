package com.e3mall.portal.controller;

import com.e3mall.common.pojo.TbContent;
import com.e3mall.content.service.ContentService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author WJX
 * @since 2018/5/2 15:30
 */
@Controller
public class IndexController {
    @Value("${CONTENT_LUNBO_ID}")
    private Long CONTENT_LUNBO_ID;

    @Autowired
    private ContentService contentService;

    @ApiOperation(notes = "显示商城首页",value = "/index")
    @RequestMapping("/index")
    public String showIndex(Model model){
        List<TbContent> adList = contentService.getContentListByCid(CONTENT_LUNBO_ID);
        model.addAttribute("adList", adList);
        return "index";
    }
}
