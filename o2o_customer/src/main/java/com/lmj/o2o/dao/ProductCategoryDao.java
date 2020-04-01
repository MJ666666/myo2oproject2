package com.lmj.o2o.dao;

import com.lmj.o2o.entity.Shop;

import java.util.List;
import java.util.Map;

/**
 * ClassName: ProductCategoryDao
 * Description:
 * date: 2020/3/20 13:22
 *
 * @author MJ
 */
public interface ProductCategoryDao {

    List<Map<String,Object>> getProductCatesListByShopId(Shop shop);
}
