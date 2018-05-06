package com.e3mall.sso.service.impl;

import com.e3mall.common.jedis.JedisHelper;
import com.e3mall.common.pojo.TbUser;
import com.e3mall.common.util.E3Result;
import com.e3mall.common.util.JsonUtils;
import com.e3mall.sso.service.TokenService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author WJX
 * @since 2018/5/3 13:33
 */
@Service("tokenService")
public class TokenServiceImpl implements TokenService {
    @Autowired
    private JedisHelper jedisHelper;

    @Value("${SESSION_EXPIRE}")
    private Integer SESSION_EXPIRE;

    @Override
    public E3Result getUserByToken(String token) {
        //根据token到redis中取用户信息
        String json = jedisHelper.get("SESSION:" + token);
        //取不到用户信息，登录已经过期，返回登录过期
        if (StringUtils.isBlank(json)) {
            return E3Result.build(201, "用户登录已经过期");
        }
        //取到用户信息更新token的过期时间
        jedisHelper.expire("SESSION:" + token, SESSION_EXPIRE);
        //返回结果，E3Result其中包含TbUser对象
        TbUser user = JsonUtils.jsonToPojo(json, TbUser.class);
        return E3Result.ok(user);
    }
}
