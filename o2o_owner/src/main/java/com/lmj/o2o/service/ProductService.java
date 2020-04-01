package com.lmj.o2o.service;



import com.lmj.o2o.dto.ProductTO;
import com.lmj.o2o.entity.Product;
import com.lmj.o2o.entity.ProductImg;
import com.lmj.o2o.vo.PageVO;

import java.util.List;

/**
 * ClassName: ProductCategoryService
 * Description:
 * date: 2020/3/7 16:30
 *
 * @author MJ
 */
public interface ProductService {

    ProductTO getProductByShopId(PageVO shopPage);

    ProductTO addNewProduct(Product product, List<ProductImg> imgPaths);

    ProductTO updateProduct(Product product, List<ProductImg> imgPaths);

    ProductTO updateProductStatus(Product product);

    ProductTO getProductInfoById(Product product);
}
