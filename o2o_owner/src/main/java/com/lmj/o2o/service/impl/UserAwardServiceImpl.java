package com.lmj.o2o.service.impl;

import com.lmj.o2o.consts.Consts;
import com.lmj.o2o.dao.ShopAuthManageDao;
import com.lmj.o2o.dao.UserAwardDao;
import com.lmj.o2o.dao.UserShopDao;
import com.lmj.o2o.dto.UserAwardTO;
import com.lmj.o2o.entity.ShopAuthMap;
import com.lmj.o2o.entity.UserAwardMap;
import com.lmj.o2o.enums.OperationEnum;
import com.lmj.o2o.exception.PonitExecuteException;
import com.lmj.o2o.service.RedisService;
import com.lmj.o2o.service.UserAwardService;
import com.lmj.o2o.utils.GsonUtils;
import com.lmj.o2o.utils.PageUtil;
import com.lmj.o2o.utils.UuidUtils;
import com.lmj.o2o.vo.PageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ClassName: UserAwardServiceImpl
 * Description:
 * date: 2020/3/21 13:39
 *
 * @author MJ
 */
@Service
public class UserAwardServiceImpl implements UserAwardService{

    @Autowired
    private UserAwardDao userAwardDao;

    @Autowired
    private UserShopDao userShopDao;

    @Autowired
    private RedisService redisService;

    @Transactional
    @Override
    public UserAwardTO addNewUserAwardRecord(UserAwardMap userAwardMap) {
        int i = userAwardDao.addNewUserAwardMap(userAwardMap);
        int substractPointResult = userShopDao.updateUserPoint(userAwardMap);
        if(i==1 && substractPointResult==1){
            return getUserAwardTO(1);
        }else {
            throw new PonitExecuteException("更新积分失败");
        }
    }

    @Autowired
    private ShopAuthManageDao shopAuthManageDao;

    @Override
    public UserAwardTO updateUserAwardRecord(UserAwardMap userAwardMap, ShopAuthMap shopAuthMap) {
        UserAwardTO userAwardTO;
        ShopAuthMap existAuth = shopAuthManageDao.queryExistAuth(shopAuthMap);
        if (existAuth==null) {
            userAwardTO=new UserAwardTO(OperationEnum.UNGRANTED_OPERATION);
            return userAwardTO;
        }
        String codeKey=userAwardMap.getAwardCode();
        if (!redisService.existKey(codeKey) || !"0".equals(redisService.get(codeKey))) {
            return new UserAwardTO(OperationEnum.INNER_ERROR);
        }
        int i = userAwardDao.updateUserAwardMap(userAwardMap);
        if (i == 1) {
            userAwardTO = new UserAwardTO(OperationEnum.SUCCESS);
            redisService.storeValue(codeKey,"1");
            redisService.expireKey(codeKey,Consts.EXPIRE_TIME);
            return userAwardTO;
        }
        userAwardTO = new UserAwardTO(OperationEnum.INNER_ERROR);
        return userAwardTO;
    }

    @Override
    public String getRandomCode() {
        String awardCode = UuidUtils.getUUID();
        redisService.storeValue(awardCode,"0");
        return awardCode;
    }

    @Override
    public UserAwardTO getCustomersAwardRecords(PageVO pageVO) {

        String recordsKey = Consts.USER_AWARD_RECORD_LIST_OWNER_KEY + pageVO.getShopId().toString() + "_page_" + pageVO.getCurrentPage();
        Map<String, Object> map = new HashMap<>();
        UserAwardTO userAwardTO;
        //每次查询看数据库记录总数看有没有新添记录，有就删除redis的数据
        String recordNumsKey=Consts.USER_AWARD_RECORD_LIST_OWNER_KEY+pageVO.getShopId().toString()+"_totals";
        int totalRecords = userAwardDao.countTotalRecords(pageVO);
        if (!redisService.existKey(recordNumsKey)) {
            redisService.storeValue(recordNumsKey,String.valueOf(totalRecords));
        }else {
            String storeNum = redisService.get(recordNumsKey);
            int num=Integer.valueOf(storeNum);
            if (num!=totalRecords) {
                //删除前三页的缓存，其他的等时间过期
                String delKeyOne = Consts.USER_AWARD_RECORD_LIST_OWNER_KEY + pageVO.getShopId().toString() + "_page_1";
                String delKeyTwo = Consts.USER_AWARD_RECORD_LIST_OWNER_KEY + pageVO.getShopId().toString() + "_page_1";
                String delKeyThree = Consts.USER_AWARD_RECORD_LIST_OWNER_KEY + pageVO.getShopId().toString() + "_page_1";
                redisService.deleteKey(delKeyOne);
                redisService.deleteKey(delKeyTwo);
                redisService.deleteKey(delKeyThree);
                redisService.storeValue(recordNumsKey,String.valueOf(totalRecords));
            }
        }
        //查询记录
        List<UserAwardMap> userProductMaps;
        if (!redisService.existKey(recordsKey)) {
            userProductMaps = userAwardDao.queryAllUserAwardByShopId(pageVO);
            redisService.storeValue(recordsKey, GsonUtils.toGsonString(userProductMaps));
            redisService.expireKey(recordsKey,Consts.PAGE_EXPIRE_TIME);
        }else{
            String userProductJsons = redisService.get(recordsKey);
            userProductMaps = GsonUtils.GsonToList(userProductJsons, UserAwardMap.class);
        }
        PageUtil.fillParamsIntoMap(map,pageVO,totalRecords);
        if (userProductMaps != null && userProductMaps.size() > 0) {
            map.put("records",userProductMaps);
            userAwardTO = new UserAwardTO(OperationEnum.SUCCESS);
            userAwardTO.setMap(map);
            return userAwardTO;
        }
        userAwardTO = new UserAwardTO(OperationEnum.NULL_RESULT);
        return userAwardTO;

    }

    @Override
    public UserAwardTO getHistoryAwardRecords(PageVO userAwardMap) {
        UserAwardTO userAwardTO;
        List<UserAwardMap> userAwardMapsRecords;
        String storeKey=Consts.USER_AWARD_HIISTORY_LIST_KEY+userAwardMap.getUserId().toString()+"_"+userAwardMap.getCurrentPage();
        if (!redisService.existKey(storeKey)) {
            userAwardMapsRecords = userAwardDao.queryAllUserAward(userAwardMap);
            redisService.storeValue(storeKey, GsonUtils.toGsonString(userAwardMapsRecords));
            redisService.expireKey(storeKey, Consts.EXPIRE_TIME);
        }else {
            String recordsJsons = redisService.get(storeKey);
            userAwardMapsRecords = GsonUtils.GsonToList(recordsJsons, UserAwardMap.class);
        }
        if (userAwardMapsRecords != null && userAwardMapsRecords.size() != 0) {
            userAwardTO = new UserAwardTO(userAwardMapsRecords, OperationEnum.SUCCESS);
            return userAwardTO;
        }
        userAwardTO = new UserAwardTO(OperationEnum.NULL_RESULT);
        return userAwardTO;
    }


    private UserAwardTO getUserAwardTO(int i) {
        UserAwardTO userAwardTO;
        if (i == 1) {
            userAwardTO = new UserAwardTO(OperationEnum.SUCCESS);
            return userAwardTO;
        }
        userAwardTO = new UserAwardTO(OperationEnum.INNER_ERROR);
        return userAwardTO;
    }



}
