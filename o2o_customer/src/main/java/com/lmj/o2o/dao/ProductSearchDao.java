package com.lmj.o2o.dao;

import com.lmj.o2o.entity.Product;
import com.lmj.o2o.vo.ProductSearchVO;

import java.util.List;
import java.util.Map;

/**
 * ClassName: ProductSearchDao
 * Description:
 * date: 2020/3/20 13:26
 *
 * @author MJ
 */
public interface ProductSearchDao {

    List<Map<String,Object>> searchProduct(ProductSearchVO productSearchVO);

    List<Map<String,Object>> searchProductById(Product product);
}
