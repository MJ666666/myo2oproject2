package com.lmj.o2o.dao.impl;

import com.lmj.o2o.consts.Consts;
import com.lmj.o2o.dao.UserProductDao;
import com.lmj.o2o.entity.UserProductMap;
import com.lmj.o2o.utils.ElasticUtils;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * ClassName: UserProductDaoImpl
 * Description:
 * date: 2020/3/20 14:25
 *
 * @author MJ
 */
@Repository
public class UserProductDaoImpl implements UserProductDao {

    @Autowired
    private TransportClient transportClient;

    @Override
    public List<Map<String, Object>> getUserProdcutList(UserProductMap userProductMap) {
        SearchRequestBuilder searchRequestBuilder = transportClient.prepareSearch(Consts.ES_INDEX).setTypes(Consts.ES_USER_PRODUCT_MAP_TYPE);
        BoolQueryBuilder boolBuilder = QueryBuilders.boolQuery().must(QueryBuilders.termQuery("user_id", userProductMap.getUserId()));
        if (userProductMap.getShopId()!=null) {
            boolBuilder.must(QueryBuilders.termQuery("shop_id", userProductMap.getShopId()));
        }
        List<Map<String, Object>> hitSource = ElasticUtils.getHitSource(searchRequestBuilder, boolBuilder);
        return hitSource;
    }
}


