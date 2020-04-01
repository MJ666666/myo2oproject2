package com.lmj.o2o.dao;

import com.lmj.o2o.entity.Product;

import java.util.List;
import java.util.Map;

/**
 * ClassName: ImageDao
 * Description:
 * date: 2020/3/20 13:18
 *
 * @author MJ
 */
public interface ImageDao {

    List<Map<String,Object>> getProductImgList(Product product);
}
