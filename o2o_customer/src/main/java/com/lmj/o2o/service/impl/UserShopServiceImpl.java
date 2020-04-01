package com.lmj.o2o.service.impl;

import com.lmj.o2o.dao.UserShopDao;
import com.lmj.o2o.dto.UserShopTO;
import com.lmj.o2o.entity.UserShopMap;
import com.lmj.o2o.enums.OperationEnum;
import com.lmj.o2o.service.UserShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * ClassName: UserShopServiceImpl
 * Description:
 * date: 2020/3/20 11:23
 *
 * @author MJ
 */
@Service
public class UserShopServiceImpl implements UserShopService {

    @Autowired
    private UserShopDao userShopDao;

/**
 * Description: 查询用户在特定商店的信息
 * @author: MJ
 * @date: 2020/3/20 13:56
 * @param:
 * @return:
 */
    @Override
    public UserShopTO getUserShopInfo(UserShopMap userShopMap) {
        UserShopTO userShopTO;
        List<Map<String, Object>> userShopInfo = userShopDao.getUserShopInfo(userShopMap);
        if (userShopInfo.size()!=0) {
            userShopTO=new UserShopTO(OperationEnum.SUCCESS);
            userShopTO.setDataList(userShopInfo);
            return userShopTO;
        }
        userShopTO=new UserShopTO(OperationEnum.NULL_RESULT);
        return userShopTO;
    }
}
