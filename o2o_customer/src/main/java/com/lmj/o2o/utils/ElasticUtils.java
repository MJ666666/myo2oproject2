package com.lmj.o2o.utils;

import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * ClassName: ElasticUtils
 * Description:
 * date: 2020/3/18 15:52
 *
 * @author MJ
 */
public class ElasticUtils {


    public static List<Map<String, Object>> getHitSource(SearchRequestBuilder searchRequestBuilder, BoolQueryBuilder boolQueryBuilder) {
        searchRequestBuilder.setQuery(boolQueryBuilder);
        SearchResponse searchResponse = searchRequestBuilder.get();
        SearchHits hits = searchResponse.getHits();
        List<Map<String,Object>> dataList = new ArrayList<>();
        for (SearchHit hit : hits) {
            dataList.add(hit.getSource());
        }
        return dataList;
    }
}
