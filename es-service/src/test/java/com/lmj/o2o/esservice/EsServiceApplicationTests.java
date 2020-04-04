package com.lmj.o2o.esservice;

import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
class EsServiceApplicationTests {

    @Test
    void contextLoads() {
    }

    @Autowired
    private TransportClient transportClient;

    @Test
    public void test1() {
        SearchRequestBuilder requestBuilder = transportClient.prepareSearch("o2o")
                .setTypes("area").addSort("priority", SortOrder.DESC);
        SearchResponse searchResponse = requestBuilder.get();
        SearchHits hits = searchResponse.getHits();
        List<Map<String,Object>> areaList= new ArrayList<Map<String,Object>>();
        for (SearchHit hit : hits) {
            areaList.add(hit.getSource());
        }
        System.out.println(areaList);

    }
}
