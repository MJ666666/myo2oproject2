package com.lmj.o2o.dao;

import com.lmj.o2o.entity.Shop;
import com.lmj.o2o.entity.ShopAuthMap;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * ClassName: ShopAuthManageDao
 * Description:
 * date: 2020/3/26 12:57
 *
 * @author MJ
 */
@Repository
public interface ShopAuthManageDao {


    List<ShopAuthMap> queryAllShopAuth(Shop shop);

    int deleteShopAuth(ShopAuthMap shopAuthMap);

    int addShopAuth(ShopAuthMap shopAuthMap);

    int updateShopAuth(ShopAuthMap shopAuthMap);

    ShopAuthMap getShopAuth(ShopAuthMap shopAuthMap);

    //小程序端是否有权限扫码
    ShopAuthMap queryExistAuth(ShopAuthMap shopAuthMap);
}
