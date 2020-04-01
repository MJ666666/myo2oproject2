package com.lmj.o2o.service;


import com.lmj.o2o.dto.ShopTO;
import com.lmj.o2o.vo.ShopSearchVO;

/**
 * ClassName: ShopSearchService
 * Description:
 * date: 2020/3/14 21:42
 *
 * @author MJ
 */
public interface ShopSearchService {

    ShopTO searchShopViaElasticSearch(ShopSearchVO shopSearchVO);

}
