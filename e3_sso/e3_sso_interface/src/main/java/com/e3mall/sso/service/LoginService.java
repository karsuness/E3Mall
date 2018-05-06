package com.e3mall.sso.service;

/**
 * @author WJX
 * @since 2018/5/3 12:20
 */

import com.e3mall.common.util.E3Result;

/**
 * 登录接口
 */
public interface LoginService {
    /**
     * 登录
     *
     * @param username 用户名
     * @param password 用户密码
     * @return 封装结果
     */
    E3Result userLogin(String username, String password);

    /**
     * 用户退出
     * @param token
     * @return
     */
    E3Result userLogout(String token);

}
