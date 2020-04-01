package com.lmj.o2o.service;

import com.lmj.o2o.dto.UserShopTO;
import com.lmj.o2o.entity.UserShopMap;

/**
 * ClassName: PointService
 * Description:
 * date: 2020/3/20 11:20
 *
 * @author MJ
 */
public interface UserShopService {


    UserShopTO getUserShopInfo(UserShopMap userShopMap);
}
