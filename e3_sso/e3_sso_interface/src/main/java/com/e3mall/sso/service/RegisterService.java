package com.e3mall.sso.service;

/**
 * @author WJX
 * @since 2018/5/3 12:19
 */

import com.e3mall.common.pojo.TbUser;
import com.e3mall.common.util.E3Result;

/**
 * 注册接口
 */
public interface RegisterService {
    /**
     * 检测用户数据
     * @param param 测试数据
     * @param type 测试的类型
     * @return 封装结果
     */
    E3Result checkData(String param, int type);

    /**
     *注册
     * @param user 用户信息
     * @return 封装结果
     */
    E3Result register(TbUser user);
}
