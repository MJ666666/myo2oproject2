package com.lmj.o2o.service;

import com.lmj.o2o.dto.WechatAuthTO;
import com.lmj.o2o.entity.LocalAuth;
import com.lmj.o2o.entity.WechatAuth;

/**
 * ClassName: WechatAuthService
 * Description:
 * date: 2020/3/22 13:38
 *
 * @author MJ
 */
public interface WechatAuthService {

    WechatAuthTO addNewWechatUser(WechatAuth wechatAuth);

    /**
     * Description: 微信账号绑定
     * @author: MJ
     * @date: 2020/3/23 13:18
     * @param:
     * @return:
     */
    WechatAuthTO bindAccount(LocalAuth localAuth);


    WechatAuthTO modifyPsw(LocalAuth localAuth);
}
