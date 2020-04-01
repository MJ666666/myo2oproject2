package com.lmj.o2o.dao.impl;

import com.lmj.o2o.consts.Consts;
import com.lmj.o2o.dao.ShopCategoryDao;
import com.lmj.o2o.entity.ShopCategory;
import com.lmj.o2o.utils.ElasticUtils;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * ClassName: ShopCategoryDaoImpl
 * Description:
 * date: 2020/3/20 13:34
 *
 * @author MJ
 */
@Repository
public class ShopCategoryDaoImpl implements ShopCategoryDao {
    @Autowired
    private TransportClient transportClient;


    @Override
    public List<Map<String, Object>> getShopCategoryParentList() {
        SearchRequestBuilder searchRequestBuilder = transportClient.prepareSearch(Consts.ES_INDEX)
                .setTypes(Consts.ES_SHOP_CATEGORY_TYPE).addSort("priority", SortOrder.DESC).setFrom(0).setSize(6);
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery().mustNot(QueryBuilders.existsQuery("parent_id"));
        List<Map<String, Object>> hitSource = ElasticUtils.getHitSource(searchRequestBuilder, boolQueryBuilder);
        return hitSource;
    }

    @Override
    public List<Map<String, Object>> getShopCategoryListByParentId(ShopCategory shopCategory) {
        SearchRequestBuilder searchRequestBuilder = transportClient.prepareSearch(Consts.ES_INDEX)
                .setTypes(Consts.ES_SHOP_CATEGORY_TYPE).addSort("priority", SortOrder.DESC);
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery().must(QueryBuilders.termQuery("parent_id",shopCategory.getParentId()));
        List<Map<String, Object>> hitSource = ElasticUtils.getHitSource(searchRequestBuilder, boolQueryBuilder);

        return hitSource;
    }
}
