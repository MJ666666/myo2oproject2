package com.lmj.o2o.service.impl;

import com.lmj.o2o.consts.Consts;
import com.lmj.o2o.dao.ShopCategoryDao;
import com.lmj.o2o.dto.HeadLineTO;
import com.lmj.o2o.dto.ShopCategoryTO;
import com.lmj.o2o.entity.ShopCategory;
import com.lmj.o2o.enums.OperationEnum;
import com.lmj.o2o.service.ShopCategoryService;
import com.lmj.o2o.utils.ElasticUtils;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * ClassName: ShopCategoryServiceImpl
 * Description:
 * date: 2020/3/18 15:30
 *
 * @author MJ
 */
@Service
public class ShopCategoryServiceImpl implements ShopCategoryService {

    @Autowired
    private ShopCategoryDao shopCategoryDao;

    @Override
    public ShopCategoryTO getShopCategoryParentList() {
        List<Map<String, Object>> shopCategoryParentList = shopCategoryDao.getShopCategoryParentList();

        return getShopCategoryTO(shopCategoryParentList);
    }

    @Override
    public ShopCategoryTO getShopCategoryListByParentId(ShopCategory shopCategory) {
        List<Map<String, Object>> dataList = shopCategoryDao.getShopCategoryListByParentId(shopCategory);
        return getShopCategoryTO(dataList);
    }



    private ShopCategoryTO getShopCategoryTO(List<Map<String,Object>> dataList) {
        ShopCategoryTO shopCategoryTO;
        if (!dataList.isEmpty()) {
            shopCategoryTO = new ShopCategoryTO(dataList, OperationEnum.SUCCESS);
            return shopCategoryTO;
        }
        shopCategoryTO = new ShopCategoryTO(OperationEnum.NULL_PARAM);
        return shopCategoryTO;
    }


}
