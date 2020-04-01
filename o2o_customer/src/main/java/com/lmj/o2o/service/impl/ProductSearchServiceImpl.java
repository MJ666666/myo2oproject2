package com.lmj.o2o.service.impl;


import com.lmj.o2o.dao.ProductSearchDao;
import com.lmj.o2o.dto.ProductTO;
import com.lmj.o2o.entity.Product;
import com.lmj.o2o.enums.OperationEnum;
import com.lmj.o2o.service.ProductSearchService;
import com.lmj.o2o.utils.ElasticUtils;
import com.lmj.o2o.utils.PageUtil;
import com.lmj.o2o.vo.ProductSearchVO;
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
 * ClassName: ProductSearchServiceImpl
 * Description:
 * date: 2020/3/16 14:00
 *
 * @author MJ
 */
@Service
public class ProductSearchServiceImpl implements ProductSearchService {

   @Autowired
   private ProductSearchDao productSearchDao;


    @Override
    public ProductTO searchProduct(ProductSearchVO productSearchVO) {
        ProductTO productTO;
        List<Map<String, Object>> dataList = productSearchDao.searchProduct(productSearchVO);
        return getProductTO(dataList);
    }


    @Override
    public ProductTO searchProductById(Product product) {
        ProductTO productTO;
        List<Map<String, Object>> dataList = productSearchDao.searchProductById(product);
        return getProductTO(dataList);
    }



    private ProductTO getProductTO(List<Map<String, Object>> hitSource) {
        ProductTO productTO;
        if (!hitSource.isEmpty()) {
            productTO = new ProductTO(hitSource, OperationEnum.SUCCESS);
            return productTO;
        }
        productTO = new ProductTO(OperationEnum.NULL_PARAM);
        return productTO;
    }

}
