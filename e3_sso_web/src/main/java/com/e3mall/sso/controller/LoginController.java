package com.e3mall.sso.controller;

import com.e3mall.common.util.CookieUtils;
import com.e3mall.common.util.E3Result;
import com.e3mall.sso.service.LoginService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author WJX
 * @since 2018/5/3 12:35
 */
@Controller
public class LoginController {
    @Autowired
    private LoginService loginService;

    /**
     * 登录成功返回码
     */
    private final int LOGIN_SUCCESS = 200;

    @Value("${TOKEN_KEY}")
    private String TOKEN_KEY;

    @ApiOperation(notes = "显示登录页面", value = "/page/login")
    @RequestMapping("/page/login")
    public String showLogin(String redirect, Model model) {
        model.addAttribute("redirect", redirect);
        return "login";
    }

    @ApiOperation(notes = "登录", value = "/user/login")
    @RequestMapping(value = "/user/login", method = RequestMethod.POST)
    @ResponseBody
    public E3Result login(String username, String password, HttpServletRequest request, HttpServletResponse response) {
        E3Result e3Result = loginService.userLogin(username, password);
        //判断是否登录成功
        if (e3Result.getStatus() == LOGIN_SUCCESS) {
            String token = e3Result.getData().toString();
            //如果登录成功需要把token写入cookie
            CookieUtils.setCookie(request, response, TOKEN_KEY, token);
        }
        //返回结果
        return e3Result;
    }

    @ApiOperation(notes = "退出", value = "/user/logout")
    @RequestMapping(value = "/user/logout", method = RequestMethod.GET)
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        //获取cookie
        String token = CookieUtils.getCookieValue(request, TOKEN_KEY);
        //删除缓存
        loginService.userLogout(token);
        //删除cookie
        CookieUtils.deleteCookie(request, response,TOKEN_KEY);
        return "login";
    }
}
