package com.lmj.o2o.dao;

import com.lmj.o2o.entity.UserShopMap;

import java.util.List;
import java.util.Map;

/**
 * ClassName: UserShopDao
 * Description:
 * date: 2020/3/20 13:45
 *
 * @author MJ
 */
public interface UserShopDao {

    List<Map<String,Object>> getUserShopInfo(UserShopMap userShopMap);
}
