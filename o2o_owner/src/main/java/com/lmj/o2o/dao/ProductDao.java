package com.lmj.o2o.dao;


import com.lmj.o2o.entity.Product;
import com.lmj.o2o.entity.Shop;
import com.lmj.o2o.vo.PageVO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * ClassName: ProductDao
 * Description:
 * date: 2020/3/7 17:04
 *
 * @author MJ
 */
@Repository
public interface ProductDao {

    List<Product> queryProductByShopId(PageVO shopPage);

    int addNewProduct(Product product);

    int updateProduct(Product product);

    int countTotalRecordsByShopId(PageVO shopPage);

    Product getProductInfo(Product product);

    List<Product> queryProductCateByShopId(Shop shop);

}
