package com.lmj.o2o.service.impl;


import com.lmj.o2o.consts.Consts;
import com.lmj.o2o.dao.HeadLineDao;
import com.lmj.o2o.dto.HeadLineTO;
import com.lmj.o2o.entity.Area;
import com.lmj.o2o.entity.HeadLine;
import com.lmj.o2o.enums.OperationEnum;
import com.lmj.o2o.service.HeadLineService;
import com.lmj.o2o.service.RedisService;
import com.lmj.o2o.utils.GsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ClassName: HeadLineServiceImpl
 * Description:
 * date: 2020/3/15 11:37
 *
 * @author MJ
 */
@Service
public class HeadLineServiceImpl implements HeadLineService {

    @Autowired
    private RedisService redisService;

    @Autowired
    private HeadLineDao headLineDao;

    @Override
    public HeadLineTO getHeadLineList() {
        HeadLineTO headLineTO;
        List<HeadLine> headLines;

        if (!redisService.existKey(Consts.HEAD_LINE_LIST_KEY)) {
            headLines=headLineDao.queryHeadLines();
            redisService.storeValue(Consts.HEAD_LINE_LIST_KEY, GsonUtils.toGsonString(headLines).replaceAll("/","aaaa"));
            redisService.expireKey(Consts.HEAD_LINE_LIST_KEY,Consts.EXPIRE_TIME);
        }else {
            String headLineJsons = redisService.get(Consts.HEAD_LINE_LIST_KEY);
            headLines = GsonUtils.GsonToList(headLineJsons.replaceAll("aaaa","/"), HeadLine.class);
        }
        if (headLines!=null && headLines.size()!=0) {
            headLineTO=new HeadLineTO(OperationEnum.SUCCESS);
            headLineTO.setHeadLineList(headLines);
            return headLineTO;
        }
        headLineTO=new HeadLineTO(OperationEnum.INNER_ERROR);

        return headLineTO;
    }

    /**
     * Description: 管理员用
     * @author: MJ
     * @date: 2020/3/27 16:47
     * @param:
     * @return:
     */
    @Override
    public HeadLineTO getAllHeadLineList() {
        HeadLineTO headLineTO;
        List<HeadLine> headLines = headLineDao.queryAllHeadLines();
        if (headLines!=null || headLines.size()!=0) {
            headLineTO = new HeadLineTO(OperationEnum.SUCCESS);
            headLineTO.setHeadLineList(headLines);
            return headLineTO;
        }
        headLineTO = new HeadLineTO(OperationEnum.INNER_ERROR);
        return headLineTO;
    }

    @Override
    public HeadLineTO addHeadLine(HeadLine headLine) {
        int i = headLineDao.addHeadLine(headLine);
        return getHeadLineTO(i);
    }

    @Override
    public HeadLineTO updateHeadLine(HeadLine headLine) {
        int i = headLineDao.updateHeadLine(headLine);
        return getHeadLineTO(i);
    }

    @Override
    public HeadLineTO deleteHeadLine(HeadLine headLine) {
        int i = headLineDao.deleteHeadLine(headLine);
        return getHeadLineTO(i);
    }

    private HeadLineTO getHeadLineTO(int i) {
        HeadLineTO headLineTO;
        if (i == 1) {
            headLineTO = new HeadLineTO(OperationEnum.SUCCESS);
            redisService.deleteKey(Consts.HEAD_LINE_LIST_KEY);
            return headLineTO;
        }
        headLineTO = new HeadLineTO(OperationEnum.INNER_ERROR);
        return headLineTO;
    }
}
