package com.lmj.o2o.dao.impl;

import com.lmj.o2o.consts.Consts;
import com.lmj.o2o.dao.HeadLineDao;
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
 * ClassName: HeadLineDaoImpl
 * Description:
 * date: 2020/3/20 13:11
 *
 * @author MJ
 */
@Repository
public class HeadLineDaoImpl implements HeadLineDao{
    @Autowired
    private TransportClient transportClient;
    @Override
    public List<Map<String, Object>> getHeadLineList() {

        SearchRequestBuilder requestBuilder = transportClient.prepareSearch(Consts.ES_INDEX)
                .setTypes(Consts.ES_HEAD_LINE_TYPE).addSort("priority", SortOrder.DESC).setFrom(0).setSize(5);
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery().must(QueryBuilders.termQuery("enable_status", "1"));
        List<Map<String, Object>> hitSource = ElasticUtils.getHitSource(requestBuilder, boolQueryBuilder);
        return hitSource;
    }
}
