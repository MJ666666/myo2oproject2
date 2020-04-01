package com.lmj.o2o.dao;


import com.lmj.o2o.entity.Product;
import com.lmj.o2o.entity.ProductImg;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * ClassName: ImageDao
 * Description:
 * date: 2020/3/8 11:59
 *
 * @author MJ
 */
@Repository
public interface ImageDao {

    int addImages(List<ProductImg> productImgs);

    int deleteImages(Product product);

    List<ProductImg> getImgList(Product product);
}
