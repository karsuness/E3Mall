package com.e3mall.portal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author WJX
 * @since 2018/5/2 15:30
 */
@Controller
public class IndexController {
    @RequestMapping("/index")
    public String showIndex(){//Model model){
//        List<TbContent> ad1List = contentService.getContentListByCid(CONTENT_LUNBO_ID);
//        model.addAttribute("ad1List", ad1List);
        return "index";
    }
}
