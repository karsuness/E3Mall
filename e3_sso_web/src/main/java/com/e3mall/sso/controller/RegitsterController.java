package com.e3mall.sso.controller;

import com.e3mall.common.pojo.TbUser;
import com.e3mall.common.util.E3Result;
import com.e3mall.sso.service.RegisterService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author WJX
 * @since 2018/5/3 12:52
 */
@Controller
public class RegitsterController {
    @Autowired
    private RegisterService registerService;

    @ApiOperation(notes = "跳转到注册页",value = "/page/register")
    @RequestMapping("/page/register")
    public String showRegister() {
        return "register";
    }

    @ApiOperation(notes = "检查用户是否注册",value = "/user/check/{param}/{type}")
    @RequestMapping("/user/check/{param}/{type}")
    @ResponseBody
    public E3Result checkData(@PathVariable String param, @PathVariable Integer type) {
        E3Result e3Result = registerService.checkData(param, type);
        return e3Result;
    }

    @ApiOperation(notes = "注册功能",value = "/user/register")
    @RequestMapping(value="/user/register", method=RequestMethod.POST)
    @ResponseBody
    public E3Result register(TbUser user) {
        E3Result e3Result = registerService.register(user);
        return e3Result;
    }
}
