package com.e3mall.sso.service;

/**
 * @author WJX
 * @since 2018/5/3 12:20
 */

import com.e3mall.common.util.E3Result;

/**
 * 创建token
 */
public interface TokenService {
    /**
     * 通过用户token获取用户信息
     * @param token token
     * @return 封装结果
     */
    E3Result getUserByToken(String token);
}
