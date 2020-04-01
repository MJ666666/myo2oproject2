package com.lmj.o2o.service.impl;

import com.lmj.o2o.dao.ShopSearchDao;
import com.lmj.o2o.dto.ShopTO;
import com.lmj.o2o.enums.ShopStateEnum;
import com.lmj.o2o.service.ShopSearchService;
import com.lmj.o2o.utils.PageUtil;
import com.lmj.o2o.vo.ShopSearchVO;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * ClassName: ShopSearchServiceImpl
 * Description:
 * date: 2020/3/14 21:44
 *
 * @author MJ
 */
@Service
public class ShopSearchServiceImpl implements ShopSearchService {

    @Autowired
    private ShopSearchDao shopSearchDao;

    @Override
    public ShopTO searchShopViaElasticSearch(ShopSearchVO shopSearchVO) {
        ShopTO shopTO;
        List<Map<String, Object>> dataList = shopSearchDao.searchShopViaElasticSearch(shopSearchVO);

        if (!dataList.isEmpty()) {
            shopTO=new ShopTO(dataList,ShopStateEnum.SUCCESS);
            return shopTO;
        }
        shopTO=new ShopTO(ShopStateEnum.NULL_RESULT);
        return shopTO;
    }

/*
    @Override
    public ShopTO blurrySearch(ShopSearchVO shopSearchVO) {
        ShopTO shopTO;
        String keyword = shopSearchVO.getKeyword();
        String parent_category_id = shopSearchVO.getParent_category_id();

        SearchRequestBuilder requestBuilder = client.prepareSearch(ShopSearchVO.INDEX).setTypes(ShopSearchVO.TYPE);
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery().must(QueryBuilders.termQuery("parent_category_id",parent_category_id));
        QueryBuilder queryBuilder = QueryBuilders.matchQuery("shop_name", keyword);
        BoolQueryBuilder boolQueryBuilder1 = QueryBuilders.boolQuery().must(queryBuilder);
        boolQueryBuilder.must(boolQueryBuilder1);
        requestBuilder.setQuery(boolQueryBuilder);


        SearchResponse searchResponse = requestBuilder.get();
        SearchHits hits = searchResponse.getHits();
        List<Map<String, Object>> sourceList = new ArrayList<>();
        for (SearchHit hit : hits) {
            sourceList.add(hit.getSource());
        }
        if (!sourceList.isEmpty()) {
            shopTO=new ShopTO(sourceList,ShopStateEnum.SUCCESS);
            return shopTO;
        }
        shopTO=new ShopTO(ShopStateEnum.NULL_RESULT);
        return shopTO;
    }
*/


}
