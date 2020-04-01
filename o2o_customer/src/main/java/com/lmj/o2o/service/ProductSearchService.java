package com.lmj.o2o.service;


import com.lmj.o2o.dto.ProductTO;
import com.lmj.o2o.entity.Product;
import com.lmj.o2o.vo.ProductSearchVO;

/**
 * ClassName: ProductSearchService
 * Description:
 * date: 2020/3/16 13:57
 *
 * @author MJ
 */
public interface ProductSearchService {


    ProductTO searchProduct(ProductSearchVO productSearchVO);

    ProductTO searchProductById(Product product);

}
