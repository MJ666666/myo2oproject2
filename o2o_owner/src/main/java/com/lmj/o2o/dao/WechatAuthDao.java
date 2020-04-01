package com.lmj.o2o.dao;

import com.lmj.o2o.entity.WechatAuth;
import org.springframework.stereotype.Repository;

/**
 * ClassName: WechatAuthDao
 * Description:
 * date: 2020/3/22 13:27
 *
 * @author MJ
 */
@Repository
public interface WechatAuthDao {


    int addNewWechatUser(WechatAuth wechatAuth);

    WechatAuth queryUserByOpenid(WechatAuth wechatAuth);

}
