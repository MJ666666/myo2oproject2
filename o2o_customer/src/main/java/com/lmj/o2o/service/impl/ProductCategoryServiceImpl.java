package com.lmj.o2o.service.impl;

import com.lmj.o2o.consts.Consts;
import com.lmj.o2o.dao.ProductCategoryDao;
import com.lmj.o2o.dto.ProductCategoryTO;
import com.lmj.o2o.entity.ProductCategory;
import com.lmj.o2o.entity.Shop;
import com.lmj.o2o.enums.OperationEnum;
import com.lmj.o2o.service.ProductCategoryService;
import org.apache.tomcat.util.bcel.Const;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * ClassName: ProductCategoryServiceImpl
 * Description:
 * date: 2020/3/19 16:45
 *
 * @author MJ
 */
@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {


    @Autowired
    private ProductCategoryDao productCategoryDao;

    @Override
    public ProductCategoryTO getProductCatesListByShopId(Shop shop) {
        ProductCategoryTO productCategoryTO;
        List<Map<String, Object>> productCatesList = productCategoryDao.getProductCatesListByShopId(shop);
        if (productCatesList.size()!=0) {
            productCategoryTO=new ProductCategoryTO(OperationEnum.SUCCESS);
            productCategoryTO.setDataList(productCatesList);
            return productCategoryTO;
        }
        productCategoryTO=new ProductCategoryTO(OperationEnum.NULL_RESULT);
        return productCategoryTO;
    }
}
