package com.lmj.o2o.service;

import com.lmj.o2o.dto.ProductCategoryTO;
import com.lmj.o2o.entity.Shop;

/**
 * ClassName: ProductCategoryService
 * Description:
 * date: 2020/3/19 16:43
 *
 * @author MJ
 */
public interface ProductCategoryService {

    ProductCategoryTO getProductCatesListByShopId(Shop shop);
}
