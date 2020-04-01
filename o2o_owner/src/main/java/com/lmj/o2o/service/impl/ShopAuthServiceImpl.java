package com.lmj.o2o.service.impl;

import com.lmj.o2o.consts.Consts;
import com.lmj.o2o.dao.ShopAuthManageDao;
import com.lmj.o2o.dto.ShopAuthManageTO;
import com.lmj.o2o.entity.Shop;
import com.lmj.o2o.entity.ShopAuthMap;
import com.lmj.o2o.enums.OperationEnum;
import com.lmj.o2o.service.RedisService;
import com.lmj.o2o.service.ShopAuthManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * ClassName: ShopAuthServiceImpl
 * Description:
 * date: 2020/3/26 13:15
 *
 * @author MJ
 */
@Service
public class ShopAuthServiceImpl implements ShopAuthManageService {

    @Autowired
    private ShopAuthManageDao shopAuthManageDao;

    @Autowired
    private RedisService redisService;

    @Override
    public ShopAuthManageTO getShopAuthListByShopId(Shop shop) {
        ShopAuthManageTO shopAuthManageTO;
        List<ShopAuthMap> shopAuthMaps = shopAuthManageDao.queryAllShopAuth(shop);
        if (shopAuthMaps==null || shopAuthMaps.size()==0) {
            shopAuthManageTO = new ShopAuthManageTO(OperationEnum.NULL_RESULT);
            return shopAuthManageTO;
        }
        shopAuthManageTO = new ShopAuthManageTO(OperationEnum.SUCCESS);
        shopAuthManageTO.setList(shopAuthMaps);
        return shopAuthManageTO;
    }

    @Override
    public ShopAuthManageTO deleteShopAuth(ShopAuthMap shopAuthMap) {
        ShopAuthManageTO shopAuthManageTO;
        int effectRow = shopAuthManageDao.deleteShopAuth(shopAuthMap);
        if (effectRow>0) {
            shopAuthManageTO = new ShopAuthManageTO(OperationEnum.SUCCESS);
            return shopAuthManageTO;
        }
        shopAuthManageTO = new ShopAuthManageTO(OperationEnum.INNER_ERROR);
        return shopAuthManageTO;
    }

    @Override
    public ShopAuthManageTO addShopAuth(ShopAuthMap shopAuthMap) {
        ShopAuthManageTO shopAuthManageTO;
        shopAuthMap.setTitleFlag(1);
        shopAuthMap.setEnableStatus(1);
        shopAuthMap.setLastEditTime(new Date());
        shopAuthMap.setCreateTime(new Date());
        int i = shopAuthManageDao.addShopAuth(shopAuthMap);
        if (i == 1) {
            shopAuthManageTO = new ShopAuthManageTO(OperationEnum.SUCCESS);
            String codeKey = Consts.SHOP_AUTH_CODE_PREFIX + shopAuthMap.getShopId().toString();
            redisService.deleteKey(codeKey);
            return shopAuthManageTO;
        }
        shopAuthManageTO = new ShopAuthManageTO(OperationEnum.INNER_ERROR);
        return shopAuthManageTO;
    }

    @Override
    public ShopAuthManageTO updateShopAuth(ShopAuthMap shopAuthMap) {
        ShopAuthManageTO shopAuthManageTO;
        int effectRow = shopAuthManageDao.updateShopAuth(shopAuthMap);
        if (effectRow == 1) {
            shopAuthManageTO = new ShopAuthManageTO(OperationEnum.SUCCESS);
            return shopAuthManageTO;
        }
        shopAuthManageTO = new ShopAuthManageTO(OperationEnum.INNER_ERROR);
        return shopAuthManageTO;
    }

    @Override
    public ShopAuthManageTO queryShopAuth(ShopAuthMap shopAuthMap) {
        ShopAuthManageTO shopAuthManageTO;
        ShopAuthMap shopAuth = shopAuthManageDao.getShopAuth(shopAuthMap);
        if (shopAuth!=null) {
            shopAuthManageTO = new ShopAuthManageTO(OperationEnum.SUCCESS);
            shopAuthManageTO.setShopAuthMap(shopAuth);
            return shopAuthManageTO;
        }
        shopAuthManageTO = new ShopAuthManageTO(OperationEnum.NULL_RESULT);
        return shopAuthManageTO;
    }
}
