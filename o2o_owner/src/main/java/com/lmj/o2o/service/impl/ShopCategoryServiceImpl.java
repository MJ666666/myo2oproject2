package com.lmj.o2o.service.impl;

import com.lmj.o2o.consts.Consts;
import com.lmj.o2o.dao.ShopCategoryDao;
import com.lmj.o2o.dto.ShopCategoryTO;
import com.lmj.o2o.entity.ShopCategory;
import com.lmj.o2o.enums.OperationEnum;
import com.lmj.o2o.service.RedisService;
import com.lmj.o2o.service.ShopCategoryService;
import com.lmj.o2o.utils.GsonUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

/**
 * ClassName: ShopCategoryServiceImpl
 * Description:
 * date: 2020/3/12 17:50
 *
 * @author MJ
 */
@Service
public class ShopCategoryServiceImpl implements ShopCategoryService {

    @Reference(version = "${demo.service.version}")
    private RedisService redisService;

    @Autowired
    private ShopCategoryDao shopCategoryDao;

    @Override
    public ShopCategoryTO getShopCategoryList() {
        List<ShopCategory> shopCategorys;
        if (!redisService.existKey(Consts.SHOP_CATEGORY_ALL_LIST_KEY)) {
            shopCategorys= shopCategoryDao.getShopCategorys();
            redisService.storeValue(Consts.SHOP_CATEGORY_ALL_LIST_KEY, GsonUtils.toGsonString(shopCategorys).replaceAll("/","aaaa"));
            redisService.expireKey(Consts.SHOP_CATEGORY_ALL_LIST_KEY,Consts.EXPIRE_TIME);
        }else {
            String jsons = redisService.get(Consts.SHOP_CATEGORY_ALL_LIST_KEY).replaceAll("aaaa","/");
            shopCategorys= GsonUtils.GsonToList(jsons, ShopCategory.class);
        }
        return getShopCategoryTO(shopCategorys);
    }

    @Override
    public ShopCategoryTO getShopParentCategoryList() {
        List<ShopCategory> shopParentCategorys;
        if (!redisService.existKey(Consts.SHOP_CATEGORY_PARENT_LIST_KEY)) {
            shopParentCategorys= shopCategoryDao.getShopParentCategorys();
            redisService.storeValue(Consts.SHOP_CATEGORY_PARENT_LIST_KEY, GsonUtils.toGsonString(shopParentCategorys).replaceAll("/","aaaa"));
            redisService.expireKey(Consts.SHOP_CATEGORY_PARENT_LIST_KEY,Consts.EXPIRE_TIME);
        }else {
            String jsons = redisService.get(Consts.SHOP_CATEGORY_PARENT_LIST_KEY).replaceAll("aaaa","/");
            shopParentCategorys= GsonUtils.GsonToList(jsons, ShopCategory.class);
        }
        return getShopCategoryTO(shopParentCategorys);
    }

    @Override
    public ShopCategoryTO getShopCategoryListByParentId(ShopCategory shopCategory) {
        String storeKey=Consts.SHOP_CATEGORY_SON_LIST_KEY+"_"+shopCategory.getParentId().toString();
        List<ShopCategory> shopSonCategorys;
        if (!redisService.existKey(storeKey)) {
            shopSonCategorys= shopCategoryDao.getShopCategoryByParentId(shopCategory);
            redisService.storeValue(storeKey, GsonUtils.toGsonString(shopSonCategorys).replaceAll("/","aaaa"));
            redisService.expireKey(storeKey,Consts.EXPIRE_TIME);
        }else {
            String jsons = redisService.get(storeKey).replaceAll("aaaa","/");
            shopSonCategorys= GsonUtils.GsonToList(jsons, ShopCategory.class);
        }

        return getShopCategoryTO(shopSonCategorys);

    }

    @Override
    public ShopCategoryTO getAllShopCategory() {
        ShopCategoryTO shopCategoryTO;
        List<ShopCategory> shopCategories = shopCategoryDao.queryAllShopCategory();
        if (shopCategories != null && shopCategories.size() != 0) {
            shopCategoryTO = new ShopCategoryTO(OperationEnum.SUCCESS);
            shopCategoryTO.setShopCategoryList(shopCategories);
            return shopCategoryTO;
        }
        shopCategoryTO = new ShopCategoryTO(OperationEnum.INNER_ERROR);
        return shopCategoryTO;
    }

    @Override
    public ShopCategoryTO addShopCategory(ShopCategory shopCategory) {
        ShopCategoryTO shopCategoryTO;
        int i = shopCategoryDao.addShopCategory(shopCategory);
        if (i==1) {
            shopCategoryTO = new ShopCategoryTO(OperationEnum.SUCCESS);
            return shopCategoryTO;
        }
        shopCategoryTO = new ShopCategoryTO(OperationEnum.INNER_ERROR);
        return shopCategoryTO;
    }

    @Override
    public ShopCategoryTO modifyShopCategory(ShopCategory shopCategory) {
        ShopCategoryTO shopCategoryTO;
        int i = shopCategoryDao.updateShopCategory(shopCategory);
        if (i==1) {
            shopCategoryTO = new ShopCategoryTO(OperationEnum.SUCCESS);
            return shopCategoryTO;
        }
        shopCategoryTO = new ShopCategoryTO(OperationEnum.INNER_ERROR);
        return shopCategoryTO;
    }


    private ShopCategoryTO getShopCategoryTO(List<ShopCategory> shopParentCategorys) {
        ShopCategoryTO shopCategoryTO;
        if (shopParentCategorys == null || shopParentCategorys.size() == 0) {
            shopCategoryTO = new ShopCategoryTO(OperationEnum.NULL_RESULT);
            return shopCategoryTO;
        }
        shopCategoryTO = new ShopCategoryTO(OperationEnum.SUCCESS);
        shopCategoryTO.setShopCategoryList(shopParentCategorys);
        return shopCategoryTO;
    }
}
