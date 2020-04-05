package com.lmj.o2o.service.impl;


import com.lmj.o2o.consts.Consts;
import com.lmj.o2o.dao.ShopDao;
import com.lmj.o2o.dto.ShopTO;
import com.lmj.o2o.entity.Shop;
import com.lmj.o2o.enums.ShopStateEnum;
import com.lmj.o2o.service.RedisService;
import com.lmj.o2o.service.ShopService;
import com.lmj.o2o.utils.GsonUtils;
import com.lmj.o2o.utils.PageUtil;
import com.lmj.o2o.vo.PageVO;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ClassName: ShopServiceImpl
 * Description:
 * date: 2020/3/7 11:37
 *
 * @author MJ
 */
@Service
public class ShopServiceImpl implements ShopService {

    @Reference(version = "${demo.service.version}")
    private RedisService redisService;

    @Autowired
    private ShopDao shopDao;

    @Override
    public ShopTO getShopList(PageVO shopPage) {
        Map<String,Object> map=new HashMap<>();
        List<Shop> shops;
        String storeKey= Consts.SHOP_LIST_KEY+"_"+shopPage.getOwnerId().toString()+"_page_"+shopPage.getCurrentPage();
        if (!redisService.existKey(storeKey)) {
            shops = shopDao.queryOwnerShops(shopPage);
            redisService.storeValue(storeKey, GsonUtils.toGsonString(shops).replaceAll("/","aaaa"));
            redisService.expireKey(storeKey, Consts.PAGE_EXPIRE_TIME);
        }else {
            String shopJsons = redisService.get(storeKey).replaceAll("aaaa","/");
            shops=GsonUtils.GsonToList(shopJsons,Shop.class);
        }
        map.put("shopList",shops);
        String recordNumsKey=Consts.SHOP_LIST_KEY+"_"+shopPage.getOwnerId().toString()+"_totalRecords";
        int totalRecords;
        if (!redisService.existKey(recordNumsKey)) {
            totalRecords = shopDao.countTotalShops(shopPage.getShop());
            redisService.storeValue(recordNumsKey,String.valueOf(totalRecords));
            redisService.expireKey(recordNumsKey,Consts.PAGE_EXPIRE_TIME);
        }else {
            totalRecords=Integer.valueOf(redisService.get(recordNumsKey));
        }
        PageUtil.fillParamsIntoMap(map,shopPage,totalRecords);
        ShopTO shopTO;
        try {
            if (shops != null && shops.size() != 0) {
                shopTO = new ShopTO(map, ShopStateEnum.SUCCESS);
            }else{
                shopTO=new ShopTO(ShopStateEnum.NULL_SHOP);
            }

        }catch (Exception e){
            shopTO=new ShopTO(ShopStateEnum.INNER_ERROR);
            return shopTO;
        }

        return shopTO;
    }

    @Override
    public ShopTO modifyShopInfo( Shop shop) {
        String shopKey=Consts.SHOP_KEY+"_"+shop.getShopId().toString();
        Date date = new Date();
        shop.setLastEditTime(date);
        ShopTO shopTO=null;
        try {
        int i = shopDao.updateShopInfo(shop);
        if (i==1) {
            shopTO= new ShopTO(ShopStateEnum.SUCCESS);
            redisService.delKey(shopKey);
        }else{
            shopTO=new ShopTO(ShopStateEnum.NULL_SHOP_INFO);
        }

        }catch (Exception e){
            shopTO=new ShopTO(ShopStateEnum.INNER_ERROR);
            return shopTO;
        }
        return shopTO;
    }

    @Override
    public ShopTO addNewShop(Shop shop) {
        String storeKey= Consts.SHOP_LIST_KEY+"_"+shop.getOwnerId().toString()+"_page_1";
        String recordNumsKey=Consts.SHOP_LIST_KEY+"_"+shop.getOwnerId().toString()+"_totalRecords";
        Date date = new Date();
        ShopTO shopTO;
        shop.setCreateTime(date);
        shop.setLastEditTime(date);
        shop.setEnableStatus(0);
        int i = shopDao.addNewShop(shop);
        if (i==1) {
            shopTO= new ShopTO(ShopStateEnum.SUCCESS);
            shopTO.setShop(shop);
            redisService.delKey(storeKey);
            redisService.delKey(recordNumsKey);
        }else{
            shopTO=new ShopTO(ShopStateEnum.NULL_SHOP_INFO);
        }

        return shopTO;
    }

    @Override
    public ShopTO getShopInfo(Shop shop) {
        String shopKey=Consts.SHOP_KEY+"_"+shop.getShopId().toString();
        ShopTO shopTO;
        Shop responseShop;
        if (!redisService.existKey(shopKey)) {
            responseShop = shopDao.queryShopByShopId(shop);
            redisService.storeValue(shopKey,GsonUtils.toGsonString(responseShop).replaceAll("/","aaaa"));
            redisService.expireKey(shopKey,Consts.EXPIRE_TIME);
        }else {
            String shopJson = redisService.get(shopKey).replaceAll("aaaa","/");
            responseShop= GsonUtils.GsonToBean(shopJson, Shop.class);
        }
        if (responseShop.getShopId()!=null) {
            shopTO = new ShopTO(ShopStateEnum.SUCCESS);
            shopTO.setShop(responseShop);
        }else {
            shopTO=new ShopTO(ShopStateEnum.NULL_SHOP_INFO);
        }

        return shopTO;
    }

    @Override
    public ShopTO getAllShops(PageVO adminPageVO) {
        ShopTO shopTO;
        List<Shop> shops = shopDao.queryAllShops(adminPageVO);
        int i = shopDao.countTotalShops(new Shop());
        if (shops != null && shops.size() != 0) {
            Map<String, Object> map = new HashMap<>();
            map.put("shopList",shops);
            PageUtil.fillParamsIntoMap(map, adminPageVO, i);
            shopTO = new ShopTO(map, ShopStateEnum.SUCCESS);
            return shopTO;
        }
        shopTO = new ShopTO(ShopStateEnum.NULL_SHOP);
        return shopTO;
    }

    @Override
    public ShopTO adminShopInfo(Shop shop) {
        ShopTO shopTO;
        int i = shopDao.updateShopInfo(shop);
        if (i == 1) {
            shopTO = new ShopTO(ShopStateEnum.SUCCESS);
            return shopTO;
        }
        shopTO = new ShopTO(ShopStateEnum.INNER_ERROR);
        return shopTO;
    }

}
