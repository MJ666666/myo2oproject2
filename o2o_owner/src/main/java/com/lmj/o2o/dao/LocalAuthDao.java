package com.lmj.o2o.dao;

import com.lmj.o2o.entity.LocalAuth;
import com.lmj.o2o.entity.WechatAuth;
import org.springframework.stereotype.Repository;

/**
 * ClassName: LocalAuthDao
 * Description:
 * date: 2020/3/22 9:22
 *
 * @author MJ
 */
@Repository
public interface LocalAuthDao {

    //web端sso,登录验证
    LocalAuth verifyAccount(LocalAuth localAuth);

    int addNewAccount(LocalAuth localAuth);

    int updateAccount(LocalAuth localAuth);

    //管理员登录
    LocalAuth adminLogin(LocalAuth localAuth);



    //微信小程序端绑定用户
    int queryExistAccount(LocalAuth localAuth);

    int queryExistBindAccount(LocalAuth localAuth);


}
