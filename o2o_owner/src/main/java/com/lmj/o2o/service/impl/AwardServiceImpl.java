package com.lmj.o2o.service.impl;

import com.lmj.o2o.consts.Consts;
import com.lmj.o2o.dao.AwardDao;
import com.lmj.o2o.dto.AwardTO;
import com.lmj.o2o.entity.Award;
import com.lmj.o2o.entity.Shop;
import com.lmj.o2o.enums.OperationEnum;
import com.lmj.o2o.service.AwardSerice;
import com.lmj.o2o.service.RedisService;
import com.lmj.o2o.utils.GsonUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * ClassName: AwardServiceImpl
 * Description:
 * date: 2020/3/19 10:11
 *
 * @author MJ
 */
@Service
public class AwardServiceImpl implements AwardSerice {

    @Autowired
    private AwardDao awardDao;

    @Reference(version = "${demo.service.version}")
    private RedisService redisService;


    @Override
    public AwardTO addNewAward(Award award) {
        String storeKey= Consts.AWARD_LIST_KEY+"_"+award.getShopId().toString();
        AwardTO awardTO;
        awardDao.addAward(award);
        if (award.getAwardId()>0) {
            awardTO=new AwardTO(OperationEnum.SUCCESS);
            redisService.delKey(storeKey);
            return awardTO;
        }
        awardTO=new AwardTO(OperationEnum.INNER_ERROR);

        return awardTO;
    }

    @Override
    public AwardTO deleteAward(Award award) {
        String storeKey= Consts.AWARD_LIST_KEY+"_"+award.getShopId().toString();
        AwardTO awardTO;
        int i = awardDao.deleteAward(award);
        if (i==1) {
            awardTO=new AwardTO(OperationEnum.SUCCESS);
            awardTO.setAward(award);
            redisService.delKey(storeKey);
            return awardTO;
        }
        awardTO=new AwardTO(OperationEnum.INNER_ERROR);
        return awardTO;
    }

    @Override
    public AwardTO updateAward(Award award) {
        String storeKey= Consts.AWARD_KEY+"_"+award.getShopId().toString()+"_"+award.getAwardId().toString();
        String storeListKey= Consts.AWARD_LIST_KEY+"_"+award.getShopId().toString();
        Date date = new Date();
        award.setLastEditTime(date);
        AwardTO awardTO;
        int i = awardDao.updateAward(award);
        if (i==1) {
            awardTO=new AwardTO(OperationEnum.SUCCESS);
            redisService.delKey(storeKey);
            redisService.delKey(storeListKey);
            return awardTO;
        }
        awardTO=new AwardTO(OperationEnum.INNER_ERROR);

        return awardTO;
    }

    @Override
    public AwardTO getAwardListByShopId(Shop shop) {
        String storeKey= Consts.AWARD_LIST_KEY+"_"+shop.getShopId().toString();
        AwardTO awardTO;
        List<Award> awardList;
        if (!redisService.existKey(storeKey)) {
             awardList = awardDao.getAwardListByShopId(shop);
            redisService.storeValue(storeKey, GsonUtils.toGsonString(awardList).replaceAll("/","aaaa"));
            redisService.expireKey(storeKey,Consts.EXPIRE_TIME);
        }else{
            awardList=GsonUtils.GsonToList(redisService.get(storeKey).replaceAll("aaaa","/"),Award.class);
        }
        if (awardList!=null && awardList.size()!=0) {
            awardTO=new AwardTO(awardList,OperationEnum.SUCCESS);
            return awardTO;
        }
        awardTO=new AwardTO(OperationEnum.INNER_ERROR);


        return awardTO;
    }

    @Override
    public AwardTO getAwardByAwardId(Award award) {
        String storeKey= Consts.AWARD_KEY+"_"+award.getShopId().toString()+"_"+award.getAwardId().toString();
        AwardTO awardTO;
        Award awardInfo;
        if (!redisService.existKey(storeKey)) {
            awardInfo = awardDao.getAwardByAwardId(award);
            redisService.storeValue(storeKey, GsonUtils.toGsonString(awardInfo).replaceAll("/","aaaa"));
            redisService.expireKey(storeKey,Consts.EXPIRE_TIME);
        }else{
            awardInfo=GsonUtils.GsonToBean(redisService.get(storeKey).replaceAll("aaaa","/"),Award.class);
        }
        if (awardInfo!=null) {
            awardTO=new AwardTO(OperationEnum.SUCCESS);
            awardTO.setAward(awardInfo);
            return awardTO;
        }
        awardTO=new AwardTO(OperationEnum.INNER_ERROR);

        return awardTO;
    }
}
