package com.lmj.o2o.dao.impl;

import com.lmj.o2o.consts.Consts;
import com.lmj.o2o.dao.AwardDao;
import com.lmj.o2o.entity.Award;
import com.lmj.o2o.entity.Shop;
import com.lmj.o2o.utils.ElasticUtils;
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
 * ClassName: AwardDaoImpl
 * Description:
 * date: 2020/3/20 13:05
 *
 * @author MJ
 */
@Repository
public class AwardDaoImpl implements AwardDao{

    @Autowired
    private TransportClient transportClient;

    @Override
    public List<Map<String, Object>> getAwardListByShopId(PageVO awardPage) {
        SearchRequestBuilder searchRequestBuilder =
                transportClient.prepareSearch(Consts.ES_INDEX).setTypes(Consts.ES_AWARD_TYPE);
        if (awardPage.getPageSize()!=0) {
            searchRequestBuilder.setSize(awardPage.getPageSize());
        }
        BoolQueryBuilder condition = QueryBuilders.boolQuery().must(QueryBuilders.termQuery("shop_id", awardPage.getShopId()));
        List<Map<String, Object>> hitSource = ElasticUtils.getHitSource(searchRequestBuilder, condition);
        return hitSource;
    }

    @Override
    public List<Map<String, Object>> getAwardByAwardId(Award award) {
        SearchRequestBuilder searchRequestBuilder =
                transportClient.prepareSearch(Consts.ES_INDEX).setTypes(Consts.ES_AWARD_TYPE);
        BoolQueryBuilder condition = QueryBuilders.boolQuery().must(QueryBuilders.termQuery("award_id", award.getAwardId()));
        List<Map<String, Object>> hitSource = ElasticUtils.getHitSource(searchRequestBuilder, condition);

        return hitSource;
    }
}
