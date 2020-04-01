package com.lmj.o2o.dao.impl;

import com.lmj.o2o.dao.ShopSearchDao;
import com.lmj.o2o.utils.ElasticUtils;
import com.lmj.o2o.utils.PageUtil;
import com.lmj.o2o.vo.ShopSearchVO;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * ClassName: ShopSearchDaoImpl
 * Description:
 * date: 2020/3/20 13:41
 *
 * @author MJ
 */
@Repository
public class ShopSearchDaoImpl implements ShopSearchDao {


    @Autowired
    private TransportClient client;

    @Override
    public List<Map<String, Object>> searchShopViaElasticSearch(ShopSearchVO shopSearchVO) {

        int currentPage = shopSearchVO.getCurrentPage();
        int pageSize = shopSearchVO.getPageSize();
        String area_id = shopSearchVO.getArea_id();
        String shop_category_id = shopSearchVO.getShop_category_id();
        String parent_id = shopSearchVO.getParent_category_id();
        String keyword = shopSearchVO.getKeyword();
        //  System.out.println(parent_id);
        SearchRequestBuilder searchRequestBuilder=client.prepareSearch(ShopSearchVO.INDEX).setTypes(ShopSearchVO.TYPE);
        BoolQueryBuilder builder = QueryBuilders.boolQuery();
        if (!StringUtils.isEmpty(area_id)) {
            builder.must(QueryBuilders.termQuery("area_id",area_id));
        }
        if (!StringUtils.isEmpty(parent_id)) {
            builder.must(QueryBuilders.termQuery("parent_category_id",parent_id));
        }
        if (!StringUtils.isEmpty(shop_category_id)) {
            builder.must(QueryBuilders.termQuery("shop_category_id", shop_category_id));
        }
        if (!StringUtils.isEmpty(keyword)) {
            QueryBuilder queryBuilder = QueryBuilders.matchQuery("shop_name", keyword);
            builder.must(queryBuilder);
        }
        if (shopSearchVO.getCurrentPage()>0) {
            searchRequestBuilder.setFrom(PageUtil.pageCountConvert(currentPage, pageSize))
                    .setSize(pageSize);
        }
        List<Map<String, Object>> hitSource = ElasticUtils.getHitSource(searchRequestBuilder, builder);

        return hitSource;
    }
}
