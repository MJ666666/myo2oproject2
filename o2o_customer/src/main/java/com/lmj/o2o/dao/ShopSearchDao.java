package com.lmj.o2o.dao;

import com.lmj.o2o.vo.ShopSearchVO;

import java.util.List;
import java.util.Map;

/**
 * ClassName: ShopSearchDao
 * Description:
 * date: 2020/3/20 13:40
 *
 * @author MJ
 */
public interface ShopSearchDao {


    List<Map<String,Object>> searchShopViaElasticSearch(ShopSearchVO shopSearchVO);

}
