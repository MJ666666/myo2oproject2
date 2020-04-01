package com.lmj.o2o.dao.impl;

import com.lmj.o2o.consts.Consts;
import com.lmj.o2o.dao.UserShopDao;
import com.lmj.o2o.entity.UserShopMap;
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
 * ClassName: UserShopDaoImpl
 * Description:
 * date: 2020/3/20 13:46
 *
 * @author MJ
 */
@Repository
public class UserShopDaoImpl implements UserShopDao {

    @Autowired
    private TransportClient transportClient;


    @Override
    public List<Map<String, Object>> getUserShopInfo(UserShopMap userShopMap) {
        SearchRequestBuilder searchRequestBuilder = transportClient.prepareSearch(Consts.ES_INDEX).setTypes(Consts.ES_USER_SHOP_MAP_TYPE);
        BoolQueryBuilder boolBuilder = QueryBuilders.boolQuery().must(QueryBuilders.termQuery("user_id", userShopMap.getUserId()));
        if(userShopMap.getShopId()!=null){
            boolBuilder.must(QueryBuilders.termQuery("shop_id", userShopMap.getShopId()));
        }
        List<Map<String, Object>> hitSource = ElasticUtils.getHitSource(searchRequestBuilder, boolBuilder);
        return hitSource;
    }
}
