package com.lmj.o2o.service;

import com.lmj.o2o.dto.UserProductTO;
import com.lmj.o2o.entity.UserProductMap;

/**
 * ClassName: UserProductService
 * Description:
 * date: 2020/3/20 14:32
 *
 * @author MJ
 */
public interface UserProductService {

    UserProductTO getRecords(UserProductMap userProductMap);


}
