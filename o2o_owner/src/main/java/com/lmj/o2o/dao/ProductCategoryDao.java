package com.lmj.o2o.dao;


import com.lmj.o2o.entity.ProductCategory;
import com.lmj.o2o.entity.Shop;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * ClassName: ProductCategoryDao
 * Description:
 * date: 2020/3/7 16:04
 *
 * @author MJ
 */
@Repository
public interface ProductCategoryDao {

    /**
     * Description:
     * @author: MJ
     * @date: 2020/3/7 16:06
     * @param: 需要传入商店的id
     * @return:
     */
    List<ProductCategory> queryProductCategory(Shop shop);



    int addProductCategory(ProductCategory productCategory);

    int deleteProductCategory(ProductCategory productCategory);
}
