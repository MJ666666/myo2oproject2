package com.lmj.o2o.service;

import com.lmj.o2o.dto.UserProductTO;
import com.lmj.o2o.entity.Shop;
import com.lmj.o2o.entity.ShopAuthMap;
import com.lmj.o2o.entity.UserProductMap;
import com.lmj.o2o.vo.PageVO;

/**
 * ClassName: UserProductService
 * Description:
 * date: 2020/3/20 17:08
 *
 * @author MJ
 */
public interface UserProductService {


    UserProductTO getConsumeRecords(PageVO recordsPage);

    UserProductTO buyTheProduct(UserProductMap userProductMap, ShopAuthMap shopAuthMap);

    UserProductTO statisticTheRecords(Shop shop);
}
