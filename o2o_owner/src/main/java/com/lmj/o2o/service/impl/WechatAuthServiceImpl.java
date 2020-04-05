package com.lmj.o2o.service.impl;

import com.lmj.o2o.consts.Consts;
import com.lmj.o2o.dao.LocalAuthDao;
import com.lmj.o2o.dao.PersonInfoDao;
import com.lmj.o2o.dao.WechatAuthDao;
import com.lmj.o2o.dto.WechatAuthTO;
import com.lmj.o2o.entity.LocalAuth;
import com.lmj.o2o.entity.WechatAuth;
import com.lmj.o2o.enums.OperationEnum;
import com.lmj.o2o.exception.AuthExecuteException;
import com.lmj.o2o.service.RedisService;
import com.lmj.o2o.service.WechatAuthService;
import com.lmj.o2o.utils.GsonUtils;
import com.lmj.o2o.utils.MD5Utils;
import com.lmj.o2o.utils.UuidUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * ClassName: WechatAuthServiceImpl
 * Description:
 * date: 2020/3/22 13:40
 *
 * @author MJ
 */

@Service
public class WechatAuthServiceImpl implements WechatAuthService {

    @Autowired
    private WechatAuthDao wechatAuthDao;

    @Autowired
    private PersonInfoDao personInfoDao;

    @Autowired
    private LocalAuthDao localAuthDao;

    @Reference(version = "${demo.service.version}")
    private RedisService redisService;

    /**
     * Description: 用uuid标识用户，为localAuth，wechatAuth，personInfo三表的用户标识
     * @author: MJ
     * @date: 2020/3/23 13:05
     * @param:
     * @return:
     */
    @Transactional
    @Override
    public WechatAuthTO addNewWechatUser(WechatAuth wechatAuth) {
        WechatAuth responseAuth;
        String storeKey = Consts.WECHAT_USER + wechatAuth.getOpenId();
        if (!redisService.existKey(storeKey)) {
            //判断是否存在该用户
            WechatAuth existUser = wechatAuthDao.queryUserByOpenid(wechatAuth);

            if (existUser != null && existUser.getUserId() != null) {
                redisService.storeValue(storeKey, GsonUtils.toGsonString(existUser).replaceAll("/","aaaa"));
                redisService.expireKey(storeKey, Consts.EXPIRE_TIME);
                return new WechatAuthTO(existUser, OperationEnum.USER_EXISTS);
            }
            wechatAuth.setUuid(UuidUtils.getUUID());
            wechatAuth.setCreateTime(new Date());
            int i = personInfoDao.addUserViaWechat(wechatAuth);
            if (wechatAuth.getUserId() == null) {
                throw new AuthExecuteException("插入uuid失败");
            }

            int addAccountResult = wechatAuthDao.addNewWechatUser(wechatAuth);
            if (i == 1 && addAccountResult == 1) {
                 responseAuth = new WechatAuth();
                responseAuth.setUserId(wechatAuth.getUserId());
                redisService.storeValue(storeKey, GsonUtils.toGsonString(wechatAuth.getUserId()).replaceAll("/","aaaa"));
                redisService.expireKey(storeKey, Consts.EXPIRE_TIME);
                return new WechatAuthTO(responseAuth, OperationEnum.SUCCESS);
            } else {
                throw new AuthExecuteException("注册微信新用户失败");
            }
        }else {
           String storeUserAuth =redisService.get(storeKey).replaceAll("aaaa","/");
            responseAuth = GsonUtils.GsonToBean(storeUserAuth, WechatAuth.class);
            return new WechatAuthTO(responseAuth, OperationEnum.SUCCESS);
        }

    }

    @Override
    public WechatAuthTO bindAccount(LocalAuth localAuth) {
        WechatAuthTO wechatAuthTO;
        int existRow = localAuthDao.queryExistBindAccount(localAuth);
        if (existRow==1) {
            wechatAuthTO=new WechatAuthTO(OperationEnum.ACCOUNT_ALREADY_BIND);
            return wechatAuthTO;
        }
        localAuth.setCreateTime(new Date());
        localAuth.setPassword(MD5Utils.md5(localAuth.getPassword()));
        int bindRow = localAuthDao.addNewAccount(localAuth);
        if (bindRow==1) {
            wechatAuthTO = new WechatAuthTO(OperationEnum.SUCCESS);
            return wechatAuthTO;
        }
        wechatAuthTO = new WechatAuthTO(OperationEnum.INNER_ERROR);
        return wechatAuthTO;
    }

    @Override
    public WechatAuthTO modifyPsw(LocalAuth localAuth) {
        WechatAuthTO wechatAuthTO;
        localAuth.setLastEditTime(new Date());
        localAuth.setOldPassword(MD5Utils.md5(localAuth.getOldPassword()));
        localAuth.setPassword(MD5Utils.md5(localAuth.getPassword()));
        int effectRow = localAuthDao.updateAccount(localAuth);
        if (effectRow==1) {
            wechatAuthTO = new WechatAuthTO(OperationEnum.SUCCESS);
            return wechatAuthTO;
        }
        wechatAuthTO = new WechatAuthTO(OperationEnum.NULL_RESULT);
        return wechatAuthTO;
    }

}
