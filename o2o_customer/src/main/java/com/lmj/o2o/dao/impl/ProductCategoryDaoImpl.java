package com.lmj.o2o.dao.impl;

import com.lmj.o2o.consts.Consts;
import com.lmj.o2o.dao.ProductCategoryDao;
import com.lmj.o2o.entity.Shop;
import com.lmj.o2o.utils.ElasticUtils;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * ClassName: ProductCategoryDaoImpl
 * Description:
 * date: 2020/3/20 13:23
 *
 * @author MJ
 */
@Repository
public class ProductCategoryDaoImpl implements ProductCategoryDao {

    @Autowired
    private TransportClient transportClient;


    @Override
    public List<Map<String, Object>> getProductCatesListByShopId(Shop shop) {
        SearchRequestBuilder searchRequestBuilder = transportClient.prepareSearch(Consts.ES_INDEX).setTypes(Consts.ES_PRODUCT_CATEGORY_TYPE);
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery().must(QueryBuilders.termQuery("shop_id", shop.getShopId()));
        List<Map<String, Object>> hitSource = ElasticUtils.getHitSource(searchRequestBuilder, queryBuilder);
        return hitSource;
    }
}
