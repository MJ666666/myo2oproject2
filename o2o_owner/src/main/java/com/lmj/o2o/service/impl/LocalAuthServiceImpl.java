package com.lmj.o2o.service.impl;

import com.lmj.o2o.consts.Consts;
import com.lmj.o2o.dao.LocalAuthDao;
import com.lmj.o2o.dao.PersonInfoDao;
import com.lmj.o2o.dto.LocalAuthTO;
import com.lmj.o2o.entity.LocalAuth;
import com.lmj.o2o.entity.PersonInfo;
import com.lmj.o2o.entity.WechatAuth;
import com.lmj.o2o.enums.OperationEnum;
import com.lmj.o2o.exception.AuthExecuteException;
import com.lmj.o2o.service.LocalAuthService;
import com.lmj.o2o.service.RedisService;
import com.lmj.o2o.utils.GsonUtils;
import com.lmj.o2o.utils.MD5Utils;
import com.lmj.o2o.utils.UuidUtils;
import com.lmj.o2o.vo.RegisterEntity;
import com.sun.xml.internal.bind.v2.runtime.reflect.opt.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.UUID;

/**
 * ClassName: LocalAuthServiceImpl
 * Description:
 * date: 2020/3/22 9:54
 *
 * @author MJ
 */
@Service
public class LocalAuthServiceImpl implements LocalAuthService {


    @Autowired
    private LocalAuthDao localAuthDao;

    @Autowired
    private PersonInfoDao personInfoDao;


    @Autowired
    private RedisService redisService;


    @Transactional
    @Override
    public LocalAuthTO registerOwnerAccount(RegisterEntity registerEntity) {
        LocalAuthTO localAuthTO;
        LocalAuth localAuth = new LocalAuth();
        localAuth.setUserName(registerEntity.getUserName());
        int existRow = localAuthDao.queryExistAccount(localAuth);
        if (existRow==1) {
            return new LocalAuthTO(OperationEnum.USER_EXISTS);
        }

        PersonInfo personInfo = new PersonInfo();
        personInfo.setPhone(registerEntity.getPhone());
        personInfo.setEmail(registerEntity.getEmail());
        personInfo.setName(registerEntity.getNickName());
        personInfo.setProfileImg(registerEntity.getProfileImg());
        personInfo.setCreateTime(new Date());
        personInfo.setEnableStatus(1);
        personInfo.setShopOwnerFlag(1);
        personInfo.setCustomerFlag(1);
        personInfo.setAdminFlag(0);
        personInfo.setLastEditTime(new Date());
        personInfo.setUserUuid(UuidUtils.getUUID());
        int i = personInfoDao.addPersonInfo(personInfo);
        if (i!=1 || personInfo.getUserId()==null) {
            throw new AuthExecuteException("插入用户信息失败");
        }
        localAuth.setUserId(personInfo.getUserId());
        localAuth.setPassword(MD5Utils.md5(registerEntity.getPassword()));
        localAuth.setUuid(personInfo.getUserUuid());
        localAuth.setCreateTime(new Date());
        localAuth.setLastEditTime(new Date());
        int addRow = localAuthDao.addNewAccount(localAuth);
        if (addRow!=1) {
            throw new AuthExecuteException("创建新用户失败");
        }
        localAuthTO = new LocalAuthTO(OperationEnum.SUCCESS);
        localAuthTO.setLocalAuth(localAuth);
        String token = Consts.REDIS_SSO_KEY + UuidUtils.getUUID();
        redisService.storeValue(token, GsonUtils.toGsonString(localAuth).replaceAll("/","aaaa"));
        redisService.expireKey(token, Consts.EXPIRE_TIME);
        localAuthTO.setToken(token);
        return localAuthTO;
    }

    @Override
    public LocalAuthTO login(LocalAuth localAuth) {
        LocalAuthTO localAuthTO;
        localAuth.setPassword(MD5Utils.md5(localAuth.getPassword()));
        LocalAuth  existUser = localAuthDao.verifyAccount(localAuth);
        if (null==existUser) {
            LocalAuth userNameCheck = new LocalAuth();
            userNameCheck.setUserName(localAuth.getUserName());
            LocalAuth checkRow = localAuthDao.verifyAccount(userNameCheck);
            if (null==checkRow) {
                localAuthTO = new LocalAuthTO(OperationEnum.USER_NOT_EXISTS);
                return localAuthTO;
            }else {
                localAuthTO = new LocalAuthTO(OperationEnum.WRONG_PASSWORD);
                return localAuthTO;
            }
        }
        String token= Consts.REDIS_SSO_KEY+UuidUtils.getUUID();
        redisService.storeValue(token, GsonUtils.toGsonString(existUser).replaceAll("/","aaaa"));
        redisService.expireKey(token, Consts.EXPIRE_TIME);
        localAuthTO=new LocalAuthTO(OperationEnum.SUCCESS);
        localAuthTO.setToken(token);
        localAuthTO.setLocalAuth(existUser);
        return localAuthTO;
    }

    @Override
    public LocalAuthTO verifyAccount(LocalAuth localAuth) {
        int existRow = localAuthDao.queryExistAccount(localAuth);
        if (existRow==0) {
            return new LocalAuthTO(OperationEnum.USER_NOT_EXISTS);
        }
        localAuth.setPassword(MD5Utils.md5(localAuth.getPassword()));
        LocalAuth currentAccount = localAuthDao.verifyAccount(localAuth);
        if (currentAccount!=null && currentAccount.getUserId()!=null) {
            return new LocalAuthTO(currentAccount,OperationEnum.SUCCESS);
        }
        return new LocalAuthTO(OperationEnum.INNER_ERROR);
    }

    @Override
    public LocalAuthTO updateAccount(LocalAuth localAuth) {
        localAuth.setLastEditTime(new Date());
        int i = localAuthDao.updateAccount(localAuth);
        if (i==1) {
            return new LocalAuthTO(OperationEnum.SUCCESS);
        }
        return new LocalAuthTO(OperationEnum.INNER_ERROR);
    }


    /**
     * Description: 小程序端登录
     * @author: MJ
     * @date: 2020/3/25 15:28
     * @param:
     * @return:
     */
    @Transactional
    @Override
    public LocalAuthTO registerAccount(LocalAuth localAuth) {
        int existRow = localAuthDao.queryExistAccount(localAuth);
        if (existRow==1) {
            return new LocalAuthTO(OperationEnum.USER_EXISTS);
        }
        LocalAuthTO localAuthTO;
        localAuth.setPassword(MD5Utils.md5(localAuth.getPassword()));
        localAuth.setUuid(UuidUtils.getUUID());
        localAuth.setCreateTime(new Date());
        localAuth.setLastEditTime(new Date());
        int insertUidResult = personInfoDao.addUserViaWeb(localAuth);
        if (localAuth.getUserId()==null) {
            throw new AuthExecuteException("插入uuid失败");
        }
        int i = localAuthDao.addNewAccount(localAuth);
        if (insertUidResult==1 && i==1) {
        localAuthTO=new LocalAuthTO(OperationEnum.SUCCESS);
            LocalAuth newLocalAuth = new LocalAuth();
            newLocalAuth.setUserId(localAuth.getUserId());
            localAuthTO.setLocalAuth(newLocalAuth);
            return localAuthTO;
        }else {
            throw new AuthExecuteException("注册新用户失败");

        }
    }

    @Override
    public LocalAuthTO adminLogin(LocalAuth localAuth) {
        LocalAuthTO localAuthTO;
        localAuth.setPassword(MD5Utils.md5(localAuth.getPassword()));
        LocalAuth  existUser = localAuthDao.adminLogin(localAuth);
        if (null==existUser) {
            localAuthTO = new LocalAuthTO(OperationEnum.INNER_ERROR);
            return localAuthTO;
        }

        String token= Consts.REDIS_SSO_KEY+UuidUtils.getUUID();
        redisService.storeValue(token, GsonUtils.toGsonString(existUser).replaceAll("/","aaaa"));
        redisService.expireKey(token, Consts.EXPIRE_TIME);
        localAuthTO=new LocalAuthTO(OperationEnum.SUCCESS);
        localAuthTO.setToken(token);
        localAuthTO.setLocalAuth(existUser);
        return localAuthTO;
    }


}
