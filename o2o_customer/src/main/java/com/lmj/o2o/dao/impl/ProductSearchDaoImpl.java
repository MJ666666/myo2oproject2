package com.lmj.o2o.dao.impl;

import com.lmj.o2o.dao.ProductSearchDao;
import com.lmj.o2o.entity.Product;
import com.lmj.o2o.utils.ElasticUtils;
import com.lmj.o2o.utils.PageUtil;
import com.lmj.o2o.vo.ProductSearchVO;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHits;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * ClassName: ProductSearchDaoImpl
 * Description:
 * date: 2020/3/20 13:26
 *
 * @author MJ
 */
@Repository
public class ProductSearchDaoImpl implements ProductSearchDao {
    @Autowired
    private TransportClient client;



    @Override
    public List<Map<String, Object>> searchProduct(ProductSearchVO productSearchVO) {
        String shop_id = productSearchVO.getShop_id();
        int currentPage = productSearchVO.getCurrentPage();
        int pageSize = productSearchVO.getPageSize();
        String keyword = productSearchVO.getKeyword();
        String product_category_id = productSearchVO.getProduct_category_id();
        SearchRequestBuilder searchRequestBuilder=client.prepareSearch(ProductSearchVO.INDEX).setTypes(ProductSearchVO.TYPE);
        BoolQueryBuilder builder = QueryBuilders.boolQuery();
        if (!StringUtils.isEmpty(shop_id)) {
            builder.must(QueryBuilders.termQuery("shop_id",shop_id));
        }
        if (!StringUtils.isEmpty(product_category_id)) {
            builder.must(QueryBuilders.termQuery("product_category_id",product_category_id));
        }
        if (!StringUtils.isEmpty(keyword)) {
            QueryBuilder queryBuilder = QueryBuilders.matchQuery("product_name", keyword);
            builder.must(queryBuilder);
        }


        if (currentPage>0) {
            searchRequestBuilder.setFrom(PageUtil.pageCountConvert(currentPage, pageSize))
                    .setSize(pageSize);
        }
        List<Map<String, Object>> hitSource = ElasticUtils.getHitSource(searchRequestBuilder, builder);


        return hitSource;
    }

    @Override
    public List<Map<String, Object>> searchProductById(Product product) {

        String product_id = product.getProductId().toString();
        SearchRequestBuilder searchRequestBuilder=client.prepareSearch(ProductSearchVO.INDEX).setTypes(ProductSearchVO.TYPE);
        BoolQueryBuilder builder = QueryBuilders.boolQuery();
        builder.must(QueryBuilders.termQuery("product_id",product_id));
        searchRequestBuilder.setQuery(builder);

        SearchResponse searchResponse = searchRequestBuilder.get();
        SearchHits hits = searchResponse.getHits();
        List<Map<String, Object>> sourceList = new ArrayList<>();
        sourceList.add(hits.getAt(0).getSource());
        return sourceList;
    }
}
