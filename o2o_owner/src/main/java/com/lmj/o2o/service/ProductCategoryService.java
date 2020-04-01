package com.lmj.o2o.service;


import com.lmj.o2o.dto.ProductCategoryTO;
import com.lmj.o2o.entity.ProductCategory;
import com.lmj.o2o.entity.Shop;

/**
 * ClassName: ProductCategoryService
 * Description:
 * date: 2020/3/7 16:30
 *
 * @author MJ
 */
public interface ProductCategoryService {

    ProductCategoryTO getProductCategoryByShopId(Shop shop);

    ProductCategoryTO addNewProductCategory(ProductCategory productCategory);

    ProductCategoryTO deleteProductCategory(ProductCategory productCategory);
}
