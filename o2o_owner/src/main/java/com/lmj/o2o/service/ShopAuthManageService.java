package com.lmj.o2o.service;

import com.lmj.o2o.dto.ShopAuthManageTO;
import com.lmj.o2o.entity.Shop;
import com.lmj.o2o.entity.ShopAuthMap;

/**
 * ClassName: ShopAuthManageService
 * Description:
 * date: 2020/3/26 13:12
 *
 * @author MJ
 */
public interface ShopAuthManageService {

    ShopAuthManageTO getShopAuthListByShopId(Shop shop);

    ShopAuthManageTO deleteShopAuth(ShopAuthMap shopAuthMap);

    ShopAuthManageTO addShopAuth(ShopAuthMap shopAuthMap);

    ShopAuthManageTO updateShopAuth(ShopAuthMap shopAuthMap);

    ShopAuthManageTO queryShopAuth(ShopAuthMap shopAuthMap);

}
