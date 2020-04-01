package com.lmj.o2o.service;

import com.lmj.o2o.dto.ImageTO;
import com.lmj.o2o.entity.Product;


/**
 * ClassName: ImageService
 * Description:
 * date: 2020/3/11 15:50
 *
 * @author MJ
 */
public interface ImageService {

    ImageTO deleteImagesByProductId(Product product);

    ImageTO getImgListByProductId(Product product);
}
