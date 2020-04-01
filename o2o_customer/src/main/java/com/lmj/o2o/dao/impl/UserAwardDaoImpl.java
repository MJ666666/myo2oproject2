package com.lmj.o2o.dao.impl;

import com.lmj.o2o.consts.Consts;
import com.lmj.o2o.dao.UserAwardDao;
import com.lmj.o2o.entity.UserAwardMap;
import com.lmj.o2o.utils.ElasticUtils;
import com.lmj.o2o.vo.ExchangeRecordPage;
import com.lmj.o2o.vo.PageVO;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * ClassName: UserAwardDaoImpl
 * Description:
 * date: 2020/3/20 15:01
 *
 * @author MJ
 */
@Repository
public class UserAwardDaoImpl implements UserAwardDao {

    @Autowired
    private TransportClient transportClient;

    @Override
    public List<Map<String, Object>> getExchangeList(ExchangeRecordPage exchangeRecordPage) {
        SearchRequestBuilder searchRequestBuilder = transportClient.prepareSearch(Consts.ES_INDEX).setTypes(Consts.ES_USER_AWARD_MAP_TYPE);
        BoolQueryBuilder boolBuilder = QueryBuilders.boolQuery().must(QueryBuilders.termQuery("user_id", exchangeRecordPage.getUserId()));
        if (exchangeRecordPage.getUsedStatus()!=null) {
            boolBuilder.must(QueryBuilders.termQuery("used_status", exchangeRecordPage.getUsedStatus()));
        }
        if (exchangeRecordPage.getShopId() != null) {
            boolBuilder.must(QueryBuilders.termQuery("shop_id", exchangeRecordPage.getShopId()));
        }
        if (exchangeRecordPage.getPageSize() != 0) {
            searchRequestBuilder.setSize(exchangeRecordPage.getPageSize());
        }
        List<Map<String, Object>> hitSource = ElasticUtils.getHitSource(searchRequestBuilder, boolBuilder);
        return hitSource;
    }
}
