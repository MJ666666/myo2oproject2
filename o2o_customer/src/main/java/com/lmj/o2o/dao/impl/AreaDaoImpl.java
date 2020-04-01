package com.lmj.o2o.dao.impl;

import com.lmj.o2o.consts.Consts;
import com.lmj.o2o.dao.AreaDao;
import com.lmj.o2o.dto.AreaTO;
import com.lmj.o2o.enums.OperationEnum;
import com.lmj.o2o.service.AreaService;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * ClassName: AreaDaoImpl
 * Description:
 * date: 2020/3/20 11:27
 *
 * @author MJ
 */
@Repository
public class AreaDaoImpl implements AreaDao {

    @Autowired
    private TransportClient transportClient;


    @Override
    public List<Map<String, Object>> getAreaList() {
        SearchRequestBuilder requestBuilder = transportClient.prepareSearch(Consts.ES_INDEX)
                .setTypes(Consts.ES_AREA_TYPE).addSort("priority", SortOrder.DESC);
        SearchResponse searchResponse = requestBuilder.get();
        SearchHits hits = searchResponse.getHits();
        List<Map<String,Object>> areaList= new ArrayList<Map<String,Object>>();
        for (SearchHit hit : hits) {
            areaList.add(hit.getSource());
        }

        return areaList;
    }
}
