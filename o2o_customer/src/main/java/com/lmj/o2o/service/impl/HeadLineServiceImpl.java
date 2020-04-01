package com.lmj.o2o.service.impl;

import com.lmj.o2o.consts.Consts;
import com.lmj.o2o.dao.HeadLineDao;
import com.lmj.o2o.dto.HeadLineTO;
import com.lmj.o2o.dto.ProductTO;
import com.lmj.o2o.entity.HeadLine;
import com.lmj.o2o.enums.OperationEnum;
import com.lmj.o2o.service.HeadLineService;
import com.lmj.o2o.utils.ElasticUtils;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * ClassName: HeadLineServiceImpl
 * Description:
 * date: 2020/3/18 14:55
 *
 * @author MJ
 */
@Service
public class HeadLineServiceImpl implements HeadLineService {

   @Autowired
   private HeadLineDao headLineDao;

    @Override
    public HeadLineTO getHeadLinesList() {
     HeadLineTO headLineTO;
        List<Map<String, Object>> headLineList = headLineDao.getHeadLineList();
        if (!headLineList.isEmpty()) {
            headLineTO=new HeadLineTO(headLineList,OperationEnum.SUCCESS);
            return headLineTO;
        }
        headLineTO=new HeadLineTO(OperationEnum.NULL_RESULT);
        return headLineTO;
    }
}
