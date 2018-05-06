package com.e3mall.sso.service.impl;

import com.e3mall.common.jedis.JedisHelper;
import com.e3mall.common.pojo.TbUser;
import com.e3mall.common.pojo.TbUserExample;
import com.e3mall.common.util.E3Result;
import com.e3mall.common.util.JsonUtils;
import com.e3mall.mapper.TbUserMapper;
import com.e3mall.sso.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.List;
import java.util.UUID;

/**
 * @author WJX
 * @since 2018/5/3 13:17
 */
@Service("loginService")
public class LoginServiceImpl implements LoginService {
    @Autowired
    private TbUserMapper userMapper;

    @Autowired
    private JedisHelper jedisHelper;

    @Value("${SESSION_EXPIRE}")
    private Integer SESSION_EXPIRE;

    @Override
    public E3Result userLogin(String username, String password) {
        // 1、判断用户和密码是否正确
        //根据用户名查询用户信息
        TbUserExample example = new TbUserExample();
        TbUserExample.Criteria criteria = example.createCriteria();
        criteria.andUsernameEqualTo(username);
        //执行查询
        List<TbUser> list = userMapper.selectByExample(example);
        if (list == null || list.size() == 0) {
            //返回登录失败
            return E3Result.build(400, "用户名或密码错误");
        }
        //取用户信息
        TbUser user = list.get(0);
        //判断密码是否正确
        if (!DigestUtils.md5DigestAsHex(password.getBytes()).equals(user.getPassword())) {
            // 2、如果不正确，返回登录失败
            return E3Result.build(400, "用户名或密码错误");
        }
        // 3、如果正确生成token。
        String token = UUID.randomUUID().toString();
        // 4、把用户信息写入redis，key：token value：用户信息
        user.setPassword(null);
        jedisHelper.set("SESSION:" + token, JsonUtils.objectToJson(user));
        // 5、设置Session的过期时间
        jedisHelper.expire("SESSION:" + token, SESSION_EXPIRE);
        // 6、把token返回
        return E3Result.ok(token);
    }

    @Override
    public E3Result userLogout(String token) {
        //根据用户token删除缓存中的数据
        jedisHelper.del("SESSION:" + token);
        return E3Result.ok();
    }

}
