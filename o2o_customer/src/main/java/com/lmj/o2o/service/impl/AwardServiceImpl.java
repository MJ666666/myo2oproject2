package com.lmj.o2o.service.impl;

import com.lmj.o2o.consts.Consts;
import com.lmj.o2o.dao.AwardDao;
import com.lmj.o2o.dto.AwardTO;
import com.lmj.o2o.entity.Award;
import com.lmj.o2o.entity.Shop;
import com.lmj.o2o.enums.OperationEnum;
import com.lmj.o2o.service.AwardService;
import com.lmj.o2o.utils.ElasticUtils;
import com.lmj.o2o.vo.PageVO;
import org.apache.tomcat.util.bcel.Const;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * ClassName: AwardServiceImpl
 * Description:
 * date: 2020/3/20 10:53
 *
 * @author MJ
 */
@Service
public class AwardServiceImpl implements AwardService{


    @Autowired
    private AwardDao awardDao;

    @Override
    public AwardTO getAwardListByShopId(PageVO awardPage) {
        AwardTO awardTO;
        List<Map<String, Object>> awardList = awardDao.getAwardListByShopId(awardPage);
        return getAwardTO(awardList);
    }

    @Override
    public AwardTO getAwardByAwardId(Award award) {
        AwardTO awardTO;
        List<Map<String, Object>> awardList = awardDao.getAwardByAwardId(award);
        return getAwardTO(awardList);
    }

    private AwardTO getAwardTO(List<Map<String, Object>> awardList) {
        AwardTO awardTO;
        if (awardList.size() > 0) {
            awardTO = new AwardTO(OperationEnum.SUCCESS);
            awardTO.setDataList(awardList);
            return awardTO;
        }
        awardTO = new AwardTO(OperationEnum.NULL_RESULT);
        return awardTO;
    }
}
