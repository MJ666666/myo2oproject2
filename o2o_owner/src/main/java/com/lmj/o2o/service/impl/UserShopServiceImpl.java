package com.lmj.o2o.service.impl;

import com.lmj.o2o.consts.Consts;
import com.lmj.o2o.dao.UserShopDao;
import com.lmj.o2o.dto.UserShopTO;
import com.lmj.o2o.entity.UserShopMap;
import com.lmj.o2o.enums.OperationEnum;
import com.lmj.o2o.service.RedisService;
import com.lmj.o2o.service.UserShopService;
import com.lmj.o2o.utils.GsonUtils;
import com.lmj.o2o.utils.PageUtil;
import com.lmj.o2o.vo.PageVO;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ClassName: UserShopServiceImpl
 * Description:
 * date: 2020/3/24 17:44
 *
 * @author MJ
 */
@Service
public class UserShopServiceImpl implements UserShopService {


    @Reference(version = "${demo.service.version}")
    private RedisService redisService;

    @Autowired
    private UserShopDao userShopDao;

    @Override
    public UserShopTO getAllCustomers(PageVO pageVO) {
        String recordsKey = Consts.USER_SHOP_LIST_KEY + pageVO.getShopId().toString() + "_page_" + pageVO.getCurrentPage();
        Map<String, Object> map = new HashMap<>();
        UserShopTO userShopTO;
        //每次查询看数据库记录总数看有没有新添记录，有就删除redis的数据
        String recordNumsKey=Consts.USER_SHOP_LIST_KEY+pageVO.getShopId().toString()+"_totals";
        int totalRecords = userShopDao.countAllCustomers(pageVO);
        if (!redisService.existKey(recordNumsKey)) {
            redisService.storeValue(recordNumsKey,String.valueOf(totalRecords));
        }else {
            String storeNum = redisService.get(recordNumsKey);
            int num=Integer.valueOf(storeNum);
            if (num!=totalRecords) {
                //删除第一页的缓存，其他的等时间过期
                String delKeyOne = Consts.USER_SHOP_LIST_KEY + pageVO.getShopId().toString() + "_page_1";
                redisService.delKey(delKeyOne);
                redisService.storeValue(recordNumsKey,String.valueOf(totalRecords));
            }
        }
        //查询记录
        List<UserShopMap> userShopMaps;
        if (!redisService.existKey(recordsKey)) {
            userShopMaps = userShopDao.queryAllCustomerByShopID(pageVO);
            redisService.storeValue(recordsKey, GsonUtils.toGsonString(userShopMaps));
            redisService.expireKey(recordsKey,Consts.PAGE_EXPIRE_TIME);
        }else{
            String userShopJsons = redisService.get(recordsKey);
            userShopMaps = GsonUtils.GsonToList(userShopJsons, UserShopMap.class);
        }
        PageUtil.fillParamsIntoMap(map,pageVO,totalRecords);
        if (userShopMaps != null && userShopMaps.size() > 0) {
            map.put("records",userShopMaps);
            userShopTO = new UserShopTO(OperationEnum.SUCCESS);
            userShopTO.setMap(map);
            return userShopTO;
        }
        userShopTO = new UserShopTO(OperationEnum.NULL_RESULT);
        return userShopTO;

    }
}
