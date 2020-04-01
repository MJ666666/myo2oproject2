package com.lmj.o2o.service;

import com.lmj.o2o.dto.LocalAuthTO;
import com.lmj.o2o.entity.LocalAuth;
import com.lmj.o2o.entity.PersonInfo;
import com.lmj.o2o.entity.WechatAuth;
import com.lmj.o2o.vo.RegisterEntity;

/**
 * ClassName: LocalAuthService
 * Description:
 * date: 2020/3/22 9:50
 *
 * @author MJ
 */
public interface LocalAuthService {


    LocalAuthTO registerOwnerAccount(RegisterEntity registerEntity);


    LocalAuthTO login(LocalAuth localAuth);


    LocalAuthTO verifyAccount(LocalAuth localAuth);

    LocalAuthTO updateAccount(LocalAuth localAuth);

    LocalAuthTO registerAccount(LocalAuth localAuth);


    LocalAuthTO adminLogin(LocalAuth localAuth);



}
