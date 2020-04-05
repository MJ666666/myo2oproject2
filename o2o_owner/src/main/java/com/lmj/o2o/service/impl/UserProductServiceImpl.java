package com.lmj.o2o.service.impl;

import com.lmj.o2o.consts.Consts;
import com.lmj.o2o.dao.ProductDao;
import com.lmj.o2o.dao.ShopAuthManageDao;
import com.lmj.o2o.dao.UserProductDao;
import com.lmj.o2o.dao.UserShopDao;
import com.lmj.o2o.dto.UserProductTO;
import com.lmj.o2o.entity.*;
import com.lmj.o2o.enums.OperationEnum;
import com.lmj.o2o.exception.UserProductExecuteException;
import com.lmj.o2o.service.RedisService;
import com.lmj.o2o.service.UserProductService;
import com.lmj.o2o.utils.GsonUtils;
import com.lmj.o2o.utils.PageUtil;
import com.lmj.o2o.vo.PageVO;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ClassName: UserProductServiceImpl
 * Description:
 * date: 2020/3/20 17:09
 *
 * @author MJ
 */

@Service
public class UserProductServiceImpl implements UserProductService {

    @Autowired
    private UserProductDao userProductDao;

/**
 * Description: web的查询消费记录服务
 * @author: MJ
 * @date: 2020/3/24 14:59
 * @param:
 * @return:
 *
 */

@Reference(version = "${demo.service.version}")
  private RedisService redisService;

    @Override
    public UserProductTO getConsumeRecords(PageVO recordsPage) {
        String recordsKey = Consts.USER_PRODUCT_RECORD_LIST_KEY + recordsPage.getShopId().toString() + "_page_" + recordsPage.getCurrentPage();
        Map<String, Object> map = new HashMap<>();
        UserProductTO userProductTO;
        //每次查询看数据库记录总数看有没有新添记录，有就删除redis的数据
        String recordNumsKey=Consts.USER_PRODUCT_RECORD_LIST_KEY+recordsPage.getShopId().toString()+"_totals";
        int totalRecords = userProductDao.countRecords(recordsPage);
        if (!redisService.existKey(recordNumsKey)) {
            redisService.storeValue(recordNumsKey,String.valueOf(totalRecords));
        }else {
            String storeNum = redisService.get(recordNumsKey);
            int num=Integer.valueOf(storeNum);
            if (num!=totalRecords) {
                //删除前三页的缓存，其他的等时间过期
                String delKeyOne = Consts.USER_PRODUCT_RECORD_LIST_KEY + recordsPage.getShopId().toString() + "_page_1";
                String delKeyTwo = Consts.USER_PRODUCT_RECORD_LIST_KEY + recordsPage.getShopId().toString() + "_page_1";
                String delKeyThree = Consts.USER_PRODUCT_RECORD_LIST_KEY + recordsPage.getShopId().toString() + "_page_1";
                redisService.delKey(delKeyOne);
                redisService.delKey(delKeyTwo);
                redisService.delKey(delKeyThree);
                redisService.storeValue(recordNumsKey,String.valueOf(totalRecords));
            }
        }
        //查询记录
        List<UserProductMap> userProductMaps;
        if (!redisService.existKey(recordsKey)) {
            userProductMaps = userProductDao.geConsumeProductList(recordsPage);
            redisService.storeValue(recordsKey, GsonUtils.toGsonString(userProductMaps));
            redisService.expireKey(recordsKey,Consts.PAGE_EXPIRE_TIME);
        }else{
            String userProductJsons = redisService.get(recordsKey);
            userProductMaps = GsonUtils.GsonToList(userProductJsons, UserProductMap.class);
        }
        PageUtil.fillParamsIntoMap(map,recordsPage,totalRecords);
        if (userProductMaps != null && userProductMaps.size() > 0) {
            map.put("records",userProductMaps);
            userProductTO = new UserProductTO(OperationEnum.SUCCESS);
            userProductTO.setMap(map);
            return userProductTO;
        }
        userProductTO = new UserProductTO(OperationEnum.NULL_RESULT);
        return userProductTO;
    }


    @Autowired
    private UserShopDao userShopDao;


    @Autowired
    private ShopAuthManageDao shopAuthManageDao;
    /**
     * Description: 购买商品后，扫码更新积分
     * @author: MJ
     * @date: 2020/3/23 17:30
     * @param:
     * @return:
     */
    @Transactional
    @Override
    public UserProductTO buyTheProduct(UserProductMap userProductMap,ShopAuthMap shopAuthMap) {
        UserProductTO userProductTO;
        //看看操作员是否有权限
        ShopAuthMap existAuth = shopAuthManageDao.queryExistAuth(shopAuthMap);
        if (existAuth==null) {
            userProductTO=new UserProductTO(OperationEnum.UNGRANTED_OPERATION);
            return userProductTO;
        }
        //插入消费记录
        int addRow = userProductDao.addConsumeRecord(userProductMap);
        if (addRow!=1) {
            throw new UserProductExecuteException("插入消费记录失败");
        }
        int existRow = userShopDao.queryExistCustomer(userProductMap);
        if (existRow!=1) {
            //用户在该商铺不存在消费记录
            int effectRow = userShopDao.addNewCustomer(userProductMap);
            if (effectRow==1) {
                userProductTO = new UserProductTO(OperationEnum.SUCCESS);
                return userProductTO;
            }
            throw new UserProductExecuteException("添加新用户积分信息失败");
        }
        int pointEffectRow = userShopDao.addUserPoint(userProductMap);
        if (pointEffectRow==1) {
            userProductTO = new UserProductTO(OperationEnum.SUCCESS);
            return userProductTO;
        }else {
            throw new UserProductExecuteException("更新新用户积分信息失败");
        }
    }

    @Autowired
    private ProductDao productDao;

    @Override
    public UserProductTO statisticTheRecords(Shop shop) {
        UserProductTO userProductTO;
        shop.setShopId(20L);
        List<Product> products = productDao.queryProductCateByShopId(shop);
        int size=products.size();
        List<List<UserProductCount>> countPerWeek = new ArrayList<>();
        for (int j = 0; j < 7; j++) {
            List<UserProductCount> countPerDay = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                UserProductCount userProductCount = new UserProductCount();
                userProductCount.setBeforeDay(j+1);
                userProductCount.setProductId(products.get(i).getProductId());
                userProductCount.setProductName(products.get(i).getProductName());
                int countConsumeRecords = userProductDao.countConsumeRecords(userProductCount);
                userProductCount.setCount(countConsumeRecords);
                countPerDay.add(userProductCount);
            }
            countPerWeek.add(countPerDay);
        }
        if (countPerWeek.size() != 0) {
            userProductTO=new UserProductTO(OperationEnum.SUCCESS);
            userProductTO.setCountList(countPerWeek);
            return userProductTO;
        }
        userProductTO = new UserProductTO(OperationEnum.NULL_RESULT);
        return userProductTO;
    }
}
